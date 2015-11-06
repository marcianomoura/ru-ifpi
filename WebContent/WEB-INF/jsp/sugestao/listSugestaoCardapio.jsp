<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sugestões</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12" style="margin-top: 20px;">
<div class="row">
	<div class="col-md-6">
		<div class="panel panel-default">
			<div class="panel-heading">Visualize as sugestões de cardápio</div>
			<div class="panel-body">
				<form action='<c:url value="/sugestao/totalizasugestoes" ></c:url>' method="get">
					<div class="form-group">
						<label class="small" for="">Selecione a data desejada</label>
						<div class="controls">
							<div class="row">
								<div class="col-md-9">
							    	<select  name="disponibilidade.id" class="form-control" required>
								    	<option></option>
								    	
										<c:forEach var="disponibilidade" items="${disponibilidades}"> 
											<option value="${disponibilidade.id}"> <fmt:formatDate value="${disponibilidade.dataDisponibilidade}"/></option>			
										</c:forEach>
								    </select>						    
							    </div>
							    
							    <div class="col-md-3">
							  		<button type="submit" class="btn btn-primary"> Ver resultado</button>
							  	</div>
							</div>					  									  					    				    
					 	</div>
					</div>								
				</form>	
			</div>
		</div>	
	</div>

<div class="col-md-6">
	<div class="panel panel-default">
		<div class="panel-heading">Resultado da Votação</div>
		<div class="panel-body">
			
				<c:forEach items="${itensSugeridosCardapio}" var="itemsugerido">	
					<li class="list-group-item"><span class="badge">${itemsugerido.value}</span>${itemsugerido.key}</li>
				</c:forEach>						
		</div>	
	</div>	
</div>
</div>
</div>
</body>
</html>