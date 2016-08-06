<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pratos</title>
</head>
<body>
<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading"><h5>Cadastro de Pratos Prontos</h5></div>
		<div class="panel-body">		
			<form action='<c:url value="/pratopronto/addItem"></c:url>'>
				<fieldset>
					<div class="form-group col-md-6">
					  <label class="small" for="">Monte o escolhendo os Itens na lista abaixo</label>
					  <div class="controls">
					    <select  name="item.id" class="form-control" required>
					    	<option></option>
							<c:forEach var="item" items="${items}"> 
								<option value="${item.id}">${item.descricao} - ${item.classeNutricional.descricao}</option>			
							</c:forEach>
					    </select>
					  </div>
					</div>
					
					<div class="form-group col-md-2">
						<br>
						<button  type="submit"	class="btn btn-primary "><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Adicionar</button>
					</div>
					
					<div class="form-group col-md-3">
						<label class="small">Total de Calorias </label>
						<input class="form-control" value="<fmt:formatNumber value="${totalCaloria}" maxFractionDigits="2" />" readonly="readonly">
					</div>
				</fieldset>
			</form>
			
			
			
			<table class="table table-responsive table table-hover">
				<thead>
					<tr class="active">
						<th class="small">Descricao</th>
						<th class="small">Total de Calorias</th>
						<th class="small">Classe Nutricional</th>
						<th class="small">Remoção</th>
					</tr>
				</thead>
				<c:forEach var="itemPratoPronto" items="${listItemPratoPronto}">
					<tbody>
						<tr>
						<td>${itemPratoPronto.item.descricao}</td>
						<td>${itemPratoPronto.item.valorCalorico}</td>
						<td>${itemPratoPronto.item.classeNutricional.descricao}</td>
						<td>
							<a class="btn btn-danger" data-toggle="tooltip" data-placement="top" title="Remover este item" 
							href='<c:url value="/pratopronto/remocao?id=${itemPratoPronto.item.id}"></c:url>'>
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
						</td>
					</tr>
					</tbody>
					
				</c:forEach>
			</table>
			<hr>	
			<form class="form-horizontal" action='<c:url value="/pratopronto/save"></c:url>' method="post" >
				<fieldset>
					
					<input name="pratoPronto.id" value="${pratoPronto.id}" type="hidden">
					
					<div class="form-group col-md-6">
					  <label class="small">Finalize o cadastro escolhendo um titulo para Prato</label>
					  <input  required name="pratoPronto.tituloPrato" value="${pratoPronto.tituloPrato}" type="text" 
					  placeholder="Ex: Baião de Dois com Frango" class="form-control">
					</div>
					
					<div class="form-group col-md-12">
						<button  type="submit"	class="btn btn-primary"><span class="glyphicon glyphicon-saved" aria-hidden="true"></span> Cadastrar</button>
						<a data-toggle="tooltip" data-placement="top" title="Cancelar operação" class="btn btn-danger" 
						href="pratopronto/clear" ><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Desistir
						</a>
					</div>
				</fieldset>
			</form>		
		</div>
	</div>
</div>

</body>
</html>