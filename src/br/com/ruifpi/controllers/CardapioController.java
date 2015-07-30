package br.com.ruifpi.controllers;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.components.FuncionarioSession;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Cardapio;
import br.com.ruifpi.models.Item;
import br.com.ruifpi.models.ItemCardapio;
import br.com.ruifpi.util.RestricaoAcesso;
import br.com.ruifpi.util.RestricaoAcesso.AcessoAdministrativo;

@Controller
public class CardapioController {
	
	@Inject private DaoImplementacao daoImplementacao;
	@Inject private Validator validator;
	@Inject private Result result;
	@Inject private FuncionarioSession usuarioSession;
	@Inject private ItemController itemController;
			private static List<ItemCardapio> itemsCardapio = new ArrayList<>();
			private DecimalFormat decimalFormat = new DecimalFormat("0.00");
			private static Cardapio cardapioAlteracao = new Cardapio();
			private SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
			private static double valorTotalCaloriaCardapio = 0;
	
	@AcessoAdministrativo		
	@RestricaoAcesso		
	@Path("/cardapio")
	public void formCardapio() {			
		itemController.listaItensAlimentares();
		result.include("totalCaloria", Double.parseDouble(decimalFormat.format(valorTotalCaloriaCardapio).replace(",", ".")));
		mostraItensCardapioLista();
	}
	
	@AcessoAdministrativo
	@RestricaoAcesso
	@Path("/cardapio/itens")
	public void itensCardapio(Cardapio cardapio) {
		detalhesItensCardapios(cardapio);
	}
	
	@AcessoAdministrativo
	@RestricaoAcesso
	@Path("/cardapios")	
	public void listCardapio() {
		list_show_Cardapios();
	}
	
	@AcessoAdministrativo
	@RestricaoAcesso
	@Path("/cardapio/save")
	public void save(Cardapio cardapio) {
		try {
			if (validaDadosCardapio(cardapio) == null) {
				validator.onErrorRedirectTo(this).formCardapio();
			} else {	// Se os dados do cardápio estiver tudo ok ...
				if(cardapioAlteracao == null){	// Se Não for alteração ... 
					cardapio.setFuncionario(this.usuarioSession.getFuncionario());
					insereIdCardapio(cardapio);
					cardapio.setItemCardapio(itemsCardapio);
					daoImplementacao.save(cardapio);
					result.include("sucesso", "Cardapio inserido com sucesso.");
					result.redirectTo(this).formCardapio();
				}else{
					insereIdCardapio(cardapioAlteracao);
					cardapioAlteracao.setFuncionario(this.usuarioSession.getFuncionario());
					cardapioAlteracao.setItemCardapio(itemsCardapio);
					cardapioAlteracao.setDataCardapio(cardapio.getDataCardapio());
					cardapioAlteracao.setTotalCaloria(cardapio.getTotalCaloria());
					removeItensCardapioDoBanco(cardapioAlteracao);
					daoImplementacao.save(cardapioAlteracao);
					result.include("sucesso", "Cardapio alterado com sucesso.");
				}
				itemsCardapio.clear();
				valorTotalCaloriaCardapio = 0;
				result.redirectTo(this).formCardapio();
			}
		} catch (Exception e) {
			result.include("erro","Cardapio não inserido. Verifique os dados informados e tente novamente.");
			result.redirectTo(this).formCardapio();
		}
	}
	
	// Função que remove do banco de dados os itens do cardápio que irá ser alterado para que sejam substituidos pelos novos itens.
	@AcessoAdministrativo
	@RestricaoAcesso
	public void removeItensCardapioDoBanco(Cardapio cardapio) {
		try {
			Cardapio cardapioRemocaoItens = (Cardapio) daoImplementacao.findById(Cardapio.class, cardapio.getId());
			for (ItemCardapio itemCardapio : cardapioRemocaoItens.getItemCardapio()) {
				daoImplementacao.remove(itemCardapio);
			}
		} catch (Exception e) {
			System.out.println("Erro na remoção dos itens");
		}
		
	}
	
	@RestricaoAcesso
	public void mostraItensCardapioLista() {
		result.include("itemsLista", itemsCardapio);
	}
	
	@RestricaoAcesso
	public void insereIdCardapio(Cardapio cardapio) {
		for (ItemCardapio itemCardapio : itemsCardapio) {
			itemCardapio.setCardapio(cardapio);
		}
	}
	
	@AcessoAdministrativo
	@RestricaoAcesso
	@Path("/cardapio/addItem")
	public void addItemCardapio(Item item) {
		try {
			Item itemBanco = (Item) daoImplementacao.findById(Item.class, item.getId());
			ItemCardapio itemCardapio = new ItemCardapio();
			itemCardapio.setItem(itemBanco);
			itemCardapio.setTotalCaloria(itemBanco.getValorCalorico());
			valorTotalCaloriaCardapio += itemCardapio.getTotalCaloria();
			itemsCardapio.add(itemCardapio);
			result.redirectTo(this).formCardapio();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na inserção do item. Tente novamente");
			result.redirectTo(this).formCardapio();
		}
		
	}
	
	@AcessoAdministrativo
	@RestricaoAcesso
	@Path("/cardapio/removeItem")
	public void removeItem(Long id) {	// Id do Produto
		try {
			for (ItemCardapio itemCardapio : itemsCardapio) {
				if(itemCardapio.getItem().getId() == id){
					valorTotalCaloriaCardapio -= itemCardapio.getTotalCaloria();
					itemsCardapio.remove(itemCardapio);
					break;
				}
			}
			result.redirectTo(this).formCardapio();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro ao tentar remover o item do cardapio.");
			result.redirectTo(this).formCardapio();
		}
	}
	
	@AcessoAdministrativo
	@RestricaoAcesso
	@Path("/carpapio/alteracao")
	public void alteracaoCardapio(Cardapio cardapio) {
		cardapioAlteracao = (Cardapio) daoImplementacao.findById(Cardapio.class, cardapio.getId());
		for (ItemCardapio itemCardapio : cardapioAlteracao.getItemCardapio()) {
			itemsCardapio.add(itemCardapio);
			valorTotalCaloriaCardapio+=itemCardapio.getTotalCaloria();
		}
		result.redirectTo(this).formCardapio();
	}
	
	@RestricaoAcesso
	public void listCardapioDia() {
		
	}
	
	@RestricaoAcesso
	public void cardapioSemana() {
	}
	
	@RestricaoAcesso
	public Date formataData() {
		Date dataFormatada = new Date();
		try {
			dataFormatada = formatadorData.parse(formatadorData.format(new Date()));
			System.out.println("data atual" + dataFormatada);
		} catch (ParseException e) {
			System.out.println("Erro na Conversão de datas...");
			e.printStackTrace();
		}
		return dataFormatada;
	}
	
	@AcessoAdministrativo
	@RestricaoAcesso
	public Cardapio validaDadosCardapio(Cardapio cardapio) {
		if(cardapio.getDataCardapio().before(formataData()) || cardapio.getDataCardapio() == null){
			validator.add(new I18nMessage("dataCardapio", "cardapio.datacardapio.invalida"));
			return null;
		}
		if(cardapio.getTotalCaloria() <= 0.0 ){
			validator.add(new I18nMessage("totalCaloria", "cardapio.totalcaloria.invalido"));
			return null;
		}
		if(itemsCardapio.isEmpty() || itemsCardapio.size() <= 0){
			validator.add(new I18nMessage("itemsDoCardapio", "cardapio.items.vazio"));
			return null;
		}
		return cardapio;
	}
	
	@RestricaoAcesso
	@AcessoAdministrativo
	@SuppressWarnings("unchecked")
	public List<Cardapio> list_show_Cardapios() {
		List<Cardapio> cardapios = daoImplementacao.find(Cardapio.class);
		result.include("cardapios", cardapios);
		return cardapios;
	}
	
	@RestricaoAcesso
	public List<ItemCardapio> detalhesItensCardapios(Cardapio cardapio) {
		List<ItemCardapio> itemCardapios  =  new ArrayList<>();
		try {
			Cardapio cardapioEncontrado = (Cardapio) daoImplementacao.findById(Cardapio.class, cardapio.getId());
			for (ItemCardapio itemCardapio : cardapioEncontrado.getItemCardapio()) {
				itemCardapios.add(itemCardapio);
			}
			result.include("itemCardapios", itemCardapios);
			return itemCardapios;
		} catch (Exception e) {
			result.include("erro", "Erro na listagem de itens do cardápio.");
			return null;
		}
	}
	
}
