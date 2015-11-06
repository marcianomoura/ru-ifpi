package br.com.ruifpi.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.sun.crypto.provider.DESCipher;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.components.ListItemSugerido;
import br.com.ruifpi.components.UsuarioSession;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Disponibilidade;
import br.com.ruifpi.models.Item;
import br.com.ruifpi.models.ItemSugestaoCardapio;
import br.com.ruifpi.models.SugestaoCardapio;
import br.com.ruifpi.util.RestricaoAcesso;
import br.com.ruifpi.util.RestricaoAcesso.AcessoUsuario;

@Controller
public class SugestaoController {

	@Inject
	private Result result;
	@Inject
	private Validator validator;
	@Inject
	private DaoImplementacao daoImplementacao;
	@Inject
	private ListItemSugerido listItemSugerido;
	@Inject
	private UsuarioSession usuarioSession;
	private List<SugestaoCardapio> sugestaoCardapioUil = new ArrayList<SugestaoCardapio>();
	@Inject DisponibilidadeController disponibilidadeController;
	
	@Inject HttpServletRequest resquest;

	@RestricaoAcesso
	@AcessoUsuario
	@Path("/sugestao")
	public void formSugestao() {
		listItensDisponiveis();
		result.include("totalCaloriaCardapioSugerido", calculaValorTotalCaloria());
		result.include("itemsLista", listItemSugerido.getItemSugestaoCardapios());
	}
	
	@RestricaoAcesso		
	@Path("/sugestoes")		
	public void listSugestaoCardapio() {
		result.include("disponibilidades", disponibilidadeController.listDatasDisponibilizadas());
	}
	
	@RestricaoAcesso
	public List<Item> listItensDisponiveis() {
		List<Item> items = daoImplementacao.find(Item.class);
		new Item().ordenaItemPorNome(items);
		result.include("items", items);
		return items;
	}
	
	@RestricaoAcesso
	public double calculaValorTotalCaloria() {
		double somaTotalCaloria = 0;
		for (ItemSugestaoCardapio itemSugestaoCardapio : listItemSugerido.getItemSugestaoCardapios()) {
			somaTotalCaloria += itemSugestaoCardapio.getTotalCaloria(); 
		}
		return somaTotalCaloria;
	}
	
	@RestricaoAcesso
	public boolean validaItensLista(Item item) {
		if(!validaTamanhoList()){
			validator.add(new I18nMessage("Tamanho da Lista", "numero.itens.nao.permitido"));
			return false;
		}
		
		if(item.getId() == null){
			validator.add(new I18nMessage("Item da Lista", "item.lista.nao.informado"));
			return false;
		}
		return true;
	}
	
	@RestricaoAcesso
	@AcessoUsuario
	@Path("/sugestao/add")
	public void add(Item item) {
		if(!validaItensLista(item)){
			validator.onErrorRedirectTo(this).formSugestao();
		}
		
		try {
			Item itemBanco = (Item) daoImplementacao.findById(Item.class, item.getId());
			ItemSugestaoCardapio itemSugestaoCardapio = new ItemSugestaoCardapio();
			itemSugestaoCardapio.setTotalCaloria(itemBanco.getValorCalorico());
			itemSugestaoCardapio.setItem(itemBanco);
			listItemSugerido.getItemSugestaoCardapios().add(itemSugestaoCardapio);
			result.redirectTo(this).formSugestao();
		} catch (Exception e) {
			result.include("erro", "Não foi possivel inserir o item na lista");
			result.redirectTo(this).formSugestao();	
		}		
	}
	
	@RestricaoAcesso
	public boolean validaDadosSugestao(SugestaoCardapio sugestaoCardapio) {
		if(listItemSugerido.getItemSugestaoCardapios().isEmpty()){
			validator.add(new I18nMessage("Lista de itens", "lista.itens.sugeridos.invalida"));
			return false;
		}
		return true;
	}
	
