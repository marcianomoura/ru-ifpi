package br.com.ruifpi.util;

import javax.persistence.EntityManager;

import br.com.ruifpi.models.TipoPrato;

public class CreateDefaultTipoPrato {

	public static void inserePratosDefault() {
		EntityManager em = JpaUtil.getEntityAtual();
		TipoPrato tipoPrato1 = new TipoPrato(1L, "Almoço");
		TipoPrato tipoPrato2 = new TipoPrato(2L, "Jantar");
		em.merge(tipoPrato1);
		em.merge(tipoPrato2);
	}
}
