package br.com.ruifpi.auxiliar;

import java.util.List;

import br.com.ruifpi.models.Funcionario;
import br.com.ruifpi.models.Usuario;

public interface Metodos {

		
	public List<Funcionario> buscaFuncionariosAtivos();
	
	public List<Usuario> buscaUsuariosAtivos();
	
}
