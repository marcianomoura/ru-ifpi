package br.com.ruifpi.components;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import br.com.ruifpi.models.Usuario;

@SessionScoped
@Named("usuarioSessao")
public class UsuarioSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void login(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void logout() {
		this.usuario = null;
	}
	
	public boolean logado() {
		if(this.usuario == null){
			return false;
		}else{
			return true;
		}
	}
}
