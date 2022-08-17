<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout"%>
<layout:templateAdmin>
	<jsp:attribute name="cssEspecificos">
      </jsp:attribute>
	<jsp:attribute name="scriptsEspecificos">
      </jsp:attribute>
	<jsp:body>
      	<!-- tab content -->
		<div class="forms-main">
		  <h2 class="inner-tittle">
		    Solicitação
		  </h2>
		  <div class="graph-form">
		    <div class="validation-form">
		      <!---->
		      <form>
		        <h3>
		          Dados pessoais
		        </h3>
		        <div class="graph-form">
		          <div class="vali-form">
		            <div class="col-md-3 form-group1">
		              <label class="control-label">
		                Nome:
		              </label>
		              <label class="control-label">
		                <b>
		                  Fulano de tal
		                </b>
		              </label>
		            </div>
		            <div class="col-md-3 form-group1">
		              <label class="control-label">
		                RA:
		              </label>
		              <label class="control-label">
		                <b>
		                  1495496
		                </b>
		              </label>
		            </div>
		            <div class="col-md-3 form-group1">
		              <label class="control-label">
		                E-mail:
		              </label>
		              <label class="control-label">
		                <b>
		                  email@email.com
		                </b>
		              </label>
		            </div>
		            <div class="col-md-3 form-group1">
		              <label class="control-label">
		                Telefone:
		              </label>
		              <label class="control-label">
		                <b>
		                  46 0000 0000
		                </b>
		              </label>
		            </div>
		            <div class="clearfix">
		            </div>
		          </div>
		        </div>
		        <h3>
		          Dados do veículo
		        </h3>
		        <div class="graph-form">
		          <div class="vali-form">
		            <div class="col-md-3 form-group1">
		              <label class="control-label">
		                Marca:
		              </label>
		              <label class="control-label">
		                <b>
		                  Batata
		                </b>
		              </label>
		            </div>
		            <div class="col-md-3 form-group1">
		              <label class="control-label">
		                Modelo:
		              </label>
		              <label class="control-label">
		                <b>
		                  Batata
		                </b>
		              </label>
		            </div>
		            <div class="col-md-3 form-group1">
		              <label class="control-label">
		                Placa:
		              </label>
		              <label class="control-label">
		                <b>
		                  XXX-0000
		                </b>
		              </label>
		            </div>
		            <div class="col-md-3 form-group1">
		              <label class="control-label">
		                Cor:
		              </label>
		              <label class="control-label">
		                <b>
		                  Batata
		                </b>
		              </label>
		            </div>
		            <div class="col-md-3 form-group1">
		              <label class="control-label">
		                Documento:
		              </label>
		              <label class="control-label">
		                <b>
		                  Arquivo
		                </b>
		              </label>
		            </div>
		            <div class="clearfix">
		            </div>
		          </div>
		          <div class="clearfix">
		          </div>
		          <div class="clearfix">
		          </div>
		          <div id="terceiros">
		            <h3>
		              Dados dos terceiros
		            </h3>
		            <div class="graph-form">
		              <div class="vali-form">
		                <div class="col-md-3 form-group1">
		                  <label class="control-label">
		                    Nome:
		                  </label>
		                  <label class="control-label">
		                    <b>
		                      Batata
		                    </b>
		                  </label>
		                </div>
		                <div class="col-md-3 form-group1">
		                  <label class="control-label">
		                    Telefone:
		                  </label>
		                  <label class="control-label">
		                    <b>
		                      4684113375
		                    </b>
		                  </label>
		                </div>
		                <div class="col-md-3 form-group1">
		                  <label class="control-label">
		                    Parentesco:
		                  </label>
		                  <label class="control-label">
		                    <b>
		                      Batata
		                    </b>
		                  </label>
		                </div>
		                <div class="col-md-3 form-group1">
		                  <label class="control-label">
		                    Documento:
		                  </label>
		                  <label class="control-label">
		                    <b>
		                      File
		                    </b>
		                  </label>
		                </div>
		                <div class="clearfix">
		                </div>
		              </div>
		            </div>
		          </div>
		          <div class="clearfix">
		          </div>
		        </div>
		        <div class="col-md-12 form-group button-2">
		          <button type="submit" class="btn btn-primary">
		            Aceitar
		          </button>
		          <button type="submit" class="btn red">
		            Recusar
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