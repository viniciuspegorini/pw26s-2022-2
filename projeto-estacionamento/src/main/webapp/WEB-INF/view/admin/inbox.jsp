<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
    <layout:templateAdmin>
      <jsp:attribute name="cssEspecificos">
      </jsp:attribute>
      <jsp:attribute name="scriptsEspecificos">
      <script type="text/javascript">
			$(document).ready(function(){
				carregarDados();
			}); //Fim document.ready

			function carregarDados(){
				$("#tabela tbody").html("");
				$.ajax({
					type : 'POST',
					data : {
						'nome': $("#nome").val()
					},
					url : '<c:url value="/admin/dados/"/>' + $("#status").val() + '/' + $("#tipoUsuario").val(),
					success : function(dados) {
						$.each(dados.data, function(){
							var editar = "";
							if (this[8] == 4){
								editar = "<a class='btn btn-primary' href='<c:url value="/admin/tercerizado/editar/"/>" + this[0] + "'>Editar</a>";
							}else{
								editar = "<a class='btn btn-primary' href='<c:url value="/admin/"/>" + this[0] + "'>Editar</a>";
							}
							
							$("<tr class='table-row'>").appendTo("#tabela tbody")
				            .append($("<td class='table-text'>")
							            .append($("<h6>").text(this[1])).append($("</h6>"))
							            .append($("<p>").text(this[2] + ' - ' +  getTipo(this[8]))).append($("</p>"))
	           						.append($("</td>"))
				            )
				            .append($("<td class='table-text'>")
							            .append($("<h6>").text(this[3])).append($("</h6>"))
	           						.append($("</td>"))
				            )
				            .append($("<td class='table-text'>")
							            .append($("<h6>").text(this[4])).append($("</h6>"))
	           						.append($("</td>"))
				            )
							.append($("<td class='table-text'>")
						            .append($("<h6>").text(this[5])).append($("</h6>"))
	       						.append($("</td>"))
			            	)
			            	.append($("<td class='table-text'>")
						            .append($("<h6>").text(this[7])).append($("</h6>"))
		       						.append($("</td>"))
				            )
				            .append($("<td class='table-text'>")
						            .append($("<h6>").text(this[6])).append($("</h6>"))
		       						.append($("</td>"))
				            )
							.append($("<td/>").html(editar) );
						});//Fim each
					}, //Fim success
					error: function (data) {
						
					}
				}); //Fim ajax
				
			}
			function carregarDados2(){
				var status = $("#status").val();
				
				
				$.getJSON('<c:url value="/admin/dados/"/>' + status + '/' + $("#tipoUsuario").val() , function(dados){
					$("#tabela tbody").html("");
					$.each(dados.data, function(){
						var editar = "";
						if (this[8] == 4){
							editar = "<a class='btn btn-primary' href='<c:url value="/admin/tercerizado/editar/"/>" + this[0] + "'>Editar</a>";
						}else{
							editar = "<a class='btn btn-primary' href='<c:url value="/admin/"/>" + this[0] + "'>Editar</a>";
						}
						
						$("<tr class='table-row'>").appendTo("#tabela tbody")
			            .append($("<td class='table-text'>")
						            .append($("<h6>").text(this[1])).append($("</h6>"))
						            .append($("<p>").text(this[2] + ' - ' +  getTipo(this[8]))).append($("</p>"))
           						.append($("</td>"))
			            )
			            .append($("<td class='table-text'>")
						            .append($("<h6>").text(this[3])).append($("</h6>"))
           						.append($("</td>"))
			            )
			            .append($("<td class='table-text'>")
						            .append($("<h6>").text(this[4])).append($("</h6>"))
           						.append($("</td>"))
			            )
						.append($("<td class='table-text'>")
					            .append($("<h6>").text(this[5])).append($("</h6>"))
       						.append($("</td>"))
		            	)
		            	.append($("<td class='table-text'>")
					            .append($("<h6>").text(this[7])).append($("</h6>"))
	       						.append($("</td>"))
			            )
			            .append($("<td class='table-text'>")
					            .append($("<h6>").text(this[6])).append($("</h6>"))
	       						.append($("</td>"))
			            )
						.append($("<td/>").html(editar) );
					});//Fim each
				});//Fim getJSON
			}

			function getTipo(id){
				if (id == 1 ){
					return "Servidor";
				}else if (id == 3 ){
					return "Aluno";
				}else if (id == 4 ){
					return "Externo";
				}
			}
			
			function excluirRegistro(id){
				bootbox.confirm('Deseja excluir o registro?!', 
						function(resultado){
					if (resultado){
						var destino = "<c:url value="/categoria/excluir/"/>" + id;
						$.ajax({
							type : 'GET',
							url : destino,
							success: function(data){
								exibirMensagem(data.situacao, data.mensagem);
								if (data.situacao = "OK"){
									carregarDados();
								}
							} //Fim success
						}); //Fim ajax
					}//Fim if
				}); //Fim bootbox
			} //Fim excluirRegistro
		</script>
		<script type="text/javascript" src="<c:url value="/static/js/jquery.maskedinput.min.js"/>" ></script>
      <script>
      	function verificaPlaca(){
      		var ehPlacaValida = function(){  
      			var er = /[a-z]{3}-?\d{4}/gim;  
      			er.lastIndex = 0;  
      			return er.test( $("#placa").val());
      		}
      	}
      	
      	$(function() {
      		$('#placa').mask('aaa-9999');
      	});
      	
      	
      </script>
      </jsp:attribute>
      <jsp:body>
        <!-- tab content -->
        <div class="col-md-16 tab-content tab-content-in">
          <div class="tab-pane active text-style" id="tab1">
            <div class="inbox-right">
              <div class="mailbox-content">
                <div class="mail-toolbar clearfix">
               
                  <h1>Lista de Solicitações</h1>
                  <div class="col-md-2 form-group2">
	                  <label class="control-label">Situação:</label>
	                  <select class="form-control" id="status" name="status">
	                    <option value="0">Solicitado</option>
	                    <option value="1">Aprovado</option>
	                    <option value="2">Recusado</option>
	                    <option value="3">Cancelado</option>
	                  </select>
	               </div>
	               <div class="col-md-2 form-group2">
	               		<label class="control-label">Tipo do usuário:</label>
	                  <select class="form-control" id="tipoUsuario" name="tipoUsuario">
	                    <option value="1">Servidores</option>
	                    <option value="3">Alunos</option>
	                    <option value="4">Externos</option>
	                    <option value="0" selected>Todos</option>
	                  </select>
	               </div>
	               
               		<div class="col-md-4 form-group2">
	               		<label class="control-label">Solicitante:<small>(não é necessário digitar todo o nome)</small></label>
               			<input size="10" type="text" id="nome" name="nome" class="form-control" />
               		</div>
	               <div class="col-md-2 form-group2">
					  <br />
	                  <a class="btn btn-primary" href="javascript:void(0)" onclick="carregarDados();" role="button">Filtrar</a>
                	</div>
                </div>
                <table class="table table-striped table-hover" id="tabela">
                	<thead>
						<tr>
						  <th>Solicitante</th>
						  <th>Veículo</th>
						  <th>Placa</th>
						  <th>Data da Solicitação</th>
						  <th>N. Adesivo</th>
						  <th>Status</th>
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