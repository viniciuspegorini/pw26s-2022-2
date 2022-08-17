function redirecionar(destino){
	setTimeout("window.location ='" + destino + "'",1000);
}

$("#frm").submit(function(){
	$.ajax({
		type : 'POST', 
		url : '<c:url value="/admin/entradaTerceiros/salvar"/>',
		data: $("#frm").serialize(),
		success: function(data){
			if(data.situacao == "OK"){
				alert(data.mensagem);
				redirecionar('<c:url value="/admin/listarEntradaTerceiros"/>');
			}else{
				alert(data.mensagem);
			}
		},
		error: function(){
			alert("Erro", "Falha ao salvar!");;
	    }//Fim success
	}); //Fim Ajax
	return false;		
}); //Fim form submit