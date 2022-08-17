package br.edu.utfpr.estacionafacil.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import br.edu.utfpr.estacionafacil.model.Condutor;
import br.edu.utfpr.estacionafacil.model.LogAcesso;
import br.edu.utfpr.estacionafacil.model.Permissao;
import br.edu.utfpr.estacionafacil.model.TipoUsuario;
import br.edu.utfpr.estacionafacil.model.Usuario;
import br.edu.utfpr.estacionafacil.model.UsuarioLdap;
import br.edu.utfpr.estacionafacil.repository.CondutorRepository;
import br.edu.utfpr.estacionafacil.repository.LogAcessoRepository;
import br.edu.utfpr.estacionafacil.repository.PermissaoRepository;
import br.edu.utfpr.estacionafacil.repository.TipoUsuarioRepository;
import br.edu.utfpr.estacionafacil.repository.UsuarioRepository_;

@Controller
public class LoginController {

	@Autowired
	private CondutorRepository condutorRepository;
	
	@Autowired
	private LogAcessoRepository logAcessoRepository;

	@Autowired
	private UsuarioRepository_ usuarioRepository;

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private TipoUsuarioRepository tipoRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redireciona() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {

		if (error != null) {
			model.addAttribute("error", "Usuário e/ou senha inválidos!");
		}

		return "login";
	}
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {

		if (error != null) {
			model.addAttribute("error", "Usuário e/ou senha inválidos!");
		}

		return "login";
	}

