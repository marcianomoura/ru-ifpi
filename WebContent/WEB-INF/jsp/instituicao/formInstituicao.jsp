<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Instituições</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="row">
		<div class="col-md-5">
			<div class="panel panel-default">
				<div class="panel-heading"><h5>Cadastro de Instituição</h5></div>
				<div class="panel-body">
					<label class="small" style="color: red" > * Campos Obrigatórios</label><br>
					<br>
					<form action='<c:url value="/instituicao/save"></c:url>'>
						<input  type="hidden" name="instituicao.id" value="${instituicao.id}" >
						
						<div class="form-group">
							<label class="small">* Nome</label>
					      	<input class="form-control" type="text" name="instituicao.nome" value="${instituicao.nome}"
					      	placeholder="informe o nome da instituição" required>
						</div>
					    
					    <div class="form-group">
					    	<label class="small">* Endereço</label>
					      	<input required class="form-control" type="text" name="instituicao.endereco" value="${instituicao.endereco}"
					      	placeholder="Rua, Nº da instituição" required > 
					    </div>
					  		    
					    
					   	<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-saved" aria-hidden="true"></span>Cadastrar</button>
					    <a class="btn btn-warning" href='<c:url value="/instituicao"></c:url>'>
						    	<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Desistir
						    </a>					
					</form>	
				
				</div>
			</div>
		</div>
	
		<div class="col-md-7">
			<h4 align="center">Instituições Cadastradas</h4>
			<hr>
			<table class="table table-responsive">
				<thead>
					<tr class="small">
						<th>Id</th>
						<th>Nome</th>
						<th>Endereço</th>
						<th>Alteração</th>
						
					</tr>
				</thead>
				<c:forEach items="${instituicoes}" var="instituicao">
					<tbody>
						<tr>	
							<td>${instituicao.id}</td>
							<td>${instituicao.nome}</td>
							<td>${instituicao.endereco}</td>
							<td> 
								<a class="btn btn-warning btn-sm" data-toggle="tooltip" data-placement="top" title="Alterar os dados desta instituição"
								href='<c:url value="/instituicao/alteracao?id=${instituicao.id}"></c:url>'>
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