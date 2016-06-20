<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Detalhes do Prato</title>

<c:import url="/base.jsp"></c:import>
</head>
<body>


<div style="margin-top: 15px;  margin-bottom: 10px; margin-left: 14px;" >
	<button class="btn btn-danger btn-sm" onclick="window.close()"> Fechar esta Pagina</button>	
</div>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading">Componentes do Prato do Dia</div>
		<div class="panel-body">
			<table class="table table-responsive table table-hover">
				<thead>
					<tr class="active">
						<th class="small">Descricao</th>
						<th class="small">Total de Calorias</th>
						<th class="small">Classe Nutricional</th>
					</tr>
				</thead>
				<c:forEach var="itemCardapio" items="${itensCardapio}">
					<tbody>
						<tr>
						<td>${itemCardapio.item.descricao}</td>
						<td>${itemCardapio.item.valorCalorico}</td>
						<td>${itemCardapio.item.classeNutricional.descricao}</td>
					</tr>
					</tbody>
					
				</c:forEach>
			</table>
		</div>
	</div>
</div>

</body>
</html>