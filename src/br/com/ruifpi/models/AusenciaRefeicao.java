package br.com.ruifpi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AusenciaRefeicao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private PratoDia pratoDia;
	
	@ManyToOne
	private Usuario usuario;
	
	public AusenciaRefeicao() {
		// TODO Auto-generated constructor stub
	}

	public AusenciaRefeicao(Long id, PratoDia pratoDia, Usuario usuario) {
		super();
		this.id = id;
		this.pratoDia = pratoDia;
		this.usuario = usuario;
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

	@Override
	public String toString() {
		return "AusenciaRefeicao [id=" + id + ", pratoDia=" + pratoDia + ", usuario=" + usuario + "]";
	}
	
	
	
}
