package br.com.ruifpi.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.auxiliar.ImplementacaoMetodos;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Funcionario;
import br.com.ruifpi.util.RestricaoAcesso;
import br.com.ruifpi.util.RestricaoAcesso.AcessoAdministrativo;


@Controller
public class FuncionarioController {
	
	@Inject	private DaoImplementacao daoImplementacao;
	@Inject private Result result;
	@Inject private Validator validator;
	@Inject private ImplementacaoMetodos implementacaoMetodos;
	
	@Path("/funcionario")
	public void formFuncionario() {
		result.include("funcionarios", listaFuncionarios());
	}
		
	@RestricaoAcesso
	@Path("/funcionario/save")
	public void save(Funcionario funcionario) {
		try {
			if(validaDadosFuncionario(funcionario) == null){
				validator.onErrorRedirectTo(this).formFuncionario();
			}else{
				if(verificaDadosFuncionarios(funcionario) == null){	// Login e senha disponíveis para cadastro...
					daoImplementacao.save(funcionario);
					result.include("sucesso", "Dados inserido com sucesso.");
					result.redirectTo(this).formFuncionario();
				}else{
					if(verificaDadosFuncionarios(funcionario).getId().equals(funcionario.getId())){	// Quando for alteração de dados ...	
						daoImplementacao.save(funcionario);
						result.include("sucesso", "Alteração realizada com sucesso.");
						result.redirectTo(this).formFuncionario();
					}else{
						validator.add(new I18nMessage("login",  "login.senha.invalidos"));
						validator.onErrorRedirectTo(this).formFuncionario();
					}
				}
			}
		} catch (Exception e) {
			result.include("erro", "Erro na inserção dos dados. Verifique as informações e tente novamente.");
			result.redirectTo(this).formFuncionario();
		}
	}
	
	@AcessoAdministrativo
	@RestricaoAcesso
	@Get("/funcionario/alteracao")
	public void alteracaoDados(Long id) {
		try {
			Funcionario funcionario = (Funcionario) daoImplementacao.findById(Funcionario.class, id);
			result.include("funcionario", funcionario);
			result.redirectTo(this).formFuncionario();
		} catch (Exception e) {
			
		}
	}
	
	@AcessoAdministrativo
	@RestricaoAcesso
	@Path("/funcionario/remocao")
	public void removerFuncionario(Long id) {
		try {
			Funcionario funcionario = (Funcionario) daoImplementacao.findById(Funcionario.class, id);
			funcionario.setStatusOperacional(true);
			daoImplementacao.save(funcionario);
			result.include("sucesso", "Funcionario removido.");
			result.redirectTo(this).formFuncionario();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na remoção do Funcionario.");
			result.redirectTo(this).formFuncionario();
		}
	}
	
	// Faz a verificação se o login e senha usados no cadastro estão disponiveis
	@SuppressWarnings("unchecked")
	@RestricaoAcesso
	public Funcionario verificaDadosFuncionarios(Funcionario funcionario) {
		Funcionario  funcionarioEncontrado = null;
		List<Funcionario> funcionarios = daoImplementacao.find(Funcionario.class);
		for (Funcionario funcionarioBanco : funcionarios) {
			if(funcionarioBanco.getLogin().equals(funcionario.getLogin()) && funcionarioBanco.getSenha().equals(funcionario.getSenha())){
				funcionarioEncontrado = funcionarioBanco;
				break;
			}
		}
		return funcionarioEncontrado;
	}
	
	@RestricaoAcesso
	public List<Funcionario> listaFuncionarios() {
		return implementacaoMetodos.buscaFuncionariosAtivos();
	}
	
	// Valida a consistência dos dados do formulário.
	@RestricaoAcesso
	public Funcionario validaDadosFuncionario(Funcionario funcionario) {				
		if(funcionario.getNome() == null){
			validator.add(new I18nMessage("nome", "funcionario.nome.vazio"));
			return null;
		}else if(funcionario.getMatricula() == null){
			validator.add(new I18nMessage("matricula", "funcionario.matricula.vazio"));
			return null;		
		}else if(funcionario.getLogin() == null){
			validator.add(new I18nMessage("login", "funcionario.login.vazio"));
			return null;
		}else if(funcionario.getSenha() == null){
			validator.add(new I18nMessage("senha", "funcionario.senha.vazio"));
			return null;
		}else{	// está tudo ok.
			return funcionario;
		}
	}
}

