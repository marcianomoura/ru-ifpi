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
public class SugestaoCardapio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Usuario usuario;
	
	@Temporal(TemporalType.DATE)
	private Date dataSugestao;
	
	@OneToMany(mappedBy="itemSugerido", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<ItemSugerido> itemSugerido ;

	public SugestaoCardapio(Long id, Usuario usuario, Date dataSugestao,
			List<ItemSugerido> itemSugerido) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.dataSugestao = dataSugestao;
		this.itemSugerido = itemSugerido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataSugestao() {
		return dataSugestao;
	}

	public void setDataSugestao(Date dataSugestao) {
		this.dataSugestao = dataSugestao;
	}

	public List<ItemSugerido> getItemSugerido() {
		return itemSugerido;
	}

	public void setItemSugerido(List<ItemSugerido> itemSugerido) {
		this.itemSugerido = itemSugerido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((itemSugerido == null) ? 0 : itemSugerido.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		SugestaoCardapio other = (SugestaoCardapio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itemSugerido == null) {
			if (other.itemSugerido != null)
				return false;
		} else if (!itemSugerido.equals(other.itemSugerido))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
}
