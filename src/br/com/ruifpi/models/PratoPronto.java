package br.com.ruifpi.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PratoPronto implements Serializable, Comparable<PratoPronto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String tituloPrato;

	@OneToMany(mappedBy = "pratoPronto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ItemPratoPronto> itemPratoProntos;

	public PratoPronto() {
		// TODO Auto-generated constructor stub
	}

	public PratoPronto(Long id, String tituloPrato, List<ItemPratoPronto> itemPratoProntos) {
		super();
		this.id = id;
		this.tituloPrato = tituloPrato;
		this.itemPratoProntos = itemPratoProntos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTituloPrato() {
		return tituloPrato;
	}

	public void setTituloPrato(String tituloPrato) {
		this.tituloPrato = tituloPrato;
	}

	public List<ItemPratoPronto> getItemPratoProntos() {
		return itemPratoProntos;
	}

	public void setItemPratoProntos(List<ItemPratoPronto> itemPratoProntos) {
		this.itemPratoProntos = itemPratoProntos;
	}

	@Override
	public String toString() {
		return "PratoPronto [id=" + id + ", tituloPrato=" + tituloPrato + ", itemPratoProntos=" + itemPratoProntos
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemPratoProntos == null) ? 0 : itemPratoProntos.hashCode());
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
		PratoPronto other = (PratoPronto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemPratoProntos == null) {
			if (other.itemPratoProntos != null)
				return false;
		} else if (!itemPratoProntos.equals(other.itemPratoProntos))
			return false;
		return true;
	}

	@Override
	public int compareTo(PratoPronto o) {
		return 0;
	}
	
	public void ordenaPratoByTitulo(List<PratoPronto> pratoProntos) {
		Collections.sort(pratoProntos, new Comparator<PratoPronto>() {
		
		@Override
		public int compare(PratoPronto o1, PratoPronto o2) {
			// TODO Auto-generated method stub
			return o1.getTituloPrato().compareToIgnoreCase(o2.getTituloPrato());
		}
		});
	}

}
