
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
			<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
			<script type="text/javascript" src="<c:url value="/js/jquery-2.1.1.js"/>"></script>
			<link rel="stylesheet" href="<c:url value='/css/jquery-ui-1.10.4.custom.css'/>">
			<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.10.4.custom.js"/>"></script>
			<script type="text/javascript" src="<c:url value="/js/bootstrap.js"/>"></script>

<script type="text/javascript">
	$(document).ready(function(){
	    $(window).scroll(function () {
	           if ($(this).scrollTop() > 50) {
	               $('#back-to-top').fadeIn();
	           } else {
	               $('#back-to-top').fadeOut();
	           }
	       });
	       // scroll body to 0px on click
	       $('#back-to-top').click(function () {
	           $('body,html').animate({
	               scrollTop: 0
	           }, 800);
	           return false;
	       });
	       	
	});
</script>
			
<script type="text/javascript">
$(function () {
  $('[data-toggle="tooltip"]').tooltip();
  $('[data-toggle="popover"]').popover();
  
});
</script>
	
<script type="text/javascript">
 $(function() {
    $( "#datepicker" ).datepicker({dateFormat: "dd/mm/yy"});
    $( "#datepicker2" ).datepicker({dateFormat: "dd/mm/yy"});
    $( "#dataCardapio" ).datepicker({dateFormat: "dd/mm/yy"});
    $( "#dataDisponibilizada" ).datepicker({dateFormat: "dd/mm/yy"});
  });
</script>


<script type="text/javascript">
              
  $(document).ready(function() {       	       	
     $("#campoDate").keypress(verificaNumero);
     $("#valorCalorico").keypress(verificaNumero);

     function verificaNumero(e) {
    	    if (e.which != 8 && e.which != 0 && e.which != 13  && (e.which < 48 || e.which > 57)) {
    	      	alert("Prezado Usuário, "+ "\nApenas Numeros  são permitidos neste campo !");
    	    	return false;
    	    }else{
    	    	
    	    	return true;
    	    }
    	}
    });                    
</script>


</head>	
<body></body>
</html>