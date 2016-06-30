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
	private SugestaoPrato sugestaoPrato;
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private ItemSugestaoPratoPronto itemSugestaoPratoPronto;

	public Votacao() {
		// TODO Auto-generated constructor stub
	}

	public Votacao(Long id, SugestaoPrato sugestaoPrato, Usuario usuario, ItemSugestaoPratoPronto itemSugestaoPratoPronto) {
		super();
		this.id = id;
		this.sugestaoPrato = sugestaoPrato;
		this.usuario = usuario;
		this.itemSugestaoPratoPronto = itemSugestaoPratoPronto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SugestaoPrato getSugestaoPrato() {
		return sugestaoPrato;
	}

	public void setSugestaoPrato(SugestaoPrato sugestaoPrato) {
		this.sugestaoPrato = sugestaoPrato;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setItemSugestaoPratoPronto(ItemSugestaoPratoPronto itemSugestaoPratoPronto) {
		this.itemSugestaoPratoPronto = itemSugestaoPratoPronto;
	}

	public ItemSugestaoPratoPronto getItemSugestaoPratoPronto() {
		return itemSugestaoPratoPronto;
	}
	
}
