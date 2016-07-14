package br.com.ruifpi.testes;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.ruifpi.auxiliar.RepositorioMetodos;
import br.com.ruifpi.controllers.AvaliacaoController;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.AvaliacaoRefeicao;
import br.com.ruifpi.models.TipoPrato;
import br.com.ruifpi.models.Usuario;
import junit.framework.TestCase;

public class AvaliacaoControlTest extends TestCase{
	
	private MockValidator validator;
	private MockResult result;
	private AvaliacaoController avaliacaoCardapioController;
	private DaoImplementacao daoimplementacao;
	private List<AvaliacaoRefeicao> avaliacoes;
	private AvaliacaoRefeicao avaliacaoCardapio1;
	private AvaliacaoRefeicao avaliacaoCardapio2;
	private TipoPrato tipoPrato;
	private RepositorioMetodos repositorioMetodos;
	
	@Before
	public void setUp() {
		daoimplementacao = new DaoImplementacao();
		result = new MockResult();
		avaliacaoCardapio1 = new AvaliacaoRefeicao();
		avaliacaoCardapio2 = new AvaliacaoRefeicao();
		tipoPrato = new TipoPrato();
		validator = new MockValidator();
		avaliacaoCardapioController = new AvaliacaoController(result,validator,daoimplementacao, repositorioMetodos);
		avaliacoes = new ArrayList<AvaliacaoRefeicao>();
	}
	
	@Test
	public void testaMediaDasAvaliacoesValoresPositivos() {
		double valorEsperado = 8.0;
		avaliacaoCardapio1.setNotaAvaliativa(10);
		avaliacaoCardapio2.setNotaAvaliativa(6);
		avaliacoes.add(avaliacaoCardapio1);
		avaliacoes.add(avaliacaoCardapio2);
		double resultado = this.avaliacaoCardapioController.calculaMediaAvaliacao(avaliacoes);
		assertEquals(valorEsperado, resultado);		
	}
		
	@Test
	public void testaMediaDasAvaliacoesValoresNegativo() {		
		avaliacaoCardapio1.setNotaAvaliativa(-2);
		avaliacaoCardapio2.setNotaAvaliativa(-4);
		avaliacoes.add(avaliacaoCardapio1);
		avaliacoes.add(avaliacaoCardapio2);
		double resultado = this.avaliacaoCardapioController.calculaMediaAvaliacao(avaliacoes);
		assertEquals(0, resultado, 0);
	}
	
	@Test
	public void testaMediaDasAvaliacoesValoresMistos() {
		avaliacaoCardapio1.setNotaAvaliativa(-2);
		avaliacaoCardapio2.setNotaAvaliativa(6);
		avaliacoes.add(avaliacaoCardapio1);
		avaliacoes.add(avaliacaoCardapio2);
		double resultado = this.avaliacaoCardapioController.calculaMediaAvaliacao(avaliacoes);
		assertEquals(6, resultado, 0);
	}
		
	@Test
	public void testaErroListagemAvaliacoesDataInvalida() {
		avaliacaoCardapioController.listaAvaliacoesCardapioByData(null);
		assertTrue(result.included().containsKey("erro"));
	}
	
	@Test(expected=NullPointerException.class)
	public void testaPesquisaAvaliacoesByIdPratoDIaNull() {
		List<AvaliacaoRefeicao> avaliacoes = new ArrayList<>();
		try {
			avaliacoes = repositorioMetodos.buscaAvaliacaoDeUmCardapio(null);
			fail();
		} catch (Exception e) {
			assertTrue(avaliacoes.isEmpty());
		}	
	}
	
	@Test
	public void testaNotaAvalicaoIgualZero() {
		this.avaliacaoCardapio1 = new AvaliacaoRefeicao();
		this.avaliacaoCardapio1.setNotaAvaliativa(0);	
		assertFalse(this.avaliacaoCardapioController.verificaNotaAvaliativa(avaliacaoCardapio1));
	}
	
	@Test
	public void testaNotaAvaliativaMaiorDez() {
		this.avaliacaoCardapio1 = new AvaliacaoRefeicao();
		this.avaliacaoCardapio1.setNotaAvaliativa(11);	
		assertFalse(this.avaliacaoCardapioController.verificaNotaAvaliativa(avaliacaoCardapio1));
	}
	
	@Test
	public void testaAvaliacaoTipoPratoNull() {
		this.tipoPrato.setId(null);
		boolean valoresperado = avaliacaoCardapioController.validaHorarioAvaliacao(tipoPrato);
		avaliacaoCardapioController.validaHorarioAvaliacao(tipoPrato);
		assertTrue(result.included().containsKey("erro"));
		assertFalse(valoresperado);	
	}
	
	@Test(expected = NullPointerException.class)
	public void testaVerificaAvaliacaoSubmetidaUsuarioIdNUll() {
		this.avaliacaoCardapio1.setUsuario(new Usuario());
		boolean resultado = true;
		try {
			resultado = avaliacaoCardapioController.verificaAvaliacaoJaSubmetida(avaliacaoCardapio1);
			assertFalse(resultado);
			assertEquals(false, resultado);
			fail();
		} catch (Exception e) {	
			
		}	
	}	
	
	
}

