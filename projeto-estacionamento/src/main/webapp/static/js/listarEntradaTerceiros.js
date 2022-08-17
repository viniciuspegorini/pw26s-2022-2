function redirecionar(destino){
	setTimeout("window.location ='" + destino + "'",1000);
}

$(document).ready(function(){
	carregarDados();
});
function dataAtualFormatada(){
    var data = new Date();
    var dia = data.getDate();
    if (dia.toString().length == 1)
      dia = "0"+dia;
    var mes = data.getMonth()+1;
    if (mes.toString().length == 1)
      mes = "0"+mes;
    var ano = data.getFullYear();  
    return dia+"/"+mes+"/"+ano;
}
function carregarDados(){
	$.ajax({
		type: "GET",
		url: '<c:url value="/admin/listarEntradaTerceiros/listar"/>',
		success: function (data){
			if (data.entradaTerceiros.length > 0){ 
				$.each(data.entradaTerceiros, function(i, obj){
					$("<tr class='table-row'>").appendTo("#tabela tbody")
		            .append($("<td class='table-text'>")
					            .append($("<h6>").text(obj.id)).append($("</h6>"))
	   						.append($("</td>"))
		            )
		            .append($("<td class='table-text'>")
					            .append($("<h6>").text(obj.placaVeiculo)).append($("</h6>"))
	   						.append($("</td>"))
		            )
		            .append($("<td class='table-text'>")
					            .append($("<h6>").text(obj.localDestino)).append($("</h6>"))
	   						.append($("</td>"))
		            )
		            .append($("<td class='table-text'>")
					            .append($("<h6>").text(dataAtualFormatada(obj.dataHoraEntrada))).append($("</h6>"))
	   						.append($("</td>"))
		            )
		            .append($("<td/>").html("<button class='btn btn-primary' onclick='excluirRegistro( "+ obj.id +")'>Excluir</button>") );
					
				})
			}
		},
		error: function(err) {
			console.log(err)
			alert('Erro');
		}
	})
}

function excluirRegistro(id){
	bootbox.confirm('Deseja excluir o registro?', 
			function(resultado){
		if (resultado){
			var destino = '<c:url value="/admin/listarEntradaTerceiros/excluir/"/>' + id;
			$.ajax({
				type : 'GET',
				url : destino,
				success: function(data){
					alert(data.mensagem);
					carregarDados();
					redirecionar('<c:url value="/admin/listarEntradaTerceiros"/>');
				} //Fim success
			}); //Fim ajax
		}//Fim if
	}); //Fim bootbox
} //Fim excluirRegistro