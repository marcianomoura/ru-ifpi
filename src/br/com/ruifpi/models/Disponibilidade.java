package br.com.ruifpi.models;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Disponibilidade implements Comparable<Disponibilidade> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date dataDisponibilidade;
	private boolean disponivel;

	public Disponibilidade() {
		// TODO Auto-generated constructor stub
	}

	public Disponibilidade(Long id, Date dataDisponibilidade, boolean disponivel) {
		super();
		this.id = id;
		this.dataDisponibilidade = dataDisponibilidade;
		this.disponivel = disponivel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataDisponibilidade() {
		return dataDisponibilidade;
	}

	public void setDataDisponibilidade(Date dataDisponibilidade) {
		this.dataDisponibilidade = dataDisponibilidade;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	@Override
	public String toString() {
		return "Disponibilidade_Sugestao [id=" + id + ", dataDisponibilidade="
				+ dataDisponibilidade + ", disponibilidade=" + disponivel
				+ "]";
	}

	@Override
	public int compareTo(Disponibilidade o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void ordenaPorData(List<Disponibilidade> disponibilidades) {
		Collections.sort(disponibilidades, new Comparator<Disponibilidade>() {
			@Override
			public int compare(Disponibilidade o1, Disponibilidade o2) {
				// TODO Auto-generated method stub
				return  o1.getDataDisponibilidade().compareTo(o2.getDataDisponibilidade());
			}
		});
	}

}












