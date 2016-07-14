package br.com.ruifpi.testes;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.ruifpi.controllers.VotacaoController;
import br.com.ruifpi.models.SugestaoPrato;
import br.com.ruifpi.models.Votacao;
import junit.framework.TestCase;

public class VotacaoControllerTest extends TestCase{

	private MockResult result;
	private VotacaoController votacaoController;
	private Votacao votacao; 
	private SugestaoPrato sugestaoPrato;

	@Before
	public void setUp() {
		votacao = new Votacao();
		sugestaoPrato = new SugestaoPrato();
		result = new MockResult();
		votacaoController = new VotacaoController(result);
	}
	
	@Test
	public void testaAddVotacaoNull() throws Exception {
		votacaoController.addvoto(new Votacao());	// Erro
		assertTrue(result.included().containsKey("erro"));
	}
	
	@Test
	public void testaVotacaoUsuarioSessaoNull() throws Exception {
		sugestaoPrato.setId(1L);
		votacao.setSugestaoPrato(sugestaoPrato);
		votacao.setUsuario(null);	// Erro
		votacaoController.addvoto(votacao);
		assertTrue(result.included().containsKey("erro"));
		assertFalse(result.included().containsKey("sucesso"));
	}
	
	@Test
	public void testaDadosValidacaoVoto() throws Exception {
		boolean resultado = votacaoController.validaDadosVotacao(null);	// erro
		assertTrue(result.included().containsKey("erro"));
		assertFalse(resultado);
	}
	
}
