<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Usuário</title>

<c:import url="/base.jsp"></c:import>

</head>
<body>
<div class="mensagens" style="margin-bottom: 20px; margin-top: 20px;">
	<c:import url="/mensagens.jsp"></c:import>
</div>

<form action='<c:url value="/usuario/save"></c:url>' method="post">
	<div class="col-md-12">
		<div class="panel panel-primary">
			<div class="panel-heading">Cadastro de Usuários</div>			
			<div class="panel-body">
				<input name="usuario.id" value="${usuario.id}" type="hidden" >
				
				<input name="usuario.matriculado" value="${usuario.matriculado}" type="hidden" >
				
				<div class="col-md-4">
					<div class="form-group">
					<label class="small">Primeiro Nome</label>
						<input type="text" class="form-control" name="usuario.primeiroNome" value="${usuario.primeiroNome}" required >
					</div>
				</div>
				
				<div class="col-md-4">
					<label class="small">Sobrenome</label>
					<div class="form-group">
						<input type="text" class="form-control" name="usuario.sobrenome" value="${usuario.sobrenome}" required >
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
					<label class="small">Data de Nascimento</label>
						<input type="text" class="form-control" name="usuario.dataNascimento" id="dataNascimento"
						value="<fmt:formatDate value="${usuario.dataNascimento}" pattern="dd/MM/yyyy" />" required >
					</div>
				</div>
				<div class="col-md-4">
				<label class="small">Matricula</label>
					<div class="form-group">
						<input readonly="readonly" type="text" class="form-control" name="usuario.matricula" value="${usuario.matricula}" required >
					</div>
				</div>
		
				<div class="col-md-3">
					<label class="small">Sexo</label>
					<select class="form-control" name="usuario.sexo" required>
						<option value="${usuario.sexo}">${usuario.sexo}</option>
					  	<option>Masculino</option>
					  	<option>Feminino</option>
					</select>
				</div>
				
				<div class="col-md-3">
					<label class="small">Perfl</label>
					<select class="form-control" name="usuario.perfil" required>
						<option value="${usuario.perfil}">${usuario.perfil}</option>
					  	<option>Aluno / Servidores</option>
					</select>
				</div>
				
				<div class="col-md-12">
					<div class="checkbox">
					    <label>
					      <input type="checkbox"  name="usuario.vegetariano" value="true" > Sou Vegetariano
					    </label>
					</div>
				</div>
				
				<div class="col-md-4">
					<div class="form-group">
					<label class="small">Login</label>
						<input readonly="readonly" type="text" class="form-control" name="usuario.login" value="${usuario.matricula}" required >
					</div>
				</div>
				
				<div class="col-md-4">
					<div class="form-group">
					<label class="small">Senha</label>
						<input type="password" class="form-control" name="usuario.senha" value="${usuario.senha}" required >
					</div>
					
				</div>
				<div class="col-md-12">
					<button class="btn btn-primary" type="submit">Cadastrar</button>
					<a href='<c:url value="/usuario/cadastrardepois"></c:url>' class="btn btn-default"> Cadastrar-me depois</a>
				</div>
			</div>
		</div>
	</div>
</form>

</body>
</html>