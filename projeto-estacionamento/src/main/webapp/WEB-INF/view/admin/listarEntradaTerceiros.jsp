<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
    <layout:templateAdmin>
      <jsp:attribute name="cssEspecificos">
      </jsp:attribute>
      <jsp:attribute name="scriptsEspecificos">
      <script type="text/javascript" src="/static/js/listarEntradaTerceiros.js" ></script>
      <script src="/static/js/bootbox.js"/></script>
      
      </jsp:attribute>
      <jsp:body>
        <!-- tab content -->
        <div class="col-md-16 tab-content tab-content-in">
          <div class="tab-pane active text-style" id="tab1">
            <div class="inbox-right">
              <div class="mailbox-content">
                <div class="mail-toolbar clearfix">
                  <h1>
                    Entradas de Terceiros
                  </h1>
                </div>
                <a class="btn btn-primary" href="/admin/entradaTerceiros" role="button">Novo</a>
                <table class="table" id="tabela">
                	<thead>
						<tr>
						  <th>Cód.</th>
						  <th>Placa</th>
						  <th>Local Destino</th>
						  <th>Data/Hora</th>
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
      </jsp:body>
    </layout:templateAdmin>