package br.com.ruifpi.controllers;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.auxiliar.MetodosUtilImplementacao;
import br.com.ruifpi.components.FuncionarioSession;
import br.com.ruifpi.dao.DaoException;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Cardapio;
import br.com.ruifpi.models.Item;
import br.com.ruifpi.models.ItemCardapio;
import br.com.ruifpi.util.ControleAcesso;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;
import br.com.ruifpi.util.ControleAcesso.AcessoUsuario;

@Controller
public class CardapioController {
			
	private DaoImplementacao daoImplementacao;
	private Validator validator;
	private Result result;
	@Inject 
	private FuncionarioSession usuarioSession;
	@Inject 
	private ItemController itemController;
	private static List<ItemCardapio> itemsCardapio = new ArrayList<>();
	private DecimalFormat decimalFormat = new DecimalFormat("0.00");
	private static Cardapio cardapioAlteracao = new Cardapio();
	private SimpleDateFormat formatadorDataUtil = new SimpleDateFormat();
	private static double valorTotalCaloriaCardapio = 0;
	private List<Cardapio> cardapioUtil = new ArrayList<Cardapio>();
	private MetodosUtilImplementacao metodosUtilImplementacao;
	
	public CardapioController() {
		this(null, null, null, null);
	}
	
	@Inject
	public CardapioController(DaoImplementacao daoImplementacao, Validator validator, Result result, 
			MetodosUtilImplementacao metodosUtilImplementacao) {
		this.validator = validator;
		this.metodosUtilImplementacao = metodosUtilImplementacao;
		this.daoImplementacao = daoImplementacao;
		this.result = result;
	}
	
	@AcessoAdministrativo
	public void setListItens(List<ItemCardapio> listaItens) {
		itemsCardapio = listaItens;
	}
	
	@AcessoAdministrativo
	public List<ItemCardapio> getListaItens() {
		return itemsCardapio;
	}
			
	@AcessoAdministrativo				
	@Path("/cardapio")
	public void formCardapio() {			
		itemController.listaItensAlimentares();
		result.include("totalCaloria", Double.parseDouble(decimalFormat.format(valorTotalCaloriaCardapio).replace(",", ".")));
		mostraItensCardapioLista();
	}
	
	@ControleAcesso
	@Path("/cardapio/semana")
	public void cardapioSemanal() {
		
	}
	
	@ControleAcesso
	@Path("/cardapio/itens")
	public void itensCardapio(Cardapio cardapio) {
		detalhesItensCardapios(cardapio);
	}
	
	@AcessoAdministrativo
	@Path("/cardapios")	
	public void listCardapio() {
		listCardapios();
	}
	
	@AcessoAdministrativo
	@Path("/cardapio/save")
	public void save(Cardapio cardapio) {
		if (!validaDadosCardapio(cardapio) || !verificaListaItensVazia() || !verificaCardapioPublicadoNaoAlteracao(cardapio)) {
			validator.onErrorRedirectTo(this).formCardapio();
		}
		try {			
			if (cardapioAlteracao.getId() == null) { // Não é alteração de cardápio ...
				cardapio.setFuncionario(this.usuarioSession.getFuncionario());
				insereIdCardapio(cardapio);
				cardapio.setItemCardapio(itemsCardapio);
				daoImplementacao.save(cardapio);
				result.include("sucesso", "Cardapio inserido com sucesso.");

			} else {
				insereIdCardapio(cardapioAlteracao);
				cardapioAlteracao.setFuncionario(this.usuarioSession.getFuncionario());
				cardapioAlteracao.setItemCardapio(itemsCardapio);
				cardapioAlteracao.setDataCardapio(cardapio.getDataCardapio());
				cardapioAlteracao.setTotalCaloria(cardapio.getTotalCaloria());
				removeItensCardapioDoBanco(cardapioAlteracao);
				daoImplementacao.save(cardapioAlteracao);
				result.include("sucesso", "Cardapio alterado com sucesso.");
			}
			limpaDadosCardapio();

		} catch (Exception e) {
			result.include("erro","Cardapio não inserido. Verifique os dados informados e tente novamente.");
			result.redirectTo(this).formCardapio();
		}
	}
	
	@AcessoAdministrativo
	@Path("/cardapio/clear")
	public void limpaDadosCardapio() {
		cardapioAlteracao = new Cardapio();
		itemsCardapio.clear();
		valorTotalCaloriaCardapio = 0.0;
		result.redirectTo(this).formCardapio();
	}
	
	// Função que remove do banco de dados os itens do cardápio que irá ser alterado para que sejam substituidos pelos novos itens.
	@AcessoAdministrativo
	public void removeItensCardapioDoBanco(Cardapio cardapio) {
		try {
			Cardapio cardapioRemocaoItens = (Cardapio) daoImplementacao.findById(Cardapio.class, cardapio.getId());
			for (ItemCardapio itemCardapio : cardapioRemocaoItens.getItemCardapio()) {
				daoImplementacao.remove(itemCardapio);
			}
		} catch (Exception e) {
			throw new DaoException("erro na remoção dos itens");
		}
		
	}
	
	@AcessoAdministrativo
	public void mostraItensCardapioLista() {
		result.include("itemsLista", itemsCardapio);
	}
	
	@AcessoAdministrativo
	public void insereIdCardapio(Cardapio cardapio) {
		for (ItemCardapio itemCardapio : itemsCardapio) {
			itemCardapio.setCardapio(cardapio);
		}
	}
	
	@AcessoAdministrativo
	public double somaCaloriaCardapio(ItemCardapio itemCardapio) {
		return valorTotalCaloriaCardapio += itemCardapio.getTotalCaloria();
	}
	
	@AcessoAdministrativo
	public double subtraiCaloriaCardapio(ItemCardapio itemCardapio) {
		return valorTotalCaloriaCardapio -= itemCardapio.getTotalCaloria();
	}
	
	@AcessoAdministrativo
	@Path("/cardapio/addItem")
	public void addItemCardapio(Item item) {
		try {
			Item itemBanco = (Item) daoImplementacao.findById(Item.class, item.getId());
			ItemCardapio itemCardapio = new ItemCardapio();
			itemCardapio.setItem(itemBanco);
			itemCardapio.setTotalCaloria(itemBanco.getValorCalorico());
			somaCaloriaCardapio(itemCardapio);
			itemsCardapio.add(itemCardapio);
			result.redirectTo(this).formCardapio();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na inserção do item. Tente novamente");
			result.redirectTo(this).formCardapio();
		}
		
	}
	
	@AcessoAdministrativo
	@Path("/cardapio/removeItem")
	public void removeItem(Long id) {	// Id do Produto
		try {
			for (ItemCardapio itemCardapio : itemsCardapio) {
				if(itemCardapio.getItem().getId() == id){
					subtraiCaloriaCardapio(itemCardapio);
					itemsCardapio.remove(itemCardapio);
					break;
				}
			}
			result.redirectTo(this).formCardapio();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro ao tentar remover o item do cardapio.");
			result.redirectTo(this).formCardapio();
		}
	}
	
	@AcessoAdministrativo
	@Path("/carpapio/alteracao")
	public void alteracaoCardapio(Cardapio cardapio) {
		cardapioAlteracao = (Cardapio) daoImplementacao.findById(Cardapio.class, cardapio.getId());
		for (ItemCardapio itemCardapio : cardapioAlteracao.getItemCardapio()) {
			itemsCardapio.add(itemCardapio);
			valorTotalCaloriaCardapio+=itemCardapio.getTotalCaloria();
		}
		result.redirectTo(this).formCardapio();
	}
	
	@SuppressWarnings("unchecked")
	@AcessoAdministrativo
	public boolean verificaCardapioDia(Cardapio cardapio) {
		boolean cardapioEncontrado = false;
		try {
			formatadorDataUtil.applyPattern("yyyy-MM-dd");
			String dataCardapioString = formatadorDataUtil.format(cardapio.getDataCardapio());
			java.sql.Date dataCardapioSql = new java.sql.Date(formatadorDataUtil.parse(dataCardapioString).getTime());
			cardapioUtil = daoImplementacao.find(Cardapio.class);
			for (Cardapio cardapio2 : cardapioUtil) {
				if(dataCardapioSql.equals(cardapio2.getDataCardapio())){
					cardapioEncontrado = true;
					break;
				}
			}
		} catch (Exception e) {
			cardapioEncontrado = false;
			result.include("erro", "Erro na operação de verificação do cardapio");
		}		
		return cardapioEncontrado;
	}
		
	@AcessoAdministrativo
	public Date formataData() {
		Date dataFormatada = new Date();
		try {
			formatadorDataUtil.applyPattern("dd/MM/yyyy");
			dataFormatada = formatadorDataUtil.parse(formatadorDataUtil.format(new Date()));
		} catch (ParseException e) {
			System.out.println("Erro na Conversão de datas...");
			e.printStackTrace();
		}
		return dataFormatada;
	}

	@AcessoAdministrativo
	public boolean validaDadosCardapio(Cardapio cardapio){	
		try {
			if(cardapio.getDataCardapio().before(formataData())){
				validator.add(new I18nMessage("dataCardapio", "cardapio.datacardapio.invalida"));
				return false;
			}
		} catch (Exception e) {
			result.include("erro", "Erro na comparação de datas...");
			return false;
		}
				
		if(cardapio.getDataCardapio() == null){
			validator.add(new I18nMessage("dataCardapio", "cardapio.datacardapio.invalida"));
			return false;			
		}		
		if(cardapio.getTotalCaloria() <= 0.0 ){
			validator.add(new I18nMessage("totalCaloria", "cardapio.totalcaloria.invalido"));
			return false;
		}
		return true;
	}
	
	@AcessoAdministrativo
	public boolean verificaCardapioPublicadoNaoAlteracao(Cardapio cardapio) {
		if ((verificaCardapioDia(cardapio)) && (cardapioAlteracao.getId() == null)) {	// Se nao for alteração ...
			validator.add(new I18nMessage("dataCardapio", "cardapio.existente"));
			return false;
		}else{
			return true;
		}
	}
	
	@AcessoAdministrativo
	public boolean verificaListaItensVazia() {
		if(itemsCardapio.isEmpty() || itemsCardapio.size() <= 0){
			validator.add(new I18nMessage("itemsDoCardapio", "cardapio.items.vazio"));
			return false;
		}else{
			return true;
		}
	}
	
	@AcessoAdministrativo
	@SuppressWarnings("unchecked")
	public List<Cardapio> listCardapios() {
		List<Cardapio> cardapios = daoImplementacao.find(Cardapio.class);
		new Cardapio().ordenaCardapioByData(cardapios);
		Collections.reverse(cardapios);	// Mostrando pela ultima data...
		result.include("cardapios", cardapios);
		return cardapios;
	}
	
	@AcessoAdministrativo
	public List<ItemCardapio> detalhesItensCardapios(Cardapio cardapio) {
		List<ItemCardapio> itemCardapios  =  new ArrayList<>();
		try {
			Cardapio cardapioEncontrado = (Cardapio) daoImplementacao.findById(Cardapio.class, cardapio.getId());
			for (ItemCardapio itemCardapio : cardapioEncontrado.getItemCardapio()) {
				itemCardapios.add(itemCardapio);
			}
			result.include("itemCardapios", itemCardapios);
			return itemCardapios;
		} catch (Exception e) {
			result.include("erro", "Erro na listagem de itens do cardápio.");
			return null;
		}
	}
	
	@ControleAcesso
	@Path("/cardapio/periodo")
	public void buscaCardapioSemanal(java.util.Date periodoInicial, java.util.Date periodoFinal) {
		try {
			System.out.println("Entrou na pesquisa de cardapios da semana ...");
			formatadorDataUtil.applyPattern("yyyy-MM-dd");
			String inicioString =  formatadorDataUtil.format(periodoInicial);
			String finalString =  formatadorDataUtil.format(periodoFinal);
			java.sql.Date inicioSql = new java.sql.Date(formatadorDataUtil.parse(inicioString).getTime());
			java.sql.Date finalSql = new java.sql.Date(formatadorDataUtil.parse(finalString).getTime());
			cardapioUtil = metodosUtilImplementacao.buscaCardapioPeriodoSelecionado(inicioSql, finalSql);
			result.include("cardapios", cardapioUtil);
			result.redirectTo(this).cardapioSemanal();
		} catch (Exception e) {
			result.include("erro", "Erro na listagem dos cardápios da semana");
			result.redirectTo(this).cardapioSemanal();
		}
	}
}

