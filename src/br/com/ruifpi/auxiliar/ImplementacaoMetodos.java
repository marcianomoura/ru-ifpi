package br.com.ruifpi.auxiliar;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.ruifpi.dao.DaoException;
import br.com.ruifpi.models.Funcionario;
import br.com.ruifpi.models.Usuario;
import br.com.ruifpi.util.JpaUtil;

@Stateless
public class ImplementacaoMetodos implements Metodos {
	
	@PersistenceContext(unitName="ruifpi")
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> buscaFuncionariosAtivos() {
		entityManager = JpaUtil.getSessionAtual();
		try {
			Query query = entityManager.createQuery("select funcionario from Funcionario funcionario WHERE funcionario.statusOperacional=false" );
			List<Funcionario> funcionarios = query.getResultList();
			return funcionarios;
		} catch (Exception e) {
			System.out.println("Erro na pesquisa de  funcionarios ...");
			throw new DaoException("Erro na pesquisa de funcionarios...");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> buscaUsuariosAtivos() {
		entityManager = JpaUtil.getSessionAtual();
		try {
			Query query = entityManager.createQuery("select usuario from Usuario usuario WHERE usuario.matriculado=true" );
			List<Usuario> usuarios = query.getResultList();
			return usuarios;
		} catch (Exception e) {
			System.out.println("Erro na pesquisa de  usuarios ...");
			throw new DaoException("Erro na pesquisa de usuario...");
		}
	}
	
}
