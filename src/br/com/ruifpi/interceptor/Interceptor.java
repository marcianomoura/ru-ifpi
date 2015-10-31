package br.com.ruifpi.interceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.ruifpi.components.FuncionarioSession;
import br.com.ruifpi.components.UsuarioSession;
import br.com.ruifpi.controllers.AutenticacaoController;
import br.com.ruifpi.util.RestricaoAcesso;
import br.com.ruifpi.util.RestricaoAcesso.AcessoAdministrativo;
import br.com.ruifpi.util.RestricaoAcesso.AcessoUsuario;

/*
 *	Interceptor utilizado no controle de acesso as uri do sistema... 
 * 
 */

@Intercepts
@RequestScoped
public class Interceptor{
	private final String AUTENTICACAO = "Usuário não logado. Informe seu login e senha para acessar o sistema.";
	private final String AUTORIZACAO = "Usuário sem permissão.";
	
	@Inject private Result result;
	@Inject	private FuncionarioSession funcionarioSession;
	@Inject private UsuarioSession usuarioSession;

	
	@AroundCall
	public void interceptUsuario(SimpleInterceptorStack stack, ControllerMethod metodo) {
		
		if((!usuarioSession.logado() && !funcionarioSession.logado()) && metodo.containsAnnotation(RestricaoAcesso.class)){
			result.include("erro", AUTENTICACAO);
			System.out.println("Interceptando autenticação nao realizada...");
			result.redirectTo(AutenticacaoController.class).home();
		}else if(usuarioSession.logado() && metodo.containsAnnotation(AcessoAdministrativo.class)){
			result.include("erro", AUTORIZACAO);
			System.out.println("Interceptando autorização apenas para funcionario...");
			result.redirectTo(AutenticacaoController.class).index();
		}else if(funcionarioSession.logado() && metodo.containsAnnotation(AcessoUsuario.class)){
			result.include("erro", AUTORIZACAO);
			System.out.println("Interceptando autorização apenas para Alunos/Servidor...");
			result.redirectTo(AutenticacaoController.class).index();
		}
		stack.next();
	}
}