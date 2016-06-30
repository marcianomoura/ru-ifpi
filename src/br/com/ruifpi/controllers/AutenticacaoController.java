package br.com.ruifpi.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.auxiliar.MetodosUtilImplementacao;
import br.com.ruifpi.components.FuncionarioSession;
import br.com.ruifpi.components.UsuarioSession;
import br.com.ruifpi.models.Funcionario;
import br.com.ruifpi.models.Usuario;
import br.com.ruifpi.util.ControleAcesso;

@Controller
public class AutenticacaoController {

	@Inject private Result result;
	@Inject private Validator validator;
	@Inject private MetodosUtilImplementacao implementacaoMetodos;
			private Funcionario funcionario = null;
	//		private List<ItemCardapioAEscolher> itemCardapioAEscolher = new ArrayList<ItemCardapioAEscolher>();
			private Usuario usuario = null;
	@Inject private FuncionarioSession funcionarioSession;
	@Inject private UsuarioSession usuarioSession;
	@Inject private PratoDiaController pratoDiaController;
	@Inject private SugestaoController sugestaoController;		
	
	@Path("/")
	public void home() {
		pratoDiaController.pratoDiaPublicado();
		sugestaoController.listCardapiosAbertosParaVotacao();
	
	}
	
	@ControleAcesso
	@Path("/index")
	public void index() { }	
	
	public boolean verificaAutenticacaoFuncionario(Funcionario funcionario) {
		List<Funcionario> funcionariosValidos = implementacaoMetodos.buscaFuncionariosAtivos();
		for (Funcionario funcionarioBanco : funcionariosValidos) {
			if(funcionarioBanco.getLogin().equalsIgnoreCase(funcionario.getLogin()) && 
					funcionarioBanco.getSenha().equalsIgnoreCase(funcionario.getSenha())){
				this.funcionario = funcionarioBanco;
				break;
			}
		}			
		if(this.funcionario != null){
			this.funcionarioSession.login(this.funcionario);
			return true;
		}else{
			validator.add(new I18nMessage("funcionario", "credenciais.nao.conferem"));
			return false;
		}
	}
	
	@Path("/autenticacao/administracao")
	public void autenticacao(Funcionario funcionario) {
		if (!verificaAutenticacaoFuncionario(funcionario)) {
			validator.onErrorRedirectTo(this).home();
		}else{
			result.redirectTo(this).index();
		}
	}
	
	@Path("/logout/funcionario")
	public void logoutFuncionario() {
		this.funcionarioSession.logout();
		result.redirectTo(this).home();
	}
	
	@Path("/logout/usuario")
	public void logout() {
		this.usuarioSession.logout();
		result.redirectTo(this).home();
	}
	
	public boolean verificaAutenticacaoUsuario(Usuario usuario) {		
		List<Usuario> usuariosValidos = implementacaoMetodos.buscaUsuariosAtivos();
		for (Usuario usuarioBanco : usuariosValidos) {
			if(usuarioBanco.getLogin().equalsIgnoreCase(usuario.getLogin()) && 	usuarioBanco.getSenha().equalsIgnoreCase(usuario.getSenha())){
				this.usuario = usuarioBanco;
				break;
			}
		}
		if(this.usuario != null){
			this.usuarioSession.login(this.usuario);
			return true;
		}else{
			validator.add(new I18nMessage("usuario", "credenciais.nao.conferem"));
			return false;
		}		
	}

	@Path("/autenticacao/usuario")
	public void autenticao(Usuario usuario) {
		if(!verificaAutenticacaoUsuario(usuario)){
			validator.onErrorRedirectTo(this).home();
		}else{
			result.redirectTo(this).index();;
		}
	}
}
