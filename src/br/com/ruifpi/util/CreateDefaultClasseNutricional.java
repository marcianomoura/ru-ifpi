package br.com.ruifpi.util;

import javax.persistence.EntityManager;

import br.com.ruifpi.models.ClasseNutricional;

public class CreateDefaultClasseNutricional {

	public static void insereClasseNutricionalDefault() {
		EntityManager entityManager = JpaUtil.getEntityAtual();
		ClasseNutricional nutricional1 = new ClasseNutricional(1L, "Carboidratos");
		ClasseNutricional nutricional2 = new ClasseNutricional(2L, "Lipidios");
		ClasseNutricional nutricional3 = new ClasseNutricional(3L, "Proteinas");
		ClasseNutricional nutricional4 = new ClasseNutricional(4L, "Vitaminas");
		entityManager.merge(nutricional1);
		entityManager.merge(nutricional2);
		entityManager.merge(nutricional3);
		entityManager.merge(nutricional4);
	}

}
