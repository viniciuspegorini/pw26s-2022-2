package br.edu.utfpr.estacionafacil.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.utfpr.estacionafacil.model.Condutor;
import br.edu.utfpr.estacionafacil.model.Solicitacao;
import br.edu.utfpr.estacionafacil.model.Usuario;
import br.edu.utfpr.estacionafacil.model.Veiculo;
import br.edu.utfpr.estacionafacil.model.VeiculoTerceiro;
import br.edu.utfpr.estacionafacil.repository.CondutorRepository;
import br.edu.utfpr.estacionafacil.repository.SolicitacaoRepository;
import br.edu.utfpr.estacionafacil.repository.VeiculoRepository;
import br.edu.utfpr.estacionafacil.repository.VeiculoTerceiroRepository;

@Controller
@RequestMapping("/condutor")
public class CondutorController {

	@Autowired
	private CondutorRepository condutorRepository;

	@Autowired
	private VeiculoRepository veiculoRepository;

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private VeiculoTerceiroRepository veiculoTerceiroRepository;

	@RequestMapping(value = "/perfil", method = RequestMethod.GET)
	public String perfil(Model model, HttpServletRequest request) {
		String registro = (String) request.getSession().getAttribute("registro");
		Condutor condutor = condutorRepository.findByRegistro(registro);
		List<Veiculo> veiculos = veiculoRepository.findByCondutor(condutor);

		for (Veiculo veiculo : veiculos) {
			List<VeiculoTerceiro> veiculoTerceiros = veiculoTerceiroRepository.findByVeiculo(veiculo);
			veiculo.setVeiculoTerceiros(veiculoTerceiros);
			veiculo.setSolicitacao( solicitacaoRepository.findByVeiculo(veiculo) );
		}

		
		model.addAttribute("condutor", condutor);
		model.addAttribute("veiculos", veiculos);
		

		return "condutor/perfil";
	}

	@RequestMapping(value = "/veiculo/cancelar/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String cancelarVeiculo(@PathVariable Long id) {
		try {
			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getDetails();
			Veiculo v = veiculoRepository.findOne(id);
			if (usuario.getCondutor().getId().equals(v.getCondutor().getId())) {
				Solicitacao solicitacao = solicitacaoRepository.findByVeiculo(v);
				solicitacao.setStatus(3);
				solicitacao.setUsuario(usuario);
				solicitacaoRepository.save(solicitacao);

				return new JSONObject().put("mensagem", "OK").toString();
			} else {
				return new JSONObject().put("mensagem", "Esse veículo está associado a outro usuário.").toString();
			}
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			return new JSONObject().put("mensagem", "Veículo está vinculado a outro registro").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject().put("mensagem", "ERRO").toString();
		}

	}
	
	@RequestMapping(value = "/veiculo/reativar/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String reativarVeiculo(@PathVariable Long id) {
		try {
			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getDetails();
			Veiculo v = veiculoRepository.findOne(id);
			if (usuario.getCondutor().getId().equals(v.getCondutor().getId())) {
				Solicitacao solicitacao = solicitacaoRepository.findByVeiculo(v);
				solicitacao.setStatus(0);
				solicitacao.setUsuario(usuario);
				solicitacaoRepository.save(solicitacao);

				return new JSONObject().put("mensagem", "OK").toString();
			} else {
				return new JSONObject().put("mensagem", "Esse veículo está associado a outro usuário.").toString();
			}
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			return new JSONObject().put("mensagem", "Veículo está vinculado a outro registro.").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject().put("mensagem", "ERRO").toString();
		}

	}

	/*
	 * @RequestMapping(value="/veiculo/excluir", method=RequestMethod.GET, produces
	 * = MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ResponseBody public String excluirVeiculo(@RequestParam(value = "veiculo")
	 * String veiculo){ try{ Veiculo v =
	 * veiculoRepository.findOne(Long.parseLong(veiculo)); List<Solicitacao>
	 * solicitacoes = solicitacaoRepository.findByVeiculo(v); for (Solicitacao
	 * solicitacao : solicitacoes) { solicitacaoRepository.delete(solicitacao); }
	 * 
	 * veiculoRepository.delete(v); System.out.println("entrou"); return new
	 * JSONObject().put("mensagem", "OK").toString();
	 * }catch(DataIntegrityViolationException e){ e.printStackTrace(); return new
	 * JSONObject().put("mensagem",
	 * "Veículo está vinculado a outro registro").toString(); }catch (Exception e) {
	 * e.printStackTrace(); return new JSONObject().put("mensagem",
	 * "ERRO").toString(); }
	 * 
	 * }
	 */
}
