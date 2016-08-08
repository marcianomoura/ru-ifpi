<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resultado Votacao</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>
		
<div class="col-md-12">

<div style="margin-top: 15px;  margin-bottom: 10px;" >
	<a class="btn btn-default" href='<c:url value="/sugestoes" />'> 
	<span class="glyphicon glyphicon-hand-left" aria-hidden="true"></span> Voltar</a>
</div>
	
	<div class="panel panel-default">            	
		<div class="panel-heading">Quantidade de votos : 
				<kbd><fmt:formatNumber value="${totalVotos}" maxFractionDigits="2"/> Votos</kbd>		    	
		</div>
		<div class="panel-body">
			<div class="col-md-12">
				<table class="table table-responsive">
					<thead>
						<tr class="small">
							<th>Prato</th>
							<th>Quantidade</th>
							<th>%</th>
														
						</tr>
					</thead>
					<c:forEach items="${mapaResultado}" var="itemsugerido">
						<tbody>								
							<tr>
								<td>${itemsugerido.key}</td>									
								
								<td><span class="badge">${itemsugerido.value}</span> votos</td>
								
								<td><span class="badge">
										<fmt:formatNumber maxFractionDigits="2" value="${(itemsugerido.value * 100) / totalVotos}" />
									% </span>
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