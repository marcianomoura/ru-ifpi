<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Item Alimentar</title>
</head>
<body>

<div style="margin-top: 60px;">
	<c:import url="/header.jsp"></c:import>
</div>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading">Cadastro dos Item Alimentares</div>
		<div class="panel-body">
			<label class="small" style="color: red" > * Campos Obrigatórios</label><br>
			<form class="form-horizontal" action='<c:url value="/item/save"></c:url>' method="post">
				<fieldset>				
					<input type="hidden" name="item.id" value="${item.id}" >	
				
					<div class="control-group col-md-7">
					  <label class="control-label small" for="nomeItem">* Nome do Item</label>
					  <small style="color: red" >${errors.from('descricao')}</small>
					  <div class="controls">
					    <input  name="item.descricao" value="${item.descricao}"  type="text"   class="form-control" required="">
					    <p class="help-block">Ex: Arroz a grega, Frango Assado</p>
					  </div>
					</div>			
				
					<div class="control-group col-md-4">
					  <label class="control-label small" for="valorCalorico">* Valor Calórico</label>
					  <small style="color: red" >${errors.from('valorCalorico')}</small>
					  <div class="controls">				    
					    <input id="valorCalorico"  name="item.valorCalorico" value="<fmt:formatNumber maxFractionDigits="2"  
					    value="${item.valorCalorico}"></fmt:formatNumber>" type="text"  class="form-control" required="">
					    <p class="help-block">Ex: 200 Kcal</p>
					  </div>
					</div>				
				
					<div class="control-group col-md-8">
					  <label class="control-label small" for="informacoesNutricionais">* Informações Nutricionais importantes</label>
					  <small style="color: red" >${errors.from('informacaoNutricional')}</small>
					  <div class="controls">
					    <input name="item.informacoesNutricionais" value="${item.informacoesNutricionais}" type="text"
					    class="form-control" required="">
					    <p class="help-block">Ex: Fonte de Vitamina A e C</p>
					  </div>
					</div>				
				
					<div class="control-group col-md-5">
					  <label class="control-label small" for="beneficios">Benefícios à saude </label>
					  <small style="color: red" >${errors.from('beneficios')}</small>
					  <div class="controls">
					    <input name="item.beneficios" value="${item.beneficios}" type="text" class="form-control">
					    <p class="help-block">ex: Recomendado para diabéticos</p>
					  </div>
					</div>
				
					<div class="control-group col-md-5">
					  <label class="control-label small" for="contraIndicacoes">Contra Indicação</label>
					  <small style="color: red" >${errors.from('maleficios')}</small>
					  <div class="controls">
					    
					    <input name="item.maleficios" value="${item.maleficios}" type="text" class="form-control">
					    <p class="help-block">Ex: Não recomendado para diabéticos</p>
					  </div>
					</div>					
					
					<div class="control-group col-md-4">
					  <label class="control-label small" for="classeNutricional">Classe Nutricional</label>
					  <small style="color: red" >${errors.from('classeNutricional')}</small>
					  <div class="controls">
					    <select  name="item.classeNutricional.id" class="form-control" required>
					      
					      <option value="${item.classeNutricional.id}">${item.classeNutricional.descricao}</option>
					      
					      <c:forEach items="${classes}" var="item">
					      		<option value="${item.id}">${item.descricao}</option>
					      </c:forEach>
					    </select>
					  </div>
					</div>
				
					<div class="control-group col-md-12">
					  <label class="control-label" for="button1id"></label>
					  <div class="controls">
					    <button type="submit" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-saved" aria-hidden="true"></span></button>
					    <button type="reset" class="btn btn-warning btn-lg"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
					  </div>
					</div>
				
				</fieldset>
			</form>		
		</div>
	</div>
	
</div>


</body>
</html>