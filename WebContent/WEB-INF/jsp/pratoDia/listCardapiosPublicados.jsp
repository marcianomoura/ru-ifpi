<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cardápios Publicados</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="panel panel-default" style="max-height: 500px; overflow-y: scroll; overflow-x: hidden; ">
		<div class="panel-heading">Cardápios Publicados</div>
		<div class="panel-body">
			<table class="table table-responsive">
				<thead>
					<tr class="small">
				  		<th >Data do Cardápio</th>
				  		<th >Tipo</th>
				  		<th >Sobremesa</th>
				  		<th >Total Calorias</th>
				  		<th >Itens do Prato</th>
				  		<th >Alteração</th>
				  	</tr>
				</thead>
				
				  <c:forEach items="${pratosPublicados}" var="pratoDia">
				  	<tbody>
				  		<tr>
					  		<td><fmt:formatDate pattern="dd/MM/yyyy" value="${pratoDia.dataCardapio}" /></td>
					  		<td>${pratoDia.tipoPrato.descricao}</td>
					  		<td>${pratoDia.sobremesa.descricao}</td>
					  		<td>${pratoDia.totalCaloria}</td>
					  		<td>
					  			<a target="_blank" href='<c:url value="/pratodia/itens?id=${pratoDia.pratoPronto.id}"></c:url>' >
					  				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> detalhes
					  			</a>
					  		</td>
					  		<td> 
								<a  class="btn btn-warning btn-sm" data-toggle="tooltip" data-placement="top" title="Alterar este pratoDia" 
									href='<c:url value="/pratodia/alteracao?id=${pratoDia.id}"></c:url>'><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
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