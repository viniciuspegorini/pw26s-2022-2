<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<title>Login - Estaciona Fácil</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="shortcut icon" href="http://www.utfpr.edu.br/favicon.ico" type="image/x-icon">
    <meta name="keywords"
	content="UTFPR, Estacionamento, Pato Branco, UTFPR Pato Branco, Universidade Tecnologica Federal do Paraná, Sistema de Estacionamento" />
<link href="<c:url value="/static/css/bootstrap.min.css"/>" rel='stylesheet'
	type='text/css' />
<!-- Custom CSS -->
<link href="<c:url value="/static/css/style.css"/>" rel='stylesheet' type='text/css' />
<!-- Graph CSS -->
<link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet">
<link href='//fonts.googleapis.com/css?family=Roboto:700,500,300,100italic,100,400' rel='stylesheet' type='text/css'>

<!--clock init-->
</head>

<body>
	<!--/login-->

	<div class="error_page">
		<div class="error-top">
			<img alt="Logo" src="<c:url value="/static/images/logo.png"/>">
			<div class="buttons login">
				<form id="frm" name="frm" method="POST">
					<!-- <label class="radio-inline"><input type="radio" name="tipo" value="1" checked>Condutor/Aluno</label>  -->
					<label class="radio-inline"><input type="radio" name="tipo" value="2" checked>Condutor/Servidor</label>
					<label class="radio-inline"><input type="radio" name="tipo" value="3">Gestor</label>
					<br>
						<label class="stats-left label" id="reg">Usuário</label> 
						<input type="text" id="username" name="username" class="text" placeholder="Usuário" required> 
						<label class="stats-left label">Senha</label>
						<input type="password" placeholder="Senha" id="password" name="password" required>
					<div class="alert-danger">
						<div id="divErro">${requestScope.error}</div>
					</div>
					<div class="submit">
						<input id="btn" type="submit" value="Login" data-loading-text="..."/>
					</div>
					<div class="clearfix"></div>
				</form>
			</div>
		</div>
	</div>

	<!--//login-->
	<!--footer section start-->
	<div class="footer">
		<div class="error-btn">
			<a class="read fourth"></a>
		</div>
	</div>
	<!--footer section end-->
	<!--/404-->
	<!--js -->
	<script src="<c:url value="/static/js/jquery-1.10.2.min.js"/>"></script>
	<script src="<c:url value="/static/js/jquery.nicescroll.js"/>"></script>
	<script src="<c:url value="/static/js/scripts.js"/>"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
	<script type="text/javascript">
	function redirecionar(destino){
		setTimeout("window.location ='" + destino + "'",1000);
	}
		$(document).ready(function() {
			$('input[name=tipo]').change(function() {
				if($('input[name=tipo]:checked').val() == '1'){
					$("#reg").html("Registro Acadêmico");
					$("#username").attr('placeholder', 'a1575486');
				}else if($('input[name=tipo]:checked').val() == '2'){
					$("#reg").html("Usuário");
					 $("#username").attr('placeholder', 'Usuário');
				}else{
					$("#reg").html("Usuário");
					$("#username").attr('placeholder', 'Usuário');
				}
			});
			
			
			$("#frm").submit(function(){
				if($('input[name=tipo]:checked').val() == '11' || $('input[name=tipo]:checked').val() == '22'){//condutor
					$.ajax({
						type : 'POST',
						data : {
							'username': $("#username").val(),
							'password': $("#password").val()
						},
						url : '<c:url value="/login/valida"/>',
						success : function(data) {
							data = JSON.parse(data);
							if (data.mensagem != "ERRO"){
								redirecionar("<c:url value="/index"/>");
							}else{
								$("#divErro").html("Usuário e/ou senha inválidos!");
							}
						}, //Fim success
						error: function (data) {
							$("#divErro").html("Usuário e/ou senha inválidos!");
						}
					}); //Fim ajax
					return false;
				}else{
					login();
				}
				return false;
			});//Fim Form Submit
		}); //Fim document.ready
		
		function login(){
			$.ajax({
				type : 'POST',
				data : $("#frm").serialize(),
				url :  '<c:url value="/login"/>',
				success : function(data) {
					redirecionar("<c:url value="/index"/>");
				},
				error: function (data) {
					$("#divErro").html(data.mensagem);
				}
			}); //Fim ajax
		}
	</script>
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