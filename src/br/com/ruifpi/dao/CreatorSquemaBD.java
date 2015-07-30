package br.com.ruifpi.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreatorSquemaBD {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ruifpi");
		EntityManager entityManager = factory.createEntityManager();
		System.out.println("Bando de dados criado.");
		entityManager.close();
		factory.close();
	}
}
