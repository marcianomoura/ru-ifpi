package br.com.ruifpi.components;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.ruifpi.models.Funcionario;

/*
 * 	Funcion�rios da Cordena��o do Refeit�rio em escopo de sess�o.
 */

@SessionScoped
@Named("funcionarioSessao")
public class FuncionarioSession implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Funcionario funcionario;
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public void login(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public void logout() {
		this.funcionario = null;
	}
	
	public boolean logado() {
		if(this.funcionario == null){
			return false;
		}else{
			return true;
		}
	}
}
