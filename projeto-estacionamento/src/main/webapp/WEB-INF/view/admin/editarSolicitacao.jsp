<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>
<layout:templateAdmin>
    <jsp:attribute name="cssEspecificos">
		<link rel="stylesheet" href="<c:url value="/static/css/jquery-ui.css" />" />
    </jsp:attribute>
    <jsp:attribute name="scriptsEspecificos">
        <script src="<c:url value="/static/js/jquery.blockUI.js"/>" ></script>
        <script type="text/javascript" src="<c:url value="/static/js/jquery-ui.js"/>" ></script>
        
        <script type="text/javascript">
            var idVeiculo = 0;
            var idVeiculoTerceiro = 0;
            $(document).ready(
                    function () {
						var id = $("#id").val();
                        if (id > 0) {
                            $.ajax({
                                type: 'GET',
                                url: '<c:url value="/admin/carregar/"/>' + id,
                                success: function (data) {
                                    idVeiculo = data.veiculo;
                                    idVeiculoTerceiro = data.veiculo;
                                    $("#nome").val(data.nome);
                                    $("#email").val(data.email);
                                    $("#registro").val(data.registro);
                                    $("#telefone").val(data.telefone);
                                    $("#marca").val(data.marca);
                                    $("#modelo").val(data.modelo);
                                    $("#placa").val(data.placa);
                                    $("#cor").val(data.cor);
                                    $("#proprietario").val(data.proprietario);
                                    $("#nomeTerceiro").val(data.nomeTerceiro);
                                    $("#telTerceiro").val(data.telTerceiro);
                                    $("#parentesco").val(data.parentesco);
                                    $("#fileTerceiro").val(data.fileTerceiro);
                                    $('#status option[value=' + data.status + ']').attr('selected', 'selected');
                                    $("#adesivo").val(data.adesivo);
                                    if (data.tipoUsuario === 3) {
                                    	if (data.marcaTipo === 'C'){
                                        	$("#lblTipo").html('Nº da Autorização');
                                    		$("#divDataFim").show();
                                    	}
                                    	$("#lblTipoUsuario").html('RA:');
                                    }
                                    $("#observacoes").html(data.observacoes);
                                    $("#dataFim").val(data.dataFim);
                                },
                            }) //Fim ajax
                        } //Fim if
                    }); //Fim document.ready
            $("#frm").submit(function () {
                var id = ${id};
                var status = ${status};
                $.ajax({
                    type: 'POST',
                    url: '<c:url value="/admin/salvar"/>',
                    data: $("#frm").serialize(),
                    beforeSend: function () {
                        $("#divMsg").html("Aguarde...");
                        $.blockUI({ message:'<h1><img src="<c:url value="/static/images/busy.gif"/>" /> Aguarde...</h1>',css: { backgroundColor: '#F9C51E', color: '#000000' }});
                    },
                    success: function (data) {
                        $.unblockUI();
                        $("#divMsg").html(data.mensagem);
                        $("#id").attr('value', data.id);
                        redirecionar("<c:url value="/admin/"/>");
                    }
                }); //Fim Ajax
                return false;
            }); //Fim form submit

            function redirecionar(destino) {
                setTimeout("window.location ='" + destino + "'", 1000);
            }

            $(function () {
                $("#terceiros").hide();
                $("#proprietario").on('change', function () {
                    if ($("#proprietario").val() == 1) {
                        $("#terceiros").hide();
                    } else {
                        $("#terceiros").show();
                    }
                });
            });
            $("#dataFim").datepicker({dateFormat: 'dd/mm/yy'});
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2 class="inner-tittle">Solicitação</h2>
        <div class="graph-form">
            <div class="validation-form">
                <!---->
                <form action="/admin/salvar" method="POST" id="frm" name="frm" role="form">
                    <h3>Dados pessoais</h3>
                    <div class="graph-form">
                        <div class="vali-form">
                            <input class="form-control" type="hidden" value="${id}" id="id" name="id" />
                            <div class="col-md-3 form-group1">
                                <label class="control-label">Nome:</label>
                                <input class="form-control" type="text" id="nome" name="nome" readonly required />
                            </div>
                            <div class="col-md-3 form-group1">
                                <label class="control-label" id="lblTipoUsuario">Usuário:</label>
                                <input class="form-control" type="text" id="registro" name="registro" readonly required />
                            </div>
                            <div class="col-md-3 form-group1">
                                <label class="control-label">E-mail:</label>
                                <input class="form-control" type="text" id="email" name="email" readonly required />
                            </div>
                            <div class="col-md-3 form-group1">
                                <label class="control-label">Telefone:</label>
                                <input class="form-control" type="text" id="telefone" name="telefone" readonly required />
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <h3>Dados do veículo</h3>
                    <div class="graph-form">
                        <div class="vali-form">
                            <div class="col-md-4 form-group1">
                                <label class="control-label">Marca</label>
                                <input type="text" id="marca" name="marca" class="form-control" readonly required />
                            </div>
                            <div class="col-md-4 form-group1">
                                <label class="control-label">Modelo</label>
                                <input type="text" id="modelo" name="modelo" class="form-control" readonly required />
                            </div>
                            <div class="col-md-4 form-group1 form-last">
                                <label class="control-label">Placa</label>
                                <input type="text" id="placa" name="placa" class="form-control" readonly placeholder="Placa" required />
                            </div>
                            <div class="col-md-4 form-group1 form-last">
                                <label class="control-label">Cor</label>
                                <input type="text" id="cor" name="cor" class="form-control" placeholder="Cor" readonly required />
                            </div>
                            <div class="col-md-4 form-group1 group-last">
                                <label class="control-label">Documentos do condutor/veículo</label>
                                <br />
                                <c:forEach var="anexo" items="${anexos}" varStatus="teste">
                                    <a href="<c:url value="/admin/download/${anexo.id}" />">${anexo.nome}</a>
                                    <br />
                                </c:forEach>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="clearfix"></div>

                        <div class="col-md-6 form-group2 group-mail">
                            <label class="control-label">Situação</label>
                            <select class="form-control" id="status" name="status">
                                <option value="0">Solicitado</option>
                                <option value="1">Aprovado</option>
                                <option value="2">Recusado</option>
                                <option value="3">Cancelado</option>
                            </select>
                        </div>

                        <div class="col-md-4 form-group1 group-last">
                            <label class="control-label" id="lblTipo" >Número do Adesivo</label>
                            <input class="form-control" type="text" name="adesivo" id="adesivo" />
                        </div>
                        <div id="divDataFim" style="display: none" class="col-md-4 form-group1 group-last">
	                            <label class="control-label" id="lblTipo" >Adesivo válido até</label>
	                            <input class="form-control" type="text" name="dataFim" id="dataFim" />
	                    </div>
                        <div class="clearfix"></div>
                        <div class="col-md-5 form-group2 group-mail" style="height: 120px;">
                            <label class="control-label">Observações:</label>
                            <textarea rows="4" cols="100" maxlength="1000" id="observacoes" name="observacoes"></textarea>
                        </div>
                        <div class="clearfix"></div>

                    </div>
                    <div id="divMsg"></div>
                    <div class="col-md-12 form-group button-2">
                        <button type="submit" class="btn btn-primary">Salvar</button>
                        <a class="btn btn-default" href="<c:url value="/index"/>">Voltar</a>
                    </div>
                    <div class="clearfix">
                    </div>
                </form>
            </div>
        </div>
    </jsp:body></layout:templateAdmin>