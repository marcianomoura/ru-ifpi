<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Datas Disponíveis</title>
</head>
<body>
<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-heading"><label class="small"> Registrar disponibilidade de sugestões de cardápio</label></div>
				<div class="panel-body">
					<form action='<c:url value="disponibilidade/save" ></c:url>' method="post">
						<input type="hidden" name="disponibilidade.id" value="${disponibilidade.id}">
						
						<div class="form-group">
							<label class="small">Data disponível para sugestões</label>
							
							<input type="text" id="dataDisponibilizada" required class="form-control" name="disponibilidade.dataDisponibilidade"   autocomplete="off"
							value="<fmt:formatDate value="${disponibilidade.dataDisponibilidade}" pattern="dd/MM/yyyy"/>">
						</div>
						
						<div>
							<button type="submit" class="btn btn-primary">Registrar</button>
						</div>
					
					</form>			
				
				</div>
			</div>
		</div>
		
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-heading"><label class="small"> Datas disponibilizadas para sugestões de cardápio</label></div>
				<div class="panel-body">
					<table class="table table-responsive table-condensed">
						<thead>
							<tr>
								<th>Id</th>
								<th>Data</th>
								<th>Alterar</th>
								<th>Indisponibilizar</th>
							</tr>
						</thead>
						<c:forEach items="${datasDisponiveis}" var="datadisponivel">
							<tbody>
							<tr>
								
								<td>${datadisponivel.id}</td>
								<td><fmt:formatDate value="${datadisponivel.dataDisponibilidade}" pattern="dd/MM/yyyy"/></td>
								
								<c:if test="${datadisponivel.disponivel == true}">
									<td>
										<a class="btn btn-default btn-sm" disabled="disabled">Desativado</a>
									</td>
									<td>
										<a class="btn btn-default btn-sm" disabled="disabled">Desativado</a>
									</td>								
								</c:if>
								
								<c:if test="${datadisponivel.disponivel == false}">
									<td>
										<a class="btn btn-warning btn-sm" href='<c:url value="/disponibilidade/alteracao?id=${datadisponivel.id}" />'>
										Alteração</a>
									</td>
									<td>
										<a class="btn btn-danger btn-sm" href='<c:url value="/disponibilidade/remocao?id=${datadisponivel.id}" />'>
										Indisponibilizar</a>
									</td>								
								</c:if>
								
							</tr>
						</tbody>						
						</c:forEach>
						
						
					</table>
				
				
				</div>
			</div>
		</div>
		
	
	</div>



</div>

</body>
</html>