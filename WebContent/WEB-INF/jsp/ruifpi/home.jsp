<%@ page language="java" contentType="text/html; charset=UTF-8"
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
            <a class="navbar-brand" href="#">RU-IFPI</a>
        </div>
        
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="page-scroll">
                    <a href="#cardapio">Prato do Dia</a>
                </li>
                
                <li class="page-scroll">
                    <a href="#sugestao">Escolha de Prato</a>
                </li>
                
                <li class="page-scroll">
                	<c:if test="${usuarioSessao.usuario == null}">
                		<a href="#" data-toggle="modal"  data-target="#modalAutenticacao"><span class="glyphicon glyphicon-info"></span>Entrar</a>	
                	</c:if>
                	<c:if test="${usuarioSessao.usuario != null}">
                		<a href='<c:url value="/logout/usuario"></c:url>'> Sair :  ${usuarioSessao.usuario.perfil} (a) 
                		${usuarioSessao.usuario.primeiroNome}</a>	
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
                    <h3>Prato do dia</h3>
                    <hr class="star-primary">
                </div>
            </div>
            <div class="row">                
                    <div class="panel panel-default">
						<div class="panel-heading"><h4></h4></div>
						<div class="panel-body">
							
					  		<ul class="nav nav-tabs" role="tablist">
					    		<li role="presentation" class="active"><a href="#almoco" aria-controls="home" role="tab" data-toggle="tab">Almoço</a></li>
					    		<li role="presentation"><a href="#jantar" aria-controls="profile" role="tab" data-toggle="tab">Jantar</a></li>
					  		</ul>
							
							  <!-- Tab panes -->
								<div class="tab-content">
								  	<div role="tabpanel" class="tab-pane active" id="almoco">
									    <div class="col-md-3">
									    	<br>
											<label class="" style="font-family: sans-serif;">${pratoDiaAlmoco.pratoPronto.tituloPrato} </label><br>
											
										    <div class="thumbnail">
										      <img class="img-responsive" src="img/almoco.jpg" width="100%">
										    </div>
										    
										    <label class="small">Total de Calorias (Kcal) : 
										    <fmt:formatNumber maxFractionDigits="2" value="${pratoDiaAlmoco.totalCaloria}" /></label>
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
										<br>
										<div class="col-md-6">
											<label class="small">Itens do Cardápio</label>
											<table class="table table-responsive">
												<thead>
													<tr class="small">
														<th>Item</th>
														<th>Beneficios a saude</th>
														<th>Contra Indicação</th>
														<th>Calorias</th>
													</tr>
												</thead>
												<c:forEach items="${almoco.itemPratoProntos}" var="itemPrato">
													<tbody>
														<tr>
															<td>${itemPrato.item.descricao}</td>
															<td>
																<a class="btn btn-info btn-sm" data-trigger="hover" data-container="body"   
													  				title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
													  				data-content="${itemPrato.item.beneficios}"><span class="glyphicon glyphicon-plus" 
													  				aria-hidden="true"></span> Faz bem
													  			</a>
															</td>
															
															<td>
																<a class="btn btn-danger btn-sm" data-trigger="hover" data-container="body"   
													  				title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
													  				data-content="${itemPrato.item.maleficios}"><span class="glyphicon glyphicon-plus" 
													  				aria-hidden="true"></span> Faz mal
													  			</a>
															</td>
															
															<td><span class="badge">${itemPrato.item.valorCalorico} Kcal</span></td>											
														</tr>
													</tbody>
												</c:forEach>
											</table>
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
										    <fmt:formatNumber maxFractionDigits="2" value="${pratoDiaJanta.totalCaloria}" /></label>
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
										<br>
								    	<div class="col-md-6">
											<label class="small">Itens do Cardápio</label>
											<table class="table table-responsive">
												<thead>
													<tr class="small">
														<th>Item</th>
														<th>Beneficios á saúde</th>
														<th>Contra Indicação</th>
														<th>Calorias</th>
													</tr>
												</thead>
												<c:forEach items="${janta.itemPratoProntos}" var="itemPrato">
													<tbody>
														<tr>
															<td>${itemPrato.item.descricao}</td>
															<td>
																<a class="btn btn-info btn-sm" data-trigger="hover" data-container="body"   
													  				title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
													  				data-content="${itemPrato.item.beneficios}"><span class="glyphicon glyphicon-plus" 
													  				aria-hidden="true"></span> Faz Bem
													  			</a>
															</td>
															<td>
																<a class="btn btn-danger btn-sm" data-trigger="hover" data-container="body"   
													  				title="Beneficios a saúde" data-toggle="popover" data-placement="top" 
													  				data-content="${itemPrato.item.maleficios}"><span class="glyphicon glyphicon-plus" 
													  				aria-hidden="true"></span> Faz mal
													  			</a>
															</td>
															
															<td><span class="badge">${itemPrato.item.valorCalorico} Kcal</span></td>											
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
    </section>
    
    <section class="primary" id="sugestao">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h3>Escolha o Prato do Dia</h3>
                    <hr class="star-primary">
                </div>
            </div>
            
            <div class="col-md-12">
                <div class="panel panel-default">
                	
					<div class="panel-heading"><h4></h4></div>
					<div class="panel-body">
						<div class="col-md-6">
						    <div class="thumbnail">
						      <img class="img-responsive" src="img/almoco.jpg" width="100%" alt="">
						    </div>
						</div>
						
						<div class="col-md-6">
							<table class="table table-responsive table-condensed">
						<thead>
							<tr class="small">
								<th>Id</th>
								<th>Data do cardápio</th>
								<th>Data final votação</th>
								<th>Votação de Cardápio</th>
							</tr>
						</thead>
						<c:forEach items="${listSugestaoPratos}" var="sugestaoPrato">
							<tbody>
								<tr>
									<td>${sugestaoPrato.id}</td>
									<td><fmt:formatDate value="${sugestaoPrato.dataDisponibilizada}" /></td>
									<td><fmt:formatDate value="${sugestaoPrato.dataFinalVotacao}"/></td>
									<c:if test="${usuarioSessao.usuario != null  }">
										<td>
											<a class="btn btn-info" href='<c:url value="/sugestao/itens?id=${sugestaoPrato}"></c:url>'>
											Veja as opções
											</a>
										</td>
									
									</c:if>
									
									<c:if test="${usuarioSessao.usuario == null  }">
										<td>
											<a href="#" data-toggle="modal"  data-target="#modalAutenticacao" class="btn btn-primary btn-sm">
											<span class="glyphicon glyphicon-info"></span>Escolha o Cardápio</a>
										</td>
									
									</c:if>
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
		      <li role="presentation" class=""><a href="#cadastro" id="home-tab" role="tab" data-toggle="tab" aria-controls="home" aria-expanded="true">Me Cadastrar</a></li>
		    </ul>
		    
		    <div id="myTabContent" class="tab-content">
		      <div role="tabpanel" class="tab-pane fade active in" id="usuario" aria-labelledby="profile-tab">	      	
		        <form action='<c:url value="/autenticacao/usuario"></c:url>' method="post">
					  <fieldset>
					  	  <h3 class="form-signin-heading">Usuários do Refeitório </h3>	       
						  <hr class="colorgraph"><br>
						  <small style="color: red" >${errors.from('usuario')}</small>
						  <input autocomplete="off" type="text" class="form-control" name="usuario.login" value="${usuario.login}" 
						  	placeholder="Login" required="" autofocus="" />
						  <br>
						  <input type="password" class="form-control" name="usuario.senha" value="${usuario.senha}"
						  placeholder="Senha" required=""/>     		  
						 
						  <button class="btn btn-primary" type="Submit">Autenticar</button>	
					</fieldset>
				</form>

		      </div>
		      
		      <div role="tabpanel" class="tab-pane fade" id="administracao" aria-labelledby="home-tab">
		        	<form action='<c:url value="/autenticacao/administracao"></c:url>' method="post">
					   	<fieldset>       
							<h3 class="form-signin-heading">Administração do Refeitório</h3>
						  	<hr class="colorgraph"><br>
						  	<small style="color: red" >${errors.from('funcionario')}</small>
						  	<input autocomplete="off" type="text" class="form-control" name="funcionario.login" value="${funcionario.login}" 
						  		placeholder="Username" required="" autofocus="" />
						  	<br>
							<input type="password" class="form-control" name="funcionario.senha" value="${funcionario.senha}"
						  		placeholder="Password" required=""/>     		  
						 
						  	<button class="btn btn-primary" type="Submit">Autenticar</button>	
						</fieldset>
					</form>
		      </div>
		      
		      <div role="tabpanel" class="tab-pane fade" id="cadastro" aria-labelledby="home-tab">
		        	<form action='<c:url value="/usuario/cadastro"></c:url>' method="post">
					   	<fieldset>       
							<h3 class="form-signin-heading">Cadastro de Usuário</h3>
						  	<hr class="colorgraph"><br>
						  	<small style="color: red" >${errors.from('usuario')}</small>
						  	<input autocomplete="off" type="text" class="form-control" name="usuario.matricula" value="${usuario.matricula}" 
						  		placeholder="Matricula do Usuario" required="" autofocus="" />
						  	<br>
							<input autocomplete="off" id="dataNascimento" type="text" class="form-control" name="usuario.dataNascimento" 
							value="<fmt:formatDate value="${usuario.dataNascimento}" pattern="dd/MM/yyyy"/>"
						  		placeholder="Data de nascimento" required=""/>     		  
						 	<br>
						  	<button class="btn btn-primary" type="Submit">Iniciar Cadastro</button>	
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