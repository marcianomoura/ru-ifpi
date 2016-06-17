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
public class Usuario implements Comparable<Usuario> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String primeiroNome;
	private String sobrenome;
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	private String sexo;
	private String matricula;
	private String login;
	private String senha;
	private boolean matriculado = false;
	private boolean vegetariano;
	private String perfil;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(Long id, String primeiroNome, String sobrenome, Date dataNascimento, String sexo, String matricula,
			String login, String senha, boolean matriculado, boolean vegetariano, String perfil) {
		super();
		this.id = id;
		this.primeiroNome = primeiroNome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.matricula = matricula;
		this.login = login;
		this.senha = senha;
		this.matriculado = matriculado;
		this.vegetariano = vegetariano;
		this.perfil = perfil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isMatriculado() {
		return matriculado;
	}

	public void setMatriculado(boolean matriculado) {
		this.matriculado = matriculado;
	}

	public boolean isVegetariano() {
		return vegetariano;
	}

	public void setVegetariano(boolean vegetariano) {
		this.vegetariano = vegetariano;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	@Override
	public int compareTo(Usuario arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void ordenaUsuarioPorNome(List<Usuario> usuarios) {
		Collections.sort(usuarios, new Comparator<Usuario>() {

			@Override
			public int compare(Usuario user1, Usuario user2) {
				// TODO Auto-generated method stub
				return user1.getPrimeiroNome().compareToIgnoreCase(user2.getPrimeiroNome());
			}

		});
	}
}
