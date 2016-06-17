package br.com.ruifpi.auxiliar;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.ruifpi.dao.DaoException;
import br.com.ruifpi.models.Funcionario;
import br.com.ruifpi.models.PratoDia;
import br.com.ruifpi.models.Usuario;
import br.com.ruifpi.util.JpaUtil;

@Stateless
public class MetodosUtilImplementacao implements MetodosUtil {
	
	@PersistenceContext(unitName="ruifpi")
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> buscaFuncionariosAtivos() {
		entityManager = JpaUtil.getEntityAtual();
		try {
			Query query = entityManager.createQuery("select funcionario from Funcionario funcionario WHERE funcionario.statusOperacional=false" );
			List<Funcionario> funcionarios = query.getResultList();
			return funcionarios;
		} catch (Exception e) {
			throw new DaoException("Erro na pesquisa de funcionarios...");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> buscaUsuariosAtivos() {
		entityManager = JpaUtil.getEntityAtual();
		try {
			Query query = entityManager.createQuery("select usuario from Usuario usuario WHERE usuario.matriculado=true");
			List<Usuario> usuarios = query.getResultList();
			return usuarios;
		} catch (Exception e) {
			throw new DaoException("Erro na pesquisa de usuario...");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PratoDia buscaCardapioDataSolicitada(java.sql.Date dataCardapio) {
		PratoDia cardapioEncontrado = null;
		entityManager = JpaUtil.getEntityAtual();
			try {
				Query query = entityManager.createQuery("select cardapio from Cardapio cardapio WHERE cardapio.dataCardapio="+ dataCardapio);
				List<PratoDia> pratoDias = query.getResultList();
				for (PratoDia pratoDia : pratoDias) {
					if(pratoDia.getDataCardapio().equals(pratoDia)){
						cardapioEncontrado = pratoDia;
						break;
					}
				}
			} catch (Exception e) {
				throw new DaoException("Erro na pesquisa do cardapio");
			}	
		return cardapioEncontrado;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PratoDia> buscaCardapioPeriodoSelecionado(Date periodoInicial, Date periodoFinal) {
		entityManager = JpaUtil.getEntityAtual();
		try {
			Query query = entityManager.createQuery("select cardapio from Cardapio cardapio where cardapio.dataCardapio between :periodoInicial and :periodoFinal");
			query.setParameter("periodoInicial", periodoInicial);
			query.setParameter("periodoFinal", periodoFinal);
			query.setFirstResult(0);	// Inicia do index 0 da lista ...
			query.setMaxResults(5);		// Lista apenas 5 resultados ...
			List<PratoDia> cardapioPeriodo = query.getResultList();
			return cardapioPeriodo;
		} catch (Exception e) {
			throw new DaoException("Erro na pesquisa de cardapio por periodo");
		}

	}
	
}
