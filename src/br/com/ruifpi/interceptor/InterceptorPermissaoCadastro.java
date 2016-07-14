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
import br.com.ruifpi.util.ControleAcesso.PermissaoUsuarioCadastro;

@Intercepts
@RequestScoped
@AcceptsWithAnnotations(PermissaoUsuarioCadastro.class)
public class InterceptorPermissaoCadastro {
	
	@Inject
	private UsuarioSession usuarioSession;
	@Inject
	private Result result;
	@Inject
	private FuncionarioSession funcionarioSession;

	@AroundCall
	public void verificaCadastroUsuarioValido(SimpleInterceptorStack stack, ControllerMethod method) {
		
		/*  Usuario ou funcionarios logados ... Assim permite que funcionarios cadastrem usuários 
			que tiveram problemas no cadastro e demais usuários do serviço de alimentação do refeitório.
		*/		
		if(usuarioSession.getUsuario() != null && method.containsAnnotation(PermissaoUsuarioCadastro.class) || 
				funcionarioSession.getFuncionario() != null && method.containsAnnotation(PermissaoUsuarioCadastro.class)){
			stack.next();
		}else{
			result.include("erro", "Acesso Negado.");
			result.redirectTo(RuifpiController.class).home();
		}
	}
}
