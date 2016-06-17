package br.com.ruifpi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Refeitorio {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;

	@ManyToOne
	private Campus campus;

	public Refeitorio() {
		// TODO Auto-generated constructor stub
	}

	public Refeitorio(Long id, String nome, Campus campus) {
		super();
		this.id = id;
		this.nome = nome;
		this.campus = campus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	@Override
	public String toString() {
		return "Refeitorio [id=" + id + ", nome=" + nome + ", campus=" + campus + "]";
	}

}
