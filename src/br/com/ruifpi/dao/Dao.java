package br.com.ruifpi.dao;

import java.util.List;

public interface Dao {

	public Object save(Object parametro);	// M�todo de adi��o
	
	@SuppressWarnings("rawtypes")
	public List find (Class classe);	// M�todo de busca
	
	@SuppressWarnings("rawtypes")
	public Object findById(Class classe, Object parametro);

	public void remove(Object parametro);	// M�todo de Remo��o
	
	@SuppressWarnings("rawtypes")
	public List findByIntervalo(Class classe, int primeiroResultado, int maximoResultado);
}
