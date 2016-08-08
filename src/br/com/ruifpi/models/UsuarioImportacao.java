package br.com.ruifpi.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UsuarioImportacao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	private String matricula;
	
	public UsuarioImportacao() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioImportacao(Long id, Date dataNascimento, String matricula) {
		super();
		this.id = id;
		this.dataNascimento = dataNascimento;
		this.matricula = matricula;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


}
