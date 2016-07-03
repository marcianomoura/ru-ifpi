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
import br.com.ruifpi.util.ControleAcesso;

@Intercepts
@RequestScoped
@AcceptsWithAnnotations(ControleAcesso.class)
public class InterceptorAcessoLogado {
	
	@Inject
	private UsuarioSession usuarioSession;
	@Inject
	private FuncionarioSession funcionarioSession;
	@Inject
	private Result result;
	

	@AroundCall
	public void interceptaRequisicoesSemAutenticacao(ControllerMethod method, SimpleInterceptorStack stack) {
		System.out.println("Interceptando se usuarios estão logados");

		if(!usuarioSession.logado() && !funcionarioSession.logado()){
			result.include("erro", "Não autenticado.");
			result.redirectTo(RuifpiController.class).home();
		}else{
			stack.next();
		}
	}
}

