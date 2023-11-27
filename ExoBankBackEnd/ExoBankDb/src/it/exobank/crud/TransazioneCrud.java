package it.exobank.crud;

import java.util.ArrayList;
import java.util.List;

import it.exobank.mapper.TransazioneMapper;
import it.exobank.model.Transazione;

public class TransazioneCrud {

	public static TransazioneCrud instance;

	private TransazioneCrud() {

	}

	public static TransazioneCrud getInstance() {

		if (instance == null) {

			instance = new TransazioneCrud();
			return instance;
		}
		return instance;

	}

	public List<Transazione> findAllTransazione(TransazioneMapper mapper) throws Exception {

		List<Transazione> transazioni = new ArrayList<Transazione>();

		try {
			transazioni = mapper.findAllTransazione();
			return transazioni;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findAllTransazione nel TransazioneCrud");

		}

	}

	public Transazione insertTransazione(TransazioneMapper mapper, Transazione transazione) throws Exception {

		Transazione model = new Transazione();

		try {

			mapper.insertTransazione(transazione);

			model = mapper.findLastTransazione();

			return model;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel insertTransazione nel TransazioneCrud");
		}

	}

	public Transazione findTransazioneById(TransazioneMapper mapper, Transazione transazione) throws Exception {

		Transazione model = new Transazione();

		try {
			model = mapper.findTransazioneById(transazione.getId());
			return model;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findTransazioneById nel TransazioneCrud");
		}

	}

	public Transazione updateTransazione(TransazioneMapper mapper, Transazione transazione) throws Exception {

		Transazione model = new Transazione();

		try {
			mapper.updateTransazione(transazione);
			model = mapper.findTransazioneById(transazione.getId());
			return model;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel updateTransazione nel TransazioneCrud");

		}

	}
	
	public List<Transazione> findTransazioneByUtenteId(TransazioneMapper mapper, Integer i) throws Exception {

		List<Transazione> model = new ArrayList<Transazione>();

		try {
			model = mapper.findTransazioneByUtenteId(i);
			return model;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findTransazioneByUtenteId nel TransazioneCrud");
		}

	}

}
