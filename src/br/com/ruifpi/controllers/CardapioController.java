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
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.PratoDia;
import br.com.ruifpi.util.ControleAcesso;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;

@Controller
public class CardapioController {
			
	private DaoImplementacao daoImplementacao;
	private Validator validator;
	private Result result;
	@Inject 
	private FuncionarioSession usuarioSession;
	@Inject 
	private ItemController itemController;
	private DecimalFormat decimalFormat = new DecimalFormat("0.00");
	private static PratoDia cardapioAlteracao = new PratoDia();
	private SimpleDateFormat formatadorDataUtil = new SimpleDateFormat();
	private static double valorTotalCaloriaCardapio = 0;
	private List<PratoDia> cardapioUtil = new ArrayList<PratoDia>();
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
	
	
	@ControleAcesso
	@Path("/cardapio/semana")
	public void cardapioSemanal() {
		
	}
	
	@AcessoAdministrativo
	@Path("/cardapios")	
	public void listCardapio() {
		listCardapios();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@AcessoAdministrativo
	public boolean verificaCardapioDia(PratoDia pratoDia) {
		boolean cardapioEncontrado = false;
		try {
			formatadorDataUtil.applyPattern("yyyy-MM-dd");
			String dataCardapioString = formatadorDataUtil.format(pratoDia.getDataCardapio());
			java.sql.Date dataCardapioSql = new java.sql.Date(formatadorDataUtil.parse(dataCardapioString).getTime());
			cardapioUtil = daoImplementacao.find(PratoDia.class);
			for (PratoDia cardapio2 : cardapioUtil) {
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
	public boolean validaDadosCardapio(PratoDia pratoDia){	
		try {
			if(pratoDia.getDataCardapio().before(formataData())){
				validator.add(new I18nMessage("dataCardapio", "cardapio.datacardapio.invalida"));
				return false;
			}
		} catch (Exception e) {
			result.include("erro", "Erro na comparação de datas...");
			return false;
		}
				
		if(pratoDia.getDataCardapio() == null){
			validator.add(new I18nMessage("dataCardapio", "cardapio.datacardapio.invalida"));
			return false;			
		}		
		if(pratoDia.getTotalCaloria() <= 0.0 ){
			validator.add(new I18nMessage("totalCaloria", "cardapio.totalcaloria.invalido"));
			return false;
		}
		return true;
	}
	
	@AcessoAdministrativo
	public boolean verificaCardapioPublicadoNaoAlteracao(PratoDia pratoDia) {
		if ((verificaCardapioDia(pratoDia)) && (cardapioAlteracao.getId() == null)) {	// Se nao for alteração ...
			validator.add(new I18nMessage("dataCardapio", "cardapio.existente"));
			return false;
		}else{
			return true;
		}
	}
	

	@AcessoAdministrativo
	@SuppressWarnings("unchecked")
	public List<PratoDia> listCardapios() {
		List<PratoDia> pratoDias = daoImplementacao.find(PratoDia.class);
		new PratoDia().ordenaPratoDiaByData(pratoDias);
		Collections.reverse(pratoDias);	// Mostrando pela ultima data...
		result.include("cardapios", pratoDias);
		return pratoDias;
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

