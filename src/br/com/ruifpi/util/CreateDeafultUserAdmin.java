package br.com.ruifpi.util;

import javax.persistence.EntityManager;

import br.com.ruifpi.models.Funcionario;

public class CreateDeafultUserAdmin {

	public static void insereFuncionarioDefault() {
		EntityManager em = JpaUtil.getEntityAtual();
		Funcionario func = new Funcionario("Rogerio", "123", "admin", "123", true);
		em.merge(func);
	}

}
