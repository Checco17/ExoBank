package it.exobank.crud;

import java.util.ArrayList;
import java.util.List;

import it.exobank.mapper.StatoTransazioneMapper;
import it.exobank.model.StatoTransazione;

public class StatoTransazioneCrud {

	private static StatoTransazioneCrud instance;

	private StatoTransazioneCrud() {

	}

	public static StatoTransazioneCrud getInstance() {

		if (instance == null) {

			instance = new StatoTransazioneCrud();

			return instance;
		}

		return instance;
	}

	public List<StatoTransazione> findAllStatoTransazione(StatoTransazioneMapper mapper) throws Exception {

		List<StatoTransazione> stati = new ArrayList<StatoTransazione>();

		try {
			stati = mapper.findAllStatoTransazione();
			return stati;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findAllStatoTransazione nel StatoTransazioneCrud");
		}

	}

}
