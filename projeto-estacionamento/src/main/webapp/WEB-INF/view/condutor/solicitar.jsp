<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
    <layout:templateUser>
      <jsp:attribute name="cssEspecificos">
      </jsp:attribute>
      <jsp:attribute name="scriptsEspecificos">
       <script src="<c:url value="/static/js/jquery.blockUI.js"/>"></script>
       
      <script type="text/javascript">
     
      	$(document).ready(function(){
    	  
        	$(document).ready(function(){
          		
          		if ($("#marcas").val() > 0){
          			$("#marcas").change();	
          		}
          		$('#cor').val( $('#corAux').val() );
          		
        	});//Fim document.ready*/
        	

        	$("#frm").submit(function(){
	   		  	
	       		var formData = new FormData($("#frm")[0]);
	       		$.ajax({
	       			type : 'POST', 
	       			url : '<c:url value="/condutor/solicitar/salvar"/>',
	       			data : formData,
	       			async : true,
	       			cache : false,
	       			contentType : false,
	       			processData : false,
	   			    beforeSend: function() {
	   			    	$.blockUI({ message:'<h1><img src="<c:url value="/static/images/busy.gif"/>" /> Aguarde...</h1>',css: { backgroundColor: '#F9C51E', color: '#000000' }});   
	   			    },
	       			success: function(data){
	       				$.unblockUI(); 
	       				
	       				if(data.situacao == "PLACA"){
	       					$('#divErro').addClass('alert alert-danger');
	       					$("#divErro").show();
	       					$("#divErro").html(data.mensagem);
	       				}else if(data.situacao == "OK"){
	       					$('#divErro').removeClass('alert alert-danger');
	       					$('#divErro').addClass('alert alert-success');
	       					$("#divErro").show();
	       					$("#divErro").html(data.mensagem);
	       					redirecionar('<c:url value="/condutor/perfil"/>');
	       				}else{
	       					$('#divErro').addClass('alert alert-danger');
	       					$("#divErro").show();
	       					$("#divErro").html(data.mensagem);
	       				}
	       			},
	       			error: function(){
	       				$.unblockUI(); 
	       				alert("Erro", "Falha ao salvar!");;
	       		    }//Fim success
	       		}); //Fim Ajax
	       		return false;		
       	}); //Fim form submit
       	
	});//Fim document.ready

    	function redirecionar(destino){
    		setTimeout("window.location ='" + destino + "'",1000);
    	}

    	$("#marcas").change(function() {
    		$.ajax({
    			type: "POST",
    			url: '<c:url value="/condutor/solicitar/modelos"/>',
    			data: {
    				'id': $("#marcas").val()
    			},
    			success: function (data){	
    				var option;
    				$.each(data.modelo, function(i, obj){
    					option += '<option value="'+obj.id+'">'+obj.nome+'</option>';
    				})
    				$('#modelo').html(option).show();
    				$('#modelo').val( $('#modeloId').val() );
    			}
    		});
    	});



    	
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
      		
      		$('#telefoneTerceiro').mask('(99)99999-9999');
      		$("#terceiros").hide();
      		$("input[name=isTerceiro]").on('change', function(){
      			if($("input[name=isTerceiro]:checked").val() == 1){
      				$("#terceiros").hide();
      			}else{
      				$("#terceiros").show();
      			}
      		});
      		
      		//$('#telefone').mask('(99)99999-9999');
      		$('#telefone').mask("(99) 9999-9999?9")
            	.focusout(function (event) {  
                var target, phone, element;  
                target = (event.currentTarget) ? event.currentTarget : event.srcElement;  
                phone = target.value.replace(/\D/g, '');
                element = $(target);  
                element.unmask();  
                if(phone.length > 10) {  
                    element.mask("(99)99999-999?9");  
                } else {  
                    element.mask("(99)9999-9999?9");  
                }  
            });
      	});
      	
      	
      </script>
      </jsp:attribute>
      <jsp:body>
        <h2 class="inner-tittle">
          Solicitação
        </h2>
        <div class="graph-form">
          <div class="validation-form">
            <!---->
            <form action="<c:url value="/condutor/solicitar/salvar"/>" method="POST"
                              id="frm" name="frm" enctype="multipart/form-data" role="form">
              <h3>
                Dados pessoais
              </h3>
              <div class="graph-form">
                <div class="vali-form">
                  <div class="col-md-3 form-group1">
                    <label class="control-label">Nome:</label>
                    <input class="form-control" type="text" id="nome" value="${veiculo.condutor.nome}" readonly required />
                  </div>
                  <div class="col-md-3 form-group1">
                    <label class="control-label">RA:</label>
                    <input class="form-control" type="text" id="registro" value="${veiculo.condutor.registro}" name="registro" readonly required />
                  </div>
                  <div class="col-md-3 form-group1">
                    <label class="control-label">E-mail:</label>
                    <input class="form-control" type="text" id="email" value="${veiculo.condutor.email}" name="email" readonly required />
                  </div>
                  <div class="col-md-3 form-group1">
                    <label class="control-label">Telefone:</label>
                    <input class="form-control" type="text" id="telefone" value="${veiculo.condutor.telefone}" name="telefone" required />
                  </div>
                  
                  <div class="clearfix"></div>
                </div>
              </div>
              <h3>Dados do veículo</h3>
              <input type="hidden" id="veiculoId" name="veiculoId" value="${veiculo.id}" />
              <div class="graph-form">
                <div class="vali-form">
                  <div class="col-md-6 form-group2 group-mail">
                  	<input type="hidden" id="marcaId" value="${veiculo.modelo.marca.id}" />
                    <label class="control-label">Marca</label>
                    <select id="marcas" name="marcas" ${veiculo.numAdesivo != null ? 'disabled' : ''} class="form-control" required>
                      <c:forEach var="marca" items="${marcas}">
                      	<option value="${marca.id}" ${marca.id == veiculo.modelo.marca.id ? 'selected' : ''} >${marca.nome}</option>
                      </c:forEach>
                    </select>
                  </div>
                  <div class="col-md-6 form-group2 group-mail">
                  	<input type="hidden" id="modeloId" value="${veiculo.modelo.id}" />
                    <label class="control-label">Modelo</label>
                    <select id="modelo" name="modelo" class="form-control" ${veiculo.numAdesivo != null ? 'disabled' : ''} required> 
                    </select>
                  </div>
                  <div class="col-md-4 form-group1 form-last">
                    <label class="control-label">Placa</label>
                    <input size="10" type="text" id="placa" name="placa" ${veiculo.numAdesivo != null ? 'disabled' : ''} value="${veiculo.placa}" class="form-control" placeholder="AAA-0000" required />
                  </div>
                  <div class="col-md-4 form-group2 group-mail">
                  	<input type="hidden" id="corAux" value="${veiculo.cor}" />
                    <label class="control-label">Cor</label>
                    <select class="form-control" id="cor" name="cor" ${veiculo.numAdesivo != null ? 'disabled' : ''}  required>
  						<option value="Branco">Branco</option>
  						<option value="Preto">Preto</option>
  						<option value="Prata">Prata</option>
  						<option value="Chumbo">Chumbo</option>
  						<option value="Azul">Azul</option>
  						<option value="Vermelho">Vermelho</option>
  						<option value="Verde">Verde</option>
  						<option value="Amarelo">Amarelo</option>
  						<option value="Dourado">Dourado</option>
  						<option value="Outra">Outra</option>
                    </select>
                  </div>
                  <div class="col-md-4 form-group1 group-last">
                    <label class="control-label">Documentos do Veículo e Condutor (PDF, Imagens) Máx. 3MB</label>
                    <input ${anexos.size() > 0 ? '' : 'required'} class="btn btn-default" id="anexoPrincipal" name="anexoPrincipal" multiple="multiple" type="file"  accept="application/pdf, image/*">
                  </div>
                  <div class="clearfix"></div>
                  <c:if test="${anexos.size() > 0}">
	                  <div class="col-md-4 form-group1 group-last">
	                    <label class="control-label">Anexos já cadastrados</label>
	                    <br />
	                    <c:forEach var="anexo" items="${anexos}" varStatus="teste">
	                    	<a href="<c:url value="/condutor/anexo/download/${veiculo.id}/${anexo.id}"/>">${anexo.nome}</a>
	                    	<br />
	                    </c:forEach>
	                  </div>
                  </c:if>
                </div>
                <div class="clearfix"></div>
              </div>
              <div id="divMsg" style="display: none;" class="alert alert-info"></div>
              <div id="divErro"  style="display: none;"></div>
              <div class="col-md-12 form-group button-2">
                <button type="submit" class="btn btn-primary">Enviar</button>
                <a class="btn btn-default" href="<c:url value="/index"/>">Voltar</a>
              </div>
              <div class="clearfix">
              </div>
            </form>
          </div>
        </div>
      </jsp:body>
    </layout:templateUser>