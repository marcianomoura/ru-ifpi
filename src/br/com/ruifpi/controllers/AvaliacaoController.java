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
import br.com.ruifpi.components.UsuarioSession;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.AvaliacaoRefeicao;
import br.com.ruifpi.models.PratoDia;
import br.com.ruifpi.models.Usuario;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;
import br.com.ruifpi.util.ControleAcesso.AcessoUsuario;

@Controller
public class AvaliacaoController {

	private SimpleDateFormat formatSimpleDateFormatUtil = new SimpleDateFormat();
	private  Result result;
	private DaoImplementacao daoImplementacao;
	@ Inject 
	private UsuarioSession usuarioSession;
	private  Validator validator;
	private List<AvaliacaoRefeicao> avaliacaoRefeicaosUtil = new ArrayList<>();
	private PratoDia cardapioUtil = null;
		
	public AvaliacaoController() {
		this(null, null, null);
	}
	
	@Inject
	public AvaliacaoController(Result result, Validator validator, DaoImplementacao daoImplementacao){
		this.result = result;
		this.validator = validator;
		this.daoImplementacao = daoImplementacao;
	}
	
	@AcessoAdministrativo
	@Path("/avaliacoes")
	public void listAvaliacaoRefeicao() {
	  
	}
	
	@AcessoUsuario
	@Path("/avaliacao")
	public void formAvaliacao() {
		mostraCardapioDia(new Date());
		listAvaliacoesCardapioDia();
	}
	
	@AcessoUsuario
	@SuppressWarnings("unchecked")
	public List<AvaliacaoRefeicao> listAvaliacoesCardapioDia() {
		List<AvaliacaoRefeicao> avaliCardapioEncontradas = new ArrayList<>();
		PratoDia pratoDia = mostraCardapioDia(new Date());
		if(pratoDia == null){
			result.include("erro", "Ainda n�o tem avalia��es para o card�pio do dia");
		}else{	
			avaliacaoRefeicaosUtil = daoImplementacao.find(AvaliacaoRefeicao.class);
				for (AvaliacaoRefeicao avaliacaoRefeicao : avaliacaoRefeicaosUtil) {
					if(avaliacaoRefeicao.getPratoDia().getId().equals(pratoDia.getId())){
						avaliCardapioEncontradas.add(avaliacaoRefeicao);
					}
				}
		}
		result.include("mediaAvaliacao", calculaMediaAvaliacao(avaliCardapioEncontradas));
		result.include("listaAvaliacoes", avaliCardapioEncontradas);
		return avaliCardapioEncontradas;
	}
	
	@AcessoAdministrativo
	@SuppressWarnings("unchecked")
	@Path("/avaliacao/cardapio_data")
	public void listaAvaliacoesCardapioByData(Date dataCardapio) {
		PratoDia cardapioEncontrado = null;
		try {
			List<AvaliacaoRefeicao> avaliacoesCardapioSolicitado = new ArrayList<AvaliacaoRefeicao>();
			formatSimpleDateFormatUtil.applyPattern("yyyy-MM-dd");
			String dataFormatoSql = formatSimpleDateFormatUtil.format(dataCardapio);
			java.sql.Date dataSql = new java.sql.Date(formatSimpleDateFormatUtil.parse(dataFormatoSql).getTime());
			avaliacaoRefeicaosUtil = daoImplementacao.find(AvaliacaoRefeicao.class);
			for (AvaliacaoRefeicao avaliacaoRefeicao : avaliacaoRefeicaosUtil) {
				if(avaliacaoRefeicao.getPratoDia().getDataCardapio().equals(dataSql)){
					cardapioEncontrado = avaliacaoRefeicao.getPratoDia();
					avaliacoesCardapioSolicitado.add(avaliacaoRefeicao);
				}
			}
			if(!avaliacoesCardapioSolicitado.isEmpty()){
				result.include("listaAvaliacoes", avaliacoesCardapioSolicitado);
				result.include("cardapioSolicitado", cardapioEncontrado);
				result.include("mediaAvaliacao", calculaMediaAvaliacao(avaliacoesCardapioSolicitado));
			}else{
				result.include("erro", "Cardapio ainda n�o foi avaliado ou publicado.");
			}
			result.redirectTo(this).listAvaliacaoRefeicao();
			
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro ao tentar ver as avalia��es. Verifique se a data foi informado corretamente.");
			result.redirectTo(this).listAvaliacaoRefeicao();
		}					
	}
	
