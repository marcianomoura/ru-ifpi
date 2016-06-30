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
	<div class="panel panel-default">            	
		<div class="panel-heading">Resultado da Votação : 
				<kbd><fmt:formatNumber value="${totalVotos}" maxFractionDigits="2"/> Votos</kbd>		    	
		</div>
		<div class="panel-body">
			<div class="col-md-4">
			    <div class="thumbnail">
			      <img class="img-responsive" src='<c:url value="/img/almoco.jpg"></c:url>' width="100%" alt="">
			    </div>
			</div>
			
			<div class="col-md-8">
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