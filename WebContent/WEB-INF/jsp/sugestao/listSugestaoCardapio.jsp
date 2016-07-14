<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sugest�es dispon�veis</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12" style="margin-top: 20px;">	
	<div class="panel panel-default">
		<div class="panel-heading">Sugest�es de card�pios dispon�veis para vota��o</div>
		<div class="panel-body">
			<table class="table table-responsive">
				<thead>
					<tr class="small">
						<th>Id</th>
						<th>Data do card�pio</th>
						<th>Data final vota��o</th>
						<th>Pratos</th>
						<th>Resultado Parcial</th>
					</tr>
				</thead>
				<c:forEach items="${listSugestaoPratos}" var="sugestaoPrato">
					<tbody>
						<tr>
							<td>${sugestaoPrato.id}</td>
							<td><fmt:formatDate value="${sugestaoPrato.dataDisponibilizada}" /></td>
							<td><fmt:formatDate value="${sugestaoPrato.dataFinalVotacao}"/></td>
							<td>
								<a  class="btn btn-primary" href='<c:url value="/sugestao/itens?id=${sugestaoPrato.id}"></c:url>'>
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span> Pratos
								</a>
							</td>
							<td>
								<a  class="btn btn-default" href='<c:url value="/votacao/resultado?id=${sugestaoPrato.id}"></c:url>'>
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span> Vota��o
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