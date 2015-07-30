package br.com.ruifpi.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item implements Comparable<Item>, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private String beneficios;
	private String maleficios;
	private double valorCalorico;
	private String informacoesNutricionais;
	@ManyToOne
	private ClasseNutricional classeNutricional;
	
	public Item() {
	}
	public Item(Long id, String descricao, String beneficios,
			String maleficios, double valorCalorico,
			String informacoesNutricionais, ClasseNutricional classeNutricional) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.beneficios = beneficios;
		this.maleficios = maleficios;
		this.valorCalorico = valorCalorico;
		this.informacoesNutricionais = informacoesNutricionais;
		this.classeNutricional = classeNutricional;
	}
	
	public void setClasseNutricional(ClasseNutricional classeNutricional) {
		this.classeNutricional = classeNutricional;
	}
	
	public ClasseNutricional getClasseNutricional() {
		return classeNutricional;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(String beneficios) {
		this.beneficios = beneficios;
	}

	public String getMaleficios() {
		return maleficios;
	}

	public void setMaleficios(String maleficios) {
		this.maleficios = maleficios;
	}

	public double getValorCalorico() {
		return valorCalorico;
	}

	public void setValorCalorico(double valorCalorico) {
		this.valorCalorico = valorCalorico;
	}

	public String getInformacoesNutricionais() {
		return informacoesNutricionais;
	}

	public void setInformacoesNutricionais(String informacoesNutricionais) {
		this.informacoesNutricionais = informacoesNutricionais;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(Item arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void ordenaItemPorNome(List<Item> item) {
		Collections.sort(item, new Comparator<Item>() {

			@Override
			public int compare(Item v1, Item v2) {
				return v1.getDescricao().compareToIgnoreCase(v2.getDescricao());
			}
		});
	}
	
	public void ordenaItemPorClasseNutricional(List<Item> items) {
		Collections.sort(items, new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {

				return o1.getClasseNutricional().getDescricao()
						.compareTo(o2.getClasseNutricional().getDescricao());
			}
		});
	}
}
















