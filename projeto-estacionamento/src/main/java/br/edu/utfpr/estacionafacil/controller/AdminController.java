package br.edu.utfpr.estacionafacil.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import br.edu.utfpr.estacionafacil.model.Marca;
import br.edu.utfpr.estacionafacil.model.Modelo;
import br.edu.utfpr.estacionafacil.model.Solicitacao;
import br.edu.utfpr.estacionafacil.model.Usuario;
import br.edu.utfpr.estacionafacil.model.Veiculo;
import br.edu.utfpr.estacionafacil.model.VeiculoTerceiro;
import br.edu.utfpr.estacionafacil.repository.AnexoRepository;
import br.edu.utfpr.estacionafacil.repository.CondutorRepository;
import br.edu.utfpr.estacionafacil.repository.MarcaRepository;
import br.edu.utfpr.estacionafacil.repository.ModeloRepository;
import br.edu.utfpr.estacionafacil.repository.SolicitacaoRepository;
import br.edu.utfpr.estacionafacil.repository.TipoUsuarioRepository;
import br.edu.utfpr.estacionafacil.repository.VeiculoRepository;
import br.edu.utfpr.estacionafacil.repository.VeiculoTerceiroRepository;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private AnexoRepository anexoRepository;

	@Autowired
	private VeiculoTerceiroRepository veiculoTerceiroRepository;

	@Autowired
	private VeiculoRepository veiculoRepository;

	@Autowired
	private CondutorRepository condutorRepository;

	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;

	@Autowired
	private MarcaRepository marcaRepository;

	@Autowired
	private ModeloRepository modeloRepository;
	
	@RequestMapping(value = { "/", "" })
	public String home(Model model) {
		return "admin/inbox";
	}

	@RequestMapping(value = "/dados", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String listarDados() {

		List<Solicitacao> lista = solicitacaoRepository.findByStatusOrderByDataHora(0);
		
		JSONObject retorno = new JSONObject();
		JSONArray array = new JSONArray();
		JSONArray ja;
		for (Solicitacao solicitacao : lista) {
			ja = new JSONArray();
			ja.put(solicitacao.getId());
			ja.put(solicitacao.getVeiculo().getCondutor().getNome());
			ja.put(solicitacao.getVeiculo().getCondutor().getRegistro());
			ja.put(solicitacao.getVeiculo().getModelo().getNome());
			ja.put(solicitacao.getVeiculo().getPlaca());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String format = sdf.format(solicitacao.getDataHora());
			ja.put(format);
			ja.put(getSituacao(solicitacao.getStatus()));
			array.put(ja);
		}

		retorno.put("data", array);

		return retorno.toString();
	}

	@RequestMapping(value = "/dados/{status}/{tipo}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ResponseBody
	public String listarDados(@PathVariable Integer status, @PathVariable Long tipo, Model model, HttpServletRequest request) {
		List<Solicitacao> lista;
		
		if (request.getParameter("nome") != null && !request.getParameter("nome").trim().isEmpty()) {
			String nome = "%" + request.getParameter("nome") + "%";
			
			if (tipo.longValue() == 0) {
				lista = solicitacaoRepository.findByStatusAndVeiculoCondutorNomeLikeOrderByDataHora(status, nome);
			} else {
				lista = solicitacaoRepository.findByStatusAndVeiculoCondutorTipoUsuarioIdAndVeiculoCondutorNomeLikeOrderByDataHora(status, tipo, nome);
			}
		}else {
			if (tipo.longValue() == 0) {
				lista = solicitacaoRepository.findByStatusOrderByDataHora(status);
			} else {
				lista = solicitacaoRepository.findByStatusAndVeiculoCondutorTipoUsuarioId(status, tipo);
			}
		}
		
		
		

		JSONObject retorno = new JSONObject();
		JSONArray array = new JSONArray();
		JSONArray ja;
		for (Solicitacao solicitacao : lista) {
			ja = new JSONArray();
			ja.put(solicitacao.getId());
			ja.put(solicitacao.getVeiculo().getCondutor().getNome());
			ja.put(solicitacao.getVeiculo().getCondutor().getRegistro());
			ja.put(solicitacao.getVeiculo().getModelo().getNome());
			ja.put(solicitacao.getVeiculo().getPlaca());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String format = sdf.format(solicitacao.getDataHora());
			ja.put(format);
			ja.put(getSituacao(solicitacao.getStatus()));
			if (solicitacao.getVeiculo().getNumAdesivo() == null) {
				ja.put("---");
			} else {
				ja.put(solicitacao.getVeiculo().getNumAdesivo());
			}
			ja.put(solicitacao.getVeiculo().getCondutor().getTipoUsuario().getId());
			ja.put(solicitacao.getVeiculo().getCondutor().getTipoUsuario().getDescricao());
			array.put(ja);
		}

		retorno.put("data", array);

		return retorno.toString();
	}

	public String getSituacao(int status) {
		if (status == 0) {
			return "Solicitado";
		} else if (status == 1) {
			return "Aprovado";
		} else if (status == 2) {
			return "Recusado";
		} else {
			return "Cancelado";
		}
	}

	@RequestMapping(value = "/{id}")
	public String editarSolicitacao(@PathVariable Long id, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("anexos", anexoRepository.findBySolicitacaoId(id));
		if (id != 0) {
			Solicitacao solicitacao = solicitacaoRepository.findById(id);
			model.addAttribute("status", solicitacao.getStatus());
		}

		return "admin/editarSolicitacao";
	}

	@RequestMapping(value = "carregar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String carregar(@PathVariable Long id) {
		try {
			Solicitacao solicitacao = solicitacaoRepository.findById(id);
			JSONObject retorno = new JSONObject();
			retorno.put("veiculo", solicitacao.getVeiculo().getId());
			retorno.put("nome", solicitacao.getVeiculo().getCondutor().getNome());
			retorno.put("email", solicitacao.getVeiculo().getCondutor().getEmail());
			retorno.put("registro", solicitacao.getVeiculo().getCondutor().getRegistro());
			retorno.put("telefone", solicitacao.getVeiculo().getCondutor().getTelefone());
			retorno.put("marca", solicitacao.getVeiculo().getModelo().getMarca().getNome());
			retorno.put("marcaTipo", solicitacao.getVeiculo().getModelo().getMarca().getTipo());
			retorno.put("modelo", solicitacao.getVeiculo().getModelo().getNome());
			retorno.put("placa", solicitacao.getVeiculo().getPlaca());
			retorno.put("cor", solicitacao.getVeiculo().getCor());
			retorno.put("file", "");
			retorno.put("status", solicitacao.getStatus());
			
			if (solicitacao.getDataFim() != null) {
				retorno.put("dataFim", solicitacao.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyy")))	;
			}
			retorno.put("adesivo", solicitacao.getVeiculo().getNumAdesivo());
			retorno.put("tipoUsuario", solicitacao.getVeiculo().getCondutor().getTipoUsuario().getId());
			VeiculoTerceiro vt = veiculoTerceiroRepository.findBySolicitacao(solicitacao);
			retorno.put("observacoes", solicitacao.getObservacoes());
			if (vt != null) {
				retorno.put("veiculoTerceiro", vt.getId());
				retorno.put("nomeTerceiro", vt.getNome());
				retorno.put("telTerceiro", vt.getTelefone());
				retorno.put("parentesco", vt.getParentesco());
				retorno.put("proprietario", 0);
				retorno.put("fileTerceiro", vt.getAnexo());
			} else {
				retorno.put("proprietario", 1);
			}

			return retorno.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@RequestMapping(value = "salvar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String salvar(@Valid Solicitacao solicitacao, BindingResult erros, Model model,
			@RequestParam(value = "adesivo") String numAdesivo, 
			@RequestParam("dataFim") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim) {
		JSONObject retorno = new JSONObject();
		
		try {
			Solicitacao solicitacaoAux = solicitacaoRepository.findById(solicitacao.getId());

			if (solicitacao.getStatus() == 0 && numAdesivo != null && !numAdesivo.trim().isEmpty() ) {
				solicitacaoAux.setStatus(1);
				solicitacao.setStatus(1);
				List<Veiculo> lista = veiculoRepository.findByNumAdesivoAndIdNotAndCondutorTipoUsuarioId(numAdesivo, solicitacaoAux.getVeiculo().getId(), 
						solicitacaoAux.getVeiculo().getCondutor().getTipoUsuario().getId());
				if (lista != null && lista.size() > 0) {
					retorno.put("situacao", "ERRO");
					retorno.put("mensagem", "Esse número de adesivo já foi utilizado!");
					return retorno.toString();
				}
			}
			solicitacaoAux.setDataFim(dataFim);
			solicitacaoAux.setObservacoes(solicitacao.getObservacoes());
			solicitacaoAux.setStatus(solicitacao.getStatus());
			Usuario usuario = new Usuario();
			try {
				usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			} catch (java.lang.ClassCastException e) {
				usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getDetails();
			}
			solicitacaoAux.setUsuario(usuario);
			
			solicitacaoRepository.save(solicitacaoAux);
			
			Veiculo v = new Veiculo();
			v = solicitacaoAux.getVeiculo();
			if (solicitacao.getStatus() == 1) {
				v.setAutorizado(true);
			}else {
				v.setAutorizado(false);
			}
			v.setNumAdesivo(numAdesivo);
			veiculoRepository.save(v);
			
			
			
			if (solicitacao.getStatus() != 0) {
				EmailController emailController = new EmailController();

				String status = solicitacao.getStatus() == 1 ? "aprovada" : "recusada";
				String email = v.getCondutor().getEmail();
				String mensagem = "Olá " + solicitacaoAux.getVeiculo().getCondutor().getNome() + ",\n\n";
				mensagem += "Sua solicitação de adesivo foi " + status + ".\n";
				if ("aprovada".equalsIgnoreCase(status)) {
					mensagem += "Passe no DESEG para retirar seu adesivo.";
				}else {
					mensagem += "Entre em contato com o DESEG para maiores detalhes.\n";
					mensagem += "*Observações: " + solicitacao.getObservacoes();
				}
				mensagem += "\n**Email somente informativo. Para obter informações entre em contato com o DESEG por telefone.";
				mensagem += "\n\n\nAtenciosamente,\n--\nDESEG - Departamento de Serviços Gerais.\nUTFPR - Câmpus Pato Branco.";
				emailController.enviarEmail("Solicitação de adesivo", email, mensagem);
			}

			retorno.put("situacao", "OK");
			retorno.put("mensagem", "Registro salvo com sucesso!");
			retorno.put("id", solicitacaoAux.getId());
		} catch (Exception e) {
			e.printStackTrace();
			retorno.put("situacao", "ERRO");
			retorno.put("mensagem", "Falha ao salvar o registro!");
		}

		return retorno.toString();
	}

	@RequestMapping("/download/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void download(@PathVariable("id") Long id, HttpServletResponse response) {
		Anexo anexo = anexoRepository.findOne(id);
		response.setContentType(anexo.getContentType());
		response.setContentLength(anexo.getArquivo().length);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + anexo.getNome() + "\"");

		try {
			FileCopyUtils.copy(anexo.getArquivo(), response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return "redirect:/add-document-"+id;

	}

	@RequestMapping(value = { "/tercerizado/cadastrar" })
	public String tercerizado(Model model) {
		model.addAttribute("marcas", marcaRepository.findByOrderByNome());
		return "admin/cadastrarTercerizado";
	}

	@RequestMapping(value = "/tercerizado/editar/{id}")
	public String tercerizadoCarregar(@PathVariable("id") Long id, Model model) {
		model.addAttribute("marcas", marcaRepository.findByOrderByNome());
		model.addAttribute("solicitacao", solicitacaoRepository.findOne(id));
		model.addAttribute("anexos", anexoRepository.findBySolicitacaoId(id));
		
		return "admin/cadastrarTercerizado";
	}
	
	@RequestMapping(value = "/solicitar/modelos", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String modelos(Long id) {
		JSONObject retorno = new JSONObject();
		Marca marca = marcaRepository.findOne(id);
		List<Modelo> modelo = modeloRepository.findByMarcaOrderByNome(marca);

		retorno.put("modelo", modelo);

		return retorno.toString();
	}

	@RequestMapping(value = "/tercerizado/salvar", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String salvarTercerizado(@Valid Veiculo veiculo, Solicitacao solicitacao, Condutor condutor,
			BindingResult erros, Model model, HttpServletRequest request,
			@RequestParam("anexoPrincipal") MultipartFile[] anexos) {

		if (request.getParameter("solicitacaoId") != null && !request.getParameter("solicitacaoId").isEmpty()) {
			solicitacao.setId(Long.parseLong(request.getParameter("solicitacaoId")));
			Solicitacao solicitacaoAux = solicitacaoRepository.findOne(solicitacao.getId());
			veiculo.setId(solicitacaoAux.getVeiculo().getId());
			condutor.setId(solicitacaoAux.getVeiculo().getCondutor().getId());
		}

		JSONObject retorno = new JSONObject();
		try {
			List<Veiculo> lista = veiculoRepository.findByPlaca(veiculo.getPlaca());
			if (erros.hasErrors()) {
				retorno.put("situacao", "ERRO");
				retorno.put("mensagem", "Falha ao salvar o registro!");
			} else if (lista != null && solicitacao.getId() == null && lista.size() > 0) {
			//} else if (veiculoRepository.findByPlaca(veiculo.getPlaca()) != null && solicitacao.getId() == null) {
				retorno.put("situacao", "PLACA");
				retorno.put("mensagem", "Placa já existente");
				return retorno.toString();
			} else {
				Date date = new Date(System.currentTimeMillis());
				solicitacao.setDataHora(date);

				if (!condutor.getTelefone().isEmpty()) {

					condutor.setTipoUsuario(tipoUsuarioRepository.findByDescricao("EXTERNO"));
					condutor.setRegistro(condutor.getEmail());
					condutorRepository.save(condutor);

					veiculo.setCondutor(condutor);
					veiculo.setPlaca(veiculo.getPlaca().toString().toUpperCase());
					
					if (solicitacao.getStatus() == 1) {
						veiculo.setAutorizado(true);
					}else {
						veiculo.setAutorizado(false);
					}
					
					veiculoRepository.save(veiculo);
					
					solicitacao.setVeiculo(veiculo);

					Usuario usuario = new Usuario();
					try {
						usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					} catch (java.lang.ClassCastException e) {
						usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getDetails();
					}

					if (veiculo.getNumAdesivo() != null && !veiculo.getNumAdesivo().isEmpty()) {
						solicitacao.setStatus(1);
					}
					solicitacao.setUsuario(usuario);
					solicitacaoRepository.save(solicitacao);

					// SALVAR ANEXOS
					String extensao;
					String nomeAnexo;
					try {
						if (anexos.length > 0) {
							// Caso seja um input com multiplos arquivos, grava cada anexo individualmente
							int i = 1;
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

			}
		} catch (Exception e) {
			retorno.put("situacao", "ERRO");
			retorno.put("mensagem", "Falha ao salvar o registro!");
			e.printStackTrace();
		}
		return retorno.toString();
	}

}