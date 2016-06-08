<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cardápio Semanal</title>
</head>
<body>
<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel panel-heading">Cardápios da Semana</div>	
			<div class="panel-body">
				<form  method="get" action='<c:url value="/cardapio/periodo"></c:url>'  >
				
					<div class="col-md-4">
						<label class="small">Forneca o primeiro dia da semana que deseja pesquisar</label>
						<div class="form-group">
							<input required class="form-control" type="text" name="periodoInicial" value="<fmt:formatDate value="${periodoInicial}" pattern="dd/MM/yyyy"/>" 
							id="dataPesquisaCardapio1">
						</div>
					</div>
					<div class="col-md-4">
						<label class="small"> Forneca o ultimo dia da semana que deseja pesquisar</label>
						<div class="form-group">
							<input required class="form-control" type="text" name="periodoFinal" value="<fmt:formatDate value="${periodoFinal}" pattern="dd/MM/yyyy"/>" 
							id="dataPesquisaCardapio2">
						</div>
					
					</div>
				
					<div class="col-md-2">
						<br>
						<button type="submit" class="btn btn-primary"> Pesquisar Cardápios</button>
					</div>
				</form>
			</div>
	</div>
	
	<table class="table table-responsive">
			<thead>
				<tr align="center" class="small">
			  		<th >Código</th>
			  		<th >Data do Cardápio</th>
			  		<th >Total de Calorias</th>
			  		<th >Itens do Cardápio</th>
			  		<th >Alteração</th>
			  	</tr>
			</thead>
			
			  <c:forEach items="${cardapios}" var="cardapio">
			  	<tbody>
			  		<tr align="center">
				  		<td>${cardapio.id}</td>
				  		<td><fmt:formatDate value="${cardapio.dataCardapio}" pattern="dd/MM/yyyy"/></td>
				  		<td>${cardapio.totalCaloria}</td>
				  		<td>
				  			<a target="blank" href='<c:url value="/cardapio/itens?cardapio.id=${cardapio.id}"></c:url>' >
				  				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> detalhes
				  			</a>
				  		</td>
				  		<td> 
							<a  class="btn btn-warning btn-sm" data-toggle="tooltip" data-placement="top" title="Alterar este cardapio" 
								href='<c:url value="/carpapio/alteracao?cardapio.id=${cardapio.id}"></c:url>'><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							</a>
						</td>		  		
				  	</tr>
			  	</tbody>		  	
			  </c:forEach>	  
		</table>
	

</div>

</body>
</html>