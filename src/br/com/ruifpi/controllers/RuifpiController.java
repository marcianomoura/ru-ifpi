package br.com.ruifpi.controllers;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.ruifpi.util.ControleAcesso;
import br.com.ruifpi.util.CreateDeafultUserAdmin;
import br.com.ruifpi.util.CreateDefaultClasseNutricional;
import br.com.ruifpi.util.CreateDefaultTipoPrato;

@Controller
public class RuifpiController {

	@Inject private PratoDiaController pratoDiaController;
	@Inject private SugestaoController sugestaoController;	
	@Inject private Result result;
	
	@Path("/")
	public void home() {	// Pagina Inicial sem necesidade de autenticar
		pratoDiaController.pratoDiaPublicado();
		sugestaoController.listCardapiosAbertosParaVotacao();
	
	}
	
	@ControleAcesso
	@Path("/index")
	public void index() { 	// Pagina principal com usuários autenticado
		
	}
	
	/* Método para inserir dados necessários para funcionamento do sistema.
	 * - Inseridos um funcionário default admin.
	 * - Inserção de Tipos de Pratos (Almoço e Janta)
	 * - Inserção das Classes Nutricionais
	 */
	@Path("/-Ma332021")
	public void insereObjetosDependencias() {
		try {
			CreateDeafultUserAdmin.insereFuncionarioDefault();
			CreateDefaultClasseNutricional.insereClasseNutricionalDefault();
			CreateDefaultTipoPrato.inserePratosDefault();
			result.include("sucesso", "Dependencias incluidas com sucesso");
			result.redirectTo(this).home();
		} catch (Exception e) {
			result.redirectTo(this).home();
		}
		
		
	}
}
