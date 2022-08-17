<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout"%>
<layout:templateAdmin>
	<jsp:attribute name="cssEspecificos">
      </jsp:attribute>
	<jsp:attribute name="scriptsEspecificos">
      <script type="text/javascript"
			src="<c:url value="/static/js/cadastrarUsuario.js"/>"></script>
			<script>
			$("#frm").submit(function(){
				if (confirmarSenha()){
					$.ajax({
						type : 'POST', 
						url : '<c:url value="/admin/cadastrarUsuario/salvar"/>',
						data: $("#frm").serialize(),
						success: function(data){
							if(data.situacao == "OK"){
								//alert(data.mensagem);
								redirecionar('<c:url value="/admin/listarUsuarios"/>');
							}else{
								alert(data.mensagem);
							}
						},
						error: function(){
							alert("Erro", "Falha ao salvar!");;
					    }
					});
				}
				return false;		
			});


			function confirmarSenha(){
				
				if ($("#password").val() == $("#conf_password").val()){
					return true;
				}else{
					alert('As senhas digitadas não conferem!');
					return false;
				}
			}
			</script>
      </jsp:attribute>
	<jsp:body>
      	<!-- tab content -->
		<div class="forms-main">
		  <h2 class="inner-tittle">
		    Cadastro de Funcionário
		  </h2>
		  <div class="graph-form">
		    <div class="validation-form">
		      <!---->
		      <form action="<c:url value="/admin/cadastrarUsuario/salvar"/>"
						method="POST" id="frm" name="frm" role="form">
		        <h3>
		          Dados pessoais
		        </h3>
		        <div class="graph-form">
		          <div class="vali-form">
		            <div class="col-md-3 form-group1">
		            	<input type="hidden" id="id" name="id"
										value="${usuario.id}" />
		              <label class="control-label"> Nome:</label>
		              <input class="form-control" type="text"
										value="${usuario.nome}" id="nome" name="nome" required />
					 <br>
					  <label class="control-label"> Tipo:</label>
					  <div class="col-md-12 form-group2 group-mail">
					  <select class="form-control" name="tipoUsuario" id="tipos">
						  <option value="2">Admin</option>
                      </select>
		            </div>
		            </div>
		            <div class="col-md-3 form-group1">
		              <label class="control-label"> Login:</label>
		              <input class="form-control" type="text" id="username"
										name="username" value="${usuario.username}" required />
		              
		            </div>
		            <div class="col-md-3 form-group1">
		              <label class="control-label"> Senha:</label>
		              <input class="form-control" type="password"
										id="password" name="password" required>
		            <br>
		              <label class="control-label"> Confirmar Senha:</label>
		              <input class="form-control" type="password"
										id="conf_password" name="conf_password" required>
		            </div>
		            <div class="clearfix">
		            </div>
		          </div>
		        </div>
		       <div class="col-md-12 form-group button-2">
                <button type="submit" class="btn btn-primary">Enviar</button>
                <a class="btn btn-default" href="<c:url value="/admin/listarUsuarios"/>">Voltar</a>
              </div>
		        <div class="clearfix">
		        </div>
		      </form>
		      <!---->
		    </div>
		  </div>
		  </div>
      </jsp:body>
</layout:templateAdmin>