package br.com.ruifpi.testes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.ruifpi.auxiliar.MetodosUtilImplementacao;
import br.com.ruifpi.controllers.CardapioController;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.PratoDia;
import junit.framework.TestCase;

public class CardapioControllerTest extends TestCase {

	private DaoImplementacao daoImplementacao;
	private MockResult result;
	private CardapioController cardapioController;
	private MockValidator validator;
	private PratoDia cardapioUtil;
	private SimpleDateFormat formatDatautil;
	private MetodosUtilImplementacao metodosUtil;
	
	@Before
	public void setUp() {

		daoImplementacao = new DaoImplementacao();
		formatDatautil = new SimpleDateFormat();
		result = new MockResult();
		cardapioUtil = new PratoDia();
		validator = new MockValidator();
		metodosUtil = new MetodosUtilImplementacao();
		cardapioController = new CardapioController(daoImplementacao, validator, result, metodosUtil);
	}
	
	@Test
	public void testaTotalCaloriaCardapioMenorZero() {
		cardapioUtil.setTotalCaloria(-200);
		cardapioUtil.setDataCardapio(new Date());		
		boolean valorEsperado = cardapioController.validaDadosCardapio(cardapioUtil);
		assertFalse(valorEsperado);
	}
	
	@Test
	public void testaTotalCaloriaCardapioIgualZero() {
		cardapioUtil.setTotalCaloria(0);
		cardapioUtil.setDataCardapio(new Date());		
		boolean valorEsperado = cardapioController.validaDadosCardapio(cardapioUtil);
		assertFalse(valorEsperado);
	}
	
	@Test
	public void testaDataCardapioNula_e_MensagemErro() {
		cardapioUtil.setTotalCaloria(200);
		cardapioUtil.setDataCardapio(null);
		boolean valorEsperado =  cardapioController.validaDadosCardapio(cardapioUtil);
		assertFalse(valorEsperado);
		assertTrue(result.included().containsKey("erro"));
	}
	
	@Test
	public void testaDataPublicacaoCardapioAnteriorAHoje() throws ParseException {
		String dataAnterior = "12/12/2000";
		formatDatautil.applyPattern("dd/MM/yyyy");
		cardapioUtil.setDataCardapio(formatDatautil.parse(dataAnterior));
		cardapioUtil.setTotalCaloria(200);
		boolean valorEsperado = cardapioController.validaDadosCardapio(cardapioUtil);
		assertFalse(valorEsperado);
	}
	
	@Test
	public void testaCardapioDataHojeJaPublicado() throws ParseException {
		String dataAnterior = "24/11/2015";	// Data refere-se a uma data que já existe cardapio publicado ...
		formatDatautil.applyPattern("dd/MM/yyyy");
		cardapioUtil.setDataCardapio(formatDatautil.parse(dataAnterior));
		boolean valorEsperado = cardapioController.verificaCardapioDia(cardapioUtil);
		assertTrue(valorEsperado);
	}
	
	@Test
	public void testaCardapioDataHojeDataNula() throws ParseException {
		cardapioUtil.setDataCardapio(null);
		cardapioUtil.setId(1L);
		boolean valorEsperado = cardapioController.verificaCardapioDia(cardapioUtil);
		assertFalse(valorEsperado);
		assertTrue(result.included().containsKey("erro"));
	}

	
}

