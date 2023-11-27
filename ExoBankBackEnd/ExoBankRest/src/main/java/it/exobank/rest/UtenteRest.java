package it.exobank.rest;


import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import it.exobank.utils.*;
import it.exobank.DTO.*;
import it.exobank.conf.*;
import it.exobank.ejbInterface.UtenteControllerInterface;

import it.exobank.model.*;

@Path(RouteRest.UTENTE_REST)
public class UtenteRest {
	
	private static String jsonForReact;
	
	private EjbService<UtenteControllerInterface> ejb = new EjbService<UtenteControllerInterface>(
			UtenteControllerInterface.class);

	@GET
	@Path(EndPoint.FIND_ALL_UTENTE)
	@Produces(Costanti.APPLICATION_JSON)
	public Response findAllUser() {

		Dto<List<Utente>> listUserDTO = new Dto<List<Utente>>();
		try {
			listUserDTO = ejb.getEJB().findAllUtente();

			if (listUserDTO.isSuccess()) {
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(listUserDTO.getData()).build();

			} else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}

	@POST
	@Path(EndPoint.LOGIN_UTENTE)
	@Consumes(Costanti.APPLICATION_JSON)
	@Produces(Costanti.APPLICATION_JSON)
	public Response login(Utente utenteLogin) {

		Dto<Utente> utenteDTO = new Dto<Utente>();
		try {

			utenteDTO = ejb.getEJB().findByEmailPassword(utenteLogin);

			if (utenteDTO.isSuccess()) {
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(utenteDTO.getData()).build();

			} else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}

	@POST
	@Path(EndPoint.INSERT_UTENTE)
	@Consumes(Costanti.APPLICATION_JSON)
	@Produces(Costanti.APPLICATION_JSON)
	public Response insertUtente(Utente utente) {

		Dto<Utente> utenteDTO = new Dto<Utente>();
		try {
			utenteDTO = ejb.getEJB().insertUtente(utente);
			if (utenteDTO.isSuccess()) {
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(utenteDTO.getData()).build();

			} else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}
	


	@POST
	@Path(EndPoint.FILL_CUSTOMER_CLIENT)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fillCustomerClient(String jsonUtente) {	
		
		try {
			
			if (null == jsonForReact) {		
				jsonForReact = jsonUtente;
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(jsonUtente).build();
				
			} else {				
				return Response.noContent().build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

	}
	
	
	@GET
	@Path(EndPoint.LOGIN_FOR_REACT)	
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginForReact() {
		
		try {
			
			String stringaAppoggioJson = null;
			
			if (null != jsonForReact) {	
				stringaAppoggioJson = jsonForReact;
				jsonForReact = null;
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(stringaAppoggioJson).build();
				
			} else {
				return Response.noContent().build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	//PROVA
	@GET
	@Path("/redirectResponse")	
	@Produces(MediaType.APPLICATION_JSON)
	public Response redirect() {
		
		try {
			
				System.out.println("----SONO NEL REST REDIRECT RESPONSE----");
				return Response.status(301).header("Location", "http://localhost:3000").build();
				
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	
	
	
// TENTATIVO DI RENDER DIRETTAMENTE DA REST	
//	@POST
//	@Path(EndPoint.FILL_CUSTOMER_CLIENT)
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response fillCustomerClient(String json) {	
//	    try {
//	      
//	            System.out.println("----SONO NEL REST----");
//
//	            String newLocation = "http://localhost:3000";
//	            URI uri =  new URI(newLocation);
//	            
//	            return Response.temporaryRedirect(uri).header("Location", newLocation).build();
//	        
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return Response.status(500).build();
//	    }
//	}

//	
//	@GET
//	@Path(EndPoint.FILL_CUSTOMER_CLIENT)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response fillCustomerClient() {	
//		 String wikipediaUrl = "https://localhost:3000/";
//		 System.out.println(Status.FOUND.getStatusCode());
//		    // Effettua un reindirizzamento alla pagina di Wikipedia
//		    return Response.status(Response.Status.FOUND)
//		                   .location(URI.create(wikipediaUrl))
//		                   .build();
//	}
//
	
	
	
//  METODO CHE GESTIVA ENTRAMBE LE CHIAMATE DA JSF E REACT	
//	@POST
//	@Path(EndPoint.LOGIN_FOR_REACT)
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response loginForReact1(String jsonUtente) {
//		
//		String stringaConfrontoJsonVuota = JsonConvert.convertObjectToJson(new Utente());
//		
//		try {
//			
//			/*
//			 * QUESTO IF SERVE PER SETTARE DAL CLIENT JAVA IL JSON DELL'UTENTE LOGGATO SU JSF
//			 */
//			
//			if (jsonForReact == null && !jsonUtente.equalsIgnoreCase(stringaConfrontoJsonVuota)) {
//		
//				jsonForReact = jsonUtente;
//				return Response.noContent().build();
//			
//			/*
//			 * QUESTO ELSE IF SERVE PER LA CHIAMATA DA PARTE DEL MIO COMPONENTE REACT
//			 * COSI DA RESTITUIRE IL JSON CONTENENTE I DATI DELL'UTENTE LOGGATO
//			 */
//				
//			} else if (jsonForReact != null){
//				
//				jsonUtente = jsonForReact;
//				jsonForReact = null;
//				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(jsonUtente).build();
//				
//			/*
//			 * QUESTO ELSE SERVE PER GESTIRE LA CHIAMATA AUTOMATICA CHE AVVIENE NEL MIO COMPONENTE
//			 * REACT AL CARICAMENTO DELLA PAGINA, IN MODO TALE DA RITORNARE UNA RESPONSE VUOTA
//			 */
//				
//			} else {
//				
//				return Response.noContent().build();
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
//		}
//
//	}
	

}
