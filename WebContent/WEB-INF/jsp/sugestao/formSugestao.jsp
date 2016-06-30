<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sugestão de Pratos</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading"><label class="small"> Preparando pratos para votação</label></div>
		<div class="panel-body">		
			<form action='<c:url value="/sugestao/add"></c:url>'>
				<div class="row">
					<fieldset>
						<div class="form-group col-md-4">
						  <label class="small" for="">Esolha o prato em seu catálogo de pratos prontos.</label>
						  <div class="controls">
						    <select  name="itemSugestaoPratoPronto.pratoPronto.id" class="form-control" required>
						    	<option></option>
								<c:forEach var="pratopronto" items="${listPratoProntos}"> 
									<option value="${pratopronto.id}">${pratopronto.tituloPrato}</option>			
								</c:forEach>
						    </select>
						  </div>
						</div>
					
						<div class="form-group col-md-3">
						  <label class="small" for="">Escolha a Sobremesa</label>
						  <div class="controls">
						    <select  name="itemSugestaoPratoPronto.sobremesa.id" class="form-control" required>
						    	<option></option>
								<c:forEach var="sobremesa" items="${sobremesas}"> 
									<option value="${sobremesa.id}">${sobremesa.descricao}</option>			
								</c:forEach>
						    </select>
						  </div>
						</div>
						
						<div class="col-md-2">
						<br>
							<button type="submit" class="btn btn-primary">Incluir</button>
						</div>						
					</fieldset>
				</div>
				
				
				<hr>
			</form>
			
			<table class="table table-responsive">
				<thead>
					<tr class="small">
						<th>Prato</th>
						<th>Sobremesa</th>
						<th>Total de Calorias</th>
						<th>remoção</th>
					</tr>
				</thead>
				<c:forEach items="${listItemSugestaoPrato}" var="itemPrato">
					<tbody>
						<tr>
							<td>${itemPrato.pratoPronto.tituloPrato}</td>
							<td>${itemPrato.sobremesa.descricao}</td>
							<td>${itemPrato.totalCaloria}</td>
							<td>
								<a class="btn btn-danger btn-sm" href='<c:url value="/sugestao/remocao?id=${itemPrato.pratoPronto.id}"></c:url>'>
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
							</td>											
						</tr>
					</tbody>
				</c:forEach>
			</table>
			<hr>
			<form class="form" action='<c:url value="/sugestao/save"></c:url>' method="post" >				
				<div class="row">
					<input name="sugestao.id" value="${sugestao.id}" type="hidden">
					
					
					<div class="form-group col-md-3">
						<label class="small"> Data Disponibilizada para o cardápio escolhido</label>
						<input required id="dataDisponibilizada" class="form-control" name="sugestaoPrato.dataDisponibilizada" 
						value="<fmt:formatDate value="${sugestaoPrato.dataDisponibilizada}" />" type="text">	
					</div>
					
					<div class="form-group col-md-3">
						<label class="small"> Data Final para votação</label>
						<input required id="datafinalvotacao" class="form-control" name="sugestaoPrato.dataFinalVotacao" 
						value="<fmt:formatDate value="${sugestaoPrato.dataFinalVotacao}"/>" type="text">	
					</div>
					
				</div>
				<div class="row">
					<div class="form-group col-md-2">
						<button  type="submit" class="btn btn-primary btn-block">Finalizar Sugestões</button>
					</div>
					
					<div class="form-group col-md-2">
						<a data-toggle="tooltip" data-placement="top" title="Cancelar operação" class="btn btn-danger btn-block" 
							href='<c:url value="/sugestao/clear"></c:url>' >Desistir do Cadastro</a>
					</div>
					
				</div>
			</form>
			
		</div>
	</div>
</div>
</body>
</html>