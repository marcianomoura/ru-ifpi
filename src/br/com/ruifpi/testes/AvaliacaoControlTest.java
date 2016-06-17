package br.com.ruifpi.testes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.ruifpi.controllers.AvaliacaoController;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.AvaliacaoRefeicao;
import br.com.ruifpi.models.PratoDia;
import junit.framework.TestCase;

public class AvaliacaoControlTest extends TestCase{
	
	private MockValidator validator;
	private MockResult result;
	private AvaliacaoController avaliacaoCardapioCOntroller;
	private DaoImplementacao daoimplementacao;
	private List<AvaliacaoRefeicao> avaliacoes;
	private AvaliacaoRefeicao avaliacaoCardapio1;
	private AvaliacaoRefeicao avaliacaoCardapio2;
	private SimpleDateFormat formatDate;
	
	@Before
	public void setUp() {
		daoimplementacao = new DaoImplementacao();
		result = new MockResult();
		formatDate = new SimpleDateFormat("dd/MM/yyyy");
		avaliacaoCardapio1 = new AvaliacaoRefeicao();
		avaliacaoCardapio2 = new AvaliacaoRefeicao();
		validator = new MockValidator();
		avaliacaoCardapioCOntroller = new AvaliacaoController(result,validator,daoimplementacao);
		avaliacoes = new ArrayList<AvaliacaoRefeicao>();
	}
	
	@Test
	public void testaMediaDasAvaliacoesValoresPositivos() {
		double valorEsperado = 8.0;
		avaliacaoCardapio1.setNotaAvaliativa(10);
		avaliacaoCardapio2.setNotaAvaliativa(6);
		avaliacoes.add(avaliacaoCardapio1);
		avaliacoes.add(avaliacaoCardapio2);
		double resultado = this.avaliacaoCardapioCOntroller.calculaMediaAvaliacao(avaliacoes);
		assertEquals(valorEsperado, resultado);		
	}
		
	@Test
	public void testaMediaDasAvaliacoesValoresNegativo() {		
		avaliacaoCardapio1.setNotaAvaliativa(-2);
		avaliacaoCardapio2.setNotaAvaliativa(-4);
		avaliacoes.add(avaliacaoCardapio1);
		avaliacoes.add(avaliacaoCardapio2);
		double resultado = this.avaliacaoCardapioCOntroller.calculaMediaAvaliacao(avaliacoes);
		assertEquals(0, resultado, 0);
	}
	
	@Test
	public void testaMediaDasAvaliacoesValoresMistos() {
		avaliacaoCardapio1.setNotaAvaliativa(-2);
		avaliacaoCardapio2.setNotaAvaliativa(6);
		avaliacoes.add(avaliacaoCardapio1);
		avaliacoes.add(avaliacaoCardapio2);
		double resultado = this.avaliacaoCardapioCOntroller.calculaMediaAvaliacao(avaliacoes);
		assertEquals(6, resultado, 0);
	}
	
	
	@Test
	public void testaNotaAvalicaoMenorZero() {
		AvaliacaoRefeicao avaliacaoCardapio1 = new AvaliacaoRefeicao();
		avaliacaoCardapio1.setNotaAvaliativa(-6);	
		assertFalse(this.avaliacaoCardapioCOntroller.validaNotaAvaliativa(avaliacaoCardapio1));
	}
	
	@Test
	public void testaNotaAvalicaoIgualZero() {
		this.avaliacaoCardapio1 = new AvaliacaoRefeicao();
		this.avaliacaoCardapio1.setNotaAvaliativa(0);	
		assertFalse(this.avaliacaoCardapioCOntroller.validaNotaAvaliativa(avaliacaoCardapio1));
	}
	
	@Test
	public void testaCardapioDoDiaPublicado() throws ParseException {
		PratoDia cardapioEsperado = new PratoDia();
		cardapioEsperado.setId(10L);
		cardapioEsperado.setTotalCaloria(803);
		String data = "16/11/2015";
		Date dataFormatada = formatDate.parse(data);
		PratoDia resultado =  this.avaliacaoCardapioCOntroller.mostraCardapioDia(dataFormatada);
		assertEquals(cardapioEsperado.getId(), resultado.getId());
		assertEquals(cardapioEsperado.getTotalCaloria(), resultado.getTotalCaloria(), 0);
		
	}
	
	@Test
	public void testaEnvioMensagemCardapioPublicado() throws ParseException {
		PratoDia cardapioEsperado = new PratoDia();
		cardapioEsperado.setId(10L);
		cardapioEsperado.setTotalCaloria(803);
		String data = "16/11/2015";
		Date dataFormatada = formatDate.parse(data);
		this.avaliacaoCardapioCOntroller.mostraCardapioDia(dataFormatada);
		assertTrue(result.included().containsKey("cardapioDia"));
	}
	
	@Test
	public void testaCardapioDoDiaNaoPublicado() throws ParseException {
		PratoDia valorEsperadoCardapio = null;
		String data = "01/01/1500";
		Date dataFormatada = formatDate.parse(data);
		PratoDia resultado =  this.avaliacaoCardapioCOntroller.mostraCardapioDia(dataFormatada);
		assertEquals(valorEsperadoCardapio, resultado);
	}
	
	@Test(expected=Exception.class)
	public void testaParametroDataCardapioDiaNull() throws ParseException {
		Date data =  null;
		this.avaliacaoCardapioCOntroller.mostraCardapioDia(data);
		assertTrue(result.included().containsKey("erro"));
	}
	
}

