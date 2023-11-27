package it.exobank.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import it.exobank.utils.*;
import it.exobank.conf.EjbService;
import it.exobank.ejbInterface.ContoCorrenteControllerInterface;
import it.exobank.DTO.*;
import it.exobank.model.*;

@Path(RouteRest.CONTO_CORRENTE_REST)
public class ContoCorrenteRest {

	private EjbService<ContoCorrenteControllerInterface> ejb = new EjbService<ContoCorrenteControllerInterface>(ContoCorrenteControllerInterface.class);

	@POST
	@Path(EndPoint.INSERT_CONTO_CORRENTE)
	@Consumes(Costanti.APPLICATION_JSON)
	@Produces(Costanti.APPLICATION_JSON)
	public Response insertContoCorrente(ContoCorrente contoCorrente) {
		
		Dto<ContoCorrente> contoDTO = new Dto<ContoCorrente>();
		try {			
			contoDTO = ejb.getEJB().insertContoCorrente(contoCorrente);
			if(contoDTO.isSuccess()){
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(contoDTO.getData()).build();

			} else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

		}


	}

	@GET
	@Path(EndPoint.FIND_ALL_CONTO_CORRENTE)
	@Produces(Costanti.APPLICATION_JSON)
	public Response findAllContoCorrente() {
		
		Dto<List<ContoCorrente>> listContiDTO = new Dto<List<ContoCorrente>>();
		try {
			listContiDTO = ejb.getEJB().findAllContoCorrente();
			
			if(listContiDTO.isSuccess()) {
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(listContiDTO.getData()).build();

			} else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

		}


	}

	@POST
	@Path(EndPoint.FIND_CONTO_BY_ID_UTENTE)
	@Consumes(Costanti.APPLICATION_JSON)
	@Produces(Costanti.APPLICATION_JSON)
	public Response findContoByUtenteId(Utente utente) {
		
		Dto<ContoCorrente> contoDTO = new Dto<ContoCorrente>();
		Integer i = utente.getUtenteId();
		
		try {			
			contoDTO = ejb.getEJB().findContoCorrenteByUtenteId(i);
			
			if(contoDTO.isSuccess()) {
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(contoDTO.getData()).build();

			}else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

		}

	}

	@POST
	@Path(EndPoint.UPDATE_CONTO_CORRENTE)
	@Consumes(Costanti.APPLICATION_JSON)
	public Response updateContoCorrente(ContoCorrente contoCorrente) {
		
		Dto<ContoCorrente> contoDTO = new Dto<ContoCorrente>();
		try {
			contoDTO = ejb.getEJB().updateContoCorrente(contoCorrente);
			
			if(contoDTO.isSuccess()) {
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(contoDTO.getData()).build();
			
			} else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		
	}
	
	
	

}
