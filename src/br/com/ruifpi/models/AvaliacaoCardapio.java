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
	@ManyToOne
	private Cardapio cardapio;
		
	private int notaAvaliativa;

	public AvaliacaoCardapio() {
		// TODO Auto-generated constructor stub
	}
	
	public AvaliacaoCardapio(Long id, Usuario usuario, Date dataCardapio, Cardapio cardapio,
			int notaAvaliativa) {
		super();
		this.cardapio = cardapio;
		this.id = id;
		this.usuario = usuario;
		this.notaAvaliativa = notaAvaliativa;
	}
	
	public Cardapio getCardapio() {
		return cardapio;
	}
	
	public void setCardapio(Cardapio cardapio) {
		this.cardapio = cardapio;
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

	public int getNotaAvaliativa() {
		return notaAvaliativa;
	}

	public void setNotaAvaliativa(int notaAvaliativa) {
		this.notaAvaliativa = notaAvaliativa;
	}

	@Override
	public String toString() {
		return "AvaliacaoCardapio [id=" + id + ", usuario=" + usuario
				+ ", cardapio=" + cardapio + ", notaAvaliativa="
				+ notaAvaliativa + "]";
	}
	
}
