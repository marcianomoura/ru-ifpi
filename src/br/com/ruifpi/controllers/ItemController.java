package br.com.ruifpi.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Item;

@Controller
public class ItemController {

	@Inject private DaoImplementacao daoImplementacao;
	@Inject private Validator validator;
	@Inject private Result result;
	@Inject private ClassesNutricionaisController nutricionaisController;
			private List<Item> itemsGeral = new ArrayList<>();
	
	@Path("/item")
	public void formItem() {
	nutricionaisController.listClassesNutricionais();
	}
	
	@Path("/items")
	public void listItem() {
		listaItensAlimentares();
	}
	
	@Path("/item/save")
	public void save(Item item) {
		try {
			if(validaDados(item) == null){
				validator.onErrorRedirectTo(this).formItem();
			}else{
				daoImplementacao.save(item);
				result.include("sucesso", "Dados inserido com sucesso");
				result.redirectTo(this).formItem();
			}
		} catch (Exception e) {
			result.include("erro", "Dados não inseridos. Verifique os dados informados e tente novamente.");
			result.redirectTo(this).formItem();
		}
	}
	
	@Path("/item/alteracao")
	public void alteraItem(Item item) {
		try {
			Item itemEncontrado = (Item) daoImplementacao.findById(Item.class, item.getId());
			result.include("item", itemEncontrado);
			result.redirectTo(this).formItem();
		} catch (Exception e) {
			result.include("erro", "Erro na alteração do item. Verifique os dados e tente novamente");
			result.redirectTo(this).formItem();
		}
	}
	
	@Path("/item/remocao/{id}")
	public void removeItem(Long id) {
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> listItens() {
		return daoImplementacao.find(Item.class);
	}
	
	
	public Item validaDados(Item item) {
		if(item.getDescricao().length() < 5){
			validator.add(new I18nMessage("descricao", "item.descricao.invalido"));
			return null;
		}
		if(item.getValorCalorico() <= 0){
			validator.add(new I18nMessage("valorCalorico", "item.valorCalorico.invalido"));
			return null;
		}
		if(item.getInformacoesNutricionais() == null){
			validator.add(new I18nMessage("informacaoNutricional", "item.informacaoNutricional.invalido"));
			return null;
		}
		if(item.getBeneficios().length() < 5){
			validator.add(new I18nMessage("beneficios", "item.beneficios.invalido"));
			return null;
		}
		if(item.getMaleficios().length() < 5){
			validator.add(new I18nMessage("maleficios", "item.maleficios.invalido"));
			return null;
		}
		if(item.getClasseNutricional().getId() == null){
			validator.add(new I18nMessage("classeNutricional", "item.classeNutricional.invalido"));
			return null;
		}
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public void listaItensAlimentares() {
		itemsGeral = daoImplementacao.find(Item.class);
		new Item().ordenaItemPorClasseNutricional(itemsGeral);
		if(itemsGeral.isEmpty()){
			result.include("erro", "Não há itens ainda cadastrado.");
		}
		result.include("items",	 itemsGeral);
	}
	
	@SuppressWarnings("unchecked")
	public void listaItensAlimentaresCategorizados() {
		List<Item> proteinas = new ArrayList<>();	// 3
		List<Item> lipidios = new ArrayList<>();	// 2
		List<Item> vitaminas = new ArrayList<>();	// 4
		List<Item> carboidratos = new ArrayList<>();	// 1
		try {
			itemsGeral = daoImplementacao.find(Item.class);
		 	for (Item item : itemsGeral) {
				if(item.getClasseNutricional().getId() == 1L){
					carboidratos.add(item);
				}else if(item.getClasseNutricional().getId() == 2L){
					lipidios.add(item);
				}else if(item.getClasseNutricional().getId() == 3L){
					proteinas.add(item);
				}else if(item.getClasseNutricional().getId() == 4L){
					vitaminas.add(item);
				}
			}
			result.include("proteinas", proteinas);
			result.include("lipidios", lipidios);
			result.include("carboidratos", carboidratos);
			result.include("vitaminas", vitaminas);
		} catch (Exception e) {
			result.include("erro", "Erro na listagem de itens");
			result.redirectTo(this).listItem();
		}
		
	 	
	}
}













