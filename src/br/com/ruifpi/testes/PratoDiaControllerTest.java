package br.com.ruifpi.testes;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.ruifpi.controllers.PratoDiaController;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.PratoDia;
import br.com.ruifpi.models.PratoPronto;
import br.com.ruifpi.models.Sobremesa;
import br.com.ruifpi.models.TipoPrato;
import junit.framework.TestCase;

public class PratoDiaControllerTest extends TestCase {

	private DaoImplementacao dao;
	private MockResult result;
	private MockValidator validator;
	private PratoDiaController pratoDiaController;
	private PratoDia pratoDia;
	private PratoPronto pratoPronto;
	private Sobremesa sobremesa;
	private TipoPrato tipoPrato;
	
	@Before
	public void setUp() {
		validator = new MockValidator();
		dao = new DaoImplementacao();
		result = new MockResult();
		pratoDia = new PratoDia();
		pratoPronto = new PratoPronto();
		sobremesa = new Sobremesa();
		tipoPrato = new TipoPrato();
		pratoDiaController = new PratoDiaController(validator, dao, result);
	}
		
	@Test
	public void testaPratoDiaComPratoProntoNull() throws Exception {
		this.pratoDia.setPratoPronto(new PratoPronto());	//	Erro. Id é igual a null.
		boolean resultado = pratoDiaController.validaDadosPratoDia(pratoDia);
		assertEquals(false, resultado);
		assertTrue(validator.hasErrors());
		assertTrue(validator.getErrors().size() > 0);
	}
	
	@Test
	public void testaPratoDiaComTipoPratoNull() throws Exception {
		pratoPronto.setId(1L);
		pratoDia.setPratoPronto(pratoPronto);
		this.pratoDia.setTipoPrato(new TipoPrato());
		boolean resultado = pratoDiaController.validaDadosPratoDia(pratoDia);
		assertEquals(false, resultado);
		assertTrue(validator.hasErrors());
		assertEquals(1, validator.getErrors().size());
	}
	
	@Test
	public void testaPratoDiaComSobremesaNull() throws Exception {
		pratoPronto.setId(1L);
		tipoPrato.setId(1L);
		pratoDia.setPratoPronto(pratoPronto);
		pratoDia.setTipoPrato(tipoPrato);
		this.pratoDia.setSobremesa(sobremesa);
		boolean resultado = pratoDiaController.validaDadosPratoDia(pratoDia);
		assertEquals(false, resultado);
		assertTrue(validator.hasErrors());
		assertEquals(1, validator.getErrors().size());
	}
	
	@Test
	public void testaListagemItensPratoDiaIdNull() throws Exception {
		pratoDiaController.listItensPratoDia(null);
		assertTrue(result.included().containsKey("erro"));
		assertFalse(result.included().containsKey("itensCardapio"));
	}
	
	@Test
	public void testaListagemItensPratoDiaIdMenorZero() throws Exception {
		pratoDiaController.listItensPratoDia(-2L);
		assertTrue(result.included().containsKey("erro"));
		assertFalse(result.included().containsKey("itensCardapio"));
	}
	
	
	@Test
	public void testaAlteracaoPratoDiaIdNull() throws Exception {
		pratoDiaController.alteraCardapioDia(null);
		assertTrue(result.included().containsKey("erro"));
		assertFalse(result.included().containsKey("listItemPratoPronto"));
		assertFalse(result.included().containsKey("pratoDia"));
	}
}

