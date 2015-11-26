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
import br.com.ruifpi.util.ControleAcesso;

@Intercepts
@RequestScoped
@AcceptsWithAnnotations(ControleAcesso.class)
public class InterceptorAcessoLogado {
	
	private UsuarioSession usuarioSession;
	private FuncionarioSession funcionarioSession;
	private Result result;
	
	public InterceptorAcessoLogado() {	}
	
	@Inject
	public InterceptorAcessoLogado(UsuarioSession usuarioSession, FuncionarioSession funcionarioSession, Result result) {
		super();
		this.usuarioSession = usuarioSession;
		this.funcionarioSession = funcionarioSession;
		this.result = result;
	}

	@AroundCall
	public void interceptaRequisicoesSemAutenticacao(ControllerMethod method, SimpleInterceptorStack stack) {
		
		if(!usuarioSession.logado() && !funcionarioSession.logado()){
			result.include("erro", "Não autenticado.");
			result.redirectTo(AutenticacaoController.class).home();
		}
	}
}

