package it.exobank.email;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import it.exobank.utils.Costanti;


/*
 * JavaMail è una libreria Java che consente di gestire operazioni di posta elettronica 
 * all'interno delle applicazioni Java ed invio email attraverso protocolli standard come SMTP, IMAP e POP3
 */

public class SendEmail {
	
	public void sendEmail(String recipientEmail, String subject, String messageContent) throws Exception{
		sendEmail(recipientEmail, subject, messageContent, null, null);
	}

	public void sendEmail(String recipientEmail, String subject, String messageContent, String attachmentFilePath, String estensioni) throws Exception{
		
		/*
		 * Definisco due stringhe che rappresentano lo username e la password 
		 * che mi serviranno per accedere al mio indirizzo di posta
		 */
		
		final String username = Costanti.EMAIL_MITTENTE;            		
		final String password = Costanti.PASSWORD_MITTENTE;
		
		/*
		 * Definisco un oggetto Properties(java.util) dove configuro le proprietà
		 * per la connessione SMPT (Simple Mail Transfert Protocol),
		 * usando il metodo put(key, value) poichè l'oggetto Properties 
		 * estende la classe HashTable<K,V>
		 */

		Properties props = setProperties("true", "true", "out.virgilio.it", "587");
		
		/*
		 * Definisco un oggetto Session(java.mail) utilizzando il metodo getInstance(Singleton) 
		 * e passando nei parametri due argomenti al costruttore: l'oggetto props che ho definito precedentemente 
		 * e un oggetto anonimo di un istanza Authenticator che ha la funzione di fornire le credenziali
		 * di accesso per inviare le email; all'interno di quest'oggetto sovrascrivo il metodo getPasswordAuthentication
		 * che mi restituirà un oggetto PasswordAuthentication utilizzato per incapsulare le credenziali
		 * di accesso e fornirle al server SMTP
		 */
		
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {	
			
			/*
			 * Creo una condizione per cui se la stringa attachmentFilePath è nulla, crea l'oggetto Message semplice e lo invia
			 * mentre se esiste creo un oggetto di tipo Message che accetta un allegato
			 */
			
			if(null == attachmentFilePath) {
				
				Message message = createMessage(session, username, recipientEmail, subject, messageContent); 
				
				/*
				 * La classe astratta Transport è la responsabile della gestione dell'invio dell'email
				 * Il metodo send(message) viene utilizzata da Transport per inviare l'oggetto message al server SMTP 
				 * del destinatario
	 			 */
				
				Transport.send(message);
				
			} else {
				
				Message message = createMessageWithAttachment(session, username, recipientEmail, subject, messageContent, attachmentFilePath, estensioni);				
				Transport.send(message);
			}
			
			System.out.println(Costanti.EMAIL_INVIATA_CON_SUCCESSO);

		} catch (MessagingException e) {
			System.out.println(Costanti.EMAIL_NON_INVIATA_CON_SUCCESSO);
			e.printStackTrace();
			throw new Exception("Errore invio email");
		}
	}
	
	/*
	 * MIME --> (Multipurpose Internet Mail Extensions) è un sistema di specifiche che consente di inviare contenuti multimediali e dati non testuali tramite il protocollo di posta elettronica
	 */
	
	/*
	 * Definisco un metodo privato createMessage() per la creazione e la configurazione di un oggetto Message tramite l'istanza
	 * della classe MimeMessage che è un implementazione concreta della classe astratta Message, la quale non è 
	 * istanziabile  
	 */
	
	private Properties setProperties(String... conf) {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", conf[0]);              // Indica che l'autenticazione è abilitata
		props.put("mail.smtp.starttls.enable", conf[1]);   // Indica che il protocollo TLS (Transport Layer Security) è abilitato  
		props.put("mail.smtp.host", conf[2]);              // Indica il nome dell'host a cui connettersi (in questo caso Virgilio.it)
		props.put("mail.smtp.port", conf[3]);              // Indica la porta del server SMTP a cui connettersi
		
		return props;
	}
	
	
	
	private Message createMessage(Session session, String username, String recipientEmail, String subject, String messageContent) throws MessagingException {		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username));                                           // Imposto il mittente dell'email
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));   // Imposto il destinatario convertendo la string email in un oggetto InternetAddress che rappresenta un indirizzo email valido 
		                                                                                          // Tramite Message.RecipientType.TO sto impostando il destinatario principale dell'email
		message.setSubject(subject);                                                              // Imposto l'oogetto dell'email 
		message.setText(messageContent);                                                          // Imposto il contenuto dell'email
		return message;
		
	}
	
	/*
	 * Definisco un metodo privato createMessageWithAttachment() per la creazione e la configurazione di
	 * un oggetto Message che supporti un allegato; quindi tramite l'istanza MimeMultipart che estende la classe astratta Multipart 
	 * creo un oggetto di tipo Multipart, il quale rappresenta un contenitore utilizzato per contenere le diverse parti di un email,
	 * compresi i suoi allegati; tra questi verranno aggiunti tramite il metodo addBodyPart() gli oggetto di tipo MimeBodyPart(classe che estende Bodypart e implementa 
	 * l'interfaccia MimePart): il messageBodyPart conterrà tramite il metodo setText() il messaggio dell'email, mentre invece l'attachmentPart,
	 * conterrà tramite il metodo setFileName() il nome del file con l'estensione conforme e un oggetto di tipo FileDataSource che otterrò tramite il costruttore
	 * con parametro in input il percorso del file che si vuole inviare. Tramite il metodo setDataHandler() assegno un oggetto di tipo DataHandler(oggetto che rappresenta un gestore  
	 * dei dati da varie sorgenti) a una parte del corpo dell'email, utilizzando le informazione fornite da source(FileDataSource) 
	 * 
	 *   
	 */
	
	private Message createMessageWithAttachment(Session session, String username, String recipientEmail, String subject, String messageContent, String attachmentFilePath, String estensione) throws MessagingException {
		Message message = createMessage(session, username, recipientEmail, subject, messageContent);
		Multipart multipart = new MimeMultipart(); 
		MimeBodyPart messageBodyPart = new MimeBodyPart();                           
		messageBodyPart.setText(messageContent);
		multipart.addBodyPart(messageBodyPart);		
		MimeBodyPart attachmentPart = new MimeBodyPart();
		FileDataSource source = new FileDataSource(attachmentFilePath);
		attachmentPart.setDataHandler(new DataHandler(source));
		attachmentPart.setFileName(Costanti.RIEPILOGO_TRANSAZIONE_REST + estensione); 
		multipart.addBodyPart(attachmentPart);		
		message.setContent(multipart);   // Imposto l'oggetto di tipo Multipart all'oggetto message cosi che esso comprenda sia il testo che l'allegato
		
		return message;
	}

	

	
}
