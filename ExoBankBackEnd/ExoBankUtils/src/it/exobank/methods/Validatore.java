package it.exobank.methods;

import it.exobank.model.ContoCorrente;
import it.exobank.model.Transazione;
import it.exobank.model.Utente;
import it.exobank.utils.Costanti;

public class Validatore {

	public boolean validatoreUtente(Utente utente) {

		if (null != utente 
			&& utente.getNome().length() >= 3 
			&& utente.getNome().length() <= 20 
			&& utente.getNome().matches(Costanti.REGEX_NOME_COGNOME)
			&& utente.getCognome().length() >= 3
			&& utente.getCognome().length() <= 20
			&& utente.getCognome().matches(Costanti.REGEX_NOME_COGNOME)
			&& utente.getEmail().length() >= 8
			&& utente.getEmail().length() <= 30
			&& utente.getEmail().matches(Costanti.REGEX_EMAIL)
			&& utente.getPassword().length() >= 8
			&& utente.getPassword().length() <= 20
			&& utente.getPassword().matches(Costanti.REGEX_PASSWORD)) {

			return true;

		} else
			return false;

	}

	public boolean validatoreContoCorrente(ContoCorrente contoCorrente) {

		if (null != contoCorrente 
			&&contoCorrente.getUtente() != null 
			&& contoCorrente.getNumeroConto() != null
			&& contoCorrente.getSaldo() >= 0) {

			return true;

		} else
			return false;

	}

	public boolean validatoreTransazione(Transazione transazione) {

		if (null != transazione 
			&& transazione.getContoCorrente() != null 
		    && transazione.getImporto() != null
			&& (transazione.getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_PRELIEVO
			|| transazione.getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_BONIFICO
			|| transazione.getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_RICARICA 
			|| transazione.getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_BOLLETTINO)
			&& transazione.getContoCorrente().getSaldo() >= transazione.getImporto()) {

			return true;

		} else if (null != transazione 
					&& transazione.getContoCorrente() != null 
					&& transazione.getImporto() != null
					&& transazione.getTipoTransazione().getId() == 1) {

			return true;

		} else
			return false;
	}

}
