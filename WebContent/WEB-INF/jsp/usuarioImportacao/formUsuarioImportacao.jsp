<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Importação de Usuário</title>

<c:import url="/base.jsp"></c:import>

</head>
<body>
<div class="mensagens" style="margin-bottom: 20px; margin-top: 20px;">
	<c:import url="/mensagens.jsp"></c:import>
</div>

<form action='<c:url value="/lerxls"></c:url>' method="post" enctype="multipart/form-data">
	<div class="col-md-12">
		<div class="panel panel-primary">
			<div class="panel-heading">Cadastro de Usuários</div>			
			<div class="panel-body">
				<div class="row">
					<div class="form-group col-md-6">
						<input class="" name="arquivo" type="file" required  >
					</div>
					<div class="col-md-3">
						<button class="btn btn-primary" type="submit">Atualizar Matriculas</button>
					</div>
				</div>
			</div>
				
			</div>
	</div>
</form>

</body>
</html>