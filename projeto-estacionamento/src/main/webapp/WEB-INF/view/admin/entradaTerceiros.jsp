<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
    <layout:templateAdmin>
      <jsp:attribute name="cssEspecificos">
      </jsp:attribute>
      <jsp:attribute name="scriptsEspecificos">
      <script type="text/javascript" src="/static/js/entradaTerceiros.js" ></script>
      </jsp:attribute>
      <jsp:body>
      	<!-- tab content -->
		<div class="forms-main">
		  <h2 class="inner-tittle">
		    Cadastro de entrada de terceiros
		  </h2>
		  <div class="graph-form">
		    <div class="validation-form">
		      <!---->
		      <form action="/admin/entradaTerceiros/salvar" method="POST" id="frm" name="frm" role="form">
		        <h3>
		          Dados:
		        </h3>
		        <div class="graph-form">
		          <div class="vali-form">
		            <div class="col-md-3 form-group1">
		              <label class="control-label"> Placa do veículo:</label>
		              <input class="form-control" type="text" maxlength="8" pattern="[A-Z]{3}-\d{4}" placeholder="ABC-1234" id="placaVeiculo" name="placaVeiculo" required>
		            </div>
		            </div>
		            <div class="col-md-3 form-group2 group-mail">
		              <label class="control-label"> Local de destino:</label>
		              <select class="form-control" id="localDestino" name="localDestino" required>
		              	<option value="Anfiteatro">Anfiteatro</option>
		              	<option value="Biblioteca">Biblioteca</option>
		              	<option value="Cantina">Cantina</option>
		              	<option value="Ginásio">Ginásio</option>
		              	<option value="RU">RU</option>
		              	<option value="Bloco A">Bloco A</option>
		              	<option value="Bloco B">Bloco B</option>
		              	<option value="Bloco D">Bloco D</option>
		              	<option value="Bloco E">Bloco E</option>
		              	<option value="Bloco F">Bloco F</option>
		              	<option value="Bloco H">Bloco H</option>
		              	<option value="Bloco I">Bloco I</option>
		              	<option value="Bloco J">Bloco J</option>
		              	<option value="Bloco K">Bloco K</option>
		              	<option value="Bloco L">Bloco L</option>
		              	<option value="Bloco M">Bloco M</option>
		              	<option value="Bloco N">Bloco N</option>
		              	<option value="Bloco O">Bloco O</option>
		              	<option value="Bloco P">Bloco P</option>
		              	<option value="Bloco Q">Bloco Q</option>
		              	<option value="Bloco R">Bloco R</option>
		              	<option value="Bloco S">Bloco S</option>
		              	<option value="Bloco V">Bloco V</option>
		              	<option value="Bloco Y">Bloco Y</option>
		              	<option value="Bloco Z">Bloco Z</option>
		              	<option value="Outro">Outro</option>
		              </select>
		            </div>
		            <div class="clearfix">
		            </div>
		          </div>
		            </form>
		        </div>
		        <div class="col-md-12 form-group button-2">
		          <button type="button" class="btn btn-primary"
									onclick="$('#frm').submit()">Salvar</button>
		          <a class="btn btn-default" href="/admin/listarEntradaTerceiros">Voltar</a>
		        </div>
		        <div class="clearfix">
		        </div>
		    
		    </div>

		  </div>
      </jsp:body>
    </layout:templateAdmin>