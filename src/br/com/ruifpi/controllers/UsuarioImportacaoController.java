package br.com.ruifpi.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.ruifpi.auxiliar.RepositorioMetodos;
import br.com.ruifpi.dao.DaoImplementacao;
import br.com.ruifpi.models.Usuario;
import br.com.ruifpi.models.UsuarioImportacao;
import br.com.ruifpi.util.ControleAcesso.AcessoAdministrativo;
import jxl.Sheet;
import jxl.Workbook;

@Controller
public class UsuarioImportacaoController {

	
	@Inject	private DaoImplementacao dao;
	@Inject	private Result result;
	@Inject private RepositorioMetodos repositorio;

	private List<UsuarioImportacao> usuariosArquivoXls = new ArrayList<>();
	private List<UsuarioImportacao> listUsuarioImportadoBanco = new ArrayList<>();
	private List<UsuarioImportacao> listInvalidados = new ArrayList<>();
	
	@AcessoAdministrativo
	@Path("/importacao")
	public void formUsuarioImportacao() {
		
	}
	
	@AcessoAdministrativo
	@SuppressWarnings("unchecked")
	public void removeUsuarioImportacaoNaoEncontradoXLS() throws Exception {
		listUsuarioImportadoBanco = dao.find(UsuarioImportacao.class);		
		for (UsuarioImportacao usuarioImportacaoBanco : listUsuarioImportadoBanco) {
			boolean encontrado = false;
			for (UsuarioImportacao usuarioImportacaoxls : usuariosArquivoXls) {
				if (usuarioImportacaoBanco.getMatricula().equals(usuarioImportacaoxls.getMatricula())) {
					encontrado = true;
					break;
				}
			}
			if(!encontrado){
				listInvalidados.add(usuarioImportacaoBanco);
				dao.remove(usuarioImportacaoBanco);
			}
		}
	}
	
	@AcessoAdministrativo
	public void invalidaMatriculaUsuarioSistema() throws Exception {
		List<Usuario> usuarios = new ArrayList<>();
		usuarios = repositorio.buscaUsuariosAtivos();
		for (UsuarioImportacao usuarioInvalidado : listInvalidados) {
			for (Usuario usuario : usuarios) {
				if(usuario.getMatricula().equalsIgnoreCase(usuarioInvalidado.getMatricula())){
					usuario.setMatriculado(false);
					dao.save(usuario);
					break;
				}
			}
		}
	}
	
	@AcessoAdministrativo
	public void insereNovosUsuarioImportacao() throws Exception {
		for (UsuarioImportacao usuarioXLS : usuariosArquivoXls) {
			boolean encontrada = false;
			for (UsuarioImportacao usuarioImportacao : listUsuarioImportadoBanco) {
				if(usuarioXLS.getMatricula().equalsIgnoreCase(usuarioImportacao.getMatricula())){
					encontrada = true;
					break;
				}
			}
			if(encontrada == false){	// Significa que é novato
				dao.save(usuarioXLS);
			}
		}
	}
	
	@AcessoAdministrativo
	@Path("/lerxls")
	public void lerArquivoXls(UploadedFile arquivo) {
		Workbook planilha ;
		Sheet abaPlanilha;	
		UsuarioImportacao usuarioImportacao;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			planilha = Workbook.getWorkbook(arquivo.getFile()); 
			abaPlanilha = planilha.getSheet(0);			// primeira Aba da planilha ...
			int quantLinhas = abaPlanilha.getRows(); 	// quantidades de linha da planilha...
			
			for (int i = 1; i < quantLinhas; i++) {
				usuarioImportacao = new UsuarioImportacao();
				String matricula = abaPlanilha.getCell(0, i).getContents();
				String dataNascimento = abaPlanilha.getCell(1, i).getContents();
				usuarioImportacao.setMatricula(matricula);
				java.util.Date dataFormatada = format.parse(dataNascimento);
				usuarioImportacao.setDataNascimento(dataFormatada);
				usuariosArquivoXls.add(usuarioImportacao);	// Pegando dados do arquivo .xls
			}
			
			removeUsuarioImportacaoNaoEncontradoXLS();	// Remover matriculas não encontradas no arquivo .xls
			insereNovosUsuarioImportacao();				// Inserir novas matriculas encontradas
			invalidaMatriculaUsuarioSistema();			// invalidar Acesso.
			limpaListasUtilizadas();
			result.include("sucesso", "As matriculas foram atualizadas com sucesso.");
			result.redirectTo(this).formUsuarioImportacao();
		} catch (ParseException e) {
			limpaListasUtilizadas();
			result.include("erro", "Erro na conversão de datas. Talvez exista alguma data errada no arquivo (xls). Verifique e tente novamente.");
			result.redirectTo(this).formUsuarioImportacao();
		} catch (Exception e) {
			limpaListasUtilizadas();
			result.include("erro", "Ocorreu um erro no processo de atualização de matriculas. Verifique se o arquivo "
					+ ".xls está com os dados preenchidos corretamente.");
			result.redirectTo(this).formUsuarioImportacao();
		}
		
	}
	
	public void limpaListasUtilizadas() {
		usuariosArquivoXls.clear();
		listInvalidados.clear();
		listUsuarioImportadoBanco.clear();
	}
}
