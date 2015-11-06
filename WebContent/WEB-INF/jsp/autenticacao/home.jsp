<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Área Livre</title>

</head>
<body>

<c:import url="/base.jsp"></c:import>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header page-scroll">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Ru-IFPI</a>
        </div>
        
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="page-scroll">
                    <a href="#cardapio">Cardápio</a>
                </li>
                
                <li class="page-scroll">
                    <a href="#avaliacao">Avaliações</a>
                </li>
                
                <li class="page-scroll">
                    <a href="#sugestao">Sugestões de cardápio</a>
                </li>
                
                <li class="page-scroll">
                	<c:if test="${usuarioSessao.usuario == null}">
                		<a href="#" data-toggle="modal"  data-target="#modalAutenticacao"><span class="glyphicon glyphicon-info"></span>Entrar</a>	
                	</c:if>
                	<c:if test="${usuarioSessao.usuario != null}">
                		<a href='<c:url value="/logout/usuario"></c:url>'> Sair :  ${usuarioSessao.usuario.perfil} (a) 
                		${usuarioSessao.usuario.nome}</a>	
                	</c:if>
                    
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="content-wrapper">		
    <section class="primary" id="cardapio">
 		<div class="col-md-12">
			<c:forEach var="error" items="${errors}">
				<div class="alert alert-danger">${error.category} -  ${error.message}</div>
			</c:forEach>
		</div>   
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h3>Cardapio do dia</h3>
                    <hr class="star-primary">
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-default">
						<div class="panel-heading">Itens do Cardápio 
							<label class="pull-right">
						    	<code>Total de Calorias : ${cardapioDia.totalCaloria} Kcal</code>
						   	 </label> 	
					    </div>
					   
						
						<div class="panel-body">
							<div class="col-md-4">
							    <div class="thumbnail">
							      <img class="img-responsive" src="img/almoco.jpg" width="100%">
							    </div>
							</div>
							
							<div class="col-md-8">
								<table class="table table-responsive">
									<thead>
										<tr class="small">
											<th>Item</th>
											<th>Beneficios</th>
											<th>Não recomendado</th>
											<th>Inf. Nutricionais</th>
											<th>Calorias</th>
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
						</div>
					</div> 
                </div> 		                
            </div>
        </div>
    </section>
    
    <section class="primary" id="avaliacao">
        <div class="container">
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
												
						<div class="col-md-5">
						    <div class="thumbnail">
						      <img class="img-responsive" src="img/almoco.jpg" width="100%">
						    </div>						    
						</div>
						
						<div class="col-md-7" style="max-height: 350px; overflow-y: scroll; overflow-x: hidden; ">
							<table class="table table-responsive table-condensed">
								<thead>
									<tr class="small">
										<th>Nome</th>
										<th>Matricula</th>
										<th>Nota</th>								
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
    </section>
    
    <section class="primary" id="sugestao">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h3>Sugestões de Cardápio</h3>
                    <hr class="star-primary">
                </div>
            </div>
            
            <div class="col-md-12">
                <div class="panel panel-default">
                	
					<div class="panel-heading">Itens mais sugeridos para o Cardápio do dia <b>
						<fmt:formatDate value="${dataCrdapio}" pattern="dd/MM/yyyy"/></b>
						<label class="pull-right">
					    	<code>Total de sugestões : <fmt:formatNumber value="${totalSugestoes}" maxFractionDigits="2"/></code>
					    </label>	
					</div>
					<div class="panel-body">
						<div class="col-md-6">
						    <div class="thumbnail">
						      <img class="img-responsive" src="img/almoco.jpg" width="100%" alt="">
						    </div>
						</div>
						
						<div class="col-md-6" style="max-height: 350px; overflow-y: scroll; overflow-x: hidden; ">
							<table class="table table-responsive table-condensed">
								<thead>
									<tr class="small">
										<th>Descrição</th>
										<th>Quantidade</th>
																	
									</tr>
								</thead>
								<c:forEach items="${itensSugeridosCardapio}" var="itemsugerido">
									<tbody>								
										<tr>
											<td>${itemsugerido.key}</td>									
											<td><span class="badge">${itemsugerido.value}</span></td>
										</tr>
									</tbody>
								</c:forEach>
							</table>
							
						</div>
						<a id="back-to-top" href="#" class="btn btn-primary btn-lg back-to-top" role="button" title="Topo da Página" 
						data-toggle="tooltip" data-placement="left"><span class="glyphicon glyphicon-chevron-up"></span></a>
					</div>
				</div>
            </div>
        </div>
    </section>
</div>


<!-- inicio Modal de Autenticação-->
<div class="modal fade" id="modalAutenticacao" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
	      <div class="modal-body">
	      <div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">			
		    <ul id="myTabs" class="nav nav-tabs" role="tablist">
		      <li role="presentation" class="active"><a href="#usuario" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile" aria-expanded="true">Area do Aluno</a></li>
		      <li role="presentation" class=""><a href="#administracao" id="home-tab" role="tab" data-toggle="tab" aria-controls="home" aria-expanded="true">Administração</a></li>
		    </ul>
		    
		    <div id="myTabContent" class="tab-content">
		      <div role="tabpanel" class="tab-pane fade active in" id="usuario" aria-labelledby="profile-tab">	      	
		        <form action='<c:url value="/autenticacao/usuario"></c:url>' method="get">
					  <fieldset>
					  	  <h3 class="form-signin-heading">Alunos e Servidores </h3>	       
						  <hr class="colorgraph"><br>
						  <small style="color: red" >${errors.from('usuario')}</small>
						  <input type="text" class="form-control" name="usuario.login" value="${usuario.login}" 
						  	placeholder="Login" required="" autofocus="" />
						  <br>
						  <input type="password" class="form-control" name="usuario.senha" value="${usuario.senha}"
						  placeholder="Senha" required=""/>     		  
						 
						  <button class="btn btn-lg btn-primary btn-block" type="Submit">Autenticar</button>	
					</fieldset>
				</form>

		      </div>
		      
		      <div role="tabpanel" class="tab-pane fade" id="administracao" aria-labelledby="home-tab">
		        	<form action='<c:url value="/autenticacao/administracao"></c:url>' method="get">
					   	<fieldset>       
							<h3 class="form-signin-heading">Administração do Refeitório</h3>
						  	<hr class="colorgraph"><br>
						  	<small style="color: red" >${errors.from('funcionario')}</small>
						  	<input type="text" class="form-control" name="funcionario.login" value="${funcionario.login}" 
						  		placeholder="Username" required="" autofocus="" />
						  	<br>
							<input type="password" class="form-control" name="funcionario.senha" value="${funcionario.senha}"
						  		placeholder="Password" required=""/>     		  
						 
						  	<button class="btn btn-lg btn-primary btn-block" type="Submit">Autenticar</button>	
						</fieldset>
					</form>
		      </div>
		      
		    </div>
  		</div>
			
	      </div>
      <div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
      </div>
    </div>
  </div>
</div>
<!-- Fim Modal de Autenticação -->

</body>
</html>