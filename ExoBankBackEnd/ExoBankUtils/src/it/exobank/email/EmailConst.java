package it.exobank.email;

import it.exobank.model.Utente;

public class EmailConst {
	
	public static final String BENVENUTO_IN_EXOBANK =  "Benvenuto in ExoBank";
	
	public static final String TRANSAZIONE_EFFETTUATA =  "Transazione effettuata";
	
	public static final String APERTURA_CONTO =  "Apertura conto corrente";
	
	public static final String APPROVAZIONE_CONTO = "Approvazione conto corrente";
	
	public static final String GET_MESSAGE_BENVENUTO(Utente utente) {
		String message = "Benvenuto " + utente.getNome() + " " + utente.getCognome()
							+ ",\nGrazie per esserti registrato in ExoBank."
							+ "\nAdesso puoi richiedere l'apertura di un conto ed effettuare operazioni di pagamento!";
		return message;
	}
	
	public static final String GET_MESSAGE_TRANSAZIONE_EFFETTUATA(Utente utente) {
		String message = "Sig. " + utente.getNome() + " " + utente.getCognome()
							+ ",\nLa tua transazione è stata presa in carica, attendi l'approvazione da parte dell'amministratore.";
		return message;
	}
	
	public static final String GET_MESSAGE_APERTURA_CONTO(Utente utente) {
		String message = "Sig. " + utente.getNome() + " " + utente.getCognome()
							+ ",\nApertura conto corrente presa in carico, attendi l'approvazione da parte dell'amministratore.";
		return message;
	}
	
	public static final String GET_MESSAGE_APPROVAZIONE_CONTO(Utente utente) {
		String message = "Sig. " + utente.getNome() + " " + utente.getCognome()
							+ ",\nIl tuo conto corrente è stato approvato. Adesso hai la possibilità di usufruire dei servizi della nostra banca.";
		return message;
	}
}
