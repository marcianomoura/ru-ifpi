<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sugestões disponíveis</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12" style="margin-top: 20px;">
	
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">Sugestões de cardápios disponíveis para votação</div>
				<div class="panel-body">
					<table class="table table-responsive">
						<thead>
							<tr class="small">
								<th>Id</th>
								<th>Data do cardápio</th>
								<th>Data final votação</th>
								<th>Votação de Cardápio</th>
							</tr>
						</thead>
						<c:forEach items="${listSugestaoPratos}" var="sugestaoPrato">
							<tbody>
								<tr>
									<td>${sugestaoPrato.id}</td>
									<td><fmt:formatDate value="${sugestaoPrato.dataDisponibilizada}" /></td>
									<td><fmt:formatDate value="${sugestaoPrato.dataFinalVotacao}"/></td>
									<td>
										<a  class="btn btn-info" href='<c:url value="/sugestao/itens?id=${sugestaoPrato.id}"></c:url>'>
										Veja as opções
										</a>
									</td>
								</tr>
							</tbody>
						</c:forEach>
					</table>
				</div>	
			</div>	
		</div>
	</div>

</body>
</html>