package br.com.ruifpi.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.ItemSugestaoPratoPronto;
import br.com.ruifpi.models.PratoPronto;
import br.com.ruifpi.models.Sobremesa;
import br.com.ruifpi.models.SugestaoPrato;
import br.com.ruifpi.util.ControleAcesso;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;

@Controller
public class SugestaoController {

	@Inject
	private Result result;
	@Inject
	private Validator validator;
	@Inject
	private DaoImplementacao dao;
	
	@Inject
	private SobremesaController sobremesaController;
	@Inject
	private PratoProntoController pratoProntoController;
	private List<SugestaoPrato> sugestaoPratosUtil = new ArrayList<>();
	private static List<ItemSugestaoPratoPronto> listItemSugestaoPratos = new ArrayList<>();
	
	
	@AcessoAdministrativo
	@Path("/sugestao")
	public void formSugestao() {
		pratoProntoController.listPratosProntos();
		sobremesaController.listSobremesas();
		result.include("listItemSugestaoPrato", listItemSugestaoPratos);
	}
	
	@ControleAcesso
	@Path("/sugestoes")		
	public void listSugestaoCardapio() {
		listCardapiosAbertosParaVotacao();
	}
	
	@AcessoAdministrativo
	@Path("/sugestao/add")
	public void addItemSugestaoPrato(ItemSugestaoPratoPronto itemSugestaoPratoPronto) {
			
		try {
			PratoPronto pratoPronto = (PratoPronto) dao.findById(PratoPronto.class, itemSugestaoPratoPronto.getPratoPronto().getId());
			Sobremesa sobremesa = (Sobremesa) dao.findById(Sobremesa.class, itemSugestaoPratoPronto.getSobremesa().getId()); 
			itemSugestaoPratoPronto.setTotalCaloria(pratoPronto.getTotalCaloria());
			itemSugestaoPratoPronto.setSobremesa(sobremesa);
			itemSugestaoPratoPronto.setPratoPronto(pratoPronto);
			listItemSugestaoPratos.add(itemSugestaoPratoPronto);
			result.redirectTo(this).formSugestao();
		} catch (Exception e) {
			result.include("erro", "Erro ao adicionar o prato.");
			result.redirectTo(this).formSugestao();
		}
		
	}
	
	@AcessoAdministrativo
	@Path("/sugestao/clear")
	public void limpaDadosOperacao() {
		listItemSugestaoPratos.clear();
		result.redirectTo(this).formSugestao();
	}
	
	@AcessoAdministrativo
	@Path("/sugestao/remocao")
	public void remocaoItemSugestaoPrato(Long id) {
		for (ItemSugestaoPratoPronto itemSugestaoPratoPronto : listItemSugestaoPratos) {
			if(itemSugestaoPratoPronto.getPratoPronto().getId().equals(id)){
				listItemSugestaoPratos.remove(itemSugestaoPratoPronto);
				break;
			}
		}
		result.redirectTo(this).formSugestao();
	}
	
	@AcessoAdministrativo
	public void insereIdSugestaoPrato(SugestaoPrato sugestaoPrato) {
		for (ItemSugestaoPratoPronto itemSugestaoPratoPronto : listItemSugestaoPratos) {
			itemSugestaoPratoPronto.setSugestaoPrato(sugestaoPrato);
		}
	}
	
	@AcessoAdministrativo
	public boolean validaDadosSugestaoPrato(SugestaoPrato sugestaoPrato) {
		if(listItemSugestaoPratos.size() < 3){
			validator.add(new I18nMessage("Lista de Pratos", "lista.pratossugeridos.invalida"));
			return false;
		}
		
		if(sugestaoPrato.getDataDisponibilizada().before(new Date())){
			validator.add(new I18nMessage("Data disponibilizada", "datadisponibilizada.pratossugeridos.invalida"));
			return false;
		}
		if(sugestaoPrato.getDataFinalVotacao().after(sugestaoPrato.getDataDisponibilizada())){
			validator.add(new I18nMessage("Data Votação", "datafinalvotacao.pratossugeridos.invalida"));
			return false;
		}
		return true;
	}
	
	@AcessoAdministrativo
	@Path("/sugestao/save")
	public void saveSugestaoPrato(SugestaoPrato sugestaoPrato) {
		if(!validaDadosSugestaoPrato(sugestaoPrato)){
			validator.onErrorRedirectTo(this).formSugestao();
		}else{
			try {
				insereIdSugestaoPrato(sugestaoPrato);
				sugestaoPrato.setItemSugestaoPratoProntos(listItemSugestaoPratos);
				dao.save(sugestaoPrato);
				listItemSugestaoPratos.clear();
				result.include("sucesso", "Sugestão de pratos cadastrada com sucesso.");
				result.redirectTo(this).formSugestao();
			} catch (Exception e) {
				result.include("erro", "Ocorreu um erro ao cadastrar a sugestão de pratos. Tente novamente.");
				result.redirectTo(this).formSugestao();
			}
		}
	
	}
	
	@ControleAcesso
	@SuppressWarnings("unchecked")
	public void listCardapiosAbertosParaVotacao() {
		sugestaoPratosUtil = dao.find(SugestaoPrato.class);
		List<SugestaoPrato> listSugestoesValidas = new ArrayList<>();
		for (SugestaoPrato sugestaoPrato : sugestaoPratosUtil) {
			if(new Date().before(sugestaoPrato.getDataDisponibilizada())){
				listSugestoesValidas.add(sugestaoPrato);
			}
		}
		if(listSugestoesValidas.isEmpty()){
			result.include("sucesso", "Ainda não foram publicados os cardapios para votação. Aguarde a Administração do refeitório");
		}
		result.include("listSugestaoPratos", listSugestoesValidas);
		sugestaoPratosUtil.clear();
	}
	
	@ControleAcesso
	@Path("/sugestao/itens")
	public void detalhesPratosVotacao(Long id) {
		List<ItemSugestaoPratoPronto> itemSugestaoPratoProntos = new ArrayList<>();
		try {
			SugestaoPrato sugestaoPrato = (SugestaoPrato) dao.findById(SugestaoPrato.class, id);
			itemSugestaoPratoProntos = sugestaoPrato.getItemSugestaoPratoProntos();
			result.include("listItemSugestaoPrato", itemSugestaoPratoProntos);
			result.include("sugestaoPrato", sugestaoPrato);
		} catch (Exception e) {
			result.include("erro", "Erro ao iniciar votação. Tente mais tarde");
			result.redirectTo(RuifpiController.class).index();
		}
		
	}
	
	
}







