package br.edu.utfpr.estacionafacil.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.utfpr.estacionafacil.model.Permissao;
import br.edu.utfpr.estacionafacil.model.TipoUsuario;
import br.edu.utfpr.estacionafacil.model.Usuario;
import br.edu.utfpr.estacionafacil.repository.PermissaoRepository;
import br.edu.utfpr.estacionafacil.repository.TipoUsuarioRepository;
import br.edu.utfpr.estacionafacil.repository.UsuarioRepository;


@Controller
public class UsuarioController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;
	
	@RequestMapping(value = "/admin/cadastrarUsuario")
	public String novo(){
		return "admin/cadastrarUsuario";
	}
	
	@RequestMapping(value = "/admin/listarUsuarios")
	public String listarUsuarios(){
		return "admin/list";
	}
	
	@RequestMapping(value = "/admin/carregarUser/{id}")
	public String editar(@PathVariable Long id, Model model){
		model.addAttribute("usuario", usuarioRepository.findOne(id) );
		return "admin/cadastrarUsuario";
	}
	
	@RequestMapping(value = "/admin/editarUsuario/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String busca(@PathVariable Long id){
		
		JSONObject retorno = new JSONObject();
		
		Usuario usuario = usuarioRepository.findOne(id);
		retorno.put("id", usuario.getId());
		retorno.put("nome", usuario.getNome());
		retorno.put("username", usuario.getUsername());
		retorno.put("password", usuario.getPassword());
		retorno.put("tipos", usuario.getTipoUsuario().getId());
		
		return retorno.toString();
	}
	
	@RequestMapping(value = "/admin/buscarUsuario/{search}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String buscar(@PathVariable String search){
		JSONObject retorno = new JSONObject();
		List<Usuario> usuarios = usuarioRepository.findByNomeContaining(search);
		retorno.put("usuarios", usuarios);
		
		return retorno.toString();
	}
	
	@RequestMapping(value = "/admin/listarUsuarios/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String listar(){
		JSONObject retorno = new JSONObject();
		List<Usuario> usuarios = usuarioRepository.findAll();
		retorno.put("usuarios", usuarios);
		return retorno.toString();
	}
	
	@RequestMapping(value = "/admin/listarUsuarios/excluir/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String excluir(@PathVariable Long id){
		JSONObject retorno = new JSONObject();
		
		try {
			Usuario usuario = usuarioRepository.findOne(id);
			
			if(usuario.getId()!=1){
				usuario.setPermissoes(null);
				usuarioRepository.save(usuario);
				usuarioRepository.delete(id);
				retorno.put("mensagem", "Registro removido com sucesso!");
			}else{
				retorno.put("mensagem", "Nao e possivel excluir o funcionario administrador!");
			}

		} catch (Exception e) {
			retorno.put("mensagem", "Falha ao remover registro!");
		}
		
		return retorno.toString();
	}
	
	@RequestMapping(value = "/admin/cadastrarUsuario/carregar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String carregar(){
		JSONObject retorno = new JSONObject();
		List<TipoUsuario> tipos = new ArrayList<>();
		List<TipoUsuario> l = tipoUsuarioRepository.findAll();
		for(TipoUsuario t : l){
			if(!t.getDescricao().toLowerCase().equals("user")){
				tipos.add(t);
			}
		}
		retorno.put("tipos", tipos);
		return retorno.toString();
	}
	
	@RequestMapping(value = "/admin/cadastrarUsuario/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String salvar(Usuario usuario, BindingResult erros ){
		JSONObject retorno = new JSONObject();
		try{
			if(erros.hasErrors()){
				retorno.put("situacao", "ERRO");
				retorno.put("mensagem", "Falha ao salvar o registro!");
				System.out.println(erros.toString());
			}else{
				String senha = usuario.getEncodedPassword(usuario.getPassword());
				usuario.setPassword(senha);
				//usuario.setTipoUsuario( tipoUsuarioRepository.findByDescricao("ADMIN") );
				usuario.addPermissao(getPermissao());
				usuarioRepository.save(usuario);
				
				retorno.put("situacao", "OK");
				retorno.put("mensagem", "Registro salvo com sucesso!");
				retorno.put("id", usuario.getId());
			}
		}catch(Exception e){
			retorno.put("situacao", "ERRO");
			retorno.put("mensagem", "Falha ao salvar o registro!");
		}
		return retorno.toString();
	}

	private Permissao getPermissao() {
		Permissao permissao = permissaoRepository.findByPermissao("ROLE_ADMIN");
		if (permissao == null){
			permissao = new Permissao();
			permissao.setPermissao("ROLE_ADMIN");
			permissaoRepository.save(permissao);
		}
		return permissao;
	}
	

	
}







