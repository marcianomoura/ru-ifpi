<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sugestão de itens</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading"><h5>Sugestão de Cardápio</h5></div>
		<div class="panel-body">		
			<form action='<c:url value="/sugestao/add"></c:url>'>
				<fieldset>
					<div class="form-group col-md-6">
					  <label class="small" for="">Selecione os Itens disponíveis</label>
					  <div class="controls">
					    <select  name="item.id" class="form-control" required>
					    	<option></option>
							<c:forEach var="item" items="${items}"> 
								<option value="${item.id}">NOME : ${item.descricao} - CLASSIFICACAO :  ${item.classeNutricional.descricao}</option>			
							</c:forEach>
					    </select>
					  </div>
					</div>
					
					<div class="form-group col-md-2">
						<br>
						<button  type="submit"	class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> </button>
					</div>
					
					<div class="form-group col-md-3">
						<label class="small">Total de Calorias (Kcal)</label>
						<small style="color: red" >${errors.from('totalCaloria')}</small>
						<input class="form-control" value="${totalCaloriaCardapioSugerido}" readonly="readonly">
					</div>
				</fieldset>
			</form>
			
			<hr>
			<small style="color: red" >${errors.from('itemsDoCardapio')}</small>
			<table class="table table-responsive table table-hover">
				<tr class="active col-md-12">
					
				</tr>
				<c:forEach var="itemlista" items="${itemsLista}">
					<tr>
						<td>${itemlista.item.descricao}</td>
						<td>${itemlista.item.valorCalorico}</td>
						<td align="center">
				  			<a class="btn btn-info btn-sm" data-trigger="hover" data-container="body"   title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
				  				data-content="${itemlista.item.beneficios}"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> info
				  			</a>
				  		</td>
				  		
				  		<td>${itemlista.item.maleficios}</td>
				  		
				  		<td align="center">
				  			<a class="btn btn-info btn-sm" data-trigger="hover" data-container="body" title="Informações Nutricionais" data-toggle="popover" data-placement="top" 
				  				data-content="${itemlista.item.informacoesNutricionais}"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> info
				  			</a>
				  		</td>
						
						<td>${itemlista.item.classeNutricional.descricao}</td>
						
						<td>
							<a class="btn btn-danger" data-toggle="tooltip" data-placement="top" title="Remover este item" 
							href='<c:url value="/sugestao/remocaoitem?id=${itemlista.item.id}"></c:url>'>
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
						</td>
					</tr>
				</c:forEach>
			</table>
				
			<form class="form-horizontal" action='<c:url value="/sugestao/save"></c:url>' method="post" >
				<fieldset>
					
					<input name="sugestaoCardapio.id" value="${sugestaoCardapio.id}" type="hidden">
					
					<input name="sugestaoCardapio.totalCaloria" value="<fmt:formatNumber value="${totalCaloriaCardapioSugerido}" maxFractionDigits="2"/>" 
					type="hidden">
					
					
					  
					<div class="form-group col-md-6">
						<label class="small" for="">Data(s) disponível para sugestão de cardápio</label>
						<div class="controls">
					  		<div class="row">
							  	<div class="col-md-3">
							  		<a class="btn btn-info" href='<c:url value="/disponibilidade/validas"/>'> Buscar datas </a>
							  	</div>
					  	
							    <div class="col-md-9">
							    	<select  name="sugestaoCardapio.disponibilidade.id" class="form-control" required>
								    	<option></option>
								    	
										<c:forEach var="disponibilidade" items="${disponibilidadesValidas}"> 
											<option value="${disponibilidade.id}"><fmt:formatDate value="${disponibilidade.dataDisponibilidade}" /></option>			
										</c:forEach>
								    </select>						    
							    </div>
						    </div>					    
					 	</div>
					</div>
			
					
					<div class="col-md-12" align="center">
						
						<button  type="submit"	class="btn btn-primary btn-lg" data-toggle="tooltip" data-placement="top" title="Enviar Sugestão">
							<span class="glyphicon glyphicon-saved" aria-hidden="true"></span>							
						</button>
						
						<a data-toggle="tooltip" data-placement="top" title="Cancelar operação" class="btn btn-danger btn-lg" 
							href='<c:url value="/sugestao/clear" />' ><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
						</a>
					</div>
				</fieldset>
			</form>		
		</div>
	</div>
</div>
</body>
</html>