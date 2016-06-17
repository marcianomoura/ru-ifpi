package br.com.ruifpi.components;

import javax.ejb.Stateless;

@Stateless
public enum EscalaAvaliativa {
	
	EXCELENTE(10),
	MUITO_BOM(9),
	BOM(8),
	REGULAR(6),
	RUIM(4),
	MUITO_RUIM(2);
	
	private int notaAvaliativa;
	
	private EscalaAvaliativa(int valor) {
		this.notaAvaliativa = valor;
	}

	public int getNotaAvaliativa() {
		return notaAvaliativa;
	}
	
	
}
