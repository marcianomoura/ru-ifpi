package br.com.ruifpi.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Sobremesa;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;

@Controller
public class SobremesaController {

	@Inject
	private DaoImplementacao dao;
	@Inject
	private Validator validator;
	@Inject
	private Result result;
	@Inject
	private ClassesNutricionaisController classesNutricionaisController;
	
	@AcessoAdministrativo
	@Path("/sobremesa")
	public void formSobremesa() {
		listSobremesas();
		classesNutricionaisController.listClassesNutricionais();
	}
	
	@AcessoAdministrativo
	@Path("/sobremesa/alteracao")
	public void alteraSobremesa(Long id) {
		try {
			Sobremesa sobremesa = (Sobremesa) dao.findById(Sobremesa.class, id);
			result.include("sobremesa", sobremesa);
			result.redirectTo(this).formSobremesa();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro ao tentat alterar esta sobresa. Verifique os dados informados e tentenovamente.");
			result.redirectTo(this).formSobremesa();
		}
	}
	
	@AcessoAdministrativo
	public boolean validaDadosSobremesa(Sobremesa sobremesa) {
		if (sobremesa.getDescricao().length() < 6) {
			validator.add(new I18nMessage("Descricao da sobrmesa", "descricao.sobremesa.pequena"));
			return false;
		}
		if (sobremesa.getBeneficios().length() < 6) {
			validator.add(new I18nMessage("Benefícios à saude", "beneficio.sobremesa.pequena"));
			return false;
		}
		if (sobremesa.getClasseNutricional().getId() == null) {
			validator.add(new I18nMessage("Classificação da sobremesa", "classificacao.sobremesa.nulo"));
			return false;
		}

		return true;
	}
	
	@AcessoAdministrativo
	@Path("/sobremesa/save")
	public void saveSobremesa(Sobremesa sobremesa) {
		if(!validaDadosSobremesa(sobremesa)){
			validator.onErrorRedirectTo(this).formSobremesa();
		}else{
			try {
				dao.save(sobremesa);
				result.include("sucesso", "Sobremesa cadastrada com sucesso");
				result.redirectTo(this).formSobremesa();
			} catch (Exception e) {
				result.include("erro", "Ocorreu um erro ao cadastrar a sobremesa. Verifique os dados e tente novamente.");
				result.redirectTo(this).formSobremesa();
			}
		}
	}
	
	@AcessoAdministrativo
	@SuppressWarnings("unchecked")
	public void listSobremesas() {
		List<Sobremesa> sobremesas = dao.find(Sobremesa.class);
		result.include("sobremesas", sobremesas);
	}
	
}
