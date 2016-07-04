package br.com.ruifpi.util;

import javax.persistence.EntityManager;

import br.com.ruifpi.models.Funcionario;

public class CreateDeafultUserAdmin {

	public static void insereFuncionarioDefault() {
		EntityManager em = JpaUtil.getEntityAtual();
		Funcionario func = new Funcionario(1L,"Rogerio", "123", "admin","8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918","Cordenador", true);
		em.merge(func);
	}

}
