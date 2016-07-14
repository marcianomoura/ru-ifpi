package br.com.ruifpi.controllers;
/*
 * 	Apenas testando a nova branch.
 * 
 * */
import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.auxiliar.RepositorioMetodos;
import br.com.ruifpi.components.FuncionarioSession;
import br.com.ruifpi.components.UsuarioSession;
import br.com.ruifpi.models.Funcionario;
import br.com.ruifpi.models.Usuario;

@Controller
public class AutenticacaoController {

	@Inject private Result result;
	@Inject private Validator validator;
	@Inject private RepositorioMetodos repositorio;
	@Inject private FuncionarioSession funcionarioSession;
	@Inject private UsuarioSession usuarioSession;
	
	
	@Path("/autenticacao/administracao")
	public void autenticacao(Funcionario funcionario) {
		try {
			if (!repositorio.autenticacaoFuncionario(funcionario)) {
				validator.onErrorRedirectTo(RuifpiController.class).home();
			}else{
				result.redirectTo(RuifpiController.class).index();
			}

		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na autenticação.");
			result.redirectTo(RuifpiController.class).index();
		}
	}
	
	@Path("/logout/funcionario")
	public void logoutFuncionario() {
		this.funcionarioSession.logout();
		result.redirectTo(RuifpiController.class).home();
	}
	
	@Path("/logout/usuario")
	public void logout() {
		this.usuarioSession.logout();
		result.redirectTo(RuifpiController.class).home();
	}

	@Path("/autenticacao/usuario")
	public void autenticao(Usuario usuario) {
		try {
			if(!repositorio.autenticacaoUsuario(usuario)){
				validator.onErrorRedirectTo(RuifpiController.class).home();
			}else{
				result.redirectTo(RuifpiController.class).index();;
			}
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na autenticação do Usuário");
			result.redirectTo(RuifpiController.class).index();;
		}
	}
}
