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
import br.com.ruifpi.dao.DaoException;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.AvaliacaoRefeicao;
import br.com.ruifpi.models.PratoDia;
import br.com.ruifpi.models.TipoPrato;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;
import br.com.ruifpi.util.ControleAcesso.AcessoUsuario;

@Controller
public class AvaliacaoController {

	private SimpleDateFormat formatSimpleDateFormatUtil = new SimpleDateFormat();
	private  Result result;
	private DaoImplementacao daoImplementacao;
	private  Validator validator;
	private List<AvaliacaoRefeicao> avaliacaoRefeicaosUtil = new ArrayList<>();
	@Inject
	private RepositorioMetodos metodosUtil;

	@Inject
	private PratoDiaController pratoDiaController;
	
		
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
		pratoDiaController.pratoDiaPublicado();
		listAvaliacoesCardapioDia();
	}
	
	@AcessoUsuario
	public List<AvaliacaoRefeicao> listAvaliacoesCardapioDia() {
		List<AvaliacaoRefeicao> avaliCardapioEncontradas = new ArrayList<>();
		
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
				result.include("erro", "Cardapio ainda não foi avaliado ou publicado.");
			}
			result.redirectTo(this).listAvaliacaoRefeicao();
			
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro ao tentar ver as avaliações. Verifique se a data foi informado corretamente.");
			result.redirectTo(this).listAvaliacaoRefeicao();
		}					
	}
	
	
	public double calculaMediaAvaliacao(List<AvaliacaoRefeicao> avaliacaoRefeicaos) {
		double soma = 0.0; double media = 0.0; int quantidade = 0;
		for (AvaliacaoRefeicao avaliacaoRefeicao : avaliacaoRefeicaos) {
			if(avaliacaoRefeicao.getNotaAvaliativa() >= 0){		// Se a nota for postiva efetua-se o cálculo da soma e incrementa a quantidade
				soma += avaliacaoRefeicao.getNotaAvaliativa();	
				quantidade++;
			}
		}
		if(quantidade == 0){	// Significa que todos os valores são negativos
			media = 0.0;
		}else{
			media = soma / quantidade;
		}
		return media;
	}
	
	@AcessoUsuario
	public boolean validaHorarioAvaliacao(TipoPrato tipoPrato) {
		boolean permiteAvaliacao = false;
		String horaInicioAvaliacaoAlmoco = "13:00:00";
		String horaInicioAvaliacaoJanta = "19:00:00";
		formatSimpleDateFormatUtil.applyPattern("HH:mm:ss");
		String horarioAtualRequisicao = formatSimpleDateFormatUtil.format(new Date());		
		formatSimpleDateFormatUtil.setLenient(false);
		
		try {
			Date inicioAvaliacaoAlmocoConvertida =  formatSimpleDateFormatUtil.parse(horaInicioAvaliacaoAlmoco);
			Date inicioAvaliacaoJantaConvertida =  formatSimpleDateFormatUtil.parse(horaInicioAvaliacaoJanta);
			Date horarioAtualRequisicaoConvertido =  formatSimpleDateFormatUtil.parse(horarioAtualRequisicao);
			if(tipoPrato.getId().equals(1L)){	// Se form Almoço
				if(horarioAtualRequisicaoConvertido.after(inicioAvaliacaoAlmocoConvertida)){	// Se o momento da requisição for após a horario do almoço
					permiteAvaliacao =  true;		// Permite a avaliacao
				}else{
					permiteAvaliacao = false;
				}	
			}else{
				if(horarioAtualRequisicaoConvertido.after(inicioAvaliacaoJantaConvertida)){	// Se o momento da requisição for após horario da janta.
					permiteAvaliacao =  true;		// Permite a avaliacao
				}else{
					permiteAvaliacao = false;
				}	
			}
			
		} catch (Exception e) {
			result.include("erro", "Erro ao avaliar o cardápio. Tente novamente mais tarde.");
		}
		return permiteAvaliacao;
	}
	
	public boolean verificaAvaliacaoJaSubmetida(AvaliacaoRefeicao avaliacaoRefeicao, TipoPrato tipoPrato) {
		boolean avaliacaoJaSubmetida = false;
		try {
			avaliacaoRefeicaosUtil = metodosUtil.buscaAvaliacaoDeUmCardapio(avaliacaoRefeicao.getPratoDia().getId());
			for (AvaliacaoRefeicao avaliacaoRefeicaoBanco : avaliacaoRefeicaosUtil) {
				if(avaliacaoRefeicao.getUsuario().getId().equals(avaliacaoRefeicaoBanco.getUsuario().getId())){
					avaliacaoJaSubmetida = true;
					break;
				}
			}
			return avaliacaoJaSubmetida;
		} catch (Exception e) {
			throw new DaoException("Erro na verificacao de avaliacao ja submetida.");
		}
	}
	
	public boolean validaDadosAvaliacaoCardapio(AvaliacaoRefeicao avaliacaoRefeicao, TipoPrato tipoPrato) {
		if(!validaHorarioAvaliacao(tipoPrato)){
			validator.add(new I18nMessage("Horário de Avaliação", "horarioavaliacao.nao.permitido"));
			return false;
		}
		if(avaliacaoRefeicao.getNotaAvaliativa() <= 0.0 || avaliacaoRefeicao.getNotaAvaliativa() > 10){
			validator.add(new I18nMessage("Nota", "avaliacao.nota.invalida"));
			return false;
		}
		
		if(verificaAvaliacaoJaSubmetida(avaliacaoRefeicao, tipoPrato)){
			validator.add(new I18nMessage("cardapio", "avaliacao.ja.submetida"));
			return false;
		}
		return true;
	}
	
	@AcessoUsuario
	@Path("/avaliacao/cardapio")	
	public void saveAvaliacao(AvaliacaoRefeicao avaliacaoRefeicao, TipoPrato tipoPrato) {
		System.out.println("Tipo de Prato enviado..." + tipoPrato.getId());
		if(!validaDadosAvaliacaoCardapio(avaliacaoRefeicao, tipoPrato)){
			validator.onErrorRedirectTo(this).formAvaliacao();
		}else {
			try {
				daoImplementacao.save(avaliacaoRefeicao);
				result.include("sucesso", "Avaliação de cardápio registrada.");
				result.redirectTo(this).formAvaliacao();
			} catch (Exception e) {
				result.include("erro","Ocorreu um erro na avaliação. Tente novamente mais tarde.");
				result.redirectTo(this).formAvaliacao();
			}
		}

	}
}

