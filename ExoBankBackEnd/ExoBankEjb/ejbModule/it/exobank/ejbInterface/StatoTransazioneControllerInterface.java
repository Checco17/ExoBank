package it.exobank.ejbInterface;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import it.exobank.model.StatoTransazione;

@Local
public interface StatoTransazioneControllerInterface {
	
	List<StatoTransazione> findAllStatoTransazione() throws SQLException, Exception;

}
