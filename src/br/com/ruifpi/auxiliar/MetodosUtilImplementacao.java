package br.com.ruifpi.auxiliar;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.ruifpi.dao.DaoException;
import br.com.ruifpi.models.AvaliacaoRefeicao;
import br.com.ruifpi.models.Funcionario;
import br.com.ruifpi.models.PratoDia;
import br.com.ruifpi.models.Usuario;
import br.com.ruifpi.models.Votacao;
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
	public List<PratoDia> buscaCardapioDataSolicitada(java.sql.Date dataCardapio) {
		List<PratoDia> pratosEncontrados = new ArrayList<>();
		entityManager = JpaUtil.getEntityAtual();
			try {
				Query query = entityManager.createQuery("select pratoDia from PratoDia pratoDia");
				List<PratoDia> pratoDias = query.getResultList();
				for (PratoDia pratoDia : pratoDias) {
					if(pratoDia.getDataCardapio().equals(dataCardapio)){
						pratosEncontrados.add(pratoDia);
					}
				}
				return pratosEncontrados;
			} catch (Exception e) {
				throw new DaoException("Erro na pesquisa do cardapio");
			}		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PratoDia> buscaCardapioPeriodoSelecionado(Date periodoInicial, Date periodoFinal) {
		entityManager = JpaUtil.getEntityAtual();
		try {
			Query query = entityManager.createQuery("select pratodia from PratoDia pratodia where pratodia.dataCardapio between :periodoInicial and :periodoFinal");
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
	
	@SuppressWarnings("unchecked")
	public List<Votacao> pesquisaVotacaoById(Long id) {
		entityManager = JpaUtil.getEntityAtual();
		try {
			Query query = entityManager.createQuery("select votacao from Votacao votacao where votacao.sugestaoPrato.id=" + id);
			List<Votacao> listaVotos = query.getResultList();
			return listaVotos;
		} catch (Exception e) {
			throw new DaoException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AvaliacaoRefeicao> buscaAvaliacaoDeUmCardapio(Long id) {
		entityManager = JpaUtil.getEntityAtual();
		try {
			Query query = entityManager.createQuery("select avaliacao from AvaliacaoRefeicao avaliacao where avaliacao.pratoDia.id=" + id);
			List<AvaliacaoRefeicao> listaAvaliacoes = query.getResultList();
			return listaAvaliacoes;
		} catch (Exception e) {
			throw new DaoException();
		}
	}
	
}




