package br.com.ruifpi.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemSugestaoPratoPronto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Sobremesa sobremesa;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private SugestaoPrato sugestaoPrato;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private PratoPronto pratoPronto;

	private double totalCaloria;

	public ItemSugestaoPratoPronto() {
		// TODO Auto-generated constructor stub
	}

	public ItemSugestaoPratoPronto(Long id, Sobremesa sobremesa, SugestaoPrato sugestaoPrato, PratoPronto pratoPronto,
			double totalCaloria) {
		super();
		this.id = id;
		this.sobremesa = sobremesa;
		this.sugestaoPrato = sugestaoPrato;
		this.pratoPronto = pratoPronto;
		this.totalCaloria = totalCaloria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sobremesa getSobremesa() {
		return sobremesa;
	}

	public void setSobremesa(Sobremesa sobremesa) {
		this.sobremesa = sobremesa;
	}

	public SugestaoPrato getEscolha() {
		return sugestaoPrato;
	}

	public void setSugestaoPrato(SugestaoPrato sugestaoPrato) {
		this.sugestaoPrato = sugestaoPrato;
	}

	public PratoPronto getPratoPronto() {
		return pratoPronto;
	}

	public void setPratoPronto(PratoPronto pratoPronto) {
		this.pratoPronto = pratoPronto;
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
		result = prime * result + ((sugestaoPrato == null) ? 0 : sugestaoPrato.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pratoPronto == null) ? 0 : pratoPronto.hashCode());
		result = prime * result + ((sobremesa == null) ? 0 : sobremesa.hashCode());
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
		ItemSugestaoPratoPronto other = (ItemSugestaoPratoPronto) obj;
		if (sugestaoPrato == null) {
			if (other.sugestaoPrato != null)
				return false;
		} else if (!sugestaoPrato.equals(other.sugestaoPrato))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pratoPronto == null) {
			if (other.pratoPronto != null)
				return false;
		} else if (!pratoPronto.equals(other.pratoPronto))
			return false;
		if (sobremesa == null) {
			if (other.sobremesa != null)
				return false;
		} else if (!sobremesa.equals(other.sobremesa))
			return false;
		return true;
	}

}
