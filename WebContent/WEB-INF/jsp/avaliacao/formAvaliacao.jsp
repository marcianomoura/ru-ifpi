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
					
				   Media das avaliações : <kbd> <fmt:formatNumber value="${mediaAvaliacao}" maxFractionDigits="2"/></kbd>
				    
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
							<c:forEach items="${cardapioDia.itemCardapio}" var="itemCArdapio">
								<tbody>
									<tr>
										<td>${itemCArdapio.item.descricao}</td>
										<td>
											<a class="btn btn-info btn-sm btn-block" data-trigger="hover" data-container="body"   
								  				title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
								  				data-content="${itemCArdapio.item.beneficios}"><span class="glyphicon glyphicon-plus" 
								  				aria-hidden="true"></span> info
								  			</a>
										</td>
										
										<td>${itemCArdapio.item.maleficios}</td>
										
										<td>
											<a class="btn btn-info btn-sm btn-block" data-trigger="hover" data-container="body" 
								  				title="Informações Nutricionais" data-toggle="popover" data-placement="top" 
								  				data-content="${itemCArdapio.item.informacoesNutricionais}"><span class="glyphicon glyphicon-plus" 
								  				aria-hidden="true"></span> info
								  			</a>
										</td>
										<td><span class="badge">${itemCArdapio.item.valorCalorico} Kcal</span></td>											
									</tr>
								</tbody>
							</c:forEach>
						</table>				    
					</div>
					
					<div class="col-md-4">
				
						<div class="panel panel-default">
							<div class="panel-heading"><label class="small"> Faça sua avaliação</label></div>
							<div class="panel-body">
								<c:if test="${cardapioDia !=null}">
									<a href='<c:url value="/avaliacao/cardapio?avaliacaoCardapio.notaAvaliativa=${10}"></c:url>' 
							    		class="btn btn-primary btn-sm btn-block">Excelente
								    </a>
								    <a href='<c:url value="/avaliacao/cardapio?avaliacaoCardapio.notaAvaliativa=${8}"></c:url>' 
								    	class="btn btn-primary btn-sm btn-block">Bom
								    </a>
								    <a href='<c:url value="/avaliacao/cardapio?avaliacaoCardapio.notaAvaliativa=${6}"></c:url>' 
								    	class="btn btn-warning btn-sm btn-block">Regular
								    </a>
								    <a href='<c:url value="/avaliacao/cardapio?avaliacaoCardapio.notaAvaliativa=${4}"></c:url>' 
								    	class="btn btn-danger btn-sm btn-block">Ruim
								    </a>
								    <a href='<c:url value="/avaliacao/cardapio?avaliacaoCardapio.notaAvaliativa=${2}"></c:url>' 
								    	class="btn btn-danger btn-sm btn-block">Muito Ruim
								    </a>								
								</c:if>								
								
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