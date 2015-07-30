package br.com.ruifpi.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JpaUtil {
	
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("ruifpi");
	private static ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();
	
	
	public static EntityManager getSessionAtual() {
		EntityManager entity = (EntityManager) threadLocal.get();
		if(entity==null){
			entity = factory.createEntityManager();
			threadLocal.set(entity);
		}
		return entity;
	}
	
	public static void closeEntity() {
		EntityManager entity = (EntityManager) threadLocal.get();
		if(entity.isOpen()){
			entity.close();
		}
		threadLocal.set(null);
	}
}
