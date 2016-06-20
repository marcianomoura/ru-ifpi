<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cardapio do Dia</title>
</head>
<body>
<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading"><h5>Cardápio do Dia</h5></div>
		<div class="panel-body">
			<c:if test="${pratoDia == null }">		
				<form action='<c:url value="/pratodia/montar"></c:url>'>
					<div class="row">
						<fieldset>
							<div class="form-group col-md-4">
							  <label class="small" for="">Esolha o prato em seu catálogo de pratos prontos.</label>
							  <div class="controls">
							    <select  name="pratoDia.pratoPronto.id" class="form-control" required>
							    	<option></option>
									<c:forEach var="pratopronto" items="${listPratoProntos}"> 
										<option value="${pratopronto.id}">${pratopronto.tituloPrato}</option>			
									</c:forEach>
							    </select>
							  </div>
							</div>
						
							<div class="form-group col-md-3">
							  <label class="small" for="">Escolha a Sobremesa</label>
							  <div class="controls">
							    <select  name="pratoDia.sobremesa.id" class="form-control" required>
							    	<option></option>
									<c:forEach var="sobremesa" items="${sobremesas}"> 
										<option value="${sobremesa.id}">${sobremesa.descricao}</option>			
									</c:forEach>
							    </select>
							  </div>
							</div>
							
							<div class="form-group col-md-2">
							  <label class="small" for="">Tipo de Refeição</label>
							  <div class="controls">
							    <select  name="pratoDia.tipoPrato.id" class="form-control" required>
							    	<option></option>
							    	<option value="${1}"> Almoço</option>
							    	<option value="${2}"> Jantar</option>
							    </select>
							  </div>
							</div>
							
							<div class="form-group col-md-3">
								<label class="small">Data do Cardápio</label>
								<input id="dataCardapio" name="pratoDia.dataCardapio" value="${pratoDia.dataCardapio}" class="form-control" required >
							</div>
							
						</fieldset>
					</div>
					
					<div>
						<button type="submit" class="btn btn-primary">Montar Cardapio</button>
					</div>
					<hr>
				</form>
			</c:if>
			<c:if test="${pratoDia != null }">
				<div class="row">
					<div class="col-md-8">
						<div class="row">
							<div class="col-md-4">
								<label class="small">PRATO</label>
							    <div class="thumbnail">
							      <img class="img-responsive" src="img/almoco.jpg" width="100%">
							    </div>
							</div>
							
							<div class="col-md-8">
									<label class="small">${pratoDia.pratoPronto.tituloPrato}</label>
									<table class="table table-responsive">
										<thead>
											<tr class="small">
												<th>Item</th>
	<!-- 											<th>Beneficios</th> -->
	<!-- 											<th>Não recomendado</th> -->
												<th>Calorias</th>
											</tr>
										</thead>
										<c:forEach items="${listItemPratoPronto}" var="item">
											<tbody>
												<tr>
													<td>${item.item.descricao}</td>
	<!-- 												<td> -->
	<!-- 													<a class="btn btn-info btn-sm" data-trigger="hover" data-container="body"    -->
	<!-- 										  				title="Beneficios a saúde" data-toggle="popover" data-placement="top"  -->
	<%-- 										  				data-content="${item.item.beneficios}"><span class="glyphicon glyphicon-plus"  --%>
	<!-- 										  				aria-hidden="true"></span> info -->
	<!-- 										  			</a> -->
	<!-- 												</td> -->
													
	<%-- 												<td>${item.item.maleficios}</td> --%>
													
													<td><span class="badge">${item.item.valorCalorico} Kcal</span></td>											
												</tr>
											</tbody>
										</c:forEach>
									</table>
									<label class="small">TOTAL DE CALORIAS DO PRATO : ${pratoDia.totalCaloria}</label>								
								</div>
							
						</div>
					</div>
					<div class="col-md-4">
						<label class="small">SOBREMESA</label>
						<ul class="list-group">
						  <li class="list-group-item"><label class="small">Descrição : </label> ${sobremesa.descricao}</li>
						  <li class="list-group-item"><label class="small">Vitaminas : </label> ${sobremesa.vitaminas}</li>
						  <li class="list-group-item"><label class="small">Beneficios à saúde : </label> ${sobremesa.beneficios}</li>
						  <li class="list-group-item"><label class="small">Contra Indicações :</label> ${sobremesa.maleficios}</li>
						  <li class="list-group-item"><label class="small">Classificação :</label> ${sobremesa.classeNutricional.descricao}</li>
						</ul>
					</div>
				</div>	
				<hr>	
				<form class="form-horizontal" action='<c:url value="/pratodia/save"></c:url>' method="post" >
					
					<input name="pratoDia.id" value="${pratoDia.id}" type="hidden">
					<input name="pratoDia.pratoPronto.id" value="${pratoPronto.id}" type="hidden">
					<input name="pratoDia.tipoPrato.id" value="${pratoDia.tipoPrato.id}" type="hidden">
					<input name="pratoDia.sobremesa.id" value="${sobremesa.id}" type="hidden">
					<input name="pratoDia.dataCardapio" value='<fmt:formatDate pattern="dd/MM/yyyy" value="${pratoDia.dataCardapio}" />' type="hidden">
					<input name="pratoDia.totalCaloria" value="${pratoDia.totalCaloria}" type="hidden">
					
					<div class="form-group col-md-12">
						
						<button  type="submit" class="btn btn-primary">Finalizar Cardapio</button>
						
						<a data-toggle="tooltip" data-placement="top" title="Cancelar operação" class="btn btn-danger" 
							href="pratodia" >Desistir do Cadastro</a>
					</div>
				</form>			
			</c:if>
		</div>
	</div>
</div>

</body></html>