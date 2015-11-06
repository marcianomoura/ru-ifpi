package br.com.ruifpi.components;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.ruifpi.models.ItemSugestaoCardapio;

@SessionScoped
@Named("itensSugeridos")
public class ListItemSugerido implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<ItemSugestaoCardapio> itemSugestaoCardapios;
	
	public ListItemSugerido() {
		// TODO Auto-generated constructor stub
	}
	
	public ListItemSugerido(List<ItemSugestaoCardapio> itemSugestaoCardapios) {
		super();
		this.itemSugestaoCardapios = itemSugestaoCardapios;
	}
	
	public List<ItemSugestaoCardapio> getItemSugestaoCardapios() {
		return itemSugestaoCardapios;
	}

	public void setItemSugestaoCardapios(List<ItemSugestaoCardapio> itemSugestaoCardapios) {
		this.itemSugestaoCardapios = itemSugestaoCardapios;
	}

		
	
	
}
