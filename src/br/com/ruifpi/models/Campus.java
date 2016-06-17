package br.com.ruifpi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Campus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nomeCampus;

	@ManyToOne
	private Instituicao instituicao;

	public Campus() {
		// TODO Auto-generated constructor stub
	}

	public Campus(Long id, String nomeCampus, Instituicao instituicao) {
		super();
		this.id = id;
		this.nomeCampus = nomeCampus;
		this.instituicao = instituicao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCampus() {
		return nomeCampus;
	}

	public void setNomeCampus(String nomeCampus) {
		this.nomeCampus = nomeCampus;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	@Override
	public String toString() {
		return "Campus [id=" + id + ", nomeCampus=" + nomeCampus + ", instituicao=" + instituicao + "]";
	}

}
