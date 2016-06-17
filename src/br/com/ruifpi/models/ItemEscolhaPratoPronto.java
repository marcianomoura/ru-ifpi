package br.com.ruifpi.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemEscolhaPratoPronto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Sobremesa sobremesa;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Escolha escolha;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private PratoPronto pratoPronto;

	private double totalCaloria;

	public ItemEscolhaPratoPronto() {
		// TODO Auto-generated constructor stub
	}

	public ItemEscolhaPratoPronto(Long id, Sobremesa sobremesa, Escolha escolha, PratoPronto pratoPronto,
			double totalCaloria) {
		super();
		this.id = id;
		this.sobremesa = sobremesa;
		this.escolha = escolha;
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

	public Escolha getEscolha() {
		return escolha;
	}

	public void setEscolha(Escolha escolha) {
		this.escolha = escolha;
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
	public String toString() {
		return "ItemEscolhaPratoPronto [id=" + id + ", sobremesa=" + sobremesa + ", escolha=" + escolha
				+ ", pratoPronto=" + pratoPronto + ", totalCaloria=" + totalCaloria + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((escolha == null) ? 0 : escolha.hashCode());
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
		ItemEscolhaPratoPronto other = (ItemEscolhaPratoPronto) obj;
		if (escolha == null) {
			if (other.escolha != null)
				return false;
		} else if (!escolha.equals(other.escolha))
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
