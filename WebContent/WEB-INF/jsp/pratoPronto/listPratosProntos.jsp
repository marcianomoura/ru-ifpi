<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Catálago de Pratos</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
<div class="panel panel-default" style="max-height: 500px; overflow-y: scroll; overflow-x: hidden; ">
	<div class="panel-heading">Catálogo de Pratos prontos</div>
	<div class="panel-body">
		<table class="table table-responsive">
			<thead>
				<tr class="small">
			  		<th >Código</th>
			  		<th >Titulo</th>
			  		<th >Itens do Prato</th>
			  		<th >Alteração</th>
			  	</tr>
			</thead>
			
			  <c:forEach items="${listPratoProntos}" var="pratoPronto">
			  	<tbody>
			  		<tr>
				  		<td>${pratoPronto.id}</td>
				  		<td>${pratoPronto.tituloPrato}</td>
				  		<td>
				  			<a class="btn btn-info btn-sm" href='<c:url value="/pratopronto/itens?id=${pratoPronto.id}"></c:url>' >
				  				<span class="glyphicon glyphicon-search" aria-hidden="true"></span> Ver Itens
				  			</a>
				  		</td>
				  		<td> 
							<a  class="btn btn-warning btn-sm" data-toggle="tooltip" data-placement="top" title="Alterar este prato" 
								href='<c:url value="/pratopronto/alteracao?id=${pratoPronto.id}"></c:url>'>
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Alterar
							</a>
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