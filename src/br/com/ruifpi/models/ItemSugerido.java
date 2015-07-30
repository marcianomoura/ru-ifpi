package br.com.ruifpi.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemSugerido implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Item item;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private SugestaoCardapio itemSugerido;
	
	private double totalCaloria;

	public ItemSugerido(Long id, Item item, SugestaoCardapio itemSugerido,
			double totalCaloria) {
		super();
		this.id = id;
		this.item = item;
		this.itemSugerido = itemSugerido;
		this.totalCaloria = totalCaloria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public SugestaoCardapio getItemSugerido() {
		return itemSugerido;
	}

	public void setItemSugerido(SugestaoCardapio itemSugerido) {
		this.itemSugerido = itemSugerido;
	}

	public double getTotalCaloria() {
		return totalCaloria;
	}

	public void setTotalCaloria(double totalCaloria) {
		this.totalCaloria = totalCaloria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result
				+ ((itemSugerido == null) ? 0 : itemSugerido.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemSugerido other = (ItemSugerido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (itemSugerido == null) {
			if (other.itemSugerido != null)
				return false;
		} else if (!itemSugerido.equals(other.itemSugerido))
			return false;
		return true;
	}
	
}
