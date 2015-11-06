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
import br.com.ruifpi.util.RestricaoAcesso;
import br.com.ruifpi.util.RestricaoAcesso.AcessoAdministrativo;

@Controller
public class ItemController {

	@Inject private DaoImplementacao daoImplementacao;
	@Inject private Validator validator;
	@Inject private Result result;
	@Inject private ClassesNutricionaisController nutricionaisController;
			private List<Item> itemsGeral = new ArrayList<>();
	
	@RestricaoAcesso
	@AcessoAdministrativo
	@Path("/item")
	public void formItem() {
	nutricionaisController.listClassesNutricionais();
	}
	
	@RestricaoAcesso
	@Path("/items")
	public void listItem() {
		listaItensAlimentares();
	}
	
	@RestricaoAcesso
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
	
	@RestricaoAcesso
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
	
	@Path("/item/remocao/{id}")
	public void removeItem(Long id) {
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> listItens() {
		return daoImplementacao.find(Item.class);
	}
	
	@RestricaoAcesso
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
	
}
