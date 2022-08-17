package br.edu.utfpr.estacionafacil.controller;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailController {

	private Session session;

	 public EmailController() {
			Properties props = new Properties();
			//props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.host", "smtp.utfpr.edu.br");
			
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						//return new PasswordAuthentication("estacionafacilutfpr@gmail.com","estacionafacil$1");
						return new PasswordAuthentication("estacionamento-pb","utfpr@0609");
					}
				});
	}
	

	public void enviarEmail(String assunto, String destinatario, String texto){
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("estacionamento-pb@utfpr.edu.br"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(destinatario));
			message.setSubject(assunto);
			message.setText(texto);
			
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
