package it.exobank.crud;

import java.util.ArrayList;
import java.util.List;

import it.exobank.mapper.TipoTransazioneMapper;
import it.exobank.model.TipoTransazione;

public class TipoTransazioneCrud {

	private static TipoTransazioneCrud instance;

	private TipoTransazioneCrud() {

	}

	public static TipoTransazioneCrud getInstance() {

		if (instance == null) {

			instance = new TipoTransazioneCrud();

			return instance;
		}

		return instance;
	}

	public List<TipoTransazione> findAllTipoTransazione(TipoTransazioneMapper mapper) throws Exception {

		List<TipoTransazione> tipi = new ArrayList<TipoTransazione>();
		try {
			tipi = mapper.findAllTipoTransazione();
			return tipi;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findAllTipoTransazione nel TipoTransazioneCrud");
		}

	}

}
