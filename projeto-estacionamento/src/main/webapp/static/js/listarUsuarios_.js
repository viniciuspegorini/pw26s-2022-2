function redirecionar(destino){
	setTimeout("window.location ='" + destino + "'",1000);
}

$(document).ready(function(){
	carregarDados();
});

function carregarDados(){
	$.ajax({
		type: "GET",
		url: '/admin/listarUsuarios/listar',
		success: function (data){
		
			if (data.usuarios.length > 0){ 	
				var users;
					users += '<tr class="table-row"><td class="table-text"><h3>Id</h3></td>';
					users += '<td class="table-text"><h3>Nome</h3></td>';
					users += '<td></td><td></td><td></td>';
					users += '<td class="table-text"><h3>Tipo de Funcionario</h3></td></tr>';
				$.each(data.usuarios, function(i, obj){
					if(obj.tipoUsuario.id!=1){
						users += '<tr class="table-row">';
						users += '<td class="table-text"><h6>' + obj.id + '</h6></td>';
						users += '<td class="table-text"><h6>' + obj.nome + '</h6></td>';
						users += '<td></td><td></td><td></td>';
						users += '<td class="table-text"><h6>' + obj.tipoUsuario.descricao + '</h6></td>';
						users += '<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>'
						users += '<td class="march"><button class="btn btn-primary" onclick="editarRegistro(' + obj.id + ')">Editar</button>';
						users += '<button class="btn btn-primary" onclick="excluirRegistro(' + obj.id + ')">Excluir</button></td>';
						users += '</tr>';
					}
				})
			}
			$('#tabelaUsuario').html(users);
			
		},
		error: function(err) {
			console.log(err)
			alert('Erro');
		}
	})
}

$("#busca").submit(function(){
	if ($("#search").val() != ""){
		$.ajax({
			type: "GET",
			url: '/admin/buscarUsuario/' + $("#search").val(),
			success: function (data){
			
				if (data.usuarios.length > 0){ 	
					var u;
						u += '<tr class="table-row"><td class="table-text"><h3>Id</h3></td>';
						u += '<td class="table-text"><h3>Nome</h3></td>';
						u += '<td></td><td></td><td></td>';
						u += '<td class="table-text"><h3>Tipo de Funcionario</h3></td></tr>';
					$.each(data.usuarios, function(i, obj){
						if(obj.tipoUsuario.id!=1){
							u += '<tr class="table-row">';
							u += '<td class="table-text"><h6>' + obj.id + '</h6></td>';
							u += '<td class="table-text"><h6>' + obj.nome + '</h6></td>';
							u += '<td></td><td></td><td></td>';
							u += '<td class="table-text"><h6>' + obj.tipoUsuario.descricao + '</h6></td>';
							u += '<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>'
							u += '<td class="march"><button class="btn btn-primary" onclick="editarRegistro(' + obj.id + ')">Editar</button>';
							u += '<button class="btn btn-primary" onclick="excluirRegistro(' + obj.id + ')">Excluir</button></td>';
							u += '</tr>';
						}
					})
					$('#tabelaUsuario').html(u);
				}else{
					$('#tabelaUsuario').html("");
				}
			},
			error: function(err) {
				console.log(err)
				alert('Erro');
			}
		});
	}else{
		carregarDados();
	}
	return false;
})

function excluirRegistro(id){
	bootbox.confirm('Deseja excluir o registro?', 
			function(resultado){
		if (resultado){
			var destino = "/admin/listarUsuarios/excluir/" + id;
			$.ajax({
				type : 'GET',
				url : destino,
				success: function(data){
					alert(data.mensagem);
					carregarDados();
				} //Fim success
			}); //Fim ajax
		}//Fim if
	}); //Fim bootbox
} //Fim excluirRegistro