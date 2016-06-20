package br.com.ruifpi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Votacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Escolha escolha;
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private ItemEscolhaPratoPronto escolhaPratoPronto;

	public Votacao() {
		// TODO Auto-generated constructor stub
	}

	public Votacao(Long id, Escolha escolha, Usuario usuario, ItemEscolhaPratoPronto escolhaPratoPronto) {
		super();
		this.id = id;
		this.escolha = escolha;
		this.usuario = usuario;
		this.escolhaPratoPronto = escolhaPratoPronto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Escolha getEscolha() {
		return escolha;
	}

	public void setEscolha(Escolha escolha) {
		this.escolha = escolha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ItemEscolhaPratoPronto getEscolhaPratoPronto() {
		return escolhaPratoPronto;
	}

	public void setEscolhaPratoPronto(ItemEscolhaPratoPronto escolhaPratoPronto) {
		this.escolhaPratoPronto = escolhaPratoPronto;
	}

	@Override
	public String toString() {
		return "Votacao [id=" + id + ", escolha=" + escolha + ", usuario=" + usuario + ", escolhaPratoPronto="
				+ escolhaPratoPronto + "]";
	}

}
