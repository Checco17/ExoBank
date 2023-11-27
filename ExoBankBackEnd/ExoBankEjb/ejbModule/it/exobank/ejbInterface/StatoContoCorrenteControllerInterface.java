package it.exobank.ejbInterface;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import it.exobank.model.StatoContoCorrente;

@Local
public interface StatoContoCorrenteControllerInterface {

	
	List<StatoContoCorrente> findAllStatoContoCorrente() throws SQLException, Exception;
}
