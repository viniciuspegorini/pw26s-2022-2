function redirecionar(destino){
	setTimeout("window.location ='" + destino + "'",1000);
}

$(document).ready(function(){
	/*$.ajax({
		type: "GET",
		url: '<c:url value="/admin/cadastrarUsuario/carregar"/>',
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
	})
	
	var id = $("#id").val();
	
	$.ajax({
		type: 'GET',
		url: '<c:url value="/admin/editarUsuario/"/>' + id,
		success: function (d){
			$("#nome").val(d.nome);
			$("#tipos").val(d.tipos);
			$("#username").val(d.username);
		}
	});*/
});




