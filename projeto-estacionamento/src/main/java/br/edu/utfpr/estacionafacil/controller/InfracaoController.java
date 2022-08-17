package br.edu.utfpr.estacionafacil.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.utfpr.estacionafacil.model.Infracao;
import br.edu.utfpr.estacionafacil.model.Usuario;
import br.edu.utfpr.estacionafacil.repository.InfracaoRepository;

@Controller
public class InfracaoController {

	@Autowired
	private InfracaoRepository infracaoRepository;

	@RequestMapping(value = "/admin/listarInfracoes")
	public String listarInfracoes() {
		return "/admin/listarInfracoes";
	}

	@RequestMapping(value = "/admin/listarInfracoes/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String listar() {
		JSONObject retorno = new JSONObject();
		//Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//List<Infracao> infracoes = infracaoRepository.findByUsuarioOrderByIdAsc(usuario);
		List<Infracao> infracoes = infracaoRepository.findAll();
		retorno.put("infracoes", infracoes);
		return retorno.toString();
	}

	@RequestMapping(value = "/admin/justificarInfracao/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String salvar(Infracao infracao, BindingResult erros) {
		JSONObject retorno = new JSONObject();
		try {
			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Infracao just = infracaoRepository.findByIdAndUsuario(infracao.getId(), usuario);

			if (!infracao.getJustificativa().isEmpty() && !just.equals(null)) {
				just.setJustificado(true);
				just.setJustificativa(infracao.getJustificativa());
				infracaoRepository.save(just);
				retorno.put("situacao", "OK");
				retorno.put("mensagem", "Registro salvo com sucesso!");
			}
		} catch (Exception e) {
			retorno.put("situacao", "ERRO");
			retorno.put("mensagem", "Falha ao salvar o registro!");
		}
		return retorno.toString();
	}

}
