package it.exobank.rest;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.exobank.DTO.*;
import it.exobank.model.*;
import it.exobank.utils.Costanti;
import it.exobank.document.*;
import it.exobank.utils.EndPoint;
import it.exobank.utils.EstensioniFile;
import it.exobank.utils.RouteRest;
import it.exobank.conf.EjbService;
import it.exobank.ejbInterface.*;

@Path(RouteRest.TRANSAZIONE_REST)
public class TransazioneRest {
	
	private EjbService<TransazioneControllerInterface> ejb = new EjbService<TransazioneControllerInterface>(TransazioneControllerInterface.class);
	
	@POST
	@Path(EndPoint.INSERT_TRANSAZIONE)
	@Consumes(Costanti.APPLICATION_JSON)
	@Produces(Costanti.APPLICATION_JSON)
	public Response insertTransazione(Transazione transazione) {
		
		Dto<Transazione> transazioneDTO = new Dto<Transazione>();
		try {			
			transazioneDTO = ejb.getEJB().insertTransazione(transazione);
			if(transazioneDTO.isSuccess()){
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(transazioneDTO.getData()).build();

			} else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

		}


	}
	
	@GET
	@Path(EndPoint.FIND_ALL_TRANSAZIONE)
	@Produces(Costanti.APPLICATION_JSON)
	public Response findAllTransazione() {
		
		Dto<List<Transazione>> listTransazioniiDTO = new Dto<List<Transazione>>();
		try {
			listTransazioniiDTO = ejb.getEJB().findAllTransazione();
			
			if(listTransazioniiDTO.isSuccess()) {
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(listTransazioniiDTO.getData()).build();

			} else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

		}


	}
	
	@POST
	@Path(EndPoint.FIND_TRANSAZIONE_BY_ID)
	@Consumes(Costanti.APPLICATION_JSON)
	@Produces(Costanti.APPLICATION_JSON)
	public Response findTransazioneById(Transazione transazione) {
		
		Dto<Transazione> transazioneDTO = new Dto<Transazione>();
		
		try {			
			transazioneDTO = ejb.getEJB().findTransazioneById(transazione);
			
			if(transazioneDTO.isSuccess()) {
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(transazioneDTO.getData()).build();

			}else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

		}

	}
	
	@POST
	@Path(EndPoint.UPDATE_TRANSAZIONE)
	@Consumes(Costanti.APPLICATION_JSON)
	public Response updateTransazione(Transazione transazione) {
		
		Dto<Transazione> transazioneDTO = new Dto<Transazione>();
		try {
			transazioneDTO = ejb.getEJB().updateTransazione(transazione);
			
			if(transazioneDTO.isSuccess()) {
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(transazioneDTO.getData()).build();
			
			} else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}

		
	}
	
	@POST
	@Path(EndPoint.FIND_TRANSAZIONE_BY_UTENTE_ID)
	@Consumes(Costanti.APPLICATION_JSON)
	@Produces(Costanti.APPLICATION_JSON)
	public Response findTransazioneByUtenteId(Utente utente) {
		
		Dto<List<Transazione>> transazioniDTO = new Dto<List<Transazione>>();
		
		try {			
			transazioniDTO = ejb.getEJB().findTransazioneByUtenteId(utente.getUtenteId());
			
			if(transazioniDTO.isSuccess()) {
				return Response.status(Costanti.RESPONSE_STATUS_OK).entity(transazioniDTO.getData()).build();

			}else {
				return Response.status(Costanti.RESPONSE_STATUS_NO_CONTENT).build();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

		}
	}
	
	
	@POST
	@Path(EndPoint.DOWNLOAD_XLSX)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadXlsx(Utente utente) {
	    try {
	        Dto<List<Transazione>> transazioni = ejb.getEJB().findTransazioneByUtenteId(utente.getUtenteId());

	        byte[] fileContent = new CreateXlsx().createXlsxFile(transazioni);

	        if (fileContent != null) {
	         
	        	
	            String fileName = Costanti.RIEPILOGO_TRANSAZIONE_REST + EstensioniFile.XLSX;

	            Response.ResponseBuilder response = Response.ok(fileContent);
	            response.header("Content-Disposition", "attachment; filename=" + fileName);
	            response.header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 

	            return response.build();
	        } else {
	            return Response.status(Costanti.RESPONSE_STATUS_NOT_FOUND).build();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
	    }
	}
	


	@POST
	@Path(EndPoint.DOWNLOAD_DOCX)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadDocx(Utente utente) {
        try {
            Dto<List<Transazione>> transazioni = ejb.getEJB().findTransazioneByUtenteId(utente.getUtenteId());

            byte[] docxData = new CreateDocx().createDocxFile(transazioni);

            if (docxData != null) {
               
                String fileName = Costanti.RIEPILOGO_TRANSAZIONE_REST + EstensioniFile.DOCX;

                return Response.ok(docxData).header("Content-Disposition", "attachment; filename=" + fileName).build();
            } else {
                return Response.status(Costanti.RESPONSE_STATUS_NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
	
	

	@POST
	@Path(EndPoint.DOWNLOAD_PDF)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadPdf(Utente utente) {
        try {
            Dto<List<Transazione>> transazioni = ejb.getEJB().findTransazioneByUtenteId(utente.getUtenteId());

            byte[] pdfData = new CreatePdf().createPdfTable(transazioni);

            if (pdfData != null) {
               
                String fileName = Costanti.RIEPILOGO_TRANSAZIONE_REST + EstensioniFile.PDF;

                return Response.ok(pdfData).header("Content-Disposition", "attachment; filename=" + fileName).build();
            } else {
                return Response.status(Costanti.RESPONSE_STATUS_NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Costanti.RESPONSE_STATUS_INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

	
	

}
