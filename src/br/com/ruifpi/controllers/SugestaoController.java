package br.com.ruifpi.controllers;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.ItemCardapio;
import br.com.ruifpi.models.SugestaoCardapio;
import br.com.ruifpi.util.RestricaoAcesso;
import br.com.ruifpi.util.RestricaoAcesso.AcessoUsuario;

@Controller
public class SugestaoController {

	@Inject	private Result result;
	@Inject	private Validator validator;
	@Inject private DaoImplementacao daoImplementacao;
			private List<Object> objects = new ArrayList<>();
			private SimpleDateFormat formatadorData = new SimpleDateFormat("yyyy-MM-dd");
	
	@RestricaoAcesso		
	@AcessoUsuario
	@Path("/sugestao")		
	public void listSugestaoCardapio() {
	//	listaAllSugestoes();
	}
	
	@SuppressWarnings("unchecked")
	public List<SugestaoCardapio> listaAllSugestoes() {	
		return daoImplementacao.find(SugestaoCardapio.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<SugestaoCardapio> listaSugestaoByData(Date dataSugestao) {
		List<SugestaoCardapio> sugestaoCardapios = new ArrayList<>();
		try {
			String dataAtual = formatadorData.format(new Date());
			java.sql.Date dataBanco = new java.sql.Date(formatadorData.parse(dataAtual).getTime());			
			objects = daoImplementacao.find(SugestaoCardapio.class);
			for (Object sugestaoCardapio : objects) {
				if(((SugestaoCardapio)sugestaoCardapio).getDataSugestao().equals(dataBanco) ){
					sugestaoCardapios.add((SugestaoCardapio) sugestaoCardapio);
				}
			}
		} catch (Exception e) {
		
		}
		
		
		return sugestaoCardapios;
	}
	
	public boolean verificaSugestaoRealizada(Date dataSugestao, Long idUsuario) {
	
		return false;
	}
	
	public void saveSugestao(SugestaoCardapio sugestaoCardapio, Long idUsuario) {
	
	}
	
	public List<ItemCardapio> itensAlimentares() {
		
		return null;
	}
}
