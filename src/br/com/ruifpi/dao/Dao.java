package br.com.ruifpi.dao;

import java.util.List;

public interface Dao {

	public Object save(Object parametro);	// Método de adição
	
	@SuppressWarnings("rawtypes")
	public List find (Class classe);	// Método de busca
	
	@SuppressWarnings("rawtypes")
	public Object findById(Class classe, Object parametro);

	public void remove(Object parametro);	// Método de Remoção
	
	@SuppressWarnings("rawtypes")
	public List findByIntervalo(Class classe, int primeiroResultado, int maximoResultado);
}
