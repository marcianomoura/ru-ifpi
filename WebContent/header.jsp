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
                	<a href="index"><span class="glyphicon glyphicon-home"></span></a>
                </li>
                
                 <li ><a href="index"> Cardápio do dia </a></li>
                                               
                <li><a href="sugestao"> Sugestões de Cardápio </a></li>
                
                <li><a href="avaliacao"> Avaliações de Cardápio </a></li>
                
                <li><a href='<c:url value="/logout/usuario"></c:url>'> Sair :  ${usuarioSessao.usuario.perfil} (a) ${usuarioSessao.usuario.nome}</a>
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
                	<a href="index"><span class="glyphicon glyphicon-home"></span></a>
                </li>
                               
                 <li class="dropdown">
                	<a data-toggle="dropdown"  href="#"> Cardapio </a>
                	<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                		<li role="presentation"><a role="menuitem" tabindex="-1" href="cardapio">
			 				<span class="glyphicon glyphicon-plus"></span> Registro de Cardápio </a>
			 			</li>
		 				
		 				<li role="presentation"><a role="menuitem" tabindex="-1" href="cardapios">
		 					<span class="glyphicon glyphicon-pencil"></span> Gerenciar Cardápio </a>
		 				</li>
		 				<li class="divider"></li>
		 				<li role="presentation"><a role="menuitem" tabindex="-1" href="cardapios">
		 					<span class="glyphicon glyphicon-list-alt"></span> Ver Avaliações </a>
		 				</li>
		 				
		 				<li role="presentation"><a role="menuitem" tabindex="-1" href="cardapios">
		 					<span class="glyphicon glyphicon-list-alt"></span> Ver Sugestões </a>
		 				</li>
                	</ul>
                </li>
                
               
                <li class="dropdown">
                	<a data-toggle="dropdown"  href="#"> Itens Alimentares </a>
                	<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                		<li role="presentation"><a role="menuitem" tabindex="-1" href="item">
			 				<span class="glyphicon glyphicon-plus"></span> Cadastro de Item</a>
			 			</li>
			 				<li role="presentation"><a role="menuitem" tabindex="-1" href="items">
			 				<span class="glyphicon glyphicon-pencil"></span> Alteração de Itens</a></li>
                	</ul>
                </li>  
                
                <li><a href="funcionario"> Funcionários </a></li>
                <li><a href='<c:url value="/logout/funcionario"></c:url>'> Sair :
                <span class="glyphicon glyphicon-user"></span> ${funcionarioSessao.funcionario.perfil} (a) ${funcionarioSessao.funcionario.nome}</a>
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