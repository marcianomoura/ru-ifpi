package br.com.ruifpi.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemCardapio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Item item;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Cardapio itemCardapio;
	
	private double totalCaloria;
	
	public ItemCardapio() {
	}

	public ItemCardapio(Long id, Item item, Cardapio itemCardapio,
			double totalCaloria) {
		super();
		this.id = id;
		this.item = item;
		this.itemCardapio = itemCardapio;
		this.totalCaloria = totalCaloria;
	}
	
	public Cardapio getCardapio() {
		return itemCardapio;
	}
	
	public void setCardapio(Cardapio cardapio) {
		this.itemCardapio = cardapio;
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
		result = prime * result
				+ ((itemCardapio == null) ? 0 : itemCardapio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
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
		ItemCardapio other = (ItemCardapio) obj;
		if (itemCardapio == null) {
			if (other.itemCardapio != null)
				return false;
		} else if (!itemCardapio.equals(other.itemCardapio))
			return false;
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
		return true;
	}


}
