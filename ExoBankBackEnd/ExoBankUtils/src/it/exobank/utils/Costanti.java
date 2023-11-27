 package it.exobank.utils;

import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response.Status;

public class Costanti {
	
	public final static int RESPONSE_STATUS_OK = Status.OK.getStatusCode();
	
	public final static int RESPONSE_STATUS_NO_CONTENT = Status.NO_CONTENT.getStatusCode();
	
	public final static int RESPONSE_STATUS_NOT_FOUND = Status.NOT_FOUND.getStatusCode();	
	
	public final static int RESPONSE_STATUS_INTERNAL_SERVER_ERROR = Status.INTERNAL_SERVER_ERROR.getStatusCode();
	
	public final static String APPLICATION_JSON = MediaType.APPLICATION_JSON;
	
	public static final String RIEPILOGO_TRANSAZIONE_REST = "riepilogoTransazioni";
	
	public static final String REGEX_NOME_COGNOME = "^[A-Za-z]+\\s?[A-Za-z]*$";
	
	public static final String REGEX_EMAIL = "^[A-Za-z0-9._%+-]{4,}@([A-Za-z0-9-]{4,}\\.)+[A-Za-z]{2,}$";
    
	public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&!])[A-Za-z\\d@#$%^&!]+$";
	
	public static final String EMAIL_MITTENTE = "fra.baeli@virgilio.it";
	
	public static final String PASSWORD_MITTENTE = "ykOgvszs8!";
	
	public static final String EMAIL_DESTINATARIO = "francescobaeli14@gmail.com";
	
	public static final int TIPO_TRANSAZIONE_DEPOSITO = 1;
	
	public static final int TIPO_TRANSAZIONE_PRELIEVO = 2;
	
	public static final int TIPO_TRANSAZIONE_BONIFICO = 3;
	
	public static final int TIPO_TRANSAZIONE_RICARICA = 4;
	
	public static final int TIPO_TRANSAZIONE_BOLLETTINO = 5;
	
	public static final int STATO_TRANSAZIONE_APPROVATA = 1;
	
	public static final int STATO_CONTO_CORRENTE_ATTIVO = 1;	
	
	public static final int RUOLO_UTENTE_AMMINISTRATORE = 1;	
	
	public static final int RUOLO_UTENTE_CLIENTE = 2;	
	
	public static final String CONTATTA_ASSISTENZA = "Errore: contatta l'assistenza";
	
	public static final String ERRORE_CONNESSIONE = "Errore generico nella connessione: url non trovata o problemia stabilire una connessione";
	
	public static final String EMAIL_INVIATA_CON_SUCCESSO = "Email inviata con successo!";
	
	public static final String EMAIL_NON_INVIATA_CON_SUCCESSO = "Email non inviata, controlla la classe SendEmail";
	
	public static final String EMAIL_CON_ALLEGATO_INVIATA_CON_SUCCESSO = "Email con allegato inviata con successo!";	
		
	
	
	
	
	
	

}
