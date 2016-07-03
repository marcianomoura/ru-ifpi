package br.com.ruifpi.controllers;

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
import br.com.ruifpi.auxiliar.RepositorioMetodos;
import br.com.ruifpi.components.FuncionarioSession;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.ItemPratoPronto;
import br.com.ruifpi.models.PratoDia;
import br.com.ruifpi.models.PratoPronto;
import br.com.ruifpi.models.Sobremesa;
import br.com.ruifpi.models.TipoPrato;

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
	@Inject
	private FuncionarioSession funcionarioSession;
	private List<ItemPratoPronto> listItensPratoPronto = new ArrayList<>(); 
	private SimpleDateFormat formatadorData = new SimpleDateFormat();
	@Inject
	private RepositorioMetodos metodosUtil;
	
	@Path("/pratodia")
	public void formPratoDia() {
		sobremesaController.listSobremesas();
		pratoProntoController.listPratosPronto();
	}
	
	@SuppressWarnings("unchecked")
	public List<PratoDia> listarPratosPublicados() {
		List<PratoDia> pratosPublicados = dao.find(PratoDia.class);
		Collections.reverse(pratosPublicados);
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
			TipoPrato tipoPrato = (TipoPrato) dao.findById(TipoPrato.class, pratoDia.getTipoPrato().getId());
			listItensPratoPronto = pratoPronto.getItemPratoProntos();
			pratoDia.setSobremesa(sobremesa);
			pratoDia.setTipoPrato(tipoPrato);
			pratoDia.setPratoPronto(pratoPronto);
			pratoDia.setTotalCaloria(calculaTotalCaloria(pratoDia));
			result.include("pratoDia", pratoDia);
			result.include("listItemPratoPronto", listItensPratoPronto);
			result.redirectTo(this).formPratoDia();
			
		} catch (Exception e) {
			result.include("erro", "Não foi possivel montar o prato do dia. Tente novamente.");
			result.redirectTo(this).formPratoDia();
		}
	}
	
	public double calculaTotalCaloria(PratoDia pratoDia) {
		double soma = 0.0d;
		for (ItemPratoPronto itemPratoPronto : listItensPratoPronto) {
			soma += itemPratoPronto.getTotalCaloria();
		}
		return soma;
	}
	
	@Path("/pratodia/alteracao")
	public void alteraCardapioDia(Long id) {
		try {
			PratoDia pratoDia = (PratoDia) dao.findById(PratoDia.class, id);
			PratoPronto pratoPronto = pratoDia.getPratoPronto();
			for (ItemPratoPronto itemPratoPronto : pratoPronto.getItemPratoProntos()) {	// Evitar LazyException ... 
				listItensPratoPronto.add(itemPratoPronto);
			}
			result.include("listItemPratoPronto", listItensPratoPronto);
			result.include("pratoDia", pratoDia);
			result.redirectTo(this).formPratoDia();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro ao tentar alterar o cardápio. Verifique e tente novamente.");
			result.redirectTo(this).formPratoDia();
		}
		
	}
	
	@Path("/pratodia/itens")
	public void listItensPratoDia(Long id) {
		try {
			PratoPronto pratoPronto = (PratoPronto) dao.findById(PratoPronto.class, id);
			result.include("itensCardapio", pratoPronto.getItemPratoProntos());
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro ao listar os itens do cardápio.");
			result.redirectTo(this).listCardapiosPublicados();
		}
		
	}
	
	public boolean validaDadosPratoDia(PratoDia pratoDia) {
		if(pratoDia.getPratoPronto().getId() == null){
			validator.add(new I18nMessage("Itens do Cardapio", "prato.nao.informado"));
			return false;
		}
		if(pratoDia.getTipoPrato().getId() == null){
			validator.add(new I18nMessage("Tipo de Cardápio", "tipocardapio.nao.informado"));
			return false;
		}
		if(pratoDia.getSobremesa().getId() == null){
			validator.add(new I18nMessage("Sobremesa", "sobremesa.nao.informada"));
			return false;
		}
		return true;
	}
	
	@Path("/pratodia/save")
	public void savePratoDia(PratoDia pratoDia) {
		if(!validaDadosPratoDia(pratoDia)){
			validator.onErrorRedirectTo(this).formPratoDia();
		}else{
			try {
				pratoDia.setFuncionario(funcionarioSession.getFuncionario());
				dao.save(pratoDia);
				result.include("sucesso", "Prato do dia cadastrado com sucesso");
				result.redirectTo(this).formPratoDia();
			} catch (Exception e) {
				result.include("erro", "Ocorreu um erro ao salvar o prato do dia. Verifique as informaçõe e tente novamente.");
				result.redirectTo(this).formPratoDia();
			}
		}				
	}
	
	public void pratoDiaPublicado() {
		try {
			PratoPronto janta = new PratoPronto();
			PratoPronto almoco = new PratoPronto();
			PratoDia pratoDiaAlmoco = new PratoDia();
			PratoDia pratoDiaJanta = new PratoDia();
			formatadorData.applyPattern("yyyy-MM-dd");
			String dataFormatada = formatadorData.format(new Date());
			java.sql.Date dataSqlFormatada = new java.sql.Date(formatadorData.parse(dataFormatada).getTime());
			List<PratoDia> pratosEncontrados = metodosUtil.buscaCardapioDataSolicitada(dataSqlFormatada);
			if(pratosEncontrados.isEmpty()){
				result.include("sucesso", "Cardápio ainda não publicado. Aguarde que deverá ser publicado em instantes.");
			}else{
				for (PratoDia pratoDiaBanco : pratosEncontrados) {
					if(pratoDiaBanco.getTipoPrato().getId() == 2L){		// Se for Janta...
						janta = pratoDiaBanco.getPratoPronto();
						pratoDiaJanta = pratoDiaBanco;
					}else{												// Será Almoço ...
						almoco = pratoDiaBanco.getPratoPronto();
						pratoDiaAlmoco = pratoDiaBanco;
					}
				}
				
				result.include("pratoDiaAlmoco", pratoDiaAlmoco);
				result.include("pratoDiaJanta", pratoDiaJanta);
				result.include("almoco", almoco);
				result.include("janta", janta);
			}
			
		} catch (Exception e) {
			result.include("erro", "Há um erro impedindo a listagem do cardápio.");
		}
		
	}
	
//	@SuppressWarnings("unchecked")
//	public PratoDia mostraCardapioDia(Date dataHoje) {
//		PratoDia cardapioEncontrado = null;
//		try {
//			formatadorData.applyPattern("yyyy-MM-dd");
//			String dataFormatada = formatadorData.format(dataHoje);
//			java.sql.Date dataFormatoSql = new java.sql.Date(formatadorData.parse(dataFormatada).getTime());
//			List<PratoDia> pratoDias = dao.find(PratoDia.class);
//			for (PratoDia pratoDia : pratoDias) {
//				if(pratoDia.getDataCardapio().equals(dataFormatoSql)){
//					cardapioEncontrado = pratoDia;
//					break;
//				}
//			}
//			if(cardapioEncontrado == null){
//				result.include("erro", "Ainda não existe cardapio publicado hoje.");
//			}else{
//				result.include("cardapioDia", cardapioEncontrado);
//			}
//			return cardapioEncontrado;
//		} catch (Exception e) {
//			result.include("erro", "Ocorreu um erro na pesquisa do cardapio de Hoje. Tente novamente mais tarde.");
//			return cardapioEncontrado;
//		}				
//	}
}
