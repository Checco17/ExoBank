package it.exobank.utils;

public class EndPoint {
	
	public static final String LOGIN_UTENTE = "/login";
	
	public static final String INSERT_UTENTE = "/registerClient";
	
	public static final String FIND_ALL_UTENTE = "/getListUtente";
	
	public static final String INSERT_CONTO_CORRENTE = "/apriConto";
	
	public static final String FIND_ALL_CONTO_CORRENTE = "/listaContiCorrenti";
	
	public static final String FIND_CONTO_BY_ID_UTENTE = "/findContoByIdUtente";
	
	public static final String UPDATE_CONTO_CORRENTE = "/updateContoCorrente";
	
	public static final String INSERT_TRANSAZIONE = "/insertTransazione";
	
	public static final String FIND_ALL_TRANSAZIONE = "/listaTransazione";
	
	public static final String FIND_TRANSAZIONE_BY_ID = "/findTransazioneById";
	
	public static final String FIND_TRANSAZIONE_BY_UTENTE_ID = "/findTransazioneByUtenteId";
	
	public static final String UPDATE_TRANSAZIONE = "/updateTransazione";
	
	public static final String FIND_ALL_STATO_TRANSAZIONE = "/findAllStatoTransazione";
	
	public static final String FIND_ALL_STATO_CONTO_CORRENTE = "/findAllStatoContoCorrente";
	
	public static final String FIND_ALL_TIPO_TRANSAZIONE = "/findAllTipoTransazione";
	
	public static final String DOWNLOAD_XLSX = "/downloadXlsx";
	
	public static final String DOWNLOAD_DOCX = "/downloadDocx";
	
	public static final String DOWNLOAD_PDF = "/downloadPdf";
	
	public static final String LOGIN_FOR_REACT = "/loginForReact";
	
	public static final String FILL_CUSTOMER_CLIENT= "/fillCustomerClient";
}
