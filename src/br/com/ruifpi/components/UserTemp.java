package br.com.ruifpi.components;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.ruifpi.models.Usuario;

/*
 * 	Usuários temporários. Apenas no momento que estiverem cadastrando os seus dados pessoais.
 * 
 */

@SessionScoped
@Named("userTemp")
public class UserTemp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void insereUsuarioSessaoTemporaria(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void limpaUsuarioSessaoTemporaria() {
		this.usuario = null;
	}
	
}
