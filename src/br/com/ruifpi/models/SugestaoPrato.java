package br.com.ruifpi.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SugestaoPrato implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataFinalVotacao;

	@Temporal(TemporalType.DATE)
	private Date dataDisponibilizada;

	@OneToMany(mappedBy = "sugestaoPrato", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<ItemSugestaoPratoPronto> itemSugestaoPratoProntos;

	public SugestaoPrato() {
		// TODO Auto-generated constructor stub
	}

	public SugestaoPrato(Long id, Date dataFinalVotacao, Date dataDisponibilizada,
			List<ItemSugestaoPratoPronto> itemSugestaoPratoProntos) {
		super();
		this.id = id;
		this.dataFinalVotacao = dataFinalVotacao;
		this.dataDisponibilizada = dataDisponibilizada;
		this.itemSugestaoPratoProntos = itemSugestaoPratoProntos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataFinalVotacao() {
		return dataFinalVotacao;
	}

	public void setDataFinalVotacao(Date dataFinalVotacao) {
		this.dataFinalVotacao = dataFinalVotacao;
	}

	public Date getDataDisponibilizada() {
		return dataDisponibilizada;
	}

	public void setDataDisponibilizada(Date dataDisponibilizada) {
		this.dataDisponibilizada = dataDisponibilizada;
	}

	public void setItemSugestaoPratoProntos(List<ItemSugestaoPratoPronto> itemSugestaoPratoProntos) {
		this.itemSugestaoPratoProntos = itemSugestaoPratoProntos;
	}
	
	public List<ItemSugestaoPratoPronto> getItemSugestaoPratoProntos() {
		return itemSugestaoPratoProntos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataFinalVotacao == null) ? 0 : dataFinalVotacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemSugestaoPratoProntos == null) ? 0 : itemSugestaoPratoProntos.hashCode());
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
		SugestaoPrato other = (SugestaoPrato) obj;
		if (dataFinalVotacao == null) {
			if (other.dataFinalVotacao != null)
				return false;
		} else if (!dataFinalVotacao.equals(other.dataFinalVotacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemSugestaoPratoProntos == null) {
			if (other.itemSugestaoPratoProntos != null)
				return false;
		} else if (!itemSugestaoPratoProntos.equals(other.itemSugestaoPratoProntos))
			return false;
		return true;
	}	

}
