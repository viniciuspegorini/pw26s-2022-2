<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout"%>
<layout:templateAdmin>
	<jsp:attribute name="cssEspecificos">
      </jsp:attribute>
	<jsp:attribute name="scriptsEspecificos">
      <script type="text/javascript" src="/static/js/listarInfracoes.js"></script>
      </jsp:attribute>
	<jsp:body>
       <div class="col-md-16 tab-content tab-content-in">
          <div class="tab-pane active text-style" id="tab1">
            <div class="inbox-right">
              <div class="mailbox-content">
                <div class="mail-toolbar clearfix">
                  <h1>
                    Lista de Infrações
                  </h1>
                </div>
                <table class="table" id="tabela">
                	<thead>
						<tr>
						  <th>Infração</th>
						  <th>Usuário</th>
						  <th>Data</th>
						</tr>
					</thead>
                  <tbody>
                    
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div class="clearfix">
        </div>
		<div id="justificativaModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
		    	<div class="modal-content">
		    		<form action="/admin/justificarInfracao/salvar" method="POST"
						id="frmJust" name="frmJust" role="form">
			      		<div class="modal-header">
			        		<button type="button" class="close" data-dismiss="modal">&times;</button>
			        		<label class="control-label">Infração</label>
                    		<input class="form-control" type="text" id="id"
								name="id" readonly required>
			      		</div>
			      		<div class="modal-body">
			      			<label class="control-label">Motivo</label>
			        		<textarea class="form-control" rows="5" id="justificativa"
								name="justificativa"></textarea>
			      		</div>
		      			<div class="modal-footer">
			      			<div class="btn-group">
				      			<button type="button" class="btn btn-primary"
									onclick="$('#frmJust').submit()">Salvar</button>
								<a class="btn btn-default" href="/admin/listarInfracoes">Cancelar</a>
							</div>	
			      		</div>
		      		</form>
		    	</div>
			</div>
		</div>
      </jsp:body>
</layout:templateAdmin>