package br.com.ruifpi.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.auxiliar.MetodosUtilImplementacao;
import br.com.ruifpi.components.UsuarioSession;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Votacao;
import br.com.ruifpi.util.ControleAcesso;
import br.com.ruifpi.util.ControleAcesso.AcessoUsuario;

@Controller
public class VotacaoController {
	
	@Inject
	private DaoImplementacao dao;
	@Inject
	private Result result;
	
	@Inject
	private UsuarioSession usuarioSession;
	@Inject
	private MetodosUtilImplementacao metodosUtilImplementacao;
	
	@AcessoUsuario
	@Path("/votacao/contabiliza")
	public void addvoto(Votacao votacao) {
		
		try {
			if(validaDadosVotacao(votacao.getSugestaoPrato().getId())){
				result.include("erro", "Usuário já participou da votação.");
			}else{
				votacao.setUsuario(usuarioSession.getUsuario());
				dao.save(votacao);
				result.include("sucesso", "Voto computado com sucesso");
			}
			result.redirectTo(SugestaoController.class).listSugestaoCardapio();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro no processo de votação. Tente mais tarde");
			result.redirectTo(SugestaoController.class).listSugestaoCardapio();
		}
	}

	public boolean validaDadosVotacao(Long id) {
		boolean votoEncontrado = false;
		List<Votacao> votacoes = metodosUtilImplementacao.pesquisaVotacaoById(id);		// Votos referentes ao sugestao de cardápio fornecido pelo ID.
		System.out.println("Quantidades de Votos da lista de Votação" + votacoes.size());
		for (Votacao voto : votacoes) {
			if(voto.getUsuario().getId().equals(usuarioSession.getUsuario().getId())){
				votoEncontrado = true;
				break;
			}
		}
		return votoEncontrado;
	}
}
