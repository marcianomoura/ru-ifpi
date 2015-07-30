<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listagem de Itens</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
<div class="panel panel-default">
	<div class="panel-heading">Listagem de Items Alimentares</div>
	<div class="panel-body">
		<table class="table table-hover table-bordered table-condensed">
			<tr class="active" style="font-size: 16px;">
		  		<td>Código</td>
		  		<td class="col-md-3">Descrição</td>
		  		<td>Benefícios</td>
		  		<td class="col-md-2">Contraindicações</td>
		  		<td>Inf. Nutricionais</td>
		  		<td>Valor Calórico</td>
		  		<td>Classe Nutricional</td>
		  		<td>Alteração</td>
		  	</tr>
		  <c:forEach items="${items}" var="item">
		  	<tr>
		  		<td>${item.id}</td>
		  		<td>${item.descricao}</td>
		  		<td align="center">
		  			<a class="btn btn-info btn-sm" data-trigger="hover" data-container="body"   title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
		  				data-content="${item.beneficios}"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> info
		  			</a>
		  		</td>
		  		<td>${item.maleficios}</td>
		  		<td align="center">
		  			<a class="btn btn-info btn-sm" data-trigger="hover" data-container="body" title="Informações Nutricionais" data-toggle="popover" data-placement="top" 
		  				data-content="${item.informacoesNutricionais}"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> info
		  			</a>
		  		</td>
		  		
		  		<td>${item.valorCalorico}</td>
		  		<td>${item.classeNutricional.descricao}</td>
		  		<td align="center"> 
					<a  class="btn btn-warning btn-sm" data-toggle="tooltip" data-placement="top" title="Alterar este item" 
						href='<c:url value="/item/alteracao?item.id=${item.id}"></c:url>'><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</a>
				</td>
		  		
		  	</tr>
		  </c:forEach>	  
		</table>
	</div>
</div>
</div>

</body>
</html>