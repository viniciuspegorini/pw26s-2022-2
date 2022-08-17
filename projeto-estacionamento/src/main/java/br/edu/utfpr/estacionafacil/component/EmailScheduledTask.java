package br.edu.utfpr.estacionafacil.component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.edu.utfpr.estacionafacil.controller.EmailController;
import br.edu.utfpr.estacionafacil.model.Solicitacao;
import br.edu.utfpr.estacionafacil.repository.SolicitacaoRepository;

@Component
public class EmailScheduledTask {

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;
    
    //"0 0 16 * * *")
    @Scheduled(cron = "0 0 7 * * *")//
    public void enviarEmailAdesivoExpirado() {
    	int n = 7;
    	LocalDate data = LocalDate.now().plusDays(n);	
    	List<Solicitacao> lista = solicitacaoRepository.findByDataFim(data);
    	
    	lista.forEach( s ->{ 
    		criarEmail(s.getVeiculo().getCondutor().getEmail(),s.getVeiculo().getCondutor().getNome(), s.getDataFim());
    		criarEmailDirplad("dirplad-pb@utfpr.edu.br",s.getVeiculo().getCondutor().getNome(), s.getDataFim());
    		}
		);
    }//"dirplad-pb@utfpr.edu.br"
    
    public void criarEmail(String email, String nome, LocalDate data) {
    	EmailController emailController = new EmailController();

		String mensagem = "Olá " + nome + ",\n\n";
		mensagem += "Seu adesivo expira no dia " + data.format( DateTimeFormatter.ofPattern("dd/MM/yyy") ) + ".\n";
		mensagem += "Para regulariazar seu adesivo adicione os documentos necessários na solicitação.";

		mensagem += "\n**Email somente informativo. Para obter informações entre em contato com o DESEG por telefone.";
		mensagem += "\n\n\nAtenciosamente,\n--\nDESEG - Departamento de Serviços Gerais.\nUTFPR - Câmpus Pato Branco.";
		emailController.enviarEmail("Controle de adesivos  - Adesivo Expirado.", email, mensagem);
    	
    }
    
    public void criarEmailDirplad(String email, String nome, LocalDate data) {
    	EmailController emailController = new EmailController();

		String mensagem = "Olá,\n\n";
		mensagem += "O adesivo do aluno " + nome + " irá expirar no dia " + data.format( DateTimeFormatter.ofPattern("dd/MM/yyy") ) + ".\n";
		mensagem += "O aluno foi informado que deverá regulariazar seu adesivo.";

		mensagem += "\n**Email somente informativo. Para obter informações entre em contato com o DESEG por telefone.";
		mensagem += "\n\n\nAtenciosamente,\n--\nDESEG - Departamento de Serviços Gerais.\nUTFPR - Câmpus Pato Branco.";
		emailController.enviarEmail("Controle de adesivos  - Adesivo Expirado.", email, mensagem);
    	
    }
}