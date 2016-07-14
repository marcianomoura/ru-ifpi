package br.com.ruifpi.controllers;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.auxiliar.RepositorioMetodos;
import br.com.ruifpi.components.UsuarioSession;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Usuario;
import br.com.ruifpi.models.UsuarioImportacao;
import br.com.ruifpi.util.ControleAcesso.PermissaoUsuarioCadastro;
import br.com.ruifpi.util.CriptografaSenhaUtil;

@Controller
public class UsuarioController {

	private DaoImplementacao dao;
	private Result result;
	private Validator validator;
	@Inject	
	private UsuarioSession usuarioSession;
	@Inject 
	private RepositorioMetodos repositorioMetodos;
	
	public UsuarioController() {
		this(null,null,null);
	}
	
	@Inject
	public UsuarioController(DaoImplementacao dao, Result result, Validator validator) {
		this.validator = validator;
		this.result = result;
		this.dao = dao;
	}
	
	@Path("/usuario/cadastro")
	public void iniciaCadastroUsuario(Usuario usuario) {
		verificaMatriculaValidaUsuario(usuario);
	}
	
	
	@PermissaoUsuarioCadastro
	@Path("/usuario")
	public void formUsuario() {
		
	}
	
	public boolean validaDadosCadastroUsuario(Usuario usuario) {
		if(usuario == null){
			validator.add(new I18nMessage("Usuario", "usuariocadastro.nao.encontrado"));
			return false;
		}
		return true;
	}
	
	@Path("/usuario/alteracao")
	public void alteracaoDadosUsuario(Long id) {
		try {
			if(usuarioSession.getUsuario().getId().equals(id)){
				result.include("usuario", usuarioSession.getUsuario());
				result.redirectTo(this).formUsuario();
			}else{
				result.include("erro", "Voce não tem permissão para alterar os dados deste usuario");
				result.redirectTo(this).formUsuario();
			}
			
		} catch (Exception e) {
			result.include("erro", "Erro na tentatida de alterar dados do usuario");
			result.redirectTo(this).formUsuario();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void verificaMatriculaValidaUsuario(Usuario usuario) {
		Usuario usuarioEncontrado = null;
		SimpleDateFormat formatador = new SimpleDateFormat();
		formatador.applyPattern("yyyy-MM-dd");
		try {
			java.sql.Date dataNascimentoSql = new java.sql.Date(formatador.parse(formatador.format(usuario.getDataNascimento())).getTime());
			List<UsuarioImportacao> usuariosImportacao = dao.find(UsuarioImportacao.class);
			for (UsuarioImportacao usuarioImportacao : usuariosImportacao) {
				if(usuarioImportacao.getMatricula().equals(usuario.getMatricula()) && 	// Matriculas forem iguais
						usuarioImportacao.getDataNascimento().equals(dataNascimentoSql)){	// esteja com matricula valida.
					usuarioEncontrado = new Usuario();
					usuarioEncontrado.setDataNascimento(usuario.getDataNascimento());
					usuarioEncontrado.setMatricula(usuario.getMatricula());
					usuarioSession.setUsuario(usuarioEncontrado);
					break;
				}
			}
			if(!validaDadosCadastroUsuario(usuarioEncontrado)){
				validator.onErrorRedirectTo(RuifpiController.class).home();
			}else{
				result.include("usuario", usuarioEncontrado);
				result.redirectTo(this).formUsuario();
			}
			
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro ao tentar se cadastrar no sistema. Verifique os dados informados e tente novamente.");
			result.redirectTo(RuifpiController.class).home();
		}
	
	}
	
	@PermissaoUsuarioCadastro
	public boolean verificaUsuarioCadastrado(Usuario usuario) {
		boolean usuarioEncontrado = false;
		try {
			usuarioEncontrado = repositorioMetodos.verificaUsuarioCadastrado(usuario);	// Recebe verdadeiro se não for alteração... 
		} catch (Exception e) {
			usuarioEncontrado = true;
		}
		return usuarioEncontrado;
	}
	
	@PermissaoUsuarioCadastro
	public boolean validaDadosCadastraisUsuario(Usuario usuario) {
		if(usuario.getDataNascimento() == null){
			validator.add(new I18nMessage("Data de nascimento", "datanascimento.nao.informada"));
			return false;
		}		
		if(usuario.getLogin() == null){
			validator.add(new I18nMessage("Login", "login.nao.informado"));
			return false;
		}
		if(usuario.getPerfil() == null){
			validator.add(new I18nMessage("Perfil", "perfil.nao.informado"));
			return false;
		}
		if(usuario.getMatricula() == null){
			validator.add(new I18nMessage("Matricula", "matricula.nao.informada"));
			return false;
		}
		if(usuario.getSenha() == null){
			validator.add(new I18nMessage("Senha", "senha.nao.informada"));
			return false;
		}
		
		if(verificaUsuarioCadastrado(usuario)){
			validator.add(new I18nMessage("Usuario", "usuario.ja.cadastrado"));
			return false;
		}
		return true;
	}
	
	
	@PermissaoUsuarioCadastro
	@Path("/usuario/save")
	public void save(Usuario usuario) {
		if(!validaDadosCadastraisUsuario(usuario)){
			validator.onErrorRedirectTo(this).formUsuario();
		}else{
			try {
				if(usuario.getId() !=null){	// Se for alteração dos dados ...
					result.include("sucesso", "Usuario alterado com sucesso");
				}else{
					usuarioSession.setUsuario(null);	// Seta o usuario temporário da sessão da tela de cadastro.
					result.include("sucesso", "Usuario cadastrado com sucesso");
				}
				usuario.setMatriculado(true);usuario.setMatriculado(true);
				usuario.setSenha(CriptografaSenhaUtil.criptografaSenha(usuario.getSenha()));
				dao.save(usuario);
				result.redirectTo(RuifpiController.class).index();
			} catch (Exception e) {
				usuarioSession.setUsuario(null);
				result.include("erro", "Ocorreu um erro ao inserir os dados. Verifique e tente novamente.");
				result.redirectTo(RuifpiController.class).index();
			}	
		}
	}
	
	@Path("/usuario/cadastrardepois")
	public void desistirCadastro() {
		usuarioSession.setUsuario(null);
		result.redirectTo(RuifpiController.class).home();
	}
	
}

