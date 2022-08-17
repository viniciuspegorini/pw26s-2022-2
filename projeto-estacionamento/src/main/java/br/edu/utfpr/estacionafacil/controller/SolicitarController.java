package br.edu.utfpr.estacionafacil.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import br.edu.utfpr.estacionafacil.model.Anexo;
import br.edu.utfpr.estacionafacil.model.Condutor;
import br.edu.utfpr.estacionafacil.model.LogAcesso;
import br.edu.utfpr.estacionafacil.model.Marca;
import br.edu.utfpr.estacionafacil.model.Modelo;
import br.edu.utfpr.estacionafacil.model.Solicitacao;
import br.edu.utfpr.estacionafacil.model.Usuario;
import br.edu.utfpr.estacionafacil.model.Veiculo;
import br.edu.utfpr.estacionafacil.model.VeiculoTerceiro;
import br.edu.utfpr.estacionafacil.repository.AnexoRepository;
import br.edu.utfpr.estacionafacil.repository.CondutorRepository;
import br.edu.utfpr.estacionafacil.repository.LogAcessoRepository;
import br.edu.utfpr.estacionafacil.repository.MarcaRepository;
import br.edu.utfpr.estacionafacil.repository.ModeloRepository;
import br.edu.utfpr.estacionafacil.repository.SolicitacaoRepository;
import br.edu.utfpr.estacionafacil.repository.VeiculoRepository;
import br.edu.utfpr.estacionafacil.repository.VeiculoTerceiroRepository;

@Controller
public class SolicitarController {

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private AnexoRepository anexoRepository;

	@Autowired
	private MarcaRepository marcaRepository;

	@Autowired
	private ModeloRepository modeloRepository;

	@Autowired
	private CondutorRepository condutorRepository;

	@Autowired
	private VeiculoRepository veiculoRepository;

	@Autowired
	private LogAcessoRepository logAcessoRepository;

	@SuppressWarnings("unused")
	@Autowired
	private VeiculoTerceiroRepository veiculoTerceiroRepository;

	@RequestMapping(value = "/condutor/solicitar", method = RequestMethod.GET)
	public String solicitar(@RequestParam(value = "error", required = false) String error, Model model) {
		model.addAttribute("marcas", marcaRepository.findByOrderByNome());

		Veiculo veiculo = new Veiculo();
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		veiculo.setCondutor(usuario.getCondutor());
		model.addAttribute("veiculo", veiculo);
		return "condutor/solicitar";
	}

