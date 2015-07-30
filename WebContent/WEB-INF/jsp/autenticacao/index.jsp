<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pagina Inicial</title>
</head>
<body>

<div style="margin-top: 80px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="">
	<div class="jumbotron">
		<div class="container" align="center">
			<img class="img-responsive" src="http://ironsummitmedia.github.io/startbootstrap-freelancer/img/profile.png" alt="">
		</div>
	
		<c:if test="${usuarioSessao.usuario != null}">
			<div class="container" align="center">
				<h1>Olá, <small> ${usuarioSessao.usuario.perfil}(a) ${usuarioSessao.usuario.nome}</small></h1>
			  <p><small>Seja Bem vindo(a) ao sistema de avaliação e sugestão de refeição do IFPI</small> </p>	
			</div>
		</c:if>
		
		<c:if test="${funcionarioSessao.funcionario != null}">
			<div class="container" align="center">
				<h1>Olá, <small> ${funcionarioSessao.funcionario.perfil} (a) ${funcionarioSessao.funcionario.nome}</small></h1>
			  <p><small>Seja Bem vindo(a) ao sistema de gerenciamento e avaliação e sugestão de refeição do IFPI</small> </p>	
			</div>
		</c:if>	
		
	</div>
</div>

</body>
</html>