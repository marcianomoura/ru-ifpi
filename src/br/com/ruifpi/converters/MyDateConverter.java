package br.com.ruifpi.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import br.com.caelum.iogi.exceptions.ConversionException;
import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.converter.*;
import br.com.ruifpi.dao.DaoException;

/*
 * 
 * 	Converters personalizados para melhor adequação da aplicação.
 * 
 */

@Convert(Date.class)
public class MyDateConverter implements Converter<Date> {
	
	@Override
	public Date convert(String valor, Class<? extends Date> arg1) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if(valor == null || valor.equals("")){
			return null;
		}
		try {
			Date date = dateFormat.parse(valor);	
			return date;
		} catch (ParseException e) {
		    new ConversionException("Erro de Formato de Data...");
		    return null;
		} catch (Exception e) {
			new DaoException("Erro Fatal");
			return null;
		}		
	
	}
}
