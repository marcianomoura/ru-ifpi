package br.com.ruifpi.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Campus;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;

@Controller
public class CampusController {

	@Inject
	private DaoImplementacao dao;
	@Inject
	private Result result;
	@Inject
	private Validator validator;
	
	@Inject
	private InstituicaoController instituicaoController;
	
	@AcessoAdministrativo
	@Path("/campus")
	public void formCampus() {
		listCampus();
		instituicaoController.listInstituicao();
	}
	
	@AcessoAdministrativo
	public boolean validaDadosCampus(Campus campus) {
		if(campus.getNomeCampus().length() < 6){
			validator.add(new I18nMessage("Noome do Campus", "nome.campus.pequeno"));
			return false;
		}
		if(campus.getInstituicao().getId() == null){
			validator.add(new I18nMessage("Instituicao", "instituicao.nao.informada"));
			return false;
		}
		
		return true;
	}

	@AcessoAdministrativo
	@Path("/campus/alteracao")
	public void alteraCampus(Long id) {
		try {
			Campus campus = (Campus) dao.findById(Campus.class, id);
			result.include("campus", campus);
			result.redirectTo(this).formCampus();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na tentativa de alterar os dados do Campus. Tente Novamente.");
			result.redirectTo(this).formCampus();
		}
		
	
	}
	
	@AcessoAdministrativo
	@Path("/campus/save")
	public void saveCampus(Campus campus) {
		if(!validaDadosCampus(campus)){
			validator.onErrorRedirectTo(this).formCampus();
		}else{
			try {
				dao.save(campus);
				result.include("sucesso", "Campus Registrado com sucesso.");
				result.redirectTo(this).formCampus();
			} catch (Exception e) {
				result.include("erro", "Ocorreu um erro no cadastro do campus. Tente novamente.");
				result.redirectTo(this).formCampus();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void listCampus() {
		List<Campus> campus = dao.find(Campus.class);
		result.include("listCampus", campus);
	}
		
}
