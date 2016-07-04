<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Funcionários</title>

<script type="text/javascript">
	function confirmaRemocaoFuncionario(){
		return confirm('CONFIRMA A REMOÇÃO DESTE FUNCIONÁRIO ? ');
	}
</script>

</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>


<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading"><h5>Gerenciamento de Usuários do Sistema</h5></div>
			<div class="panel-body">
				<div class="col-md-4">
				<h5>Cadastro de Funcionários - <span class="small">Usuários do Sistema</span></h5>
				<hr>
					<label class="small" style="color: red" > * Campos Obrigatórios</label><br>
					<form action='<c:url value="/funcionario/save"></c:url>' method="post">
						<input  type="hidden" name="funcionario.id" value="${funcionario.id}" >
						
					    <label class="small">* Nome</label>
<%-- 					    	<small style="color: red" >${errors.from('nome')}</small> --%>
					      	<input class="form-control" type="text" name="funcionario.nome" value="${funcionario.nome}" placeholder="informe seu nome" required>
					    
					  	<label class="small">* Matricula</label>
<%-- 					  		<small style="color: red" >${errors.from('matricula')}</small> --%>
					      	<input required class="form-control" type="text" name="funcionario.matricula" value="${funcionario.matricula}" placeholder="Informe sua matricula">
					    <label class="small">Função</label>
					    	<select required class="form-control" name="funcionario.funcao">
					    		<option value="${funcionario.funcao}">${funcionario.funcao}</option>
					    		<option>Cordenador</option>
					    		<option>Estagiario</option>
					    		<option>Administrativo</option>
					    	</select>		    
					    
					    <label class="small">* Login</label>
<%-- 					    	<small style="color: red">${errors.from('login')}</small> --%>
					      <input required class="form-control" type="text" name="funcionario.login" value="${funcionario.login}" placeholder="Informe seu login de acesso ao sistema">
					    
					    
					    <label class="small">Senha</label>
<%-- 					    <small style="color: red">${errors.from('senha')}</small> --%>
					      <input required class="form-control" type="password" name="funcionario.senha" placeholder="Informe sua senha de acesso">
					   	
					   	<button type="submit" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-saved" aria-hidden="true"></span></button>
					    <button type="reset" class="btn btn-warning btn-lg"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>					
					</form>
				</div>
			
			
			<div class="col-md-8">
				<h5 align="center">Lista de Funcionários cadastrados</h5>
				<hr>
					<table class="table table-responsive">
						<thead>
							<tr class="small">
								<th>Nome</th>
								<th>Matricula</th>
								<th>Funcão</th>
								<th>Alteração</th>
								<th>Remoção</th>
							</tr>
						</thead>
						<c:forEach items="${funcionarios}" var="funcionario">
							<tbody>
								<tr>
									<td>${funcionario.nome}</td>
									<td>${funcionario.matricula}</td>
									<td>${funcionario.funcao}</td>
									
									<td> 
										<a class="btn btn-warning btn-sm" data-toggle="tooltip" data-placement="top" title="Alterar os dados deste funcionário"
										href='<c:url value="/funcionario/alteracao?id=${funcionario.id}"></c:url>'>
											 <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										</a>
									</td>
									<td> 
										<a onclick="return confirmaRemocaoFuncionario();" data-toggle="tooltip" data-placement="top" title="Remover este funcionário"
										class="btn btn-danger btn-sm" href='<c:url value="/funcionario/remocao?id=${funcionario.id}"></c:url>'>
											 <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
										</a>
									</td>
								</tr>
							</tbody>
						
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
</div>
</body>
</html>