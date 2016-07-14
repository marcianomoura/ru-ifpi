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
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;

@Controller
public class ItemController {

	@Inject private DaoImplementacao daoImplementacao;
	@Inject private Validator validator;
	@Inject private Result result;
	@Inject private ClassesNutricionaisController nutricionaisController;
			private List<Item> itemsGeral = new ArrayList<>();
	
	
	public ItemController() {
		this(null, null, null);
	}		
	
	@Inject
	public ItemController(Validator validator, Result result, DaoImplementacao dao){
		this.daoImplementacao = dao;
		this.result = result;
		this.validator = validator;
	}
			
	@AcessoAdministrativo
	@Path("/item")
	public void formItem() {
	nutricionaisController.listClassesNutricionais();
	}
	
	@AcessoAdministrativo
	@Path("/items")
	public void listItem() {
		listaItensAlimentares();
	}
	
	@AcessoAdministrativo
	@Path("/item/save")
	public void save(Item item) {
		if(validaDados(item) == null){
			validator.onErrorRedirectTo(this).formItem();
		}
		try {			
			daoImplementacao.save(item);
			result.include("sucesso", "Dados inserido com sucesso");
			result.redirectTo(this).formItem();
		} catch (Exception e) {
			result.include("erro", "Dados não inseridos. Verifique os dados informados e tente novamente.");
			result.redirectTo(this).formItem();
		}
	}
	
	@AcessoAdministrativo
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
	
	@AcessoAdministrativo
	@Path("/item/remocao/{id}")
	public void removeItem(Long id) {
		
	}
	
	@AcessoAdministrativo
	@SuppressWarnings("unchecked")
	public List<Item> listItens() {
		return daoImplementacao.find(Item.class);
	}
	
	@AcessoAdministrativo
	public Item validaDados(Item item) {
		
		if(item == null){
			validator.add(new I18nMessage("item", "item.nulo"));
			return null;
		}
		
		if(item.getDescricao().length() < 5){
			validator.add(new I18nMessage("descricao", "item.descricao.invalido"));
			return null;
		}
		if(item.getValorCalorico() <= 0){
			validator.add(new I18nMessage("valorCalorico", "item.valorCalorico.invalido"));
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
		try {
			if(item.getClasseNutricional().getId() == null){
				validator.add(new I18nMessage("classeNutricional", "item.classeNutricional.invalido"));
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		
		return item;
	}
	
	@AcessoAdministrativo
	@SuppressWarnings("unchecked")
	public void listaItensAlimentares() {
		itemsGeral = daoImplementacao.find(Item.class);
		new Item().ordenaItemPorNome(itemsGeral);
		if(itemsGeral.isEmpty()){
			result.include("erro", "Não há itens ainda cadastrado.");
		}
		result.include("items",	 itemsGeral);
	}
	
}
