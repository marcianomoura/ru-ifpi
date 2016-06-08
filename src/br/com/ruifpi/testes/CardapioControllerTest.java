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
import br.com.ruifpi.auxiliar.MetodosUtilImplementacao;
import br.com.ruifpi.controllers.CardapioController;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Cardapio;
import br.com.ruifpi.models.Item;
import br.com.ruifpi.models.ItemCardapio;
import junit.framework.TestCase;

public class CardapioControllerTest extends TestCase {

	private DaoImplementacao daoImplementacao;
	private MockResult result;
	private CardapioController cardapioController;
	private MockValidator validator;
	private Cardapio cardapioUtil;
	private SimpleDateFormat formatDatautil;
	private List<ItemCardapio> listaItemCardapioUtil;
	private ItemCardapio itemCardapioUtil;
	private MetodosUtilImplementacao metodosUtil;
	
	@Before
	public void setUp() {
		itemCardapioUtil = new ItemCardapio();
		listaItemCardapioUtil = new ArrayList<ItemCardapio>();
		daoImplementacao = new DaoImplementacao();
		formatDatautil = new SimpleDateFormat();
		result = new MockResult();
		cardapioUtil = new Cardapio();
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
	
	@Test
	public void testaSomaAcumuladaCaloriaItemCardapio() {
		
		itemCardapioUtil.setTotalCaloria(200);
		double resultadoAcumulado = cardapioController.somaCaloriaCardapio(itemCardapioUtil);
		assertEquals(200, resultadoAcumulado, 0);
		itemCardapioUtil.setTotalCaloria(200);
		resultadoAcumulado = cardapioController.somaCaloriaCardapio(itemCardapioUtil);
		assertEquals(400, resultadoAcumulado, 0);
		itemCardapioUtil.setTotalCaloria(200);
		resultadoAcumulado = cardapioController.somaCaloriaCardapio(itemCardapioUtil);
		assertEquals(600, resultadoAcumulado, 0);
		itemCardapioUtil.setTotalCaloria(600);
		resultadoAcumulado = cardapioController.subtraiCaloriaCardapio(itemCardapioUtil);		// Zerando a variavel estatica do controller.
		assertEquals(0, resultadoAcumulado, 0);
	}
	
	@Test
	public void testaSubtracaoAcumuladaCaloriaItemCardapio() {
		
		itemCardapioUtil.setTotalCaloria(600);
		double resultadoAposAdicao = cardapioController.somaCaloriaCardapio(itemCardapioUtil);
		assertEquals(600, resultadoAposAdicao, 0);
		itemCardapioUtil.setTotalCaloria(200);
		double resultadoAcumuladoAposSubtracao = cardapioController.subtraiCaloriaCardapio(itemCardapioUtil);
		assertEquals(400, resultadoAcumuladoAposSubtracao, 0);
		itemCardapioUtil.setTotalCaloria(100);
		resultadoAcumuladoAposSubtracao = cardapioController.subtraiCaloriaCardapio(itemCardapioUtil);
		assertEquals(300, resultadoAcumuladoAposSubtracao, 0);
		itemCardapioUtil.setTotalCaloria(300);
		resultadoAcumuladoAposSubtracao = cardapioController.subtraiCaloriaCardapio(itemCardapioUtil);		// Zerando a variavel estatica do controller.
		assertEquals(0, resultadoAcumuladoAposSubtracao, 0);
	}
	
	@Test
	public void testaListaDeItensCardapioNaoVazia() {
		itemCardapioUtil.setCardapio(new Cardapio());
		itemCardapioUtil.setId(1L);
		itemCardapioUtil.setItem(new Item());
		itemCardapioUtil.setTotalCaloria(300);
		listaItemCardapioUtil.add(itemCardapioUtil);
		cardapioController.setListItens(listaItemCardapioUtil);
		boolean resultado = cardapioController.verificaListaItensVazia();
		assertTrue(resultado);
		cardapioController.limpaDadosCardapio();
	}
	
	@Test
	public void testaListaItensCardapioVazia() {
		List<ItemCardapio> lista = new ArrayList<ItemCardapio>();
		cardapioController.setListItens(lista);
		boolean resultado = cardapioController.verificaListaItensVazia();
		assertFalse(resultado);
	}
	
	@Test
	public void testaRemocaoItemCardapioDaLista() {
		
		itemCardapioUtil.setCardapio(new Cardapio());
		itemCardapioUtil.setId(1L);
		Item item = new Item();
		item.setId(1L);
		itemCardapioUtil.setItem(item);
		listaItemCardapioUtil.add(itemCardapioUtil);
		cardapioController.setListItens(listaItemCardapioUtil);
		int resultadoAntesRemocao = cardapioController.getListaItens().size();
		assertEquals(1, resultadoAntesRemocao);
		cardapioController.removeItem(1L);
		int resultadoAposRemocao = cardapioController.getListaItens().size();  
		assertEquals(0, resultadoAposRemocao);
	}
	
	@Test
	public void testaRemoverDaListaItemNull() {
		itemCardapioUtil.setCardapio(new Cardapio());
		itemCardapioUtil.setId(1L);
		listaItemCardapioUtil.add(itemCardapioUtil);
		cardapioController.setListItens(listaItemCardapioUtil);
		cardapioController.removeItem(1L);
		assertTrue(result.included().containsKey("erro"));	// NullpointException ... entrou no catch.
	}
}

