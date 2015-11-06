package br.com.ruifpi.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.ruifpi.auxiliar.MetodosUtilImplementacao;
import br.com.ruifpi.dao.DaoException;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Disponibilidade;
import br.com.ruifpi.models.ItemSugestaoCardapio;
import br.com.ruifpi.models.SugestaoCardapio;
import br.com.ruifpi.util.RestricaoAcesso;
import br.com.ruifpi.util.RestricaoAcesso.AcessoAdministrativo;
import br.com.ruifpi.util.RestricaoAcesso.AcessoUsuario;

@Controller
public class DisponibilidadeController {

	@Inject
	private Result result;
	@Inject
	private DaoImplementacao dao;
	@Inject
	private Validator validator;
	private SimpleDateFormat formatDateUtil = new SimpleDateFormat();
	@Inject MetodosUtilImplementacao metodosUtil;
	private List<Disponibilidade> disponibilidadeUtil = new ArrayList<Disponibilidade>();
	
	@RestricaoAcesso
	@AcessoAdministrativo
	@Path("/disponibilidade")
	public void formDisponibilidade() {
		listDatasDisponibilizadas();
	}
	
	@SuppressWarnings("unchecked")
	@RestricaoAcesso
	@AcessoAdministrativo
	public List<Disponibilidade> listDatasDisponibilizadas() {
		
		try {
			disponibilidadeUtil = dao.find(Disponibilidade.class);
			new Disponibilidade().ordenaPorData(disponibilidadeUtil);
			Collections.reverse(disponibilidadeUtil);
			result.include("datasDisponiveis", disponibilidadeUtil);
			return disponibilidadeUtil;
		} catch (Exception e) {		
			return disponibilidadeUtil;
		}		
	}
	
	@RestricaoAcesso
	@AcessoAdministrativo
	@Path("/disponibilidade/save")
	public void save(Disponibilidade disponibilidade) {
		if(!validaDisponibilidade(disponibilidade)){
			validator.onErrorRedirectTo(this).formDisponibilidade();
		}
		try {
			dao.save(disponibilidade);
			result.include("sucesso", "Data para sugestões de cardápio disponibilidaza com sucesso.");
			result.redirectTo(this).formDisponibilidade();
		} catch (Exception e) {
			result.include("erro", "Erro - Registro não efetuado. Verifique os dados informados e tente novamente.");
			result.redirectTo(this).formDisponibilidade();;
		}
		
	}	
	
	@RestricaoAcesso
	public boolean validaDisponibilidade(Disponibilidade disponibilidade) {				
		if(disponibilidade.getDataDisponibilidade().before(validaFormatData()) || disponibilidade.getDataDisponibilidade() == null){
			validator.add(new I18nMessage("Data disponibilizada", "data.disponibilizada.invalida"));
			return false;
		}
		if(verificaDataDisponibilizada(disponibilidade) && disponibilidade.getId() == null){
			validator.add(new I18nMessage("Data disponibilizada", "data.disponibilizada.existente"));
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@RestricaoAcesso
	public boolean verificaDataDisponibilizada(Disponibilidade disponibilidade) {
		boolean validador = false;
		
		try {
			formatDateUtil.applyPattern("yyyy-MM-dd");
			String dataFormatada = formatDateUtil.format(disponibilidade.getDataDisponibilidade());
			java.sql.Date dataSql = new java.sql.Date(formatDateUtil.parse(dataFormatada).getTime());
			disponibilidadeUtil = metodosUtil.buscaDatasDisponiveisSugestaoCardapio();
			for (Disponibilidade disponibilidade2 : disponibilidadeUtil) {
				if(dataSql.equals(disponibilidade2.getDataDisponibilidade())){
					validador = true;
					break;
				}
			}
			if(disponibilidadeUtil.size() >= 1){
				validador = true;
			}
		} catch (Exception e) {
			throw new DaoException("Erro no processamento das datas");
		}
		
		return validador;
	}
	
	@RestricaoAcesso
	public Date validaFormatData() {
		Date dataFormatada = new Date();
		try {
			formatDateUtil.applyPattern("dd/MM/yyyy");
			dataFormatada = formatDateUtil.parse(formatDateUtil.format(new Date()));
		} catch (ParseException e) {
			System.out.println("Erro na Conversão de datas...");
			e.printStackTrace();
		}
		return dataFormatada;
	}
	
	@RestricaoAcesso
	@AcessoAdministrativo
	@Path("/disponibilidade/alteracao")
	public void alteraDataDisponivel(Long id) {
		try {
			Disponibilidade disponibilidade = (Disponibilidade) dao.findById(Disponibilidade.class, id);
			result.include("disponibilidade", disponibilidade);
			result.redirectTo(this).formDisponibilidade();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na tentativa de alterar a data. Verifique e tente novamente.");
			result.redirectTo(this).formDisponibilidade();
		}
		
	}
	
	@RestricaoAcesso
	@AcessoAdministrativo
	@Path("/disponibilidade/remocao")
	public void indisponibilzarSugestoes(Long id) {
		try {
			Disponibilidade disponibilidade = (Disponibilidade) dao.findById(Disponibilidade.class, id);
			disponibilidade.setDisponivel(true);
			dao.save(disponibilidade);
			result.include("sucesso", "A data foi indisponibilizada com sucesso.");
			result.redirectTo(this).formDisponibilidade();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro ao indisponibilizar a data de sugestões. Verifique e tente novamente.");
			result.redirectTo(this).formDisponibilidade();
		}
		
	}
	
	@RestricaoAcesso
	@AcessoUsuario
	@Path("/disponibilidade/validas")
	public void listDisponibilidadesValidas() {
		List<Disponibilidade> disponibilidades = metodosUtil.buscaDatasDisponiveisSugestaoCardapio();
		formatDateUtil.applyPattern("yyyy-MM-dd");
		try {
			String dataHojeFormatada = formatDateUtil.format(new Date());
			java.sql.Date dataSql = new java.sql.Date(formatDateUtil.parse(dataHojeFormatada).getTime());
			for (Disponibilidade disponibilidade : disponibilidades) {
				if(disponibilidade.getDataDisponibilidade().after(dataSql)){
					disponibilidadeUtil.add(disponibilidade);
				}
			}
			if(disponibilidades.isEmpty()){
				result.include("erro", "Ainda não foi disponibilizada data para sugestões de cardápio.");
			}else{
				result.include("disponibilidadesValidas", disponibilidadeUtil);
				result.include("sucesso", "As datas foram carregadas com sucesso");
			}
			
				result.redirectTo(SugestaoController.class).formSugestao();
		} catch (Exception e) {
			result.include("erro", "Ocorreu um erro na listagem de datas disponiveis para sugestao de cardapio.");
			result.redirectTo(SugestaoController.class).formSugestao();
		}
		
	}
	
	@RestricaoAcesso
	public Disponibilidade dataDisponivel() {
		Disponibilidade disponibilidadeEncontrada = null;
		List<Disponibilidade> disponibilidades = metodosUtil.buscaDatasDisponiveisSugestaoCardapio();
		for (Disponibilidade disponibilidade : disponibilidades) {
			disponibilidadeEncontrada = disponibilidade;
		}
		return disponibilidadeEncontrada;
	}
	
	@RestricaoAcesso
	public void mostraResultadoSugestoesHome(Disponibilidade disponibilidade) {
		List<SugestaoCardapio> sugestoesEncontradas = new ArrayList<SugestaoCardapio>();
		List<ItemSugestaoCardapio> todosItensSugeridos = new ArrayList<ItemSugestaoCardapio>();
		if(disponibilidade == null){
			result.include("erro", "Resultado da votação não está disponível");
		}else{
			try {
				List<SugestaoCardapio> sugestoes = dao.find(SugestaoCardapio.class);
				for (SugestaoCardapio sugestaoCardapio : sugestoes) {
					if(sugestaoCardapio.getDisponibilidade().getId().equals(disponibilidade.getId())){
						sugestoesEncontradas.add(sugestaoCardapio);
					}			
				}
				if(!sugestoesEncontradas.isEmpty()){
					for (SugestaoCardapio sugestaoCardapio : sugestoesEncontradas) {
						for (ItemSugestaoCardapio itemSugestaoCardapio : sugestaoCardapio.getItemSugerido()) {
							todosItensSugeridos.add(itemSugestaoCardapio);
						}
					}
					HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
					for (ItemSugestaoCardapio itemSugestaoCardapio : todosItensSugeridos) {
						Integer contador = hashMap.get(itemSugestaoCardapio.getItem().getDescricao());
						if(contador == null){
							contador = 0;
						}
						hashMap.put(itemSugestaoCardapio.getItem().getDescricao(), contador + 1);
					}
					result.include("itensSugeridosCardapio", hashMap);
					result.include("dataCrdapio", disponibilidade.getDataDisponibilidade());
					result.include("totalSugestoes", sugestoesEncontradas.size());
								
				}else{
					result.include("sucesso", "Não foram encontradas sugestões para o cardapio disponibilidade nesta data.");
				}
				
			} catch (Exception e) {
					result.include("erro", "Erro ao contabilizar as sugestões de cardápio para esta data. Tente mais tarde");
			}
		}		
	}
}
















