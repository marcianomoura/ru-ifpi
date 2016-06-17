package br.com.ruifpi.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemPratoPronto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private PratoPronto pratoPronto;
	
	@ManyToOne
	private Item item;
	
	private double totalCaloria;
	
	public ItemPratoPronto() {
		// TODO Auto-generated constructor stub
	}

	public ItemPratoPronto(Long id, PratoPronto pratoPronto, Item item, double totalCaloria) {
		super();
		this.id = id;
		this.pratoPronto = pratoPronto;
		this.item = item;
		this.totalCaloria = totalCaloria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PratoPronto getPratoPronto() {
		return pratoPronto;
	}

	public void setPratoPronto(PratoPronto pratoPronto) {
		this.pratoPronto = pratoPronto;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((pratoPronto == null) ? 0 : pratoPronto.hashCode());
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
		ItemPratoPronto other = (ItemPratoPronto) obj;
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
		if (pratoPronto == null) {
			if (other.pratoPronto != null)
				return false;
		} else if (!pratoPronto.equals(other.pratoPronto))
			return false;
		return true;
	}
		
	
}
