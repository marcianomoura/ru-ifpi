<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Campus</title>
</head>
<body>
<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>


<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading"><h5>Gerenciamento de Campus</h5></div>
			<div class="panel-body">
				<div class="col-md-5">
				<div class="panel panel-default">
					<div class="panel-heading"><h5>Cadastro de Campus</h5></div>
					<div class="panel-body">
					
						<label class="small" style="color: red" > * Campos Obrigatórios</label><br>
						<br>
						<form action='<c:url value="/campus/save"></c:url>'>
							<input  type="hidden" name="campus.id" value="${campus.id}" >
							
							<div class="form-group">
						    <label class="small">* Nome do Campus</label>
						    	<small style="color: red" >${errors.from('nomeCampus')}</small>
						      	<input class="form-control" type="text" name="campus.nomeCampus" value="${campus.nomeCampus}"
						      	 placeholder="informe o nome do campus" required>
						    </div>
						    <div class="form-group">
						    					  	<label class="small">* Instituição vinculada</label>
						  		<small style="color: red" >${errors.from('instituicao')}</small>
						      	<select  name="campus.instituicao.id" class="form-control" required>
							      <option value="${campus.instituicao.id}">${campus.instituicao.nome}</option>
							      <c:forEach items="${instituicoes}" var="instituicao">
							      		<option value="${instituicao.id}">${instituicao.nome}</option>
							      </c:forEach>
							    </select>
						    </div>	    
						   	<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-saved" aria-hidden="true"></span> Cadastrar</button>
						    <a class="btn btn-warning" href='<c:url value="/campus"></c:url>'>
						    	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Desistir
						    </a>					
						</form>
					</div>
				</div>
				
				</div>
			
			
			<div class="col-md-7">
				<h4 align="center">Campus Cadastrados</h4>
				<hr>
					<table class="table table-responsive">
						<thead>
							<tr class="small">
								<th>Id</th>
								<th>Nome do Campus</th>
								<th>Instituição</th>
								<th>Alteração</th>
								
							</tr>
						</thead>
						<c:forEach items="${listCampus}" var="campus">
							<tbody>
								<tr>	
									<td>${campus.id}</td>
									<td>${campus.nomeCampus}</td>
									<td>${campus.instituicao.nome}</td>
									<td> 
										<a class="btn btn-warning btn-sm" data-toggle="tooltip" data-placement="top" title="Alterar os dados deste campus"
										href='<c:url value="/campus/alteracao?id=${campus.id}"></c:url>'>
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
</div>
</body>
</html>