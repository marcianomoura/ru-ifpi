<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Avaliação</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<c:if test="${cardapioSolicitado.id > 0}">
		<div class="custom_well">
			<label><strong>Dados sobre o cardápio</strong></label>
			<table class="table table-responsive table-condensed">
				<thead>
					<tr style="background-color: #D6CECE;">
						<td>Cod. Cardapio</td>
						<td>Data</td>
						<td>Total de Calorias</td>
						<td>Ver Itens</td>
						<td>Fechar</td>				
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${cardapioSolicitado.id}</td>				
						<td><fmt:formatDate value="${cardapioSolicitado.dataCardapio}" pattern="dd/MM/yyyy"/></td>
						<td>${cardapioSolicitado.totalCaloria} kcal</td>
						<td>
							<a target="_blank" class="btn btn-default btn-sm" href='<c:url value="/cardapio/itens?cardapio.id=${cardapioSolicitado.id}"/>'>
							 Ver itens
							 </a>
						</td>
						<td>
							<a  class="btn btn-danger btn-sm" href='<c:url value="/avaliacoes"/>'>
							 Fechar
							 </a>
						</td>
					</tr>
				</tbody>
				
			</table>
		</div>
	</c:if>
<div class="panel panel-default">
	<div class="panel-heading"><label><strong></strong></label> 		
			Total de avaliações : <span class="badge"> ${listaAvaliacoes.size()}</span> 
			Media das avaliações : <span class="badge"><fmt:formatNumber value="${mediaAvaliacao}" maxFractionDigits="2"/></span> 
	</div>
	
	<div class="panel-body">
		<form action='<c:url value="/avaliacao/cardapio_data" />' method="get">
			<div class="col-md-4">
				<div class="form-group">
					<label class="small">Escolha a data do cardápio para ver as avaliações</label>
					<input id="dataCardapio" class="form-control" name="dataCardapio" required onfocus autocomplete="off" >
				</div>
			</div>
			<div class="col-md-1">
				<br>
				<button class="btn btn-primary btn-lg"  type="submit"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>	
			</div>
			
		</form>
		
		<div class="col-md-12" style="max-height: 300px; overflow-y: scroll; overflow-x: hidden; ">
			<table class="table table-responsive">
				<thead>
					<tr>
						<td><strong>Aluno</strong></td>
						<td><strong>Matricula</strong></td>
						<td align="center"><strong>Nota Avaliativa</strong></td>								
					</tr>
				</thead>
				<c:forEach items="${listaAvaliacoes}" var="avaliacao">
				<tbody>
					<tr class="text-capitalize">
						<td>${avaliacao.usuario.nome}</td>
						<td>${avaliacao.usuario.matricula}</td>
						<td align="center"><span class="badge">${avaliacao.notaAvaliativa}</span></td>
					</tr>
				</tbody>								
					
				</c:forEach>
			</table>	
		</div>
	</div>
	
	
</div>
</div>
</body>
</html>