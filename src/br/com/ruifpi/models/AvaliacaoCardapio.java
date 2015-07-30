package br.com.ruifpi.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AvaliacaoCardapio{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Usuario usuario;
	
	@Temporal(TemporalType.DATE)
	private Date dataCardapio;
	
	private int notaAvaliativa;

	public AvaliacaoCardapio(Long id, Usuario usuario, Date dataCardapio,
			int notaAvaliativa) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.dataCardapio = dataCardapio;
		this.notaAvaliativa = notaAvaliativa;
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

	public Date getDataCardapio() {
		return dataCardapio;
	}

	public void setDataCardapio(Date dataCardapio) {
		this.dataCardapio = dataCardapio;
	}

	public int getNotaAvaliativa() {
		return notaAvaliativa;
	}

	public void setNotaAvaliativa(int notaAvaliativa) {
		this.notaAvaliativa = notaAvaliativa;
	}

	@Override
	public String toString() {
		return "AvaliacaoPrato [id=" + id + ", usuario=" + usuario
				+ ", dataCardapio=" + dataCardapio + ", notaAvaliativa="
				+ notaAvaliativa + "]";
	}
}
