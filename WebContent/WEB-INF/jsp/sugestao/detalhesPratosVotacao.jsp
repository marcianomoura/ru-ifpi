<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Detalhes dos Pratos</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading">Cardápios Propostos</div>
		<div class="panel-body">
		<div style="margin-top: 15px;  margin-bottom: 10px;" >
			<a class="btn btn-default" href='<c:url value="/sugestoes" />'>
			<span class="glyphicon glyphicon-hand-left" aria-hidden="true"></span> Voltar</a>
		</div>
			<table class="table table-responsive">
				<thead>
					<tr class="small">
						<th>Opção</th>
						<th>Prato</th>
						<th>Sobremesa</th>
						<th>Total de Calorias</th>
						<th>Votar</th>
					</tr>
				</thead>
				<c:forEach items="${listItemSugestaoPrato}" var="itemPrato">
					<tbody>
						<tr>
							<td><button class="btn btn-default btn-sm">${itemPrato.id}</button></td>
							<td>${itemPrato.pratoPronto.tituloPrato}</td>
							<td>${itemPrato.sobremesa.descricao}</td>
							<td>${itemPrato.totalCaloria}</td>
							<td>
								<form action='<c:url value="/votacao/contabiliza" />' method="post">
									<input type="hidden" name="votacao.id" value="${votacao.id}">
									<input type="hidden" name="votacao.sugestaoPrato.id" value="${sugestaoPrato.id}">	
									<input type="hidden" name="votacao.itemSugestaoPratoPronto.id" value="${itemPrato.id}">
									<button type="submit" class="btn btn-primary btn-sm">Votar Neste</button>
								</form>
							</td>											
						</tr>
					</tbody>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
</body>
</html>