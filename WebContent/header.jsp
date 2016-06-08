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

<!-- Header dispon�vel para usu�rios -->
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
	                                               
	                <li><a href='<c:url value="/avaliacao"/>'> Avaliar Card�pio </a></li>
	                
	                <li><a href='<c:url value="/sugestao"/>'>Escolher Card�pio</a></li>
	                
	                <li><a href='<c:url value="/cardapio/semana"/>'>Card�pio Semanal</a></li>
	                
	                <li><a href='<c:url value="/logout/usuario"></c:url>'> Sair :  ${usuarioSessao.usuario.perfil} (a) ${usuarioSessao.usuario.nome}</a>
	                </li>
	            </ul>
	        </div>
	    </div>
	</div>
</c:if>

<!-- Fim do Header do Usuario/Servidor -->


<!-- Header dispon�vel para a Administra��o -->
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
	                	<a href="index"><span class="glyphicon glyphicon-home"></span></a>
	                </li>
	                               
	                 <li class="dropdown">
	                	<a data-toggle="dropdown"  href="#"> Cardapio </a>
	                	<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
	                		<li role="presentation"><a role="menuitem" tabindex="-1" href="cardapio">
				 				<span class="glyphicon glyphicon-plus"></span> Registro de Card�pios</a>
				 			</li>
			 				
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/cardapios"></c:url>'>
			 					<span class="glyphicon glyphicon-pencil"></span> Gerenciamento de Card�pios </a>
			 				</li>
			 				<li class="divider"></li>
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/avaliacoes" />'>
			 					<span class="glyphicon glyphicon-list-alt"></span> Analisar Avalia��es de card�pios</a>
			 				</li>
			 				
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href='<c:url value="/sugestoes"/>'>
			 					<span class="glyphicon glyphicon-list-alt"></span> Ver card�pio mais votado </a>
			 				</li>
	                	</ul>
	                </li>
	                
	               
	                <li class="dropdown">
	                	<a data-toggle="dropdown"  href="#"> Alimentos </a>
	                	<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
	                		<li role="presentation"><a role="menuitem" tabindex="-1" href="item">
				 				<span class="glyphicon glyphicon-plus"></span> Cadastro de Alimentos</a>
				 			</li>
				 				<li role="presentation"><a role="menuitem" tabindex="-1" href="items">
				 				<span class="glyphicon glyphicon-pencil"></span> Gerenciamento de Alimentos</a></li>
	                	</ul>
	                </li>
	                
	                <li class="dropdown">
	                	<a data-toggle="dropdown"  href="#"> Montagem dos cardapios </a>
	                	<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
	                		<li role="presentation"><a role="menuitem" tabindex="-1" href="item">
				 				<span class="glyphicon glyphicon-plus"></span> Cardapios para escolha dos usu�rios</a>
				 			</li>
				 			<li class="divider"></li>
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href="items">
			 					<span class="glyphicon glyphicon-pencil"></span> Cardapios da semana</a>
			 				</li>
	                	</ul>
	                </li>  
	                
	                <li><a href='<c:url value="funcionario" />'> Funcion�rios </a></li>
	                
	                <li><a href='<c:url value="/logout/funcionario"></c:url>'> Sair :
	                <span class="glyphicon glyphicon-user"></span> ${funcionarioSessao.funcionario.perfil} (a) ${funcionarioSessao.funcionario.nome}</a>
	                </li>
	            </ul>
	        </div>
	    </div>
	</div>
</c:if>
<!-- Fim da Header da Administra��o -->

<div class="mensagens">
	<c:import url="/mensagens.jsp"></c:import>
</div>


</body>
</html>