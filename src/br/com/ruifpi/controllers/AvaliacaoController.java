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
import br.com.ruifpi.models.AvaliacaoCardapio;
import br.com.ruifpi.models.Cardapio;
import br.com.ruifpi.util.RestricaoAcesso;
import br.com.ruifpi.util.RestricaoAcesso.AcessoAdministrativo;
import br.com.ruifpi.util.RestricaoAcesso.AcessoUsuario;

@Controller
public class AvaliacaoController {

	private SimpleDateFormat formatSimpleDateFormatUtil = new SimpleDateFormat();
	@ Inject private Result result;
	@ Inject private DaoImplementacao daoImplementacao;
	@ Inject private UsuarioSession usuarioSession;
	@ Inject private Validator validator;
	private List<AvaliacaoCardapio> avaliacaoCardapiosUtil = new ArrayList<>();;
			
	@RestricaoAcesso
	@AcessoAdministrativo
	@Path("/avaliacoes")
	public void listAvaliacaoCardapio() {
	  
	}
	
	@RestricaoAcesso
	@AcessoUsuario
	@Path("/avaliacao")
	public void formAvaliacao() {
		mostraCardapioDia();
		listAvaliacoesCardapioDia();
	}
	
	
	@SuppressWarnings("unchecked")
	public Cardapio mostraCardapioDia() {
		Cardapio cardapioEncontrado = null;
		try {
			formatSimpleDateFormatUtil.applyPattern("yyyy-MM-dd");
			String dataFormatada = formatSimpleDateFormatUtil.format(new Date());	// Formatando a data de hoje para formato sql do banco de dados ...
			java.sql.Date dataFormatoSql = new java.sql.Date(formatSimpleDateFormatUtil.parse(dataFormatada).getTime());
			List<Cardapio> cardapios = daoImplementacao.find(Cardapio.class);
			for (Cardapio cardapio : cardapios) {
				if(cardapio.getDataCardapio().equals(dataFormatoSql)){
					cardapioEncontrado = cardapio;
					break;
				}
			}
			result.include("cardapioDia", cardapioEncontrado);
			return cardapioEncontrado;
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na pesquisa do cardapio de Hoje. Tente novamente mais tarde.");
			return cardapioEncontrado;
		}				
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AvaliacaoCardapio> listAvaliacoesCardapioDia() {
		List<AvaliacaoCardapio> avaliCardapioEncontradas = new ArrayList<>();
		Cardapio cardapio = mostraCardapioDia();
		if(cardapio == null){
			result.include("erro", "Ainda não tem avaliações para o cardápio do dia");
		}else{	
			avaliacaoCardapiosUtil = daoImplementacao.find(AvaliacaoCardapio.class);
				for (AvaliacaoCardapio avaliacaoCardapio : avaliacaoCardapiosUtil) {
					if(avaliacaoCardapio.getCardapio().getId().equals(cardapio.getId())){
						avaliCardapioEncontradas.add(avaliacaoCardapio);
					}
				}
		}
		result.include("mediaAvaliacao", calculaMediaAvaliacao(avaliCardapioEncontradas));
		result.include("listaAvaliacoes", avaliCardapioEncontradas);
		return avaliCardapioEncontradas;
	}
	
	@SuppressWarnings("unchecked")
	@Path("/avaliacao/cardapio_data")
	public void listaAvaliacoesCardapioByData(Date dataCardapio) {
				Cardapio cardapioEncontrado = null;
				try {
					List<AvaliacaoCardapio> avaliacoesCardapioSolicitado = new ArrayList<AvaliacaoCardapio>();
					formatSimpleDateFormatUtil.applyPattern("yyyy-MM-dd");
					String dataFormatoSql = formatSimpleDateFormatUtil.format(dataCardapio);
					java.sql.Date dataSql = new java.sql.Date(formatSimpleDateFormatUtil.parse(dataFormatoSql).getTime());
					avaliacaoCardapiosUtil = daoImplementacao.find(AvaliacaoCardapio.class);
					for (AvaliacaoCardapio avaliacaoCardapio : avaliacaoCardapiosUtil) {
						if(avaliacaoCardapio.getCardapio().getDataCardapio().equals(dataSql)){
							cardapioEncontrado = avaliacaoCardapio.getCardapio();
							avaliacoesCardapioSolicitado.add(avaliacaoCardapio);
						}
					}
					if(!avaliacoesCardapioSolicitado.isEmpty()){
						result.include("listaAvaliacoes", avaliacoesCardapioSolicitado);
						result.include("cardapioSolicitado", cardapioEncontrado);
						result.include("mediaAvaliacao", calculaMediaAvaliacao(avaliacoesCardapioSolicitado));
					}else{
						result.include("erro", "Cardapio ainda não foi avaliado ou publicado.");
					}
					result.redirectTo(this).listAvaliacaoCardapio();
					
				} catch (Exception e) {
					result.include("erro", "Ocorreu um erro ao tentar ver as avaliações. Verifique se a data foi informado corretamente.");
					result.redirectTo(this).listAvaliacaoCardapio();
				}	
					
	}
	
	
	public double calculaMediaAvaliacao(List<AvaliacaoCardapio> avaliacaoCardapios) {
		double soma = 0, quantidade = 0;
		for (AvaliacaoCardapio avaliacaoCardapio : avaliacaoCardapios) {
			soma += avaliacaoCardapio.getNotaAvaliativa();
			quantidade++;
		}
		double media = soma / quantidade;
		return media;
	}
	
	@RestricaoAcesso
	public boolean validaAvaliacaoCardapio(int notaAvaliativa) {
		Cardapio cardapio = mostraCardapioDia();
		if(cardapio == null){
			validator.add(new I18nMessage("cardapio", "cardapio.ainda.nao.postado"));
			return false;
		}
		if(verificaAvaliaçãoRealizada()){
			validator.add(new I18nMessage("cardapio", "avaliacao.ja.submetida"));
			return false;
		}
		if(notaAvaliativa <= 0.0){
			validator.add(new I18nMessage("Nota", "avaliacao.nota.invalida"));
			return false;
		}
		return true;
	}
	
	@Path("/avaliacao/cardapio")	
	public void saveAvaliacao(int notaAvaliativa) {
		if(!validaAvaliacaoCardapio(notaAvaliativa)){
			validator.onErrorRedirectTo(this).formAvaliacao();
		}
		try {
			AvaliacaoCardapio avaliacaoCardapio = new AvaliacaoCardapio();
			Cardapio cardapio = mostraCardapioDia();
			avaliacaoCardapio.setUsuario(usuarioSession.getUsuario());
			avaliacaoCardapio.setCardapio(cardapio);
			avaliacaoCardapio.setNotaAvaliativa(notaAvaliativa);
			daoImplementacao.save(avaliacaoCardapio);
			result.include("sucesso", "Avaliação de cardápio registrada.");
			result.redirectTo(this).formAvaliacao();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na avaliação. Tente novamente mais tarde.");
			result.redirectTo(this).formAvaliacao();
		}	
	}
	
	@SuppressWarnings("unchecked")
	public boolean verificaAvaliaçãoRealizada() {
		boolean avaliacaoJaSubmetida = false;
		Cardapio cardapioEncontrado = mostraCardapioDia();
		try {
			if (cardapioEncontrado != null) {
				avaliacaoCardapiosUtil = daoImplementacao.find(AvaliacaoCardapio.class);
				for (AvaliacaoCardapio avaliacaoCardapio : avaliacaoCardapiosUtil) {
					if(avaliacaoCardapio.getCardapio().getId().equals(cardapioEncontrado.getId()) &&
							avaliacaoCardapio.getUsuario().getId().equals(usuarioSession.getUsuario().getId())){
						avaliacaoJaSubmetida = true;
						break;
					}
				}
			}
			return avaliacaoJaSubmetida;
		} catch (Exception e) {
			return avaliacaoJaSubmetida;
		}	
	}
}

