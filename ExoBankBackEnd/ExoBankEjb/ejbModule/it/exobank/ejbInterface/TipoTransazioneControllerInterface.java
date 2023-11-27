package it.exobank.ejbInterface;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import it.exobank.model.TipoTransazione;

@Local
public interface TipoTransazioneControllerInterface {
	
	List<TipoTransazione> findAllTipoTransazione() throws SQLException, Exception;

}
