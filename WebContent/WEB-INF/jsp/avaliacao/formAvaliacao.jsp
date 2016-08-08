
<%@ page language="java" contentType="text/html; charset=UTF-8"
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
                 <h3>Avaliação do Prato de Hoje</h3>
                 <hr class="star-primary">
             </div>
         </div>
         
         <div class="col-md-12">
             <div class="panel panel-default" >
				<div class="panel-heading"></div>																	
					<div class="panel-body">
			  		<ul class="nav nav-tabs" role="tablist">
			    		<li role="presentation" class="active"><a href="#almoco" aria-controls="home" role="tab" data-toggle="tab">Almoço</a>
			    		</li>
			    		<li role="presentation"><a href="#jantar" aria-controls="profile" role="tab" data-toggle="tab">Jantar</a>
			    		</li>
			  		</ul>
						<div class="tab-content">
						  	<div role="tabpanel" class="tab-pane active" id="almoco">
							    <div class="col-md-3">
							    	<br>
									<label class="">${pratoDiaAlmoco.pratoPronto.tituloPrato} </label>
									
								    <div class="thumbnail">
								      <img class="img-responsive" src="img/almoco.jpg" width="100%">
								    </div>
								    
								    <label class="small">Total de Calorias (Kcal) : 
								    	<fmt:formatNumber maxFractionDigits="2" value="${pratoDiaAlmoco.totalCaloria}" />
								    </label>
								</div>
							    
							    <br>
							    <div class="col-md-3">
									<label class="small">Sobremesa</label>
									<ul class="list-group">
									  <li class="list-group-item"><label class="small">Descrição : </label> ${pratoDiaAlmoco.sobremesa.descricao}</li>
									  <li class="list-group-item"><label class="small">Vitaminas : </label> ${pratoDiaAlmoco.sobremesa.vitaminas}</li>
									  <li class="list-group-item"><label class="small">Beneficios à saúde : </label> ${pratoDiaAlmoco.sobremesa.beneficios}</li>
									  <li class="list-group-item"><label class="small">Contra Indicações :</label> ${pratoDiaAlmoco.sobremesa.maleficios}</li>
									  <li class="list-group-item"><label class="small">Classificação :</label> ${pratoDiaAlmoco.sobremesa.classeNutricional.descricao}</li>
									</ul>
								</div>
								
								<div class="col-md-6">
									<label class="small">Itens do Prato</label>
									<table class="table table-responsive">
										<thead>
											<tr class="small">
												<th>Item</th>
												<th>Beneficios</th>
												<th>Contra Indicado</th>
												<th>Calorias</th>
											</tr>
										</thead>
										<c:forEach items="${almoco.itemPratoProntos}" var="itemPrato">
											<tbody>
												<tr>
													<td>${itemPrato.item.descricao}</td>
													<td>
														<a class="btn btn-success btn-sm" data-trigger="hover" data-container="body"   
											  				title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
											  				data-content="${itemPrato.item.beneficios}"><span class="glyphicon glyphicon-thumbs-up" 
											  				aria-hidden="true"></span> Faz bem
											  			</a>
													</td>
													
													<td>
														<a class="btn btn-danger btn-sm" data-trigger="hover" data-container="body"   
											  				title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
											  				data-content="${itemPrato.item.maleficios}"><span class="glyphicon glyphicon-thumbs-down" 
											  				aria-hidden="true"></span> Faz mal
											  			</a>
													</td>
													
													<td></td>
													
													<td><span class="badge">${itemPrato.item.valorCalorico} Kcal</span></td>											
												</tr>
											</tbody>
										</c:forEach>
									</table>
								</div>
								
								<div class="col-md-6">
									<div class="panel panel-default">
										<div class="panel-heading"><label class="small"> Faça sua avaliação</label></div>
										<div class="panel-body">
											<c:if test="${pratoDiaAlmoco !=null}">
												<form action='<c:url  value="/avaliacao/cardapio" />'>
													<div class="row">
														<div class="col-md-6">
															<input type="hidden" name="tipoPrato.id" value="${pratoDiaAlmoco.tipoPrato.id }">
															<input type="hidden" name="avaliacaoRefeicao.id" value="${avaliacaoRefeicao.id }">
															<input type="hidden" name="avaliacaoRefeicao.usuario.id" value="${usuarioSessao.usuario.id}">
															<input type="hidden" name="avaliacaoRefeicao.pratoDia.id" value="${pratoDiaAlmoco.id}">
															<div class="form-group">
																<select class="form-control" name="avaliacaoRefeicao.notaAvaliativa" required>
																	<option></option>
																	<option value="10">Excelente</option>
																	<option value="9">Muito Bom</option>
																	<option value="8">Bom</option>
																	<option value="6">Regular</option>
																	<option value="4">Ruim</option>
																	<option value="2">Muito Ruim</option>
																</select>
															</div>
															
														</div>
														<div class="col-md-6">
															
														<button type="submit" class="btn btn-primary">Avaliar Refeição</button>
																
														</div>
													</div>
												</form>
			
											</c:if>								
											
										</div>
									</div>
								</div>
								
										
							</div>
						    <div role="tabpanel" class="tab-pane" id="jantar">
						    	<div class="col-md-3">
							    	<br>
									<label class="">${pratoDiaJanta.pratoPronto.tituloPrato} </label><br>
									
								    <div class="thumbnail">
								      <img class="img-responsive" src="img/almoco.jpg" width="100%">
								    </div>
								    
								     <label class="small">Total de Calorias (Kcal) : 
								    	<fmt:formatNumber maxFractionDigits="2" value="${pratoDiaJanta.totalCaloria}" />
								    </label>
								    
								</div>
							    
							    <br>
							    <div class="col-md-3">
									<label class="small">Sobremesa</label>
									<ul class="list-group">
									  <li class="list-group-item"><label class="small">Descrição : </label> ${pratoDiaJanta.sobremesa.descricao}</li>
									  <li class="list-group-item"><label class="small">Vitaminas : </label> ${pratoDiaJanta.sobremesa.vitaminas}</li>
									  <li class="list-group-item"><label class="small">Beneficios à saúde : </label> ${pratoDiaJanta.sobremesa.beneficios}</li>
									  <li class="list-group-item"><label class="small">Contra Indicações :</label> ${pratoDiaJanta.sobremesa.maleficios}</li>
									  <li class="list-group-item"><label class="small">Classificação :</label> ${pratoDiaJanta.sobremesa.classeNutricional.descricao}</li>
									</ul>
								</div>
								
						    	<div class="col-md-6">
									<label class="small">Itens do Prato</label>
									<table class="table table-responsive">
										<thead>
											<tr class="small">
												<th>Item</th>
												<th>Beneficios</th>
												<th>Contra Indicado</th>
												<th>Calorias</th>
											</tr>
										</thead>
										<c:forEach items="${janta.itemPratoProntos}" var="itemPrato">
											<tbody>
												<tr>
													<td>${itemPrato.item.descricao}</td>
													<td>
														<a class="btn btn-success btn-sm" data-trigger="hover" data-container="body"   
											  				title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
											  				data-content="${itemPrato.item.beneficios}"><span class="glyphicon glyphicon-thumbs-up" 
											  				aria-hidden="true"></span> Faz bem
											  			</a>
													</td>
													
													<td>
														<a class="btn btn-danger btn-sm" data-trigger="hover" data-container="body"   
											  				title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
											  				data-content="${itemPrato.item.maleficios}"><span class="glyphicon glyphicon-thumbs-down" 
											  				aria-hidden="true"></span> Faz mal
											  			</a>
													</td>
													
													<td></td>
													
													<td><span class="badge">${itemPrato.item.valorCalorico} Kcal</span></td>											
												</tr>
											</tbody>
										</c:forEach>
									</table>
								</div>
								
								<div class="col-md-6">
									<div class="panel panel-default">
										<div class="panel-heading"><label class="small"> Faça sua avaliação</label></div>
										<div class="panel-body">
											<c:if test="${pratoDiaJanta !=null}">
												<form action='<c:url  value="/avaliacao/cardapio" />'>
													<div class="row">
														<div class="col-md-6">
															<input type="hidden" name="tipoPrato.id" value="${pratoDiaJanta.tipoPrato.id }">
															<input type="hidden" name="avaliacaoRefeicao.id" value="${avaliacaoRefeicao.id }">
															<input type="hidden" name="avaliacaoRefeicao.usuario.id" value="${usuarioSessao.usuario.id}">
															<input type="hidden" name="avaliacaoRefeicao.pratoDia.id" value="${pratoDiaJanta.id}">
															<div class="form-group">
																<select class="form-control" name="avaliacaoRefeicao.notaAvaliativa" required>
																	<option></option>
																	<option value="10">Excelente</option>
																	<option value="9">Muito Bom</option>
																	<option value="8">Bom</option>
																	<option value="6">Regular</option>
																	<option value="4">Ruim</option>
																	<option value="2">Muito Ruim</option>
																</select>
															</div>
														</div>
															
														<div class="col-md-6">
															<button type="submit" class="btn btn-primary">Avaliar Refeição</button>
														</div>		
													
													</div>
												</form>
												
												
											
											</c:if>								
											
										</div>
									</div>
									
								</div>
						    </div>
					  	</div>
				</div>
			</div>
			
<!-- 			<div class="panel panel-primary"> -->
<!-- 				<div class="panel-heading">Avaliação de Outros Usuarios</div> -->
<!-- 				<div class="panel-body" style="max-height: 400px; overflow-y: scroll; overflow-x: hidden; "> -->
<!-- 					<ul class="nav nav-tabs" role="tablist"> -->
<!-- 			    		<li role="presentation" class="active"><a href="#almocoAvaliacao" aria-controls="home" role="tab" data-toggle="tab">Almoço</a></li> -->
<!-- 			    		<li role="presentation"><a href="#jantarAvaliacao" aria-controls="profile" role="tab" data-toggle="tab">Jantar</a></li> -->
<!-- 			  		</ul> -->
<!-- 			  		<div class="tab-content"> -->
<!-- 					  	<div role="tabpanel" class="tab-pane active" id="almocoAvaliacao"> -->
<!-- 						    <table class="table table-responsive table-condensed"> -->
<!-- 								<thead> -->
<!-- 									<tr> -->
<!-- 										<td>Nome</td> -->
<!-- 										<td>Matricula</td> -->
<!-- 										<td>Nota</td>								 -->
<!-- 									</tr> -->
<!-- 								</thead> -->
<%-- 								<c:forEach items="${listAvaliacaoAlmoco}" var="avaliacao"> --%>
<!-- 									<tbody>								 -->
<!-- 										<tr> -->
<%-- 											<td>${avaliacao.usuario.primeiroNome}</td> --%>
<%-- 											<td>${avaliacao.usuario.matricula}</td> --%>
<%-- 											<td><span class="badge">${avaliacao.notaAvaliativa}</span></td> --%>
<!-- 										</tr> -->
<!-- 									</tbody> -->
<%-- 								</c:forEach> --%>
<!-- 							</table> -->
<!-- 				  		</div> -->
				  		
<!-- 				  		<div role="tabpanel" class="tab-pane" id="jantarAvaliacao"> -->
<!-- 					    	<table class="table table-responsive table-condensed"> -->
<!-- 								<thead> -->
<!-- 									<tr> -->
<!-- 										<td>Nome</td> -->
<!-- 										<td>Matricula</td> -->
<!-- 										<td>Nota</td>								 -->
<!-- 									</tr> -->
<!-- 								</thead> -->
<%-- 								<c:forEach items="${listAvaliacaoJanta}" var="avaliacao"> --%>
<!-- 									<tbody>								 -->
<!-- 										<tr> -->
<%-- 											<td>${avaliacao.usuario.primeiroNome}</td> --%>
<%-- 											<td>${avaliacao.usuario.matricula}</td> --%>
<%-- 											<td><span class="badge">${avaliacao.notaAvaliativa}</span></td> --%>
<!-- 										</tr> -->
<!-- 									</tbody> -->
<%-- 								</c:forEach> --%>
<!-- 							</table> -->
<!-- 					    </div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div>		  -->
    	</div>
	</div>
</body>
</html>