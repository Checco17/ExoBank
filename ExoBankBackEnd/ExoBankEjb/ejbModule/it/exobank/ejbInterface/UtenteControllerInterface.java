package it.exobank.ejbInterface;

import javax.ejb.Local;

import it.exobank.DTO.*;
import it.exobank.model.*;

import java.sql.SQLException;
import java.util.*;

@Local
public interface UtenteControllerInterface {
	
	Dto<List<Utente>> findAllUtente() throws SQLException, Exception;
	
	Dto<Utente> insertUtente(Utente utente) throws SQLException, Exception;
	
	Dto<Utente> findUtenteById(Integer id) throws SQLException, Exception;
	
	Dto<Utente> findByEmailPassword(Utente utenteLogin) throws SQLException, Exception;

}
