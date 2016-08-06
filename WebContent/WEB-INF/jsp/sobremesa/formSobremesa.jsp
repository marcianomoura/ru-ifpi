<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sobremesas</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
			<div class="row">
			<div class="col-md-4">
				<div class="panel panel-default">
					<div class="panel-heading"><h5>Cadastro de Sobremesas</h5></div>
					<div class="panel-body">
					
						<label class="small" style="color: red" > * Campos Obrigatórios</label><br>
						<br>
						<form action='<c:url value="/sobremesa/save"></c:url>'>
							<input  type="hidden" name="sobremesa.id" value="${sobremesa.id}" >
							
								<div class="form-group">
							    	<label class="small">* Descrição da Sobremesa</label>
							      	<input class="form-control" type="text" name="sobremesa.descricao" value="${sobremesa.descricao}"
							      	placeholder="informe o nome da sobremesa" required>
							    </div>
							    
							    <div class="form-group">
							    	<label class="small">* Vitaminas que possui</label>
							      	<input class="form-control" type="text" name="sobremesa.vitaminas" value="${sobremesa.vitaminas}"
							      	placeholder="informe as vitaminas presentes" required>
							    </div>
							    
							    <div class="form-group">
							    	<label class="small">* Benefícios à saúde</label>
							      	<input class="form-control" type="text" name="sobremesa.beneficios" value="${sobremesa.beneficios}"
							      	placeholder="informe os benefícios oferecidos à saúde" required>
							    </div>
						    
							    <div class="form-group">
							    	<label class="small">* Contra Indicações</label>
							      	<input class="form-control" type="text" name="sobremesa.maleficios" value="${sobremesa.maleficios}"
							      	placeholder="informe os casos contra indicados" required>
							    </div>
							    
							    <div class="form-group">
							    	<label class="small">* Classe Nutricional</label>
							  		
							      	<select  name="sobremesa.classeNutricional.id" class="form-control" required>
								      <option value="${sobremesa.classeNutricional.id}">${sobremesa.classeNutricional.descricao}</option>
								 		<c:forEach items="${classes}" var="classe">
								      		<option value="${classe.id}">${classe.descricao}</option>
								      	</c:forEach>
								    </select>
							    </div>	    
						   	
						   	<button type="submit" class="btn btn-primary "><span class="glyphicon glyphicon-saved" aria-hidden="true"></span> Cadastrar</button>
						    <a class="btn btn-warning" href='<c:url value="/sobremesa"></c:url>'>
						    	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Desistir
						    </a>
						    
						</form>
					</div>
				</div>
				
				</div>
			
			
			<div class="col-md-8">
				<h4 align="center">Sobremesas Cadastrados</h4>
				<hr>
					<table class="table table-responsive">
						<thead>
							<tr class="small">
								<th>Id</th>
								<th>Descrição</th>
								<th>Benefícios</th>
								<th>Contra Indicações</th>
								<th>Classe Nutricional</th>
								<th><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></th>
							</tr>
						</thead>
						<c:forEach items="${sobremesas}" var="sobremesa">
							<tbody>
								<tr>	
									<td>${sobremesa.id}</td>
									<td>${sobremesa.descricao}</td>
									<td>${sobremesa.beneficios}</td>
									<td>${sobremesa.maleficios}</td>
									<td>${sobremesa.classeNutricional.descricao}</td>
									<td> 
										<a class="btn btn-warning btn-sm" data-toggle="tooltip" data-placement="top" title="Alterar esta sobremesa"
										href='<c:url value="/sobremesa/alteracao?id=${sobremesa.id}"></c:url>'>
											 <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
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