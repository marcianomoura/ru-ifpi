<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Avaliação do Usuario</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

     <div class="">
         <div class="row">
             <div class="col-lg-12 text-center">
                 <h3>Avaliação de Cardápio</h3>
                 <hr class="star-primary">
             </div>
         </div>
         <div class="col-md-12">
             <div class="panel panel-default" >
				<div class="panel-heading">Avaliação do Cardápio de Hoje 
					<label class="pull-right">
				    	<code>Media das avaliações : <fmt:formatNumber value="${mediaAvaliacao}" maxFractionDigits="2"/></code>
				    </label>
				</div>
				<div class="panel-body" >																		
					<div class="col-md-8">
					    <table class="table table-responsive table-condensed">
							<thead>
								<tr>
									<th class="small">Item</th>
									<th class="small">Beneficios</th>
									<th class="small">Não recomendado</th>
									<th class="small">Inf. Nutricionais</th>
									<th class="small">Calorias</th>
								</tr>
							</thead>
							<c:forEach items="${cardapioDia.itemCardapio}" var="item">
								<tbody>
									<tr>
										<td>${item.item.descricao}</td>
										<td>
											<a class="btn btn-info btn-sm" data-trigger="hover" data-container="body"   
								  				title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
								  				data-content="${item.item.beneficios}"><span class="glyphicon glyphicon-plus" 
								  				aria-hidden="true"></span> info
								  			</a>
										</td>
										
										<td>${item.item.maleficios}</td>
										
										<td>
											<a class="btn btn-info btn-sm" data-trigger="hover" data-container="body" 
								  				title="Informações Nutricionais" data-toggle="popover" data-placement="top" 
								  				data-content="${item.item.informacoesNutricionais}"><span class="glyphicon glyphicon-plus" 
								  				aria-hidden="true"></span> info
								  			</a>
										</td>
										<td><span class="badge">${item.item.valorCalorico} Kcal</span></td>											
									</tr>
								</tbody>
							</c:forEach>
						</table>				    
					</div>
					
					<div class="col-md-4">
				
						<div class="panel panel-default">
							<div class="panel-heading"><label class="small"> Faça sua avaliação</label></div>
							<div class="panel-body">
								
								<a href='<c:url value="/avaliacao/cardapio?notaAvaliativa=${10}"></c:url>' 
						    	class="btn btn-primary btn-sm">Excelente
							    </a>
							    <a href='<c:url value="/avaliacao/cardapio?notaAvaliativa=${8}"></c:url>' 
							    	class="btn btn-primary btn-sm">Bom
							    </a>
							    <a href='<c:url value="/avaliacao/cardapio?notaAvaliativa=${6}"></c:url>' 
							    	class="btn btn-warning btn-sm">Regular
							    </a>
							    <a href='<c:url value="/avaliacao/cardapio?notaAvaliativa=${4}"></c:url>' 
							    	class="btn btn-danger btn-sm">Ruim
							    </a>
							    <a href='<c:url value="/avaliacao/cardapio?notaAvaliativa=${2}"></c:url>' 
							    	class="btn btn-danger btn-sm">Muito Ruim
							    </a>
							</div>
						</div>
						
					</div>
					
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="panel-heading">Resultado das Avaliações para este cardapio</div>
							<div class="panel-body" style="max-height: 400px; overflow-y: scroll; overflow-x: hidden; ">
								<table class="table table-responsive table-condensed">
									<thead>
										<tr>
											<td>Nome</td>
											<td>Matricula</td>
											<td>Nota</td>								
										</tr>
									</thead>
									<c:forEach items="${listaAvaliacoes}" var="avaliacao">
										<tbody>								
											<tr>
												<td>${avaliacao.usuario.nome}</td>
												<td>${avaliacao.usuario.matricula}</td>
												<td><span class="badge">${avaliacao.notaAvaliativa}</span></td>
											</tr>
										</tbody>
									</c:forEach>
								</table>
							</div>
						</div>
					
					</div>
				</div>
			</div>   
    </div>
</div>
</body>
</html>