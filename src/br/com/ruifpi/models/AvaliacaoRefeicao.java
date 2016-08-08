package br.com.ruifpi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AvaliacaoRefeicao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private PratoDia pratoDia;

	@ManyToOne
	private Usuario usuario;

	private double notaAvaliativa;

	public AvaliacaoRefeicao() {
		// TODO Auto-generated constructor stub
	}

	public AvaliacaoRefeicao(Long id, PratoDia pratoDia, Usuario usuario, double notaAvaliativa) {
		super();
		this.id = id;
		this.pratoDia = pratoDia;
		this.usuario = usuario;
		this.notaAvaliativa = notaAvaliativa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PratoDia getPratoDia() {
		return pratoDia;
	}

	public void setPratoDia(PratoDia pratoDia) {
		this.pratoDia = pratoDia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public double getNotaAvaliativa() {
		return notaAvaliativa;
	}

	public void setNotaAvaliativa(double notaAvaliativa) {
		this.notaAvaliativa = notaAvaliativa;
	}


}
