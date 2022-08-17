<%@ tag pageEncoding="utf-8"%>

<%@ attribute name="cssEspecificos" fragment="true" required="false"%>
<%@ attribute name="scriptsEspecificos" fragment="true" required="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<!DOCTYPE HTML>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="shortcut icon" href="http://www.utfpr.edu.br/favicon.ico" type="image/x-icon">
<title>Estaciona Fácil</title>
<meta name="description" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<!-- -->
<link href="<c:url value="/static/css/bootstrap.min.css"/>"
	rel="stylesheet" />
<link href="<c:url value="/static/css/style.css"/>" rel="stylesheet"
	type='text/css' />
<link href="<c:url value="/static/css/font-awesome.css"/>"
	rel="stylesheet" type='text/css' />
<link
	href="//fonts.googleapis.com/css?family=Roboto:700,500,300,100italic,100,400"
	rel="stylesheet" type='text/css' />
<link href="<c:url value="/static/css/icon-font.min.css"/>"
	rel="stylesheet" type='text/css' />
<link href="<c:url value="/static/css/fabochart.css"/>" rel="stylesheet"
	type='text/css' />

<jsp:invoke fragment="cssEspecificos"></jsp:invoke>
</head>
<body>
	<div class="page-container">
		<!--/content-inner-->
		<div class="left-content">
			<div class="inner-content">
				<!--outter-wp-->
				<div class="outter-wp">
					<jsp:doBody />
				</div>
				<!--//forms-->
			</div>
			<!--//outer-wp-->
		</div>
	</div>
	<!--//content-inner-->
	<!--/sidebar-menu-->
	<div class="sidebar-menu">
		<header class="logo">
			<img class="img-responsive" alt="Logo" src="<c:url value="/static/images/logo.png"/>">
		</header>
		<div style="border-top: 1px solid rgba(69, 74, 84, 0.7)"></div>
		<!--/down-->
		<div class="menu">
			<ul id="menu">
				<li><a href="<c:url value="/condutor/perfil"/>"><i class="fa fa-tachometer"></i> <span>Perfil</span></a></li>
				<li><a href="<c:url value="/condutor/solicitar"/>"><i class="fa fa-folder-open-o"></i> <span>Solicitar Adesivo</span></a></li>
				<c:if test="${sessionScope.admin}">
					<li><a href="<c:url value="/admin/"/>"><i class="fa fa-lock"></i> <span>Área Administrativa</span></a></li>
				</c:if>
				
				
				<li>
					<a href="<c:url value="/logout" />">
						<i class="fa fa-sign-out"></i>
						<span>Sair</span>
					</a>
				</li>
			</ul>
		</div>
		<div style="top: 200px; margin-left: 10px; color:white;font-size: small;">
			Desenvolvimento: <br/>
			6º período do curso de Tec. em Análise e Desenvolvimento de Sistemas (Turma 2016/1).
		</div>
	</div>
	<div class="clearfix"></div>
	<script src="<c:url value="/static/js/jquery-1.10.2.min.js"/>"></script>
	<script type="application/x-javascript">
        addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); }
	</script>
	<script src="<c:url value="/static/js/amcharts.js"/>"></script>
	<script src="<c:url value="/static/js/serial.js"/>"></script>
	<script src="<c:url value="/static/js/light.js"/>"></script>
	<script src="<c:url value="/static/js/radar.js"/>"></script>
	<script src="<c:url value="/static/js/css3clock.js"/>"></script>
	<script src="<c:url value="/static/js/skycons.js"/>"></script>
	<script type="text/javascript">
		function DropDown(el) {
			this.dd = el;
			this.placeholder = this.dd.children('span');
			this.opts = this.dd.find('ul.dropdown > li');
			this.val = '';
			this.index = -1;
			this.initEvents();
		}
		DropDown.prototype = {
			initEvents : function() {
				var obj = this;

				obj.dd.on('click', function(event) {
					$(this).toggleClass('active');
					return false;
				});

				obj.opts.on('click', function() {
					var opt = $(this);
					obj.val = opt.text();
					obj.index = opt.index();
					obj.placeholder.text(obj.val);
				});
			},
			getValue : function() {
				return this.val;
			},
			getIndex : function() {
				return this.index;
			}
		}

		$(function() {

			var dd = new DropDown($('#dd'));

			$(document).click(function() {
				// all dropdowns
				$('.wrapper-dropdown-3').removeClass('active');
			});

		});
	</script>
	<script>
		var toggle = true;

		$(".sidebar-icon").click(
				function() {
					if (toggle) {
						$(".page-container").addClass("sidebar-collapsed")
								.removeClass("sidebar-collapsed-back");
						$("#menu span").css({
							"position" : "absolute"
						});
					} else {
						$(".page-container").removeClass("sidebar-collapsed")
								.addClass("sidebar-collapsed-back");
						setTimeout(function() {
							$("#menu span").css({
								"position" : "relative"
							});
						}, 400);
					}

					toggle = !toggle;
				});
	</script>
	<script src="<c:url value="/static/js/jquery.nicescroll.js"/>"></script>
	<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
	<jsp:invoke fragment="scriptsEspecificos"></jsp:invoke>
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
