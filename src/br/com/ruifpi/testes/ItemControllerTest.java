package br.com.ruifpi.testes;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.controllers.ItemController;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.ClasseNutricional;
import br.com.ruifpi.models.Item;
import junit.framework.TestCase;

public class ItemControllerTest  extends TestCase{

	private DaoImplementacao dao;
	private Validator validator;
	private Result result;
	private ItemController itemcontroller;
	private Item item;
	
	@Before
	public void setUp() {
		dao = new DaoImplementacao();
		validator = new MockValidator();
		result = new MockResult();
		item = new Item();
		itemcontroller = new ItemController(validator, result, dao);
	}
	
	@Test
	public void testaDescricaoItemMenor5Caractees() {
		item.setDescricao("AAAA");	// Erro
		Item resultado = itemcontroller.validaDados(item);
		assertNull(resultado);
		assertTrue(validator.hasErrors());
	}
	
	@Test
	public void testaTotalCaloriaMenorZero() {
		item.setDescricao("AAAAA");
		item.setValorCalorico(-300);	// Erro
		Item resultado = itemcontroller.validaDados(item);
		assertEquals(null, resultado);
		assertTrue(validator.hasErrors());
	}
	
	@Test
	public void testaTotalCaloriaZero() {
		item.setDescricao("AAAAA");
		item.setValorCalorico(0d);	// Erro
		Item resultado = itemcontroller.validaDados(item);
		assertEquals(null, resultado);
		assertTrue(validator.hasErrors());
	}
	
	@Test
	public void testaDescricaoBeneficioASaudeMenor5() {
		item.setDescricao("AAAAA");
		item.setValorCalorico(200);
		item.setBeneficios("AAA");	// Erro
		Item resultado = itemcontroller.validaDados(item);
		assertEquals(null, resultado);
		assertTrue(validator.hasErrors());
		assertTrue(validator.getErrors().size() > 0);
	}
	
	@Test
	public void testaDescricaoMaleficiosPequena() {
		item.setDescricao("AAAAA");
		item.setValorCalorico(200);
		item.setBeneficios("AAAAA");	
		item.setMaleficios("AAA"); // Erro
		Item resultado = itemcontroller.validaDados(item);
		assertEquals(null, resultado);
		assertTrue(validator.hasErrors());
		assertTrue(validator.getErrors().size() > 0);
	}
	
	@Test
	public void testaClasseNutricionalNull() {
			
		item.setDescricao("AAAAA");
		item.setValorCalorico(200);
		item.setBeneficios("AAAAA");	
		item.setMaleficios("AAAAA");
		item.setClasseNutricional(null);	// Erro
		Item resultado = itemcontroller.validaDados(item);
		assertEquals(null, resultado);
		assertTrue(validator.getErrors().size() == 0);	// Não foi pega pelo validator e sim pelo try catch ...

	}
	
	@Test
	public void testaClasseNutricionalIdNaoInformado() {
		item.setDescricao("AAAAA");
		item.setValorCalorico(200);
		item.setBeneficios("AAAAA");	
		item.setMaleficios("AAAAA");
		item.setClasseNutricional(new ClasseNutricional());	// Erro
		Item resultado = itemcontroller.validaDados(item);
		assertEquals(null, resultado);
		assertTrue(validator.hasErrors());
		assertTrue(validator.getErrors().size() == 1);
	}
	
	@Test
	public void testaAlteracaoItemNull() {
		itemcontroller.alteraItem(null);
		assertTrue(result.included().containsKey("erro"));
	}
}






























