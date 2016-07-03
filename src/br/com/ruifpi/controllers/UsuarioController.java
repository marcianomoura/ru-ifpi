package br.com.ruifpi.controllers;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.components.UsuarioSession;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Usuario;
import br.com.ruifpi.models.UsuarioImportacao;
import br.com.ruifpi.util.ControleAcesso.PermissaoUsuarioCadastro;

@Controller
public class UsuarioController {

	@Inject	private DaoImplementacao dao;
	@Inject	private Result result;
	@Inject private Validator validator;
	@Inject	private UsuarioSession usuarioSession;
	
	
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
						usuarioImportacao.getDataNascimento().equals(dataNascimentoSql) && 	// Data de Nascimento iguais
						usuarioImportacao.isMatriculaValida()){	// esteja com matricula valida.
					usuarioEncontrado = new Usuario();
					usuarioEncontrado.setDataNascimento(usuario.getDataNascimento());
					usuarioEncontrado.setMatricula(usuario.getMatricula());
					usuarioEncontrado.setMatriculado(true);
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
	
	@SuppressWarnings("unchecked")
	public boolean verificaUsuarioCadastrado(Usuario usuario) {
		boolean usuarioEncontrado = false;
		List<Usuario> usuarios = dao.find(Usuario.class);
		for (Usuario usuario2 : usuarios) {
			if(usuario.getMatricula().equals(usuario2.getMatricula()) && usuario.isMatriculado() // Usuario de mesma matricula, com esta valida e não é alteração.
					&& usuario.getId() == null){
				usuarioEncontrado= true;
				break;
			}
		}
		return usuarioEncontrado;
	}
	
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
	
	@Path("/usuario/save")
	public void save(Usuario usuario) {
		if(!validaDadosCadastraisUsuario(usuario)){
			validator.onErrorRedirectTo(this).formUsuario();
		}else{
			try {
				usuario.setMatriculado(true);
				dao.save(usuario);
				usuarioSession.setUsuario(null);
				result.include("sucesso", "Usuario cadastrado com sucesso");
				result.redirectTo(RuifpiController.class).home();
			} catch (Exception e) {
				usuarioSession.setUsuario(null);
				result.include("erro", "Ocorreu um erro ao efetuar o cadastro. Verifique e tente novamente.");
				result.redirectTo(RuifpiController.class).home();
			}	
		}
	}
	@Path("/usuario/cadastrardepois")
	public void desistirCadastro() {
		usuarioSession.setUsuario(null);
		result.redirectTo(RuifpiController.class).home();
	}
	
	
}
