package br.com.ruifpi.dao;

public class DaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DaoException() {
	}
	
	public DaoException(String mensagem) {
		super(mensagem);
	}
	
	public DaoException(String mensagem, Throwable throwable){
		super(mensagem, throwable);
	}
}
