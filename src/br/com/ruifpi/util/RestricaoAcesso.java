package br.com.ruifpi.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface RestricaoAcesso {
	
	@Target(value=ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AcessoAdministrativo{
		
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(value=ElementType.METHOD)
	public @interface AcessoUsuario{

	}
	
}
