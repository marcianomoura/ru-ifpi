<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Detalhes do Cardapio</title>

<c:import url="/base.jsp"></c:import>
</head>
<body>


<div style="margin-top: 15px;  margin-bottom: 10px; margin-left: 14px;" >
	<button class="btn btn-danger btn-sm" onclick="window.close()"> Fechar esta Pagina</button>	
</div>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading">Itens do Cardápio</div>
		<div class="panel-body">
			<table class="table table-responsive">
				<thead>
					<tr class="active">
						<td>Descricao</td>
						<td>Quantidade de Calorias</td>
						<td>Classe Nutricional</td>
					</tr>
				</thead>
				
				<c:forEach var="itemlista" items="${itemCardapios}">
					<tbody>
						<tr>
							<td>${itemlista.item.descricao}</td>
							<td>${itemlista.item.valorCalorico}</td>
							<td>${itemlista.item.classeNutricional.descricao}</td>
						</tr>
					</tbody>
					
				</c:forEach>
			</table>
		</div>
	</div>
</div>

</body>
</html>