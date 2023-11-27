package it.exobank.controller;

import it.exobank.crud.StatoTransazioneCrud;
import it.exobank.ejbInterface.StatoTransazioneControllerInterface;
import it.exobank.mapper.StatoTransazioneMapper;
import it.exobank.model.StatoTransazione;
import it.exobank.sqlmapfactory.SqlMapFactory;
import it.exobank.utils.Costanti;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless(name = "StatoTransazioneControllerInterface")
@LocalBean
public class StatoTransazioneController implements StatoTransazioneControllerInterface {

	private StatoTransazioneCrud crud = StatoTransazioneCrud.getInstance();

	public StatoTransazioneController() {

	}

	@Override
	public List<StatoTransazione> findAllStatoTransazione() throws SQLException, Exception {

		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		List<StatoTransazione> stati = new ArrayList<StatoTransazione>();
		try {
			StatoTransazioneMapper mapper = factory.getMapper(StatoTransazioneMapper.class);
			stati = crud.findAllStatoTransazione(mapper);
			if (null != stati) {
				return stati;
			} else {
				throw new Exception("La ricerca non ha prodotto alcun risultato");
			}

		} catch (SQLException s) {
			s.printStackTrace();
			throw new SQLException(Costanti.CONTATTA_ASSISTENZA);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(Costanti.CONTATTA_ASSISTENZA);

		} finally {
			factory.closeSession();
		}

	}

}
