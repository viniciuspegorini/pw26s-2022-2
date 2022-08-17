package br.edu.utfpr.estacionafacil.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.utfpr.estacionafacil.model.EntradaTerceiros;
import br.edu.utfpr.estacionafacil.repository.EntradaTerceirosRepository;

@Controller
public class EntradaTerceirosController {
	
	@Autowired
	EntradaTerceirosRepository entradaTerceirosRepository;
	
	@RequestMapping(value = "/admin/entradaTerceiros")
	public String novo(){
		return "admin/entradaTerceiros";
	}
	
	@RequestMapping(value = "/admin/listarEntradaTerceiros")
	public String listarEntradaTerceiros(){
		return "admin/listarEntradaTerceiros";
	}
	
	@RequestMapping(value = "/admin/listarEntradaTerceiros/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String listar() throws ParseException{
		try{
			JSONObject retorno = new JSONObject();
			List<EntradaTerceiros> entradaTerceiros = entradaTerceirosRepository.findAll();
			retorno.put("entradaTerceiros", entradaTerceiros);
			return retorno.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/admin/listarEntradaTerceiros/excluir/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String excluir(@PathVariable Long id){
		JSONObject retorno = new JSONObject();
		
		try {
			entradaTerceirosRepository.delete(id);
			retorno.put("mensagem", "Registro removido com sucesso!");

		} catch (Exception e) {
			retorno.put("mensagem", "Falha ao remover registro!");
		}
		
		return retorno.toString();
	}
	
	@RequestMapping(value = "/admin/entradaTerceiros/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String salvar(EntradaTerceiros entradaTerceiros, BindingResult erros){
		System.out.println("Salvar");
		JSONObject retorno = new JSONObject();
		try{
			if(erros.hasErrors()){
				retorno.put("situacao", "ERRO");
				retorno.put("mensagem", "Falha ao salvar o registro!");
				System.out.println(erros.toString());
			}
			else{
				entradaTerceiros.setDataHoraEntrada(Calendar.getInstance().getTime());
				entradaTerceirosRepository.save(entradaTerceiros);
				retorno.put("situacao", "OK");
				retorno.put("mensagem", "Registro salvo com sucesso!");
			}
		}catch(Exception e){
			retorno.put("situacao", "ERRO");
			retorno.put("mensagem", "Falha ao salvar o registro!");
		}
		return retorno.toString();
	}
	
	
	
	
}
