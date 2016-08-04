<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

<c:import url="/base.jsp"></c:import>	

<!-- Header disponível para usuários -->
<c:if test="${funcionarioSessao.funcionario == null}">
	<div id="custom-bootstrap-menu" class="navbar navbar-default navbar-fixed-top" role="navigation">
	    <div class="container-fluid">
	        <div class="navbar-header"><a class="navbar-brand" href="#">RU-IFPI</a>
	            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-menubuilder">
	            	<span class="sr-only">Menu</span>
	            	<span class="icon-bar"></span>
	            	<span class="icon-bar"></span>
	            	<span class="icon-bar"></span>
	            </button>
	        </div>
	        <div class="collapse navbar-collapse navbar-menubuilder">
	            <ul class="nav navbar-nav navbar-right">
	                <li >
	                	<a href='<c:url value="/index"/>'><span class="glyphicon glyphicon-home"></span></a>
	                </li>
	                                               
	                <li><a href='<c:url value="/avaliacao"/>'> Avaliar Cardápio </a></li>
	                
	                <li><a href='<c:url value="/sugestoes"/>'>Votar em um Cardápio</a></li>
	                
	                <li><a href='<c:url value="/cardapiosemanal"/>'>Cardápio Semanal</a></li>
	                <li class="dropdown">
	                	<a data-toggle="dropdown"  href="#">Bem vindo(a) <b><span class="glyphicon glyphicon-user"></span> ${usuarioSessao.usuario.primeiroNome}</b> </a>
	                	<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
	                		<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/usuario/alteracao?id=${usuarioSessao.usuario.id}"/>'>
				 				<span class="glyphicon glyphicon-edit"></span> Alterar meus dados</a>
				 			</li>
				 			<li class="divider"></li>
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/logout/usuario"/>'>
			 					<span class="glyphicon glyphicon-off"></span> Sair</a>
			 				</li>
	                	</ul>
	                </li>
	            </ul>
	        </div>
	    </div>
	</div>
</c:if>

<!-- Fim do Header do Usuario/Servidor -->


<!-- Header disponível para a Administração -->
<c:if test="${usuarioSessao.usuario == null}">
	<div id="custom-bootstrap-menu" class="navbar navbar-default navbar-fixed-top" role="navigation">
	    <div class="container-fluid">
	        <div class="navbar-header"><a class="navbar-brand" href="#">RU-IFPI</a>
	            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-menubuilder">
	            	<span class="sr-only">Menu</span>
	            	<span class="icon-bar"></span>
	            	<span class="icon-bar"></span>
	            	<span class="icon-bar"></span>
	            </button>
	        </div>
	        <div class="collapse navbar-collapse navbar-menubuilder">
	            <ul class="nav navbar-nav navbar-right">
	                <li >
	                	<a href='<c:url value="/index" />'><span class="glyphicon glyphicon-home"></span></a>
	                </li>
	                               
	                 <li class="dropdown">
	                	<a data-toggle="dropdown"  href="#"> Cardapio </a>
	                	<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
	                		<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/pratodia"></c:url>'>
				 				<span class="glyphicon glyphicon-plus"></span> Cadastrar cardápio do dia</a>
				 			</li>
			 				
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/sugestao"/>'>
			 					<span class="glyphicon glyphicon-plus"></span> Cadastrar cardapio para votação </a>
			 				</li>
			 				<li class="divider"></li>
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/pratos/list"></c:url>'>
			 					<span class="glyphicon glyphicon-edit"></span> Gerenciamento de Cardápios </a>
			 				</li>
			 				
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/avaliacoes" />'>
			 					<span class="glyphicon glyphicon-list-alt"></span> Analisar Avaliações de cardápios</a>
			 				</li>
	                	</ul>
	                </li>
	                
	                <li><a href='<c:url value="/sobremesa" />'> Sobremesas </a></li>
	                
	                <li class="dropdown">
	                	<a data-toggle="dropdown"  href="#"> Itens </a>
	                	<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
	                		<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/item"></c:url>'>
				 				<span class="glyphicon glyphicon-plus"></span> Cadastrar Alimentos</a></li>
				 			<li class="divider"></li>
				 			<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/items"></c:url>'>
				 				<span class="glyphicon glyphicon-edit"></span> Gerenciamento de Alimentos</a></li>
	                	</ul>
	                </li>
	                
	                <li class="dropdown">
	                	<a data-toggle="dropdown"  href="#"> Catálogo de Pratos  </a>
	                	<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
	                		<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/pratopronto"/>'>
				 				<span class="glyphicon glyphicon-plus"></span> Cadastrar Pratos para Catálogo</a>
				 			</li>
				 			<li class="divider"></li>
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/pratosprontos"/>'>
			 					<span class="glyphicon glyphicon-edit"></span> Gerenciamento de Pratos do Catálogo</a>
			 				</li>
	                	</ul>
	                </li>
	                
	                <li class="dropdown">
	                	<a data-toggle="dropdown"  href="#"> Institucional </a>
	                	<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
	                		
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/usuario"/>'>
			 					<span class="glyphicon glyphicon-plus"></span> Cadastrar Aluno</a>
			 				</li>
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/importacao"/>'>
			 					<span class="glyphicon glyphicon-refresh"></span> Atualizar Matriculas</a>
			 				</li>
	                		<li class="divider"></li>
	                		<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/campus"/>'>
				 				<span class="glyphicon glyphicon-edit"></span> Gerenciar Campus</a>
				 			</li>
				 			<li class="divider"></li>
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/instituicao"/>'>
			 					<span class="glyphicon glyphicon-edit"></span> Gerenciar Instituições</a>
			 				</li>
			 				<li class="divider"></li>
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/refeitorio"/>'>
			 					<span class="glyphicon glyphicon-edit"></span> Gerenciar de Refeitórios</a>
			 				</li>	
	                	</ul>
	                </li>   
	                
	                <li><a href='<c:url value="/funcionario" />'> Funcionários </a></li>
	                
	                <li><a href='<c:url value="/logout/funcionario"></c:url>'> Sair :
	                <span class="glyphicon glyphicon-user"></span> ${funcionarioSessao.funcionario.nome}</a>
	                </li>
	            </ul>
	        </div>
	    </div>
	</div>
</c:if>
<!-- Fim da Header da Administração -->

<div class="mensagens">
	<c:import url="/mensagens.jsp"></c:import>
</div>


</body>
</html>