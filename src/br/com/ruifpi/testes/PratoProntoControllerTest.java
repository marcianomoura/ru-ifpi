package br.com.ruifpi.testes;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.ruifpi.controllers.PratoProntoController;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.PratoPronto;
import junit.framework.TestCase;

public class PratoProntoControllerTest extends TestCase{

	private DaoImplementacao dao;
	private MockValidator validator;
	private MockResult result;
	private PratoProntoController pratoProntoController;
	private PratoPronto pratoPronto;
	
	@Before
	public void setUp() {
		this.validator = new MockValidator();
		this.result = new MockResult();
		this.dao = new DaoImplementacao();
		this.pratoProntoController = new PratoProntoController(dao, validator, result);
		this.pratoPronto = new PratoPronto();
	}
	
	
	@Test
	public void testaExcecaoAlterarPratoProntoNull() throws Exception {
		try {
			pratoProntoController.alteracaoPratoPronto(null);
			Assert.fail();
		} catch (Exception e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testaDescricaoTituloPratoMenor5() {
		this.pratoPronto.setTituloPrato("AAAA");
		boolean resultado =  this.pratoProntoController.validaDadosPrato(pratoPronto);
		assertFalse(resultado);
	}
	
	@Test
	public void testaDataInicialInvalidaPesquisaCardapioSemanal() throws Exception {
		pratoProntoController.buscaCardapiosSemana(null, new Date());
		assertTrue(result.included().containsKey("erro"));
	}
	
	@Test
	public void testaDataFinalInvalidaPesquisaCardapioSemanal() throws Exception {
		pratoProntoController.buscaCardapiosSemana(new Date(), null);
		assertTrue(result.included().containsKey("erro"));
	}
	
	@Test
	public void testaDetalhesItensPrato() throws Exception {
		pratoProntoController.detalhesItensPrato(null);
		assertTrue(result.included().containsKey("erro"));
		assertFalse(result.included().containsKey("listItensPratoPronto"));
	}
	
}