	public double calculaMediaAvaliacao(List<AvaliacaoRefeicao> avaliacaoRefeicaos) {
		double soma = 0.0; double media = 0.0; int quantidade = 0;
		for (AvaliacaoRefeicao avaliacaoRefeicao : avaliacaoRefeicaos) {
			if(avaliacaoRefeicao.getNotaAvaliativa() >= 0){		// Se a nota for postiva efetua-se o c�lculo da soma e incrementa a quantidade
				soma += avaliacaoRefeicao.getNotaAvaliativa();	
				quantidade++;
			}
		}
		if(quantidade == 0){	// Significa que todos os valores s�o negativos
			media = 0.0;
		}else{
			media = soma / quantidade;
		}
		return media;
	}
	
	@SuppressWarnings("unchecked")
	public PratoDia mostraCardapioDia(Date dataHoje) {
		PratoDia cardapioEncontrado = null;
		try {
			formatSimpleDateFormatUtil.applyPattern("yyyy-MM-dd");
			String dataFormatada = formatSimpleDateFormatUtil.format(dataHoje);
			java.sql.Date dataFormatoSql = new java.sql.Date(formatSimpleDateFormatUtil.parse(dataFormatada).getTime());
			List<PratoDia> pratoDias = daoImplementacao.find(PratoDia.class);
			for (PratoDia pratoDia : pratoDias) {
				if(pratoDia.getDataCardapio().equals(dataFormatoSql)){
					cardapioEncontrado = pratoDia;
					break;
				}
			}
			if(cardapioEncontrado == null){
				result.include("erro", "Ainda n�o existe cardapio publicado hoje.");
			}else{
				result.include("cardapioDia", cardapioEncontrado);
			}
			return cardapioEncontrado;
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na pesquisa do cardapio de Hoje. Tente novamente mais tarde.");
			return cardapioEncontrado;
		}				
	}
	
	@AcessoUsuario
	public boolean validaNotaAvaliativa(AvaliacaoRefeicao avaliacaoRefeicao) {		
		if(avaliacaoRefeicao.getNotaAvaliativa() <= 0.0){
			validator.add(new I18nMessage("Nota", "avaliacao.nota.invalida"));
			return false;
		}else{
			return true;
		}
	}
	
	@AcessoUsuario
	@SuppressWarnings("unchecked")
	public boolean validaAvalia��oRealizada(Usuario usuario) {
		boolean avaliacaoJaSubmetida = false;
		PratoDia cardapioEncontrado = mostraCardapioDia(new Date());
		try {
			if (cardapioEncontrado != null) {
				avaliacaoRefeicaosUtil = daoImplementacao.find(AvaliacaoRefeicao.class);
				for (AvaliacaoRefeicao avaliacaoRefeicao : avaliacaoRefeicaosUtil) {
					if(avaliacaoRefeicao.getPratoDia().getId().equals(cardapioEncontrado.getId()) &&
							avaliacaoRefeicao.getUsuario().getId().equals(usuario.getId())){
						avaliacaoJaSubmetida = true;
						validator.add(new I18nMessage("cardapio", "avaliacao.ja.submetida"));
						break;
					}
				}
			}
			return avaliacaoJaSubmetida;
		} catch (Exception e) {
			return avaliacaoJaSubmetida;
		}	
	}
	
	@AcessoUsuario
	@Path("/avaliacao/cardapio")	
	public void saveAvaliacao(AvaliacaoRefeicao avaliacaoRefeicao) {
		cardapioUtil = mostraCardapioDia(new Date());
		if (cardapioUtil == null) {
			result.redirectTo(this).formAvaliacao();
		} else if (!validaNotaAvaliativa(avaliacaoRefeicao) || validaAvalia��oRealizada(usuarioSession.getUsuario())) {
			validator.onErrorRedirectTo(this).formAvaliacao();
		} else {
			try {
				avaliacaoRefeicao.setUsuario(usuarioSession.getUsuario());
				avaliacaoRefeicao.setPratoDia(cardapioUtil);
				daoImplementacao.save(avaliacaoRefeicao);
				result.include("sucesso", "Avalia��o de card�pio registrada.");
				result.redirectTo(this).formAvaliacao();
			} catch (Exception e) {
				result.include("erro","Ocorreu um erro na avalia��o. Tente novamente mais tarde.");
				result.redirectTo(this).formAvaliacao();
			}
		}

	}
}

