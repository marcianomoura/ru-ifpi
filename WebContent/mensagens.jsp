<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mensagens</title>
</head>

<body>
	<!-- Mensagens de Sucesso e Erro do Sistema. -->

	<c:if test="${(erro)!=null}">
		<div class="alert alert-danger fade in">
			<button type="button" class="close" data-dismiss="alert"
				aria-hidden="true">x</button>
			${erro}
		</div>
	</c:if>

	<c:if test="${(sucesso)!=null}">
		<div class="alert alert-success fade in">
			<button type="button" class="close" data-dismiss="alert"
				aria-hidden="true">x</button>
			${sucesso}
		</div>
	</c:if>
</body>
</html>