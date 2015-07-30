package br.com.ruifpi.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.ClasseNutricional;

@Controller
public class ClassesNutricionaisController {

	@Inject private DaoImplementacao daoImplementacao;
	@Inject private Result result;
	
	
	@SuppressWarnings("unchecked")
	public void listClassesNutricionais() {
		List<ClasseNutricional> classeNutricionals =  daoImplementacao.find(ClasseNutricional.class);
		result.include("classes", classeNutricionals);
	}
	
	
}
