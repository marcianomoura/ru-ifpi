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
import br.com.ruifpi.util.ControleAcesso;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;
import br.com.ruifpi.util.ControleAcesso.AcessoUsuario;

@Controller
public class AvaliacaoController {

	private SimpleDateFormat formatSimpleDateFormatUtil = new SimpleDateFormat();
	private  Result result;
	private DaoImplementacao dao;
	private  Validator validator;
	private List<AvaliacaoRefeicao> avaliacaoRefeicaosUtil = new ArrayList<>();
	
	private RepositorioMetodos repositorioMetodos;

	@Inject
	private PratoDiaController pratoDiaController;
	
	public AvaliacaoController() {
		this(null, null, null, null);
	}
	
	@Inject
	public AvaliacaoController(Result result, Validator validator, DaoImplementacao dao, 
			RepositorioMetodos repositorioMetodos){
		this.result = result;
		this.validator = validator;
		this.repositorioMetodos = repositorioMetodos;
		this.dao = dao;
	}
	
	@AcessoAdministrativo
	@Path("/avaliacoes")
	public void listAvaliacaoRefeicao() {
	  
	}
	
	@AcessoUsuario
	@Path("/avaliacao")
	public void formAvaliacao() {
		pratoDiaController.pratoDiaPublicado();
	//	listAvaliacoesCardapioDia();
	}
	
	@ControleAcesso
	public void listAvaliacoesCardapioDia() {
		List<AvaliacaoRefeicao> listAvaliacaoAlmoco = new ArrayList<>();
		List<AvaliacaoRefeicao> listAvaliacaoJanta = new ArrayList<>();
		
		try {
			List<PratoDia> pratosDoDia = pratoDiaController.pratoDiaPublicado();
			if(!pratosDoDia.isEmpty()){
				for (PratoDia pratoDia : pratosDoDia) {
					if(pratoDia.getId() != null){	//Se for um prato Válido			
						if(pratoDia.getTipoPrato().getId().equals(1L)){
							listAvaliacaoAlmoco = repositorioMetodos.buscaAvaliacaoDeUmCardapio(pratoDia.getId());
						}else{
							listAvaliacaoJanta = repositorioMetodos.buscaAvaliacaoDeUmCardapio(pratoDia.getId());
						}
					}
				}
				result.include("listAvaliacaoAlmoco", listAvaliacaoAlmoco);
				result.include("listAvaliacaoJanta", listAvaliacaoJanta);
				result.include("mediaJanta", calculaMediaAvaliacao(listAvaliacaoJanta));
				result.include("mediaAlmoco", calculaMediaAvaliacao(listAvaliacaoAlmoco));
			}		
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na listagem das avaliacoes do cardapio do dia.");
		}
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
			avaliacaoRefeicaosUtil = dao.find(AvaliacaoRefeicao.class);
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
	
	@ControleAcesso
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
					validator.add(new I18nMessage("Horário de Avaliação", "horarioavaliacao.nao.permitido"));
					permiteAvaliacao = false;
				}	
			}else{
				if(horarioAtualRequisicaoConvertido.after(inicioAvaliacaoJantaConvertida)){	// Se o momento da requisição for após horario da janta.
					permiteAvaliacao =  true;		// Permite a avaliacao
				}else{
					validator.add(new I18nMessage("Horário de Avaliação", "horarioavaliacao.nao.permitido"));
					permiteAvaliacao = false;
				}	
			}
		} catch (Exception e) {
			result.include("erro", "Erro ao avaliar o cardápio. Tente novamente mais tarde.");
		}
		return permiteAvaliacao;
	}
	
	@AcessoUsuario
	public boolean verificaAvaliacaoJaSubmetida(AvaliacaoRefeicao avaliacaoRefeicao) {
		boolean avaliacaoJaSubmetida = false;
		try {
			avaliacaoRefeicaosUtil = repositorioMetodos.buscaAvaliacaoDeUmCardapio(avaliacaoRefeicao.getPratoDia().getId());
			for (AvaliacaoRefeicao avaliacaoRefeicaoBanco : avaliacaoRefeicaosUtil) {
				if(avaliacaoRefeicao.getUsuario().getId().equals(avaliacaoRefeicaoBanco.getUsuario().getId())){
					avaliacaoJaSubmetida = true;	// Foi encontrado uma avaliação feita ...
					break;
				}
			}
			if(avaliacaoJaSubmetida == true){
				validator.add(new I18nMessage("cardapio", "avaliacao.ja.submetida"));
			}
		} catch (Exception e) {
			throw new DaoException("Erro na verificacao de avaliacao ja submetida.");
		}
		return avaliacaoJaSubmetida;
	}
	
	@AcessoUsuario
	public boolean verificaPratoDia(AvaliacaoRefeicao avaliacaoRefeicao) {
		if(avaliacaoRefeicao.getPratoDia().getId() == null){
			validator.add(new I18nMessage("Prato", "pratoDia.nao.informado"));
			return false;
		}
		return true;
	}
	
	@AcessoUsuario
	public boolean verificaNotaAvaliativa(AvaliacaoRefeicao avaliacaoRefeicao) {
		if(avaliacaoRefeicao.getNotaAvaliativa() <= 0.0 || avaliacaoRefeicao.getNotaAvaliativa() > 10){
			validator.add(new I18nMessage("Nota", "avaliacao.nota.invalida"));
			return false;
		}
		return true;
	}
	
	@AcessoUsuario
	@Path("/avaliacao/cardapio")	
	public void saveAvaliacao(AvaliacaoRefeicao avaliacaoRefeicao, TipoPrato tipoPrato) {
		System.out.println("Tipo de Prato enviado..." + tipoPrato.getId());
		if(!verificaPratoDia(avaliacaoRefeicao) || !verificaNotaAvaliativa(avaliacaoRefeicao) || 
				verificaAvaliacaoJaSubmetida(avaliacaoRefeicao) || !validaHorarioAvaliacao(tipoPrato)){
			validator.onErrorRedirectTo(this).formAvaliacao();
		}else {
			try {
				dao.save(avaliacaoRefeicao);
				result.include("sucesso", "Avaliação de cardápio registrada.");
				result.redirectTo(this).formAvaliacao();
			} catch (Exception e) {
				result.include("erro","Ocorreu um erro na avaliação. Tente novamente mais tarde.");
				result.redirectTo(this).formAvaliacao();
			}
		}

	}
}