	@RequestMapping(value = "/login/valida", method = RequestMethod.POST)
	@ResponseBody()
	public String valida(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, HttpServletRequest request) {
		// TODO --Ajustar o Login
		// TODO --Ajustar o retorno desse método para um JSON
		Authentication auth = new UsernamePasswordAuthenticationToken(null, null);
		try {
			Usuario usuario;
			UsuarioLdap usuarioLdap = autenticarLdap(username, password); //direto
			//UsuarioLdap usuarioLdap = getUsuarioLdap(username, password); //rest
			/*
			if (usuarioLdap != null && usuarioLdap.isLogado()) {
				usuario = usuarioRepository.findByUsername(username);
				if (usuario == null && usuarioLdap.getNome() != "") {
					// Verifica se o condutor está cadastrado
					if (condutorRepository.findByRegistro(usuarioLdap.getUsuario()) == null) {
						// insere o novo condutor
						Condutor c = new Condutor();
						TipoUsuario t = new TipoUsuario();
						if (isAluno(username)) {
							t = tipoRepository.findByDescricao("ALUNO");
						}else {
							t = tipoRepository.findByDescricao("USER");
						}
						c.setTipoUsuario(t);
						
						
						c.setNome(usuarioLdap.getNome() + " " + usuarioLdap.getSobrenome());
						c.setRegistro(usuarioLdap.getUsuario());
						c.setEmail(usuarioLdap.getEmail());
						c.setTelefone("Não cadastrado");
						c.setTipoUsuario(t);
						c = condutorRepository.save(c);

						usuario = new Usuario();
						usuario.setNome(c.getNome());
						usuario.setUsername(c.getRegistro());
						String passwordCript = usuario.getEncodedPassword(password);
						usuario.setPassword(passwordCript);
						Permissao permissao = permissaoRepository.findByPermissao("ROLE_USER");
						usuario.addPermissao(permissao);
						usuario.setTipoUsuario(t);
						usuario.setCondutor(c);

						usuarioRepository.save(usuario);
					}
				}
				usuario.getPermissoes().forEach( p ->{
					if (p.getPermissao().equals("ROLE_ADMIN")) {
						request.getSession().setAttribute("admin", true);
					}
				});
				request.getSession().setAttribute("tipo", usuario.getTipoUsuario());
				
				// Faz uma autoAutenticação
				List<GrantedAuthority> permissaoList = new ArrayList<>();
				permissaoList.addAll(permissaoRepository.getListaPermissoes(usuario.getId()));
				UsernamePasswordAuthenticationToken wrappingAuth = new UsernamePasswordAuthenticationToken(
						usuario.getUsername(), password, permissaoList);
				wrappingAuth.setDetails(usuario);
				
				auth = wrappingAuth;
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				LogAcesso logAcesso = new LogAcesso();
				logAcesso.setUsuario(usuario);
				logAcesso.setData( LocalDateTime.now() );
				
				logAcessoRepository.save(logAcesso);
			} else {
				auth.setAuthenticated(false);
			}
			*/
			if (auth.isAuthenticated()) {
				return new JSONObject().put("mensagem", "OK").toString();
			} else {
				return new JSONObject().put("mensagem", "ERRO").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject().put("mensagem", "ERRO").toString();
		}

	}

	/*@Autowired
	AuthenticationManager authenticationManager;

	@RequestMapping(value = "/login/validaAdm", method = RequestMethod.POST)
	@ResponseBody()
	public String validaAdmin(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, HttpServletRequest request) {
		// TODO --Ajustar o Login
		// TODO --Filtrar lista de solicitação por status usuário admin
		// TODO --Permitir consultar as próprias solicitações
		// TODO --Ajustar o retorno desse método para um JSON
		Authentication auth = new UsernamePasswordAuthenticationToken(null, null);
		try {
			Usuario usuario;
			usuario = usuarioRepository.findByUsername(username);

			if (usuario != null) {
				// Faz uma autoAutenticação
				
				 * List<GrantedAuthority> permissaoList = new ArrayList<>();
				 * permissaoList.addAll(permissaoRepository.getListaPermissoes(usuario.getId()))
				 * ; auth = new UsernamePasswordAuthenticationToken(usuario.getUsername(),
				 * password, permissaoList);
				 * 
				 * if (auth.isAuthenticated()) { System.out.println("->> PASSOU");
				 * UsernamePasswordAuthenticationToken wrappingAuth = new
				 * UsernamePasswordAuthenticationToken( usuario.getUsername(), password,
				 * permissaoList); wrappingAuth.setDetails(usuario); auth = wrappingAuth; }
				 

				List<GrantedAuthority> permissaoList = new ArrayList<>();
				permissaoList.addAll(permissaoRepository.getListaPermissoes(usuario.getId()));
				//UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				//		usuario.getUsername(), password, permissaoList);

				Authentication result = this.authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(usuario.getUsername(), password, permissaoList));

				auth = result;
				// SecurityContextHolder.getContext.setAuthentication(result);

				// Authenticate the user
				// auth = authenticationProvider.authenticate(authRequest);
				if (result.isAuthenticated()) {
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(result);

					// Create a new session and add the security context.
					HttpSession session = request.getSession(true);
					session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
				}

			}
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

			if (auth.isAuthenticated()) {
				System.out.println("*****************************************************TRUE");
				return new JSONObject().put("mensagem", "OK").toString();
			} else {
				System.out.println("*****************************************************FALSE");
				return new JSONObject().put("mensagem", "ERRO").toString();
			}
		} catch (Exception e) {
			System.out.println("*****************************************************ERRO");
			e.printStackTrace();
			return new JSONObject().put("mensagem", "ERRO").toString();
		}

	}*/

	private boolean isAluno(String username) {
		String a = username.substring(0,1);
		if (a.equalsIgnoreCase("a")) {
			String num = username.substring(1,5);
			if (NumberUtils.isNumber(num)) {
				System.out.println("Sim");
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	@RequestMapping(value = "/index")
	public String home(Model model, HttpServletRequest request) {
		try {

			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getDetails();
			if ("USER".equalsIgnoreCase(usuario.getTipoUsuario().getDescricao()) || 
					"ALUNO".equalsIgnoreCase(usuario.getTipoUsuario().getDescricao())) {
				request.getSession().setAttribute("registro", usuario.getCondutor().getRegistro());
				return "redirect:/condutor/perfil";
			} else if ("ADMIN".equalsIgnoreCase(usuario.getTipoUsuario().getDescricao())) {
				return "redirect:/admin/";
			} else {
				return "redirect:/login";
			}

		} catch (java.lang.ClassCastException e) {
			System.out.println("TESTE");
			e.printStackTrace();
			try {

				Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if ("USER".equalsIgnoreCase(usuario.getTipoUsuario().getDescricao()) || 
						"ALUNO".equalsIgnoreCase(usuario.getTipoUsuario().getDescricao())) {
					request.getSession().setAttribute("registro", usuario.getCondutor().getRegistro());
					return "redirect:/condutor/perfil";
				} else if ("ADMIN".equalsIgnoreCase(usuario.getTipoUsuario().getDescricao())) {
					return "redirect:/admin/";
				} else {
					return "redirect:/login";
				}

			} catch (Exception e2) {
				System.out.println("TESTE 1");
				e2.printStackTrace();
				return "redirect:/";
			}
		} catch (Exception e) {
			System.out.println("TESTE 2");
			e.printStackTrace();
			return "redirect:/";
		}

	}

	@RequestMapping(value = "/erro403")
	public String error(Model model) {
		model.addAttribute("info", "Acesso negado");
		return "erro403";
	}

	@SuppressWarnings("unused")
	private UsuarioLdap autenticarLdap(String username, String password) {
		UsuarioLdap usuario = new UsuarioLdap();
		try {

			usuario = getUid(username);
			if (usuario != null) {
				/* Found user - test password */
				if (testBind(usuario.getDn(), password)) {
					usuario.setLogado(true);
					usuario.setMensagem("Usuário: " + username + " autenticado com sucesso.");
				} else {
					usuario.setLogado(false);
					usuario.setMensagem("Falha ao autenticar o usuário: " + username + ".");
				}
			} else {
				usuario = new UsuarioLdap();
				usuario.setLogado(false);
				usuario.setMensagem("O usuário: " + username + " não foi encontrado.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			usuario = new UsuarioLdap();
			usuario.setLogado(false);
			usuario.setMensagem("Falha ao autenticar o usuário: " + username + "...");
		}
		return usuario;
	}

	private final static String ldapURI = "ldap://172.29.150.133:389/ou=todos,dc=utfpr,dc=edu,dc=br";
	private final static String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";

	private DirContext ldapContext() {
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			return ldapContext(env);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private DirContext ldapContext(Hashtable<String, String> env) throws Exception {
		env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		env.put(Context.PROVIDER_URL, ldapURI);
		DirContext ctx = new InitialDirContext(env);
		return ctx;
	}

	private UsuarioLdap getUid(String user) {
		UsuarioLdap usuario = null;
		try {
			DirContext ctx = ldapContext();

			String filter = "(uid=" + user + ")";
			SearchControls ctrl = new SearchControls();
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			@SuppressWarnings("rawtypes")
			NamingEnumeration answer = ctx.search("", filter, ctrl);

			if (answer.hasMore()) {
				SearchResult result = (SearchResult) answer.next();
				Attributes attrs = result.getAttributes();
				usuario = new UsuarioLdap();
				usuario.setNome(attrs.get("givenName").get().toString());
				usuario.setSobrenome(attrs.get("sn").get().toString());
				usuario.setUsuario(attrs.get("uid").get().toString());
				usuario.setEmail(attrs.get("mail").get().toString());
				usuario.setCpf(attrs.get("employeeNumber").get().toString());
				usuario.setCampus(attrs.get("l").get().toString());
				usuario.setDn(result.getNameInNamespace());
			}
			answer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return usuario;
	}

	private boolean testBind(String dn, String password) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, dn);
		env.put(Context.SECURITY_CREDENTIALS, password);

		try {
			ldapContext(env);
		} catch (javax.naming.AuthenticationException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private UsuarioLdap getUsuarioLdap(String username, String password) {
		UsuarioLdap usuarioLdap = new UsuarioLdap();
		try {
			String ra = username;
			String senha = password;

			// Utilizado para evitar o https sem certificado do servidor
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			String retorno = "";
			String urlParameters = "usuario=" + ra + "&senha=" + senha + "";
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			String urlRequest = "https://201.89.227.156:8181/autenticaldap/authldap";
			// String urlRequest =
			// "https://172.29.13.254:8181/autenticacaldap/authldap";
			URL url = new URL(urlRequest);
			URLConnection uConn = url.openConnection();

			if (uConn instanceof HttpsURLConnection) {
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				conn.setHostnameVerifier(allHostsValid);
				conn.setDoOutput(true);
				conn.setInstanceFollowRedirects(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("charset", "utf-8");
				conn.setRequestProperty("charset", "utf-8");
				conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
				conn.setRequestProperty("", "true");
				conn.setUseCaches(false);
				conn.getOutputStream().write(postData);

				Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				for (int c; (c = in.read()) >= 0;) {
					sb.append((char) c);
				}
				retorno = sb.toString();

				usuarioLdap = new Gson().fromJson(retorno, UsuarioLdap.class);
			}
			// response.addHeader("Access-Control-Allow-Origin", "*");
			// out.print(retorno);
		} catch (NoSuchAlgorithmException | KeyManagementException ex) {
			// out.print("Erro: " + ex.getMessage());
		} catch (Exception ex) {
			// out.print("Erro: " + ex.getMessage());
		}
		return usuarioLdap;
	}
}
