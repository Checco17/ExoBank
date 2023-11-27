package it.exobank.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import java.util.*;

import it.exobank.conf.EjbService;
import it.exobank.ejbInterface.*;
import it.exobank.model.*;
import it.exobank.utils.Costanti;
import it.exobank.utils.EndPoint;
import it.exobank.utils.RouteRest;

@Path(RouteRest.TIPO_TRANSAZIONE_REST)
public class TipoTransazioneRest {
	
	private EjbService<TipoTransazioneControllerInterface> ejb = new EjbService<TipoTransazioneControllerInterface>(TipoTransazioneControllerInterface.class);

	@GET
	@Path(EndPoint.FIND_ALL_TIPO_TRANSAZIONE)
	@Consumes(Costanti.APPLICATION_JSON)
	public Response findAllTipoContoCorrente() {
		try {
			List<TipoTransazione> stati = ejb.getEJB().findAllTipoTransazione();

			return Response.status(Costanti.RESPONSE_STATUS_OK).entity(stati).build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).build();
	}

}
