$(document).ready(function(){
	$.ajax({
		type: "GET",
		url: '<c:url value="/condutor/solicitar/carregar"/>',
		success: function (data){
			$("#nome").val( data.nome);
			$("#email").val( data.email);
			$("#registro").val( data.registro);
			$("#telefone").val( data.telefone);
			if (data.marcas.length > 0){ 	
				var option = '<option>Selecione a Marca</option>';
				$.each(data.marcas, function(i, obj){
					option += '<option value='+obj.id+'>'+obj.nome+'</option>';
				})
			}
			$('#marcas').html(option).show();
		
		},
		error: function(err) {
			console.log(err)
			alert('erro');
		}
	})//Fim ajax
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
		}
	});
});

function ext(path) {
    var final = path.substr(path.lastIndexOf('/')+1);
    var separador = final.lastIndexOf('.');
    return separador <= 0 ? '' : final.substr(separador + 1);
}

$("#frm").submit(function(){
	var formData = new FormData($("#frm")[0]);
	$.ajax({
		type : 'POST', 
		url : '<c:url value="/condutor/solicitar/salvar"/>',
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		success: function(data){
			if(data.situacao == "PLACA"){
				$("#divErro").html(data.mensagem);
			}else if(data.situacao == "OK"){
				$("#divErro").html(data.mensagem);
				redirecionar('<c:url value="/condutor/solicitar"/>');
			}else{
				$("#divErro").html(data.mensagem);
			}
		},
		error: function(){
			alert("Erro", "Falha ao salvar!");;
	    }//Fim success
	}); //Fim Ajax
	return false;		
}); //Fim form submit