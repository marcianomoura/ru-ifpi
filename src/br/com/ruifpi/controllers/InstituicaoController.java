package br.com.ruifpi.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Instituicao;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;

@Controller
public class InstituicaoController {

	@Inject
	private DaoImplementacao dao;

	@Inject
	private Validator validator;

	@Inject
	private Result result;
	
	@AcessoAdministrativo
	@Path("/instituicao")
	public void formInstituicao() {
		listInstituicao();
	}
	
	@AcessoAdministrativo
	public boolean validaDadosInstituicao(Instituicao instituicao) {
		if (instituicao.getNome().length() < 3) {
			validator.add(new I18nMessage("Nome da Instituicao", "nome.instituicao.pequeno"));
			return false;
		}
		if (instituicao.getEndereco().length() < 10) {
			validator.add(new I18nMessage("Endereco da Instituicao", "endereco.instituicao.pequeno"));
			return false;
		}
		return true;
	}

	@AcessoAdministrativo
	@Path("/instituicao/save")
	public void saveInstituicao(Instituicao instituicao) {
		if(!validaDadosInstituicao(instituicao)){
			validator.onErrorRedirectTo(this).formInstituicao();
		}else{
			try {
				System.out.println("Instituição ... " + instituicao);
				dao.save(instituicao);
				result.include("sucesso", "Instituição cadastrada com sucesso");
				result.redirectTo(this).formInstituicao();
			} catch (Exception e) {
				result.include("erro", "Ocorreu um erro ao cadastrar a instituição. Tente novamente.");
				result.redirectTo(this).formInstituicao();
			}
		}
	}

	@AcessoAdministrativo
	@Path("/instituicao/alteracao")
	public void alteraInstituicao(Long id) {
		try {
			Instituicao instituicao = (Instituicao) dao.findById(Instituicao.class, id);
			result.include("instituicao", instituicao);
			result.redirectTo(this).formInstituicao();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na tentativa de alterar os dados da instituição. Tente novamente");
			result.redirectTo(this).formInstituicao();
		}
		
	}

	@AcessoAdministrativo
	@SuppressWarnings("unchecked")
	public void listInstituicao() {
		List<Instituicao> instituicaos = dao.find(Instituicao.class);
		result.include("instituicoes", instituicaos);
	}

}
