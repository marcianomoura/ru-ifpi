package br.com.ruifpi.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.auxiliar.RepositorioMetodos;
import br.com.ruifpi.components.FuncionarioSession;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Funcionario;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;
import br.com.ruifpi.util.CriptografaSenhaUtil;


@Controller
public class FuncionarioController {
	
	@Inject	private DaoImplementacao daoImplementacao;
	@Inject private Result result;
	@Inject private Validator validator;
	@Inject private RepositorioMetodos repositorio;
	@Inject private FuncionarioSession funcionarioSession;
	
	@AcessoAdministrativo
	@Path("/funcionario")
	public void formFuncionario() {
		result.include("funcionarios", listaFuncionarios());
	}
	
	@AcessoAdministrativo
	@Path("/funcionario/save")
	public void save(Funcionario funcionario) {
		if(validaDadosFuncionario(funcionario) == null){
			validator.onErrorRedirectTo(this).formFuncionario();
		}else{
			try {
				funcionario.setMatriculado(true);
				funcionario.setSenha(CriptografaSenhaUtil.criptografaSenha(funcionario.getSenha()));
				daoImplementacao.save(funcionario);
				result.include("sucesso", "Dados inserido com sucesso.");
				result.redirectTo(this).formFuncionario();
			} catch (Exception e) {
				result.include("erro", "Erro na inserção dos dados. Verifique as informações e tente novamente.");
				result.redirectTo(this).formFuncionario();
			}
		}
	}
	
	@AcessoAdministrativo
	public boolean verificaTentativaAlteracao(Funcionario funcionario) {
		if(funcionario.getId().equals(funcionarioSession.getFuncionario().getId())){
			return true;
		}else{
			return false;
		}
	}
	
	@AcessoAdministrativo
	@Get("/funcionario/alteracao")
	public void alteracaoDados(Long id) {
		try {
			Funcionario funcionario = (Funcionario) daoImplementacao.findById(Funcionario.class, id);
			if(verificaTentativaAlteracao(funcionario)){
				result.include("funcionario", funcionario);
			}else{
				result.include("erro", "Sem permissão de alterar os dados desse usuário.");
			}
			result.redirectTo(this).formFuncionario();
		} catch (Exception e) {
			result.include("erro", "Erro na tentativa de alterar os dados do funcionario");
			result.redirectTo(this).formFuncionario();
		}
	}
	
	@AcessoAdministrativo
	@Path("/funcionario/remocao")
	public void removerFuncionario(Long id) {
		try {
			if(funcionarioSession.getFuncionario().getFuncao().equals("Cordenador")){
				Funcionario funcionario = (Funcionario) daoImplementacao.findById(Funcionario.class, id);
				funcionario.setMatriculado(false);
				daoImplementacao.save(funcionario);
				result.include("sucesso", "Funcionario removido.");
			}else{
				result.include("erro", "Sem autorização para esta funcionalidade.");
			}
			result.redirectTo(this).formFuncionario();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na remoção do Funcionario.");
			result.redirectTo(this).formFuncionario();
		}
	}
	
	@AcessoAdministrativo
	public List<Funcionario> listaFuncionarios() {
		return repositorio.buscaFuncionariosAtivos();
	}
	

	@AcessoAdministrativo
	public Funcionario validaDadosFuncionario(Funcionario funcionario) {
		
		if(funcionario.getNome().length() < 6){
			validator.add(new I18nMessage("nome", "funcionario.nome.pequeno"));
			return null;
		}
		if(funcionario.getMatricula().length() < 6){
			validator.add(new I18nMessage("matricula", "funcionario.matricula.pequeno"));
			return null;		
		}
		if(funcionario.getLogin() == null){
			validator.add(new I18nMessage("login", "funcionario.login.vazio"));
			return null;
		}
		if(funcionario.getSenha() == null){
			validator.add(new I18nMessage("senha", "funcionario.senha.vazio"));
			return null;
		}
		if(funcionario.getFuncao().equals("Cordenador") && !funcionarioSession.getFuncionario().getFuncao().equals("Cordenador")){
			validator.add(new I18nMessage("Função", "funcionario.sem.autorizacao"));
			return null;
		}
		return funcionario;
	}
	

}