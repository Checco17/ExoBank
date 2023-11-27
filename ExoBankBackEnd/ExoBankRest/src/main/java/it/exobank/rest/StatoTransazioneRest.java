package it.exobank.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import java.util.*;

import it.exobank.conf.EjbService;
import it.exobank.ejbInterface.StatoTransazioneControllerInterface;
import it.exobank.model.*;
import it.exobank.utils.Costanti;
import it.exobank.utils.EndPoint;
import it.exobank.utils.RouteRest;

@Path(RouteRest.STATO_TRANSAZIONE_REST)
public class StatoTransazioneRest {

	private EjbService<StatoTransazioneControllerInterface> ejb = new EjbService<StatoTransazioneControllerInterface>(StatoTransazioneControllerInterface.class);

	@GET
	@Path(EndPoint.FIND_ALL_STATO_TRANSAZIONE)
	@Consumes(Costanti.APPLICATION_JSON)
	public Response findAllStatoContoCorrente() {
		try {
			List<StatoTransazione> stati = ejb.getEJB().findAllStatoTransazione();

			return Response.status(Costanti.RESPONSE_STATUS_OK).entity(stati).build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).build();
	}

}