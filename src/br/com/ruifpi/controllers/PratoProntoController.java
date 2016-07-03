package br.com.ruifpi.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.auxiliar.RepositorioMetodos;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Item;
import br.com.ruifpi.models.ItemPratoPronto;
import br.com.ruifpi.models.PratoDia;
import br.com.ruifpi.models.PratoPronto;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;

@Controller
public class PratoProntoController {

	private DaoImplementacao dao;
	private Validator validator;
	private Result result;
	@Inject
	private ItemController itemController;
	private static double totalCaloria = 0;
	private static List<ItemPratoPronto> listItemPratoProntos = new ArrayList<>();
	private static PratoPronto pratoProntoAlteracao = new PratoPronto(); 
	private static List<ItemPratoPronto> listAuxiliar = new ArrayList<>();
	@Inject
	private RepositorioMetodos repositorioMetodos;

	public PratoProntoController() {
		this(null, null, null);
	}

	@Inject
	public PratoProntoController(DaoImplementacao dao, Validator validator, Result result) {
		this.validator = validator;
		this.dao = dao;
		this.result = result;
	}

	@Path("/pratopronto")
	public void formPratoPronto() {
		itemController.listaItensAlimentares();
		result.include("listItemPratoPronto", listItemPratoProntos);
		result.include("totalCaloria", totalCaloria);
	}

	@Path("/pratosprontos")
	public void listPratosProntos() {
		listPratosPronto();
	}
	
	@Path("/cardapiosemanal")
	public void cardapioSemanal() {
		
	}

	@SuppressWarnings("unchecked")
	public List<PratoPronto> listPratosPronto() {
		List<PratoPronto> pratoProntos = dao.find(PratoPronto.class);
		new PratoPronto().ordenaPratoByTitulo(pratoProntos); // Ordenando por titulo ...
		result.include("listPratoProntos", pratoProntos);
		return pratoProntos;
	}

	@Path("/pratopronto/addItem")
	public void addItemPratoPronto(Item item) {
		ItemPratoPronto itemPratoPronto = new ItemPratoPronto();
		try {
			Item itemBanco = (Item) dao.findById(Item.class, item.getId());
			itemPratoPronto.setItem(itemBanco);
			itemPratoPronto.setTotalCaloria(itemBanco.getValorCalorico());
			listItemPratoProntos.add(itemPratoPronto);
			totalCaloria += itemBanco.getValorCalorico(); 
			result.redirectTo(this).formPratoPronto();

		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro ao adicionar o item. Verifique e tente novamente.");
			result.redirectTo(this).formPratoPronto();
		}
	}

	@Path("/pratopronto/remocao")
	public void removeItemPratoPronto(Long id) {
		for (ItemPratoPronto itemPratoPronto : listItemPratoProntos) {
			if (itemPratoPronto.getItem().getId().equals(id)) {
				listItemPratoProntos.remove(itemPratoPronto);
				totalCaloria -=itemPratoPronto.getItem().getValorCalorico();	//	Atualizo o total de Calorias do Prato ...
				break;
			}
		}
		result.redirectTo(this).formPratoPronto();
	}
	
	@Path("/pratopronto/alteracao")
	public void alteracaoPratoPronto(Long id) {
		pratoProntoAlteracao = (PratoPronto) dao.findById(PratoPronto.class, id);
		for (ItemPratoPronto itemPratoPronto : pratoProntoAlteracao.getItemPratoProntos()) {
			listItemPratoProntos.add(itemPratoPronto);
			listAuxiliar.add(itemPratoPronto);
			totalCaloria += itemPratoPronto.getTotalCaloria();
		}
		result.redirectTo(this).formPratoPronto();
	}
	
	public void atualizaItensAposAlteracao() {
		try {
			for (ItemPratoPronto itemPratoPronto : listAuxiliar) {
				dao.remove(itemPratoPronto);
			}	
		} catch (Exception e) {
			result.include("erro", "Erro ao remover os itens do prato");
		}	
	}

