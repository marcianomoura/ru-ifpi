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
import br.com.ruifpi.controllers.RuifpiController;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;

@Intercepts
@RequestScoped
@AcceptsWithAnnotations(AcessoAdministrativo.class)
public class InterceptorAdministracao {

	@Inject
	private Result result;
	@Inject
	private FuncionarioSession funcionarioSession;
	@Inject
	private UsuarioSession usuarioSession;
	
	
	@AroundCall
	public void interceptaAdminitracao(SimpleInterceptorStack stack , ControllerMethod method) {
		if(funcionarioSession.logado() && method.containsAnnotation(AcessoAdministrativo.class)){
			stack.next();
		}else{
		
			if(usuarioSession.logado()){
				result.include("erro", "Operação não permitida. Acesso restrito");
				result.redirectTo(RuifpiController.class).index();
			}else{
				result.include("erro", "Operação não permitida. Acesso restrito");
				result.redirectTo(RuifpiController.class).home();
			}
		}
	}
}
