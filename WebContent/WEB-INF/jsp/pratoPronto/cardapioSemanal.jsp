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
				<form  method="get" action='<c:url value="/prato/listpratosemanal"></c:url>'  >
				
					<div class="col-md-4">
						<label class="small">Forneca o primeiro dia da semana que deseja pesquisar</label>
						<div class="form-group">
							<input required class="form-control" type="text" name="dataInicioSemana" 
							value="<fmt:formatDate value="${dataInicioSemana}" pattern="dd/MM/yyyy"/>" id="dataPesquisaCardapio1">
						</div>
					</div>
					<div class="col-md-4">
						<label class="small"> Forneca o ultimo dia da semana que deseja pesquisar</label>
						<div class="form-group">
							<input required class="form-control" type="text" name="dataFinalSemana" 
							value="<fmt:formatDate value="${dataFinalSemana}" pattern="dd/MM/yyyy"/>" id="dataPesquisaCardapio2">
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
				<tr class="small">
			  		<th >Data do Cardápio</th>
			  		<th >Prato</th>
			  		<th >Sobremesa</th>
			  		<th >Total de Calorias</th>
			  		<th >Itens do Cardápio</th>
			  	</tr>
			</thead>
			
			  <c:forEach items="${listPratosSemana}" var="prato">
			  	<tbody>
			  		<tr>
				  		
				  		<td><fmt:formatDate value="${prato.dataCardapio}" pattern="dd/MM/yyyy"/></td>
				  		<td>${prato.pratoPronto.tituloPrato}</td>
				  		<td>${prato.sobremesa.descricao}</td>
				  		<td>${prato.totalCaloria}</td>
				  		<td>
				  			<a target="blank" href='<c:url value="/pratopronto/itens?id=${prato.pratoPronto.id}"></c:url>' >
				  				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> detalhes
				  			</a>
				  		</td>
				  	</tr>
			  	</tbody>		  	
			  </c:forEach>	  
		</table>
	

</div>

</body>
</html>