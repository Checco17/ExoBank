package it.exobank.ejbInterface;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import it.exobank.DTO.*;
import it.exobank.model.*;

@Local
public interface ContoCorrenteControllerInterface {

	Dto<List<ContoCorrente>> findAllContoCorrente() throws SQLException, Exception;

	Dto<ContoCorrente> insertContoCorrente(ContoCorrente contoCorrente) throws SQLException, Exception;

	Dto<ContoCorrente> findContoCorrenteByUtenteId(Integer i) throws SQLException, Exception;

	Dto<ContoCorrente> updateContoCorrente(ContoCorrente contoCorrente) throws SQLException, Exception;

}
