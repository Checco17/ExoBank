package it.exobank.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import it.exobank.DTO.Dto;
import it.exobank.controller.UtenteController;
import it.exobank.methods.CryptoString;
import it.exobank.model.Utente;
import it.exobank.myclient.MyClient;
import it.exobank.utils.Costanti;
import it.exobank.utils.CostantiPagineJSF;
import it.exobank.utils.EndPoint;
import it.exobank.utils.RouteRest;
import it.exobank.utils.VerboMetodoHttp;


/*
 * L'annotazione @Named viene utilizzata per dichiarare una classe come un bean gestito in JSF. 
 * L'annotazione @SessionScoped definisce la durata di vita di un bean gestito. In questo caso, indica che il bean ha una durata di sessione. 
 * Ciò significa che una singola istanza del bean verrà creata e gestita per l'intera durata della sessione dell'utente.
 */

@Named
@SessionScoped
public class UtenteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UtenteController utenteController;

	private Utente utente;


	public void loginForReact() {

		try {

			Utente utente = getUtente();
			Dto<Utente> utenteDTO = utenteController.findByEmailPassword(utente);

			if (utenteDTO.isSuccess()) {

				utenteDTO.getData().setPassword(new CryptoString().cryptoString(utente.getPassword()));

				
				/*
				 * FacesContext è una classe di JavaServer Faces (JSF) che fornisce informazioni
				 * sullo stato delle richieste e delle risposte JSF durante il ciclo di vita di
				 * una richiesta HTTP.
				 */

				// Ottengo l'istanza corrente di FacesContext per interagire con JSF.
				FacesContext context = FacesContext.getCurrentInstance();
				
				/*
				 * Chiamo il metodo creato nel mio client che invia una request ad un servizio Rest
				 */

				//new MyClient().callRestServiceWithoutInterfaceClient(utenteDTO.getData(), RouteRest.URL_SERVIZIO_REST_UTENTE, EndPoint.FILL_CUSTOMER_CLIENT, 
				//		VerboMetodoHttp.POST, Costanti.APPLICATION_JSON, context.getExternalContext());
				
				
				//new MyClient().callRestServiceWithInterfaceClient(utenteDTO, RouteRest.URL_SERVIZIO_REST_UTENTE, EndPoint.FILL_CUSTOMER_CLIENT);
				new MyClient().callRestServiceAndRedirect();

				// Reinizializzo l'oggetto Utente
				init();

			}

		} catch (Exception e) {
			e.printStackTrace();
			addMessageAtFacesContext("Errore nella login: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}

	}

	public void login() {

		try {
			Utente utente = getUtente();
			Dto<Utente> utenteDTO = utenteController.findByEmailPassword(utente);

			if (utenteDTO.isSuccess()) {
				setUtente(utenteDTO.getData());
				handleSuccessfulLogin(utenteDTO.getData().getRuoloId().getRuoloId());
			} else {
				addMessageAtFacesContext("Accesso non riuscito. Credenziali non valide.", FacesMessage.SEVERITY_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessageAtFacesContext("Errore nella login: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	public void insertUtente() {

		try {
			Utente utente = getUtente();
			Dto<Utente> utenteDTO = utenteController.insertUtente(utente);

			if (utenteDTO.isSuccess()) {
				setUtente(utenteDTO.getData());
				handleSuccessfulRegister();
			} else {
				addMessageAtFacesContext("Registrazione non riuscita.", FacesMessage.SEVERITY_ERROR);
			}

		} catch (Exception e) {
			e.printStackTrace();
			addMessageAtFacesContext("Errore nella registrazione: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	public void logout() {

		init();

		HandleBean handleBean = getHandleBean();

		handleBean.setSwitchNumb(CostantiPagineJSF.LOGIN_PAGE);
	}

	private void handleSuccessfulLogin(int ruoloId) {

		HandleBean handleBean = getHandleBean();

		if (ruoloId == Costanti.RUOLO_UTENTE_AMMINISTRATORE) {

			handleBean.setSwitchNumb(CostantiPagineJSF.HOME_PAGE_AMMINISTRATORE);

		} else if (ruoloId == Costanti.RUOLO_UTENTE_CLIENTE) {

			handleBean.setSwitchNumb(CostantiPagineJSF.HOME_PAGE_CLIENTE);
		}

		addMessageAtFacesContext("Accesso riuscito!", FacesMessage.SEVERITY_INFO);
	}

	
	private void handleSuccessfulRegister() {

		HandleBean handleBean = getHandleBean();

		handleBean.setSwitchNumb(CostantiPagineJSF.HOME_PAGE_CLIENTE);

		addMessageAtFacesContext("Registrazione riuscita!", FacesMessage.SEVERITY_INFO);
	}

	
	private HandleBean getHandleBean() {

		HandleBean handleBean = FacesContext.getCurrentInstance().getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{handleBean}", HandleBean.class);

		return handleBean;
	}

	
	private void addMessageAtFacesContext(String message, Severity facesMessageConst) {

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(facesMessageConst, message, ""));

	}


	@PostConstruct
	public void init() {
		utente = new Utente();
	}
	
	
	public UtenteController getUtenteController() {
		return utenteController;
	}

	
	public void setUtenteController(UtenteController utenteController) {
		this.utenteController = utenteController;
	}

	
	public Utente getUtente() {
		return utente;
	}

	
	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}
