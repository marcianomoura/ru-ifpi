package br.com.ruifpi.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Sobremesa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private String vitaminas;
	private String maleficios;
	private String beneficios;
	@ManyToOne
	private ClasseNutricional classeNutricional;

	public Sobremesa() {
		// TODO Auto-generated constructor stub
	}

	public Sobremesa(Long id, String descricao, String vitaminas, String maleficios, String beneficios,
			ClasseNutricional classeNutricional) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.vitaminas = vitaminas;
		this.maleficios = maleficios;
		this.beneficios = beneficios;
		this.classeNutricional = classeNutricional;
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

	public String getVitaminas() {
		return vitaminas;
	}

	public void setVitaminas(String vitaminas) {
		this.vitaminas = vitaminas;
	}

	public String getMaleficios() {
		return maleficios;
	}

	public void setMaleficios(String maleficios) {
		this.maleficios = maleficios;
	}

	public String getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(String beneficios) {
		this.beneficios = beneficios;
	}

	public ClasseNutricional getClasseNutricional() {
		return classeNutricional;
	}

	public void setClasseNutricional(ClasseNutricional classeNutricional) {
		this.classeNutricional = classeNutricional;
	}

	@Override
	public String toString() {
		return "Sobremesa [id=" + id + ", descricao=" + descricao + ", vitaminas=" + vitaminas + ", maleficios="
				+ maleficios + ", beneficios=" + beneficios + ", classeNutricional=" + classeNutricional + "]";
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
		Sobremesa other = (Sobremesa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
