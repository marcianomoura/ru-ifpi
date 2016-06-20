package br.com.ruifpi.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.ItemPratoPronto;
import br.com.ruifpi.models.PratoDia;
import br.com.ruifpi.models.PratoPronto;
import br.com.ruifpi.models.Sobremesa;

@Controller
public class PratoDiaController {

	@Inject
	private DaoImplementacao dao;
	@Inject
	private Result result;
	@Inject
	private Validator validator;
	@Inject
	private SobremesaController sobremesaController;
	@Inject
	private PratoProntoController pratoProntoController;
	private List<ItemPratoPronto> listItensPratoPronto = new ArrayList<>(); 
	
	@Path("/pratodia")
	public void formPratoDia() {
		sobremesaController.listSobremesas();
		pratoProntoController.listPratosPronto();
	}
	
	@SuppressWarnings("unchecked")
	public List<PratoDia> listarPratosPublicados() {
		List<PratoDia> pratosPublicados = dao.find(PratoDia.class);
		result.include("pratosPublicados", pratosPublicados);
		return pratosPublicados;
	}
	
	@Path("/pratos/list")
	public void listCardapiosPublicados() {
		listarPratosPublicados();
	}
	
	@Path("/pratodia/montar")
	public void montaCardapioDia(PratoDia pratoDia) {
		try {
			PratoPronto pratoPronto = (PratoPronto) dao.findById(PratoPronto.class, pratoDia.getPratoPronto().getId());
			Sobremesa sobremesa = (Sobremesa) dao.findById(Sobremesa.class, pratoDia.getSobremesa().getId());
			listItensPratoPronto = pratoPronto.getItemPratoProntos();
			pratoDia.setTotalCaloria(calculaTotalCaloria());
			result.include("pratoDia", pratoDia);
			result.include("pratoPronto", pratoPronto);
			result.include("sobremesa", sobremesa);
			result.include("listItemPratoPronto", listItensPratoPronto);
			result.redirectTo(this).formPratoDia();
			
		} catch (Exception e) {
			result.include("erro", "Não foi possivel montar o prato do dia. Tente novamente.");
			result.redirectTo(this).formPratoDia();
		}
	}
	
	public double calculaTotalCaloria() {
		double soma = 0.0d;
		for (ItemPratoPronto itemPratoPronto : listItensPratoPronto) {
			soma += itemPratoPronto.getTotalCaloria();
		}
		return soma;
	}
	
	@Path("/pratodia/alteracao")
	public void alteraCardapioDia(Long id) {
		PratoDia pratoDia = (PratoDia) dao.findById(PratoDia.class, id);
		result.include("pratoDia", pratoDia);
		result.redirectTo(this).formPratoDia();
	}
	
	@Path("/pratodia/itens")
	public void listItensPratoDia(Long id) {
		PratoPronto pratoPronto = (PratoPronto) dao.findById(PratoPronto.class, id);
		result.include("itensCardapio", pratoPronto.getItemPratoProntos());
	}
	
	@Path("/pratodia/save")
	public void savePratoDia(PratoDia pratoDia) {
		System.out.println("Total de Calorias..." + pratoDia.getTotalCaloria());
		dao.save(pratoDia);
		result.include("sucesso", "Prato do dia cadastrado com sucesso");
		result.redirectTo(this).formPratoDia();
	}
	
	
}
