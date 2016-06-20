<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Refeitório</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
			<div class="row">
			<div class="col-md-4">
				<div class="panel panel-default">
					<div class="panel-heading"><h5>Cadastro de Refeitórios</h5></div>
					<div class="panel-body">
					
						<label class="small" style="color: red" > * Campos Obrigatórios</label><br>
						<br>
						<form action='<c:url value="/refeitorio/save"></c:url>'>
							<input  type="hidden" name="refeitorio.id" value="${refeitorio.id}" >
							
								<div class="form-group">
							    	<label class="small">* Nome do Refeitório</label>
							      	<input class="form-control" type="text" name="refeitorio.nome" value="${refeitorio.nome}"
							      	placeholder="informe o nome do refeitorio" required>
							    </div>
							    
							    <div class="form-group">
							    	<label class="small">* Campus Vinculado</label>
							  		
							      	<select  name="refeitorio.campus.id"  class="form-control" required>
								      <option value="${refeitorio.campus.id}">${refeitorio.campus.nomeCampus}</option>
								 		<c:forEach items="${listCampus}" var="campus">
								      		<option value="${campus.id}">${campus.nomeCampus} - ${campus.instituicao.nome}</option>
								      	</c:forEach>
								    </select>
							    </div>	    
						   	
						   	<button type="submit" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-saved" aria-hidden="true"></span></button>
						    <button type="reset" class="btn btn-warning btn-lg"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>					
						</form>
					</div>
				</div>
				
				</div>
			
			
			<div class="col-md-8">
				<h4 align="center">Refeitórios Cadastrados</h4>
				<hr>
					<table class="table table-responsive">
						<thead>
							<tr class="small">
								<th>Id</th>
								<th>Nome do Refeitório</th>
								<th>Campus Vinculado</th>
								<th>Instituicao</th>
								<th><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></th>
							</tr>
						</thead>
						<c:forEach items="${refeitorios}" var="refeitorio">
							<tbody>
								<tr>	
									<td>${refeitorio.id}</td>
									<td>${refeitorio.nome}</td>
									<td>${refeitorio.campus.nomeCampus}</td>
									<td>${refeitorio.campus.instituicao.nome}</td>
									<td> 
										<a class="btn btn-warning btn-sm" data-toggle="tooltip" data-placement="top" title="Alterar os dados deste refeitorio"
										href='<c:url value="/refeitorio/alteracao?id=${refeitorio.id}"></c:url>'>
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