<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<layout:templateUser>
	<jsp:attribute name="cssEspecificos">
	</jsp:attribute>
	<jsp:attribute name="scriptsEspecificos">
		<script src="<c:url value="/static/js/bootbox.js"/>"/></script>
		<script>
			$(document).ready(function(){
				for(var i = 0; i < $("#numVeiculos").val(); i++){
					$("#lblSituacao_" + i).html( getSituacao( $("#situacaoAux_" + i).val() ) );
				}
			});
			function getSituacao(id){
				if (id == 0 ){
					return "Solicitado - aguarde";
				}else if (id == 1 ){
					return "Aprovado";
				}else if (id == 2 ){
					return "Recusado";
				}else if (id == 3 ){
					return "Cancelado";
				}
			}
			function redirecionar(destino){
	    		setTimeout("window.location ='" + destino + "'",1000);
	    	}
				
      		function cancelarSolicitacao(id, placa){
      			var resultado = confirm('Deseja cancelar a solicitação para Placa: ' + placa + '?'); 
	      		if (resultado){
		     			$.ajax({
		    				type : 'GET',
		    				data : {'veiculo': id},
		    				url : '<c:url value="/condutor/veiculo/cancelar/"/>' + id,
		    				success : function(data) {
		    					redirecionar('<c:url value="/condutor/perfil"/>');
		    				},
		    				error: function (data) {
		    					alert(data.mensagem)
		    				}
		    			});
		     			
		     			return false;
		      		}
	      	}
      		
      		function reativarSolicitacao(id, placa){
      			var resultado = confirm('Deseja reativar a solicitação para Placa: ' + placa + '?'); 
	      		if (resultado){
		     			$.ajax({
		    				type : 'GET',
		    				data : {
		    					'veiculo': id
		    				},
		    				url : '<c:url value="/condutor/veiculo/reativar/"/>' + id,
		    				success : function(data) {
		    					redirecionar('<c:url value="/condutor/perfil"/>');
		    				},
		    				error: function (data) {
		    					alert(data.mensagem)
		    				}
		    			});
		     			
		     			return false;
		      		}
	      	}
	      </script>
	</jsp:attribute>
	
	<jsp:body>
		<!--//perfil-->
		<div class="graph-form">
			<div class="">
				<h1 class="inner-tittle t-inner">Perfil</h1>
				<hr width="100%">
				<div class="login">
					<h3 class="inner-tittle t-inner">${condutor.nome}</h3>
					<div class="buttons login">
						<form>
							<label class="control-label">Usuário</label>
							<br />
							<b>${condutor.registro}</b>
							<br />
							<br />
							<label class="control-label">E-mail</label>
							<br />
							<b>${condutor.email}</b>
							<br />
							<br /> 
							<label class="control-label">Telefone</label>
							<br />
							<b>${condutor.telefone}</b>
							<hr width="100%">
							<!-- forEach -->
							<h1>Solicitações</h1>
							<input type="hidden" id="numVeiculos" value="${veiculos.size()}" />
			            	<c:forEach var="veiculo" items="${veiculos}" varStatus="count">
								<div class="graph-form">
									<div class="vali-form">
										<div class="col-md-12 form-group2">
											<input type="hidden" id="situacaoAux_${count.index}" value="${veiculo.solicitacao.status}" />
											<label class="control-label">Solicitação para placa: <strong>${veiculo.placa}</strong> &nbsp;&nbsp;--&nbsp;&nbsp; 
												Situação: <strong id="lblSituacao_${count.index}"></strong></label>
											<br/>
											<b></b> 
										</div>
										<hr width="100%">
										<div class="col-md-4 form-group2">
											<label class="control-label">Marca: <b>${veiculo.modelo.marca.nome}</b></label> 
										</div>
										<div class="col-md-4 form-group2">
											<label class="control-label">Modelo: <b>${veiculo.modelo.nome}</b></label> 
										</div>
										<div class="col-md-4 form-group2">
											<label class="control-label">Cor: <b>${veiculo.cor}</b></label> 
										</div>
										<hr width="100%">
										<div class="col-md-4 form-group2 ">
											<label class="control-label">Placa: <b>${veiculo.placa}</b></label>
										</div>
										<div class="col-md-4 form-group2">
											<label class="control-label">${veiculo.condutor.tipoUsuario.id.equals(3) ? 'Nº Autorização:' : 'Adesivo:'}   <b>${veiculo.numAdesivo}</b></label> 
										</div>
										<div class="col-md-4 form-group2">
											<label class="control-label">Autorizado:</label> <b>${veiculo.autorizado == true ? "Sim" : "Não"}</b>
										</div>
										<c:if test="${veiculo.modelo.marca.tipo == 'C' && veiculo.solicitacao.dataFim != null}">
											<div class="col-md-10 form-group2">
												<label class="control-label">Dada de expiração do adesivo:</label> 
												<b><javatime:format pattern="dd/MM/yyyy" value="${veiculo.solicitacao.dataFim}" /></b>
											</div>


										</c:if>
										
										<div class="col-md-4 form-group2">
											<label class="control-label">Observações:</label> <b>${veiculo.solicitacao.observacoes}</b>
										</div>
										<hr width="100%">
										<div class="clearfix"></div>
										<div class="col-md-1 form-group2">
											<c:if test="${veiculo.numAdesivo == null && veiculo.solicitacao.status < 3}">
												<input type="button" class="btn btn-primary" value="Cancelar" 
												onclick="cancelarSolicitacao(${veiculo.id},'${veiculo.placa}')" />
												<input type="hidden" value="${veiculo.numAdesivo == null ? '1' : '0'} " id="adesivo_${veiculo.id}" />
											</c:if>
											<c:if test="${veiculo.numAdesivo == null && veiculo.solicitacao.status == 3}">
												<input type="button" class="btn btn-primary" value="Reativar" 
												onclick="reativarSolicitacao(${veiculo.id},'${veiculo.placa}')" />
												<input type="hidden" value="${veiculo.numAdesivo == null ? '1' : '0'} " id="adesivo_${veiculo.id}" />
											</c:if>
										</div>
										
										<div class="col-md-2 form-group2">
											<c:if test="${ veiculo.numAdesivo == null || veiculo.numAdesivo.isEmpty()}">
												<a href="<c:url value="/condutor/solicitar/editar/${veiculo.id}" />" class="btn btn-primary">Editar &nbsp;&nbsp;</a>
											</c:if>
											<c:if test="${ !veiculo.numAdesivo.isEmpty() && veiculo.solicitacao.expirado }">
												<a href="<c:url value="/condutor/solicitar/editar/${veiculo.id}" />" class="btn btn-primary">Editar &nbsp;&nbsp;</a>
											</c:if>
										</div>
										
									</div>
									<div class="clearfix"></div>
									<c:forEach var="terceiro" items="${veiculo.veiculoTerceiros}">
										<div id="terceiros">
					                  		<h3>Dados dos terceiros</h3>
					                  		<div class="graph-form">
					                    		<div class="vali-form">
					                      			<div class="col-md-4 form-group1 form-last">
					                        			<label class="control-label">Nome: <b>${terceiro.nome}</b></label>
					                        
					                      			</div>
					                      			<div class="col-md-4 form-group1 form-last">
								                        <label class="control-label">Telefone: <b>${terceiro.telefone}</b></label>
					                      			</div>
					                      			<div class="col-md-4 form-group2 group-mail">
					                        			<label class="control-label">Parentesco: <b>${terceiro.parentesco}</b></label>
					                      			</div>
					                      			<div class="clearfix"></div>
					                    		</div>
					                  		</div>
					                	</div>
				                	</c:forEach>
				                </div>
				                <br />
			                 </c:forEach>
			                 <div class="clearfix"></div>
						</form>
						${error}
					</div>
				</div>
			</div>
		</div>
		<!--//perfil-->
		<!--footer section start-->
		<div class="footer">
			<div class="error-btn"></div>
		</div>
		<!--footer section end-->
	
		<!--js -->
		<script src="<c:url value="/static/js/jquery-1.10.2.min.js"/>"></script>
		<script src="<c:url value="/static/js/scripts.js"/>"></script>
		<!-- Bootstrap Core JavaScript -->
		<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>

	</jsp:body>
</layout:templateUser>