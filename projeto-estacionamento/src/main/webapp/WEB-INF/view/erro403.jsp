<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="shortcut icon" href="http://www.utfpr.edu.br/favicon.ico" type="image/x-icon">
<meta name="keywords"
	content="UTFPR, Estacionamento, Pato Branco, UTFPR Pato Branco, Universidade Tecnologica Federal do Paraná, Sistema de Estacionamento" />
<script type="application/x-javascript">
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>
<!-- Bootstrap Core CSS -->
<link href="<c:url value="/static/css/bootstrap.min.css"/>" rel='stylesheet' type='text/css' />
<!-- Custom CSS -->
<link href="<c:url value="/static/css/style.css" />" rel='stylesheet' type='text/css' />
<!-- Graph CSS -->
<link href="<c:url value="/static/css/font-awesome.css" />" rel="stylesheet">
<!-- jQuery -->
<link href='//fonts.googleapis.com/css?family=Roboto:700,500,300,100italic,100,400' rel='stylesheet' type='text/css'>
<!-- lined-icons -->
<link rel="stylesheet" href="<c:url value="css/icon-font.min.css" />" type='text/css' />
<!-- //lined-icons -->
<script src="<c:url value="/static/js/jquery-1.10.2.min.js" />" ></script>
<!--clock init-->
</head>
<body>
	<div class="error_page error">
		<!--/error-top-->
		<div class="error-top error">
			<h3>
				Ops!
			</h3>
			<span>Ocorreu um erro, você pode não ter acesso a essa funcionalidade!</span>
			<p>Contate-nos para solucionar o problema :-)</p>
			<div class="error-btn">
				<a class="read fourth" href="<c:url value="/index/" />" >Voltar ao início</a>
			</div>
		</div>
	</div>
	<div class="footer error">
		<p>
			
		</p>
	</div>
	<script src="<c:url value="/static/js/jquery.nicescroll.js" />"></script>
	<script src="<c:url value="/static/js/scripts.js" /> "></script>
	<script src="<c:url value="/static/js/bootstrap.min.js" /> "></script>
	<script>
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
	
	  ga('create', 'UA-82658231-2', 'auto');
	  ga('send', 'pageview');
	
	</script>
</body>
</html>