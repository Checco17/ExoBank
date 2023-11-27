package it.exobank.ejbInterface;

import javax.ejb.Local;

import java.sql.SQLException;
import java.util.*;
import it.exobank.model.*;

import it.exobank.DTO.*;

@Local
public interface TransazioneControllerInterface {
	
	
	Dto<Transazione> insertTransazione(Transazione transazione) throws SQLException, Exception;
	
	Dto<List<Transazione>> findAllTransazione() throws SQLException, Exception;
	
	Dto<Transazione> updateTransazione(Transazione transazione) throws SQLException, Exception;
	
	Dto<Transazione> findTransazioneById(Transazione transazione) throws SQLException, Exception;
	
	Dto<List<Transazione>> findTransazioneByUtenteId(Integer i) throws SQLException, Exception;
	
	
	

}
