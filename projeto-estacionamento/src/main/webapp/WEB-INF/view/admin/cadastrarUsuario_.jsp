<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout"%>
<layout:templateAdmin>
	<jsp:attribute name="cssEspecificos">
      </jsp:attribute>
	<jsp:attribute name="scriptsEspecificos">
      <script type="text/javascript"
			src="/static/js/cadastrarUsuario.js"></script>
      </jsp:attribute>
	<jsp:body>
      	<!-- tab content -->
		<div class="forms-main">
		  <h2 class="inner-tittle">
		    Cadastro de Usuário
		  </h2>
		  <div class="graph-form">
		    <div class="validation-form">
		      <!---->
		      <form action="/admin/cadastrarUsuario/salvar" method="POST"
                              id="frm" name="frm" role="form">
		        <h3>
		          Dados pessoais
		        </h3>
		        <div class="graph-form">
		          <div class="vali-form">
		            <div class="col-md-3 form-group1">
		              <label class="control-label"> Nome:</label>
		              <input class="form-control" type="text" id="nome" name="nome" required>
					 <br>
					  <label class="control-label"> Tipo:</label>
					  <div class="col-md-12 form-group2 group-mail">
					  <select class="form-control" name="tipoUsuario" id="tipos">
                      </select>
		            </div>
		            </div>
		            <div class="col-md-3 form-group1">
		              <label class="control-label"> Login:</label>
		              <input class="form-control" type="text" id="username" name="username" required>
		              <br>
		              <label class="control-label"> Senha:</label>
		              <input class="form-control" type="password" id="password" name="password" required>
		            </div>
		            <div class="clearfix">
		            </div>
		          </div>
		        </div>
		        <div class="col-md-12 form-group button-2">
		          <button type="submit" class="btn btn-primary">
		            Salvar
		          </button>
		          <button type="reset" class="btn red">
		            Limpar
		          </button>
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