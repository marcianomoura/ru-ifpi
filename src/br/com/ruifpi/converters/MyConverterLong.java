package br.com.ruifpi.converters;


import com.thoughtworks.xstream.converters.ConversionException;
import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.converter.*;


@Convert(Long.class)
public class MyConverterLong implements Converter<Long>  {

	@Override
	public Long convert(String valor, Class<? extends Long> arg1) {
		if(valor == null || valor.equals("")){
			System.out.println("Entrou no converter LONG, mas com valor vazio ou null...");
			return null;
		}
		
		try {
			System.out.println("Entrou no Converter Long ... " + valor);
			return Long.parseLong(valor.replace(".", ""));
		} catch (Exception e) {
			System.out.println("Retornou ZERO...");
			new ConversionException("Valor de Retorno depois da exceção");
			return null;
		}
	}
	
}
