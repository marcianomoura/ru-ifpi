<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cardápio</title>
</head>
<body>
<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading"><h5>Gerenciamento de Cardápio</h5></div>
		<div class="panel-body">		
			<form action='<c:url value="/cardapio/addItem"></c:url>'>
				<fieldset>
					<div class="form-group col-md-6">
					  <label class="small" for="">Selecione os Itens do Cardápio</label>
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
						<button  type="submit"	class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> </button>
					</div>
					
					<div class="form-group col-md-3">
						<label class="small">Total de Calorias </label>
						<small style="color: red" >${errors.from('totalCaloria')}</small>
						<input class="form-control" value="${totalCaloria}" readonly="readonly">
					</div>
				</fieldset>
			</form>
			
			<hr>
			<small style="color: red" >${errors.from('itemsDoCardapio')}</small>
			<table class="table table-hover">
				<tr class="active">
					<td>Descricao</td>
					<td>Quantidade de Calorias</td>
					<td>Classe Nutricional</td>
					<td>Remoção</td>
				</tr>
				<c:forEach var="itemlista" items="${itemsLista}">
					<tr>
						<td>${itemlista.item.descricao}</td>
						<td>${itemlista.item.valorCalorico}</td>
						<td>${itemlista.item.classeNutricional.descricao}</td>
						<td>
							<a class="btn btn-danger" data-toggle="tooltip" data-placement="top" title="Remover este item" 
							href='<c:url value="/cardapio/removeItem?id=${itemlista.item.id}"></c:url>'>
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
						</td>
					</tr>
				</c:forEach>
			</table>
				
			<form class="form-horizontal" action='<c:url value="/cardapio/save"></c:url>' method="post" >
				<fieldset>
					
					<input name="cardapio.id" value="${cardapio.id}" type="hidden">
					
					<input name="cardapio.totalCaloria" value="<fmt:formatNumber value="${totalCaloria}" maxFractionDigits="2"/>" type="hidden">
					
					<div class="form-group col-md-6">
					  <label class="small">Data do Cardápio</label>
					  <small style="color: red" >${errors.from('dataCardapio')}</small>
					  <input id="datepicker" name="cardapio.dataCardapio" value="<fmt:formatDate value="${cardapio.dataCardapio}" pattern="dd/MM/yyyy"/>" 
					    type="text" placeholder="Insira a data do cardápio" class="form-control" required="">
					</div>
					
					<div class="form-group col-md-12">
						<button  type="submit"	class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-saved" aria-hidden="true"></span></button>
					</div>
				</fieldset>
			</form>		
		</div>
	</div>
</div>

</body>
</html>