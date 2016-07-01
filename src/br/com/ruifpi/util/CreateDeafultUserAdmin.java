package br.com.ruifpi.util;

import javax.persistence.EntityManager;

import br.com.ruifpi.models.Funcionario;

public class CreateDeafultUserAdmin {
	
	public static void main(String[] args) {
		EntityManager em = JpaUtil.getEntityAtual();
		em.getTransaction().begin();
		Funcionario func = new Funcionario("Rogerio", "123", "rogerio410", "123", false);
		em.merge(func);
		em.getTransaction().commit();
	}

}
