package br.com.ruifpi.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Refeitorio;

@Controller
public class RefeitorioController {

	@Inject
	private DaoImplementacao dao;
	@Inject
	private Validator validator;
	@Inject
	private Result result;
	@Inject
	private CampusController campusController;
	
	@Path("/refeitorio")
	public void formRefeitorio() {
		campusController.listCampus();
		listRefeitorio();
	}
	
	@Path("/refeitorio/alteracao")
	public void alteraRefeitorio(Long id) {
		try {
			Refeitorio refeitorio = (Refeitorio) dao.findById(Refeitorio.class, id);
			result.include("refeitorio", refeitorio);
			result.redirectTo(this).formRefeitorio();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um ao tentar alterar este refeitório. Verifique os dados e tente novamente");
			result.redirectTo(this).formRefeitorio();
		}
	}
	
	public boolean validaDadosRefeitorio(Refeitorio refeitorio) {
		if(refeitorio.getNome().length() < 6){
			validator.add(new I18nMessage("Nome do Refeitório", "nome.refeitorio.pequeno"));
			return false;
		}
		if(refeitorio.getCampus().getId() == null){
			validator.add(new I18nMessage("Identificao do Campus", "campus.refeitorio.nulo"));
			return false;
		}
		return true;
	}

	@Path("/refeitorio/save")
	public void saveRefeitorio(Refeitorio refeitorio) {
		if(!validaDadosRefeitorio(refeitorio)){
			validator.onErrorRedirectTo(this).formRefeitorio();
		}else{
			try {
				dao.save(refeitorio);
				result.include("sucesso", "Refeitório cadastrado com sucesso.");
				result.redirectTo(this).formRefeitorio();
			} catch (Exception e) {
				result.include("erro", "Ocorreu um erro no cadastro do refeitório. Verifique e tente novamente");
				result.redirectTo(this).formRefeitorio();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void listRefeitorio() {
		List<Refeitorio> refeitorios = dao.find(Refeitorio.class);
		result.include("refeitorios", refeitorios);
	}

}