	@Path("/pratopronto/clear")
	public void clearPratoPronto() {
		totalCaloria = 0.0;
		listItemPratoProntos.clear();
		result.include("sucesso", "Operação cancelada com sucesso.");
		result.redirectTo(this).formPratoPronto();
	}

	public void insereIdPrato(PratoPronto pratoPronto) {
		for (ItemPratoPronto itemPratoPronto : listItemPratoProntos) {
			itemPratoPronto.setPratoPronto(pratoPronto);
		}
	}

	@Path("/pratopronto/save")
	public void save(PratoPronto pratoPronto) {
		if (!validaDadosPrato(pratoPronto)) {
			validator.onErrorRedirectTo(this).formPratoPronto();
		} else {
			try {
				if(pratoProntoAlteracao.getId() != null){
					pratoProntoAlteracao.setItemPratoProntos(listItemPratoProntos);
					pratoProntoAlteracao.setTituloPrato(pratoPronto.getTituloPrato());
					pratoProntoAlteracao.setTotalCaloria(totalCaloria);
					insereIdPrato(pratoProntoAlteracao);
					atualizaItensAposAlteracao();
					dao.save(pratoProntoAlteracao);
					pratoProntoAlteracao = new PratoPronto();
					listAuxiliar.clear();
					result.include("sucesso", "Prato alterado com sucesso.");
				}else{
					insereIdPrato(pratoPronto);
					pratoPronto.setItemPratoProntos(listItemPratoProntos);
					pratoPronto.setTotalCaloria(totalCaloria);
					dao.save(pratoPronto);
					result.include("sucesso", "Prato Adicionado ao catálogo de pratos prontos.");
				}
				listItemPratoProntos.clear();
				totalCaloria = 0.0;
				result.redirectTo(this).formPratoPronto();
			} catch (Exception e) {
				result.include("erro", "Ocorreu um erro ao salvar o prato. Verifique os dados informados e tente novamente.");
				result.redirectTo(this).formPratoPronto();
			}
		}
	}

	@AcessoAdministrativo
	public boolean validaDadosPrato(PratoPronto pratoPronto) {

		if (pratoPronto.getTituloPrato().length() < 5) {
			validator.add(new I18nMessage("Titulo do Prato", "prato.titulo.pequeno"));
			return false;
		}
		if (listItemPratoProntos.isEmpty()) {
			validator.add(new I18nMessage("Lista de itens", "prato.listaitens.vazia"));
			return false;
		}
		return true;
	}
	
	@Path("/pratopronto/itens")
	public void detalhesItensPrato(Long id) {
		try {
			PratoPronto pratoBanco = (PratoPronto) dao.findById(PratoPronto.class, id);
			result.include("listItensPratoPronto", pratoBanco.getItemPratoProntos());
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na listagem dos itens. Tente novamente mais tarde.");
			result.redirectTo(this).listPratosProntos();
		}	
	}
	
	@Path("/prato/listpratosemanal")
	public void buscaCardapiosSemana(Date dataInicioSemana, Date dataFinalSemana) {
		SimpleDateFormat formatador = new SimpleDateFormat();
		formatador.applyPattern("yyy-MM-dd");
		try {
			java.sql.Date dataInicioSqlConvertida = new java.sql.Date(formatador.parse(formatador.format(dataInicioSemana)).getTime());
			java.sql.Date dataFinalSqlConvertida = new java.sql.Date(formatador.parse(formatador.format(dataFinalSemana)).getTime());
			List<PratoDia> listPratosSemana = repositorioMetodos.buscaCardapioPeriodoSelecionado(dataInicioSqlConvertida, dataFinalSqlConvertida);
			if(listPratosSemana.isEmpty()){
				result.include("sucesso", "Ainda não foi publicado cardápio nesta semana.");
			}else{
				result.include("dataInicioSemana", dataInicioSemana);
				result.include("dataFinalSemana", dataFinalSemana);
				result.include("listPratosSemana", listPratosSemana);
			}
		} catch (Exception e) {
			result.include("erro", "Erro ao pesquisar os cardápios da semana.");
		}
		result.redirectTo(this).cardapioSemanal();
	}
	
}
