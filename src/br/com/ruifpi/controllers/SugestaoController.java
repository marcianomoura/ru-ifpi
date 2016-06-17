package br.com.ruifpi.controllers;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;
import br.com.ruifpi.util.ControleAcesso.AcessoUsuario;

@Controller
public class SugestaoController {

	@Inject
	private Result result;
	@Inject
	private Validator validator;
	@Inject
	private DaoImplementacao daoImplementacao;
	
	
	@AcessoUsuario
	@Path("/sugestao")
	public void formSugestao() {
		
	}
	
	@AcessoAdministrativo		
	@Path("/sugestoes")		
	public void listSugestaoCardapio() {
	
	}
		
}







