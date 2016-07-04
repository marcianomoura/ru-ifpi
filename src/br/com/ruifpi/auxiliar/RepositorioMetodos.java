package br.com.ruifpi.auxiliar;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.components.FuncionarioSession;
import br.com.ruifpi.components.UsuarioSession;
import br.com.ruifpi.dao.DaoException;
import br.com.ruifpi.models.AvaliacaoRefeicao;
import br.com.ruifpi.models.Funcionario;
import br.com.ruifpi.models.PratoDia;
import br.com.ruifpi.models.Usuario;
import br.com.ruifpi.models.Votacao;
import br.com.ruifpi.util.CriptografaSenhaUtil;
import br.com.ruifpi.util.JpaUtil;

@Stateless
public class RepositorioMetodos implements Repositorio {
	
	@PersistenceContext(unitName="ruifpi")
	private EntityManager entityManager;
	@Inject private Validator validator;
	@Inject	private FuncionarioSession funcionarioSession;
	@Inject private UsuarioSession usuarioSession;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> buscaFuncionariosAtivos() {
		entityManager = JpaUtil.getEntityAtual();
		try {
			Query query = entityManager.createQuery("select f from Funcionario f WHERE f.matriculado=true" );
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
	
	@SuppressWarnings("unchecked")
	public boolean autenticacaoFuncionario(Funcionario funcionario) {
		Funcionario funcionarioAutenticado = null;
		try {
			entityManager = JpaUtil.getEntityAtual();
			String senhaCriptografada = CriptografaSenhaUtil.criptografaSenha(funcionario);
			Query query = entityManager.createQuery("select f from Funcionario f where f.login=:login and f.senha=:senha and f.matriculado=true")
					.setParameter("login", funcionario.getLogin()).setParameter("senha", senhaCriptografada);
			List<Funcionario> funcionarios = query.getResultList();
			if(funcionarios.isEmpty()){
				validator.add(new I18nMessage("funcionario", "credenciais.nao.conferem"));
				return false;
			}else{
				funcionarioAutenticado = funcionarios.get(0);
				funcionarioSession.login(funcionarioAutenticado);
				return true;
			}
		} catch (Exception e) {
			throw new DaoException("Erro na autenticação do Funcionario ...");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean autenticacaoUsuario(Usuario usuario) {
		Usuario usuarioAutenticado = null;
		try {
			entityManager = JpaUtil.getEntityAtual();
			Query query = entityManager.createQuery("select u from Usuario u where u.login=:login and u.senha=:senha and u.matriculado=true")
					.setParameter("login", usuario.getLogin()).setParameter("senha", usuario.getSenha());
			List<Usuario> usuarios = query.getResultList();
			if(usuarios.isEmpty()){
				validator.add(new I18nMessage("usuario", "credenciais.nao.conferem"));
				return false;
			}else{
				usuarioAutenticado = usuarios.get(0);
				usuarioSession.login(usuarioAutenticado);
				return true;
			}
		} catch (Exception e) {
			throw new DaoException("Erro na autenticação do usuario ...");
		}
		
	}
	
}




