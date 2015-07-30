package br.com.ruifpi.controllers;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.AvaliacaoCardapio;
import br.com.ruifpi.models.Cardapio;
import br.com.ruifpi.util.RestricaoAcesso;
import br.com.ruifpi.util.RestricaoAcesso.AcessoUsuario;

@Controller
public class AvaliacaoController {

	@ Inject private Result result;
	@ Inject private DaoImplementacao daoImplementacao;
	
	@AcessoUsuario
	@RestricaoAcesso
	@Path("/avaliacao")
	public void listAvaliacaoCardapio() {
		listAvaliacoes(new Date());  
	}
	
	public Cardapio cardapioAvaliado() {
		// Mostra o cardapio que será avaliado pelo usuário android
		return null;
	}
	
	
	public List<AvaliacaoCardapio> listAvaliacoes(Date dataCardapio) {		
		// Retorna para a view da administração a lista de avaliações do cardapio do dia ... 
		return null;
	}
	
	public void saveAvaliacao(AvaliacaoCardapio avaliacaoCardapio, Long idUsuario) {
		// Salva a avaliação feita pelo Usuário e a identifica pelo seu id para posterior validação de uma avaliação do mesmo usuário
	}
	
	public boolean verificaAvaliaçãoRealizada(Date dataCardapio, Long idUsuario) {
			// Verifica pela data do cardápio e pelo id do usuário android, se este ja avaliou o cardapio em uma determinada data. 
		return false;
	}
	
	/*
	 * 
	 * 	Mais alguns métodos acessores aqui ...
	 * 
	 */
	
}







