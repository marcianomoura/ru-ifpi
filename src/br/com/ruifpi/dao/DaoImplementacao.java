package br.com.ruifpi.dao;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.ruifpi.util.JpaUtil;

@Stateless
public class DaoImplementacao implements Dao, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="ruifpi")
	private EntityManager entityManager;

	@Override
	public Object save(Object parametro) {
		entityManager = JpaUtil.getEntityAtual();
		try {
			Object object = entityManager.merge(parametro);
			return object;
		} catch (Exception e) {
			e.getStackTrace();
			throw new DaoException("Erro na inserção do objeto");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List find(Class classe) {
		List objects = null;
		try {
			objects = findByIntervalo(classe, -1, -1);
			return objects;
		} catch (Exception e) {
			e.getStackTrace();
			throw new DaoException("Erro na pesquisa de objetos.");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List findByIntervalo(Class classe, int primeiroResultado, int maximoResultado) {
		List objects = null;
		entityManager = JpaUtil.getEntityAtual();
		try {
			Query query = entityManager.createQuery("select objeto from "+ classe.getSimpleName() +" objeto");
			if((primeiroResultado >=0) && (maximoResultado >= 0)){
				query = query.setFirstResult(primeiroResultado).setMaxResults(maximoResultado);
			}
			objects = query.getResultList();
			return objects;
		} catch (Exception e) {
			e.getStackTrace();
			throw new DaoException("Erro na pesquisa por intervalo.");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object findById(Class classe, Object parametro) {
		entityManager = JpaUtil.getEntityAtual();
		try {
			Object object = entityManager.find(classe, parametro);
			return object;
		} catch (Exception e) {
			e.getStackTrace();
			throw new DaoException("Erro na pesquisa por Id.");
		}
	}

	@Override
	public void remove(Object parametro) {
		entityManager = JpaUtil.getEntityAtual();
		try {
			entityManager.remove(entityManager.merge(parametro));
		} catch (Exception e) {
			e.getStackTrace();
			throw new DaoException("Erro na remoção do objeto.");
		}
		
	}
}
