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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Cardapio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dataCardapio;
	
	@OneToMany(mappedBy="itemCardapio", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<ItemCardapio> itemCardapio ;

	@ManyToOne
	private Funcionario funcionario;
	
	private double totalCaloria;
	
	public Cardapio() {
		// TODO Auto-generated constructor stub
	}

	public Cardapio(Long id, Date dataCardapio,
			List<ItemCardapio> itemCardapios, Funcionario funcionario, double totalCaloria) {
		super();
		this.id = id;
		this.dataCardapio = dataCardapio;
		this.itemCardapio = itemCardapios;
		this.funcionario = funcionario;
		this.totalCaloria = totalCaloria;
	}
	
	public List<ItemCardapio> getItemCardapio() {
		return itemCardapio;
	}
	
	public void setItemCardapio(List<ItemCardapio> itemCardapio) {
		this.itemCardapio = itemCardapio;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public double getTotalCaloria() {
		return totalCaloria;
	}
	
	public void setTotalCaloria(double totalCaloria) {
		this.totalCaloria = totalCaloria;
	}
	
	public Date getDataCardapio() {
		return dataCardapio;
	}

	public void setDataCardapio(Date dataCardapio) {
		this.dataCardapio = dataCardapio;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((itemCardapio == null) ? 0 : itemCardapio.hashCode());
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
		Cardapio other = (Cardapio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemCardapio == null) {
			if (other.itemCardapio != null)
				return false;
		} else if (!itemCardapio.equals(other.itemCardapio))
			return false;
		return true;
	}
	
}
