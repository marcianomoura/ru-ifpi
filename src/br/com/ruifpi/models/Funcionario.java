package br.com.ruifpi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Funcionario{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String matricula;
	private String login;
	private String senha;
	private String funcao;
	private boolean matriculado;
	
	public Funcionario() {
		// TODO Auto-generated constructor stub
	}
	
	public Funcionario(String nome, String matricula, String login, String senha, boolean matriculado) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.login = login;
		this.senha = senha;
		this.matriculado = matriculado;
	}

	public Funcionario(Long id, String nome, String matricula, String login,
			String senha, String funcao, boolean matriculado) {
		super();
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.login = login;
		this.senha = senha;
		this.funcao = funcao;
		this.matriculado = matriculado;
	
	}
	
	public void setMatriculado(boolean matriculado) {
		this.matriculado = matriculado;
	}
	
	public boolean isMatriculado() {
		return matriculado;
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
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", matricula="
				+ matricula + ", login=" + login + ", senha=" + senha
				+ ", funcao=" + funcao + "]";
	}
	
	

}