	@RequestMapping(value = "/condutor/solicitar/carregar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String carregar() {
		try {
			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getDetails();

			JSONObject retorno = new JSONObject();
			retorno.put("nome", usuario.getCondutor().getNome());
			retorno.put("email", usuario.getCondutor().getEmail());
			retorno.put("registro", usuario.getCondutor().getRegistro());
			retorno.put("telefone", usuario.getCondutor().getTelefone());
			List<Marca> marcas = marcaRepository.findByOrderByNome();
			retorno.put("marcas", marcas);

			return retorno.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	@RequestMapping(value = "/condutor/solicitar/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Veiculo veiculo = new Veiculo();
		try {
			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getDetails();

			veiculo = veiculoRepository.findOne(id);
			if (!veiculo.getCondutor().getId().equals(usuario.getCondutor().getId())) {
				LogAcesso logAcesso = new LogAcesso();
				logAcesso.setUsuario(usuario);
				logAcesso.setData(LocalDateTime.now());
				logAcesso.setFalca("True");
				logAcessoRepository.save(logAcesso);
				return "redirect:/erro403";
			}
			model.addAttribute("marcas", marcaRepository.findByOrderByNome());
			model.addAttribute("veiculo", veiculo);

			model.addAttribute("anexos",
					anexoRepository.findBySolicitacaoId(solicitacaoRepository.findByVeiculo(veiculo).getId()));

			System.out.println("TESTE");

			return "condutor/solicitar";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("veiculo", veiculo);

			return "condutor/solicitar";
		}

	}

	@RequestMapping(value = "/condutor/solicitar/modelos", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String modelos(Long id) {
		JSONObject retorno = new JSONObject();
		Marca marca = marcaRepository.findOne(id);
		List<Modelo> modelo = modeloRepository.findByMarcaOrderByNome(marca);

		retorno.put("modelo", modelo);

		return retorno.toString();
	}

	@RequestMapping(value = "/condutor/solicitar/salvar", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String salvar(@Valid Veiculo veiculo, Solicitacao solicitacao, VeiculoTerceiro veiculoTerceiro,
			Condutor condutor, BindingResult erros, Model model, HttpServletRequest request,
			@RequestParam("anexoPrincipal") MultipartFile[] anexos) {

		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Veiculo veiculoAux = new Veiculo();
		Solicitacao solicitacaoAux = new Solicitacao();
		veiculoAux.setSolicitacao(solicitacaoAux);

		if (request.getParameter("veiculoId") != null && !request.getParameter("veiculoId").isEmpty()) {
			veiculoAux = veiculoRepository.findOne(Long.parseLong(request.getParameter("veiculoId")));
			if (!veiculoAux.getCondutor().getId().equals(usuario.getCondutor().getId())) {
				LogAcesso logAcesso = new LogAcesso();
				logAcesso.setUsuario(usuario);
				logAcesso.setData(LocalDateTime.now());
				logAcesso.setFalca("True");
				logAcessoRepository.save(logAcesso);
				return "redirect:/erro403";
			} else {
				veiculo = veiculoAux;

				veiculoAux.setSolicitacao(solicitacaoRepository.findByVeiculo(veiculoAux));
				solicitacao.setId(veiculoAux.getSolicitacao().getId());
				solicitacao.setObservacoes(veiculoAux.getSolicitacao().getObservacoes());
				solicitacao.setDataFim(veiculoAux.getSolicitacao().getDataFim());

			}
		}
		condutor = condutorRepository.findByRegistro(condutor.getRegistro());
		JSONObject retorno = new JSONObject();
		try {
			List<Veiculo> lista = veiculoRepository.findByPlaca(veiculo.getPlaca());
			if (erros.hasErrors()) {
				retorno.put("situacao", "ERRO");
				retorno.put("mensagem", "Falha ao salvar o registro!");
				return retorno.toString();

			} else if (lista != null && veiculoAux.getSolicitacao().getId() == null && lista.size() > 0) {
				retorno.put("situacao", "PLACA");
				retorno.put("mensagem", "Placa já existente, entre em contato com o DESEG!");
				return retorno.toString();
			} else if (lista != null && veiculoAux.getSolicitacao().getId() != null && lista.size() > 0) {
				for (Veiculo v : lista) {
					if (!v.getCondutor().getId().equals(veiculoAux.getCondutor().getId())) {
						v.setSolicitacao(solicitacaoRepository.findByVeiculo(v));
						if (v.getSolicitacao().getStatus() == 1) {
							retorno.put("situacao", "PLACA");
							retorno.put("mensagem", "Placa já existente, entre em contato com o DESEG!");
							return retorno.toString();
						}
					}
				}

			}

			Date date = new Date(System.currentTimeMillis());
			solicitacao.setDataHora(date);

			if (!condutor.getTelefone().isEmpty()) {
				String telefone = request.getParameter("telefone");
				condutor.setTelefone(telefone);
				condutorRepository.save(condutor);

				if (veiculo.getId() == null) {
					veiculo.setCondutor(condutor);
					veiculo.setPlaca(veiculo.getPlaca().toString().toUpperCase());
				}
				veiculo = veiculoRepository.save(veiculo);

				veiculoTerceiro.setVeiculo(veiculo);
				veiculoRepository.save(veiculo);

				solicitacao.setVeiculo(veiculo);
				solicitacao.setStatus(0);

				solicitacao.setUsuario(usuario);
				solicitacaoRepository.save(solicitacao);

				// SALVAR ANEXOS
				String extensao;
				String nomeAnexo;
				// Caso seja um input com multiplos arquivos, grava cada anexo individualmente
				int i = 1;
				List<Anexo> listaAnexo = anexoRepository.findBySolicitacaoId(solicitacao.getId());
				if (listaAnexo.size() > 0) {
					i += listaAnexo.size();
				}
				try {
					if (anexos.length > 0) {
						for (MultipartFile a : anexos) {
							extensao = a.getOriginalFilename().substring(a.getOriginalFilename().lastIndexOf("."),
									a.getOriginalFilename().length());
							nomeAnexo = solicitacao.getId() + "_" + i + extensao;
							// grava cada anexo no disco
							Anexo anexo = new Anexo();
							anexo.setSolicitacao(solicitacao);
							anexo.setTipo("Arquivo");
							anexo.setNome(nomeAnexo);
							anexo.setArquivo(a.getBytes());
							anexo.setContentType(a.getContentType());
							anexoRepository.save(anexo);
							i++;
						}
					}
				} catch (java.lang.StringIndexOutOfBoundsException e) {
					// TODO: handle exception
				}

				retorno.put("situacao", "OK");
				retorno.put("mensagem", "Registro salvo com sucesso!");
				retorno.put("id", solicitacao.getId());
			} else {
				retorno.put("situacao", "TELEFONE");
				retorno.put("mensagem", "Por favor, informe o telefone");
			}

		} catch (Exception e) {
			retorno.put("situacao", "ERRO");
			retorno.put("mensagem", "Falha ao salvar o registro!");
			e.printStackTrace();
		}
		return retorno.toString();
	}

	@SuppressWarnings("unused")
	private void salvarImagemDisco(String caminho, byte[] bytes, HttpServletRequest request) throws Exception {
		try {
			FileOutputStream fileout = new FileOutputStream(new File(caminho + ".jpeg"));
			BufferedOutputStream stream = new BufferedOutputStream(fileout);
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao fazer upload da imagem: " + e.getMessage());
		}
	}

	@RequestMapping("/condutor/anexo/download/{veiculoId}/{anexoId}")
	@ResponseStatus(HttpStatus.OK)
	public void download(@PathVariable("veiculoId") Long veiculoId, @PathVariable("anexoId") Long anexoId,
			HttpServletResponse response) {

		Veiculo veiculo = new Veiculo();
		try {
			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getDetails();

			veiculo = veiculoRepository.findOne(veiculoId);
			if (!veiculo.getCondutor().getId().equals(usuario.getCondutor().getId())) {
				LogAcesso logAcesso = new LogAcesso();
				logAcesso.setUsuario(usuario);
				logAcesso.setData(LocalDateTime.now());
				logAcesso.setFalca("True - DW");
				logAcessoRepository.save(logAcesso);
				// return "redirect:/erro403";
			} else {
				Anexo anexo = anexoRepository.findOne(anexoId);
				response.setContentType(anexo.getContentType());
				response.setContentLength(anexo.getArquivo().length);
				response.setHeader("Content-Disposition", "attachment; filename=\"" + anexo.getNome() + "\"");

				try {
					FileCopyUtils.copy(anexo.getArquivo(), response.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return "";
	}
}
