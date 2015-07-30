package br.com.ruifpi.models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario implements Comparable<Usuario> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String matricula;
	private String login;
	private String senha;
	private boolean matriculado = true;
	private boolean vegetariano;
	private String perfil;
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	

	public Usuario(Long id, String nome, String matricula, String login,
			String senha, boolean matriculado, boolean vegetariano,
			String perfil) {
		super();
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.login = login;
		this.senha = senha;
		this.matriculado = matriculado;
		this.vegetariano = vegetariano;
		this.perfil = perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public String getPerfil() {
		return perfil;
	}

	public boolean isVegetariano() {
		return vegetariano;
	}
	
	public void setVegetariano(boolean vegetariano) {
		this.vegetariano = vegetariano;
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", matricula="
				+ matricula + ", login=" + login + ", senha=" + senha
				+ ", matriculado=" + matriculado + "]";
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
				return user1.getNome().compareToIgnoreCase(user2.getNome());
			}

		});
	}
	
	
}