	@RestricaoAcesso
	public void inseriIdsugestao(SugestaoCardapio sugestaoCardapio) {
		for (ItemSugestaoCardapio itemSugestaoCardapio : listItemSugerido.getItemSugestaoCardapios()) {
			itemSugestaoCardapio.setItemSugerido(sugestaoCardapio);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RestricaoAcesso
	public boolean verificaSugestaoRealizada(Long idDisponibilidade) {
		boolean encontrou = false;
		List<SugestaoCardapio> sugestaoCardapios = daoImplementacao.find(SugestaoCardapio.class);
		for (SugestaoCardapio sugestaoCardapio : sugestaoCardapios) {
			if(sugestaoCardapio.getDisponibilidade().getId().equals(idDisponibilidade) && 
					sugestaoCardapio.getUsuario().getId().equals(usuarioSession.getUsuario().getId())){
				encontrou = true;
				break;
			}
		}
		return encontrou;
	}
	
	@RestricaoAcesso
	@Path("/sugestao/save")
	public void saveSugestao(SugestaoCardapio sugestaoCardapio) {
		if (!validaDadosSugestao(sugestaoCardapio)) {
			validator.onErrorRedirectTo(this).formSugestao();
		}
		
		try {
			if (verificaSugestaoRealizada(sugestaoCardapio.getDisponibilidade().getId())) {
				result.include("erro", "Desculpe, mas você já enviou sua sugestão de cardápio para essa data.");
				result.redirectTo(this).formSugestao();
			} else {
				sugestaoCardapio.setUsuario(usuarioSession.getUsuario());
				inseriIdsugestao(sugestaoCardapio);
				sugestaoCardapio.setItemSugerido(listItemSugerido.getItemSugestaoCardapios());
				daoImplementacao.save(sugestaoCardapio);
				result.include("sucesso", "Sugestão enviada com sucesso");
				limpaLista();
			}

		} catch (Exception e) {
			result.include("erro","Sugestão NÃO enviada. Tente novamente mais tarde");
			result.redirectTo(this).formSugestao();
		}
	}
	
	@RestricaoAcesso
	@AcessoUsuario
	@Path("/sugestao/remocaoitem")
	public void removeItem(Long id) {
		for (ItemSugestaoCardapio itemSugestaoCardapioLista : listItemSugerido.getItemSugestaoCardapios()) {
			if(itemSugestaoCardapioLista.getItem().getId().equals(id)){
				listItemSugerido.getItemSugestaoCardapios().remove(itemSugestaoCardapioLista);
				break;
			}
		}
		result.include("sucesso", "Item removido da lista");
		result.redirectTo(this).formSugestao();
	}
		
	@RestricaoAcesso
	@AcessoUsuario
	@Path("/sugestao/clear")
	public void limpaLista() {
		listItemSugerido.getItemSugestaoCardapios().clear();
		result.redirectTo(this).formSugestao();
	}
	
	@RestricaoAcesso
	public boolean validaTamanhoList() {
		if(listItemSugerido.getItemSugestaoCardapios().size() > 5){
			return false;
		}
		return true;
	}
	
	@RestricaoAcesso
	@Path("/sugestao/totalizasugestoes")
	public void totalizasSugestoesCardapio(Disponibilidade disponibilidade) {
		List<SugestaoCardapio> sugestoesEncontradas = new ArrayList<SugestaoCardapio>();
		List<ItemSugestaoCardapio> todosItensSugeridos = new ArrayList<ItemSugestaoCardapio>();
		try {
			sugestaoCardapioUil = daoImplementacao.find(SugestaoCardapio.class);
			for (SugestaoCardapio sugestaoCardapio : sugestaoCardapioUil) {
				if(sugestaoCardapio.getDisponibilidade().getId().equals(disponibilidade.getId())){
					sugestoesEncontradas.add(sugestaoCardapio);
				}			
			}
			if(!sugestoesEncontradas.isEmpty()){
				for (SugestaoCardapio sugestaoCardapio : sugestoesEncontradas) {
					for (ItemSugestaoCardapio itemSugestaoCardapio : sugestaoCardapio.getItemSugerido()) {
						todosItensSugeridos.add(itemSugestaoCardapio);
					}
				}
				HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
				for (ItemSugestaoCardapio itemSugestaoCardapio : todosItensSugeridos) {
					Integer contador = hashMap.get(itemSugestaoCardapio.getItem().getDescricao());
					if(contador == null){
						contador = 0;
					}
					hashMap.put(itemSugestaoCardapio.getItem().getDescricao(), contador + 1);
				}
				result.include("itensSugeridosCardapio", hashMap);
							
			}else{
				result.include("sucesso", "Não foram encontradas sugestões para o cardapio disponibilidade nesta data.");
			}
			
			result.redirectTo(this).listSugestaoCardapio();
		} catch (Exception e) {
				result.include("erro", "Erro ao contabilizar as sugestões de cardápio para esta data. Tente mais tarde");
				result.redirectTo(this).listSugestaoCardapio();
		}
			
	}
	
}







