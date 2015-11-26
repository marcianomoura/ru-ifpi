package br.com.ruifpi.interceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.ruifpi.components.FuncionarioSession;
import br.com.ruifpi.components.UsuarioSession;
import br.com.ruifpi.controllers.AutenticacaoController;
import br.com.ruifpi.util.ControleAcesso.AcessoUsuario;


@Intercepts
@RequestScoped
@AcceptsWithAnnotations(AcessoUsuario.class)
public class InterceptorUsuario{
	
	@Inject
	private Result result;
	@Inject
	private UsuarioSession usuarioSession;
	@Inject
	private FuncionarioSession funcionarioSession;
	
	@AroundCall
	public void interceptUsuario(SimpleInterceptorStack stack, ControllerMethod metodo) {
		System.out.println("Interceptando função de Usuario ...");
		if (usuarioSession.logado() && metodo.containsAnnotation(AcessoUsuario.class)) {
			stack.next();
		}else{
			if(funcionarioSession.logado()){
				result.include("erro", "Operação não permitida.Acesso restrito");
				result.redirectTo(AutenticacaoController.class).index();
			}
			result.include("erro", "Operação não permitida.Acesso restrito");
			result.redirectTo(AutenticacaoController.class).home();
		}
	}
}