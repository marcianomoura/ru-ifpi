package br.com.ruifpi.auxiliar;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.ruifpi.dao.DaoException;
import br.com.ruifpi.models.Cardapio;
import br.com.ruifpi.models.Disponibilidade;
import br.com.ruifpi.models.Funcionario;
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
			System.out.println("Erro na pesquisa de  funcionarios ...");
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
			System.out.println("Erro na pesquisa de  usuarios ...");
			throw new DaoException("Erro na pesquisa de usuario...");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cardapio buscaCardapioDataSolicitada(java.sql.Date dataCardapio) {
		Cardapio cardapioEncontrado = null;
		entityManager = JpaUtil.getEntityAtual();
			try {
				Query query = entityManager.createQuery("select cardapio from Cardapio cardapio WHERE cardapio.dataCardapio="+ dataCardapio);
				List<Cardapio> cardapios = query.getResultList();
				for (Cardapio cardapio : cardapios) {
					if(cardapio.getDataCardapio().equals(cardapio)){
						cardapioEncontrado = cardapio;
						break;
					}
				}
			} catch (Exception e) {
				throw new DaoException("Erro na pesquisa do cardapio");
			}	
		return cardapioEncontrado;
	}
	

	@SuppressWarnings("unchecked")
	public List<Disponibilidade> buscaDatasDisponiveisSugestaoCardapio() {
		entityManager = JpaUtil.getEntityAtual();
		try {
			Query query = entityManager.createQuery("select disponibilidade from Disponibilidade disponibilidade WHERE disponibilidade.disponivel=false");
			List<Disponibilidade> disponibilidades = query.getResultList();
			return disponibilidades;
		} catch (Exception e) {
			System.out.println("Erro na pesquisa de  usuarios ...");
			throw new DaoException("Erro na pesquisa de usuario...");
		}
	}
	
}
