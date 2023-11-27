package it.exobank.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import java.util.*;

import it.exobank.conf.EjbService;
import it.exobank.ejbInterface.StatoContoCorrenteControllerInterface;
import it.exobank.model.*;
import it.exobank.utils.Costanti;
import it.exobank.utils.EndPoint;
import it.exobank.utils.RouteRest;

@Path(RouteRest.STATO_CONTO_CORRENTE_REST)
public class StatoContoCorrenteRest {

	private EjbService<StatoContoCorrenteControllerInterface> ejb = new EjbService<StatoContoCorrenteControllerInterface>(StatoContoCorrenteControllerInterface.class);

	@GET
	@Path(EndPoint.FIND_ALL_STATO_CONTO_CORRENTE)
	@Consumes(Costanti.APPLICATION_JSON)
	public Response findAllStatoContoCorrente() {
		try {
			List<StatoContoCorrente> stati = ejb.getEJB().findAllStatoContoCorrente();

			return Response.status(Costanti.RESPONSE_STATUS_OK).entity(stati).build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).build();
	}

}
