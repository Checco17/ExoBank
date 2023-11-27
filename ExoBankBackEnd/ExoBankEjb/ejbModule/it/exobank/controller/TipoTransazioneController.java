package it.exobank.controller;

import it.exobank.model.TipoTransazione;
import it.exobank.sqlmapfactory.SqlMapFactory;
import it.exobank.utils.Costanti;
import it.exobank.crud.TipoTransazioneCrud;
import it.exobank.ejbInterface.TipoTransazioneControllerInterface;
import it.exobank.mapper.TipoTransazioneMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless(name = "TipoTransazioneControllerInterface")
@LocalBean
public class TipoTransazioneController implements TipoTransazioneControllerInterface {

	private TipoTransazioneCrud crud = TipoTransazioneCrud.getInstance();

	public TipoTransazioneController() {

	}

	@Override
	public List<TipoTransazione> findAllTipoTransazione() throws SQLException, Exception {

		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		List<TipoTransazione> tipi = new ArrayList<TipoTransazione>();
		try {
			TipoTransazioneMapper mapper = factory.getMapper(TipoTransazioneMapper.class);
			tipi = crud.findAllTipoTransazione(mapper);
			
			if(null != tipi) {
				return tipi;
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
