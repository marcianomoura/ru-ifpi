package br.com.ruifpi.converters;

import br.com.caelum.iogi.exceptions.ConversionException;
import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.converter.*;

/*
 * 
 * 	Converters personalizados para melhor adequação da aplicação.
 * 
 */

@Convert(double.class)
public class MyDoubleConverter  implements Converter<Double>{

	@Override
	public Double convert(String valor, Class<? extends Double> arg1) {
		if(valor == null || valor.equals("")){
			System.out.println("Entrou no Converter Double com valor null ou Zero ..." + valor);
			return 0.0d;
		}
		
		try {
			// Evitar exceção em campos numéricos enviados com virgula como separador decimal.
			return Double.parseDouble(valor.replace(",", "."));		
		} catch (Exception e) {
			new ConversionException("Erro de Conversão.");
			return 0.0d;
		}
	}
}
