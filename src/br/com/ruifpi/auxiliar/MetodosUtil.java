package br.com.ruifpi.auxiliar;

import java.sql.Date;
import java.util.List;

import br.com.ruifpi.models.Cardapio;
import br.com.ruifpi.models.Funcionario;
import br.com.ruifpi.models.Usuario;

public interface MetodosUtil {

		
	public List<Funcionario> buscaFuncionariosAtivos();
	
	public List<Usuario> buscaUsuariosAtivos();
	
	public Cardapio buscaCardapioDataSolicitada(Date dataCardapio);

}
