function redirecionar(destino){
	setTimeout("window.location ='" + destino + "'",1000);
}

$(document).ready(function(){
	carregarDados();
});

function carregarDados(){
	$.ajax({
		type: "GET",
		url: '/admin/listarInfracoes/listar',
		success: function (data){
			if (data.infracoes.length > 0){ 	
				var table;
				$.each(data.infracoes, function(i, obj){
					$("<tr class='table-row'>").appendTo("#tabela tbody")
		            .append($("<td class='table-text'>")
					            .append($("<h6>").text(obj.id)).append($("</h6>"))
       						.append($("</td>"))
		            )
		            .append($("<td class='table-text'>")
					            .append($("<h6>").text(obj.usuario.nome)).append($("</h6>"))
       						.append($("</td>"))
		            )
		            .append($("<td class='table-text'>")
					            .append($("<h6>").text(dataAtualFormatada(obj.dataHora))).append($("</h6>"))
       						.append($("</td>"))
		            )
		            .append($("<td/>").html("<button class='btn btn-primary' onclick='justificarInfracao( "+ obj.id +")'>Justificar</button>") );
					
				})
			}
		},
		error: function(err) {
			console.log(err)
			alert('Erro');
		}
	})
}

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

function justificarInfracao(id){
	$("#id").val(id);
	$("#justificativa").val('');
    $("#justificativaModal").modal();
}

$("#frmJust").submit(function(){
	$.ajax({
		type : 'POST', 
		url : '/admin/justificarInfracao/salvar',
		data: $("#frmJust").serialize(),
		success: function(data){
			if(data.situacao == "OK"){
				alert(data.mensagem);
				redirecionar('/condutor/listarInfracoes');
			}else{
				alert(data.mensagem);
			}
		},
		error: function(){
			alert("Erro", "Falha ao salvar!");
	    }
	});
	return false;		
});





