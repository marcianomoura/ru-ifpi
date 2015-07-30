package br.com.ruifpi.models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ClasseNutricional implements Comparable<ClasseNutricional> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	private String descricao;
	
	public ClasseNutricional() {
	}
	
	public ClasseNutricional(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
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

	@Override
	public String toString() {
		return "ClasseNutricional [id=" + id + ", descricao=" + descricao + "]";
	}
	
	
	@Override
	public int compareTo(ClasseNutricional arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void ordenaClasseNutricionalPorNome(List<ClasseNutricional> classeNutricionals) {
		Collections.sort(classeNutricionals, new Comparator<ClasseNutricional>() {
		
			@Override
			public int compare(ClasseNutricional classe1, ClasseNutricional classe2) {
				// TODO Auto-generated method stub
				return classe1.getDescricao().compareToIgnoreCase(classe2.getDescricao());
			}
		});
	}
	
}

