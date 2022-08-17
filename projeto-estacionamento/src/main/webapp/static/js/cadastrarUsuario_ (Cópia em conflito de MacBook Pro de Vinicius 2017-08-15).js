function redirecionar(destino){
	setTimeout("window.location ='" + destino + "'",1000);
}

$(document).ready(function(){
	$.ajax({
		type: "GET",
		url: '/admin/cadastrarUsuario/carregar',
		success: function (data){
		
			if (data.tipos.length > 0){ 	
				var option;
				$.each(data.tipos, function(i, obj){
						option += '<option value='+obj.id+'>'+obj.descricao+'</option>';
				})
			}
			$('#tipos').html(option).show();
			
		},
		error: function(err) {
			console.log(err)
			alert('Erro');
		}
	})//Fim ajax
});//Fim document.ready

$("#frm").submit(function(){
	$.ajax({
		type : 'POST', 
		url : '/admin/cadastrarUsuario/salvar',
		data: $("#frm").serialize(),
		success: function(data){
			if(data.situacao == "OK"){
				alert(data.mensagem);
				redirecionar('/admin/listarUsuarios');
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