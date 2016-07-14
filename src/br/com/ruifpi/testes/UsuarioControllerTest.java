package br.com.ruifpi.testes;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.ruifpi.controllers.UsuarioController;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Usuario;
import junit.framework.TestCase;

public class UsuarioControllerTest extends TestCase {

	private MockResult result;
	private MockValidator validator;
	private UsuarioController usuarioController;
	private Usuario usuario;
	private DaoImplementacao dao;
	
	@Before
	protected void setUp() throws Exception {
		this.usuario = new Usuario();
		this.dao = new DaoImplementacao();
		this.validator = new MockValidator();
		this.result = new MockResult();
		this.usuarioController = new UsuarioController(dao, result, validator);
	}
	
	@Test
	public void testaDataNascimentoUsuarioInvalida() throws Exception {
		usuario.setDataNascimento(null);
		boolean resultado = usuarioController.validaDadosCadastraisUsuario(usuario);
		assertEquals(false, resultado);
		assertTrue(validator.hasErrors());
		assertTrue(validator.getErrors().size() == 1);
	}
	
	@Test
	public void testaLoginUsuarioInvalido() throws Exception {
		usuario.setDataNascimento(new Date());
		usuario.setLogin(null);
		boolean resultado = usuarioController.validaDadosCadastraisUsuario(usuario);
		assertEquals(false, resultado);
		assertTrue(validator.getErrors().size() == 1);
	}
	
	@Test
	public void testaPerfilUsuarioinvalido() throws Exception {
		usuario.setDataNascimento(new Date());
		usuario.setLogin("loginUser");
		usuario.setPerfil(null);
		boolean resultado = usuarioController.validaDadosCadastraisUsuario(usuario);
		assertEquals(false, resultado);
		assertTrue(validator.getErrors().size() == 1);
	}
	
	@Test
	public void testaMatriculaUsuarioInvalida() throws Exception {
		usuario.setDataNascimento(new Date());
		usuario.setLogin("loginUser");
		usuario.setPerfil("Aluno");
		usuario.setMatricula(null);
		boolean resultado = usuarioController.validaDadosCadastraisUsuario(usuario);
		assertEquals(false, resultado);
		assertTrue(validator.getErrors().size() == 1);
	}
	
	@Test
	public void testaSenhaUsuarioInvalido() throws Exception {
		usuario.setDataNascimento(new Date());
		usuario.setLogin("loginUser");
		usuario.setPerfil("Aluno");
		usuario.setMatricula("20101ads0383");
		usuario.setSenha(null);
		boolean resultado = usuarioController.validaDadosCadastraisUsuario(usuario);
		assertEquals(false, resultado);
		assertTrue(validator.getErrors().size() == 1);
	}
	
	@Test
	public void testaErroNaVerificacaoUsuarioCadastrado() throws Exception {	
		boolean resultado = usuarioController.verificaUsuarioCadastrado(null);
		assertEquals(false, resultado);
		assertTrue(result.included().containsKey("erro"));
	}
	
	@Test
	public void testaErroUsuarioCadastroNull() throws Exception {
		usuarioController.verificaMatriculaValidaUsuario(new Usuario());
		result.included().containsKey("erro");
	}
	
	@Test
	public void testaErroDataNascimentoUsuarioCadastro() throws Exception {
		usuario.setMatricula("2010");
		usuario.setDataNascimento(null);
		usuarioController.verificaMatriculaValidaUsuario(usuario);
		result.included().containsKey("erro");	
	}
	
		
	
	
	
}
