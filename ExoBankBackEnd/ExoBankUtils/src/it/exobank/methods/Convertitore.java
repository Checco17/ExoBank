package it.exobank.methods;

import it.exobank.model.*;
import java.util.*;

public class Convertitore {

	public Utente fromUtenteToDTO(Utente utente) {

		Utente model = new Utente();

		model.setUtenteId(utente.getUtenteId());
		model.setNome(utente.getNome());
		model.setCognome(utente.getCognome());
		model.setEmail(utente.getEmail());
		model.setRuoloId(utente.getRuoloId());

		return model;

	}

	public List<Utente> fromListUtenteToDTO(List<Utente> list) {

		List<Utente> modelList = new ArrayList<Utente>();

		for (Utente utente : list) {

			Utente utenteDTO = fromUtenteToDTO(utente);
			modelList.add(utenteDTO);
		}

		return modelList;

	}

	public ContoCorrente fromContoCorrenteToDTO(ContoCorrente conto) {

		ContoCorrente contoDTO = new ContoCorrente();

		contoDTO.setId(conto.getId());
		contoDTO.setNumeroConto(conto.getNumeroConto());
		contoDTO.setSaldo(conto.getSaldo());
		contoDTO.setStatoContoCorrente(conto.getStatoContoCorrente());
		contoDTO.setUtente(fromUtenteToDTO(conto.getUtente()));

		return contoDTO;

	}

	public List<ContoCorrente> fromListContoCorrenteToDTO(List<ContoCorrente> list) {

		List<ContoCorrente> modelList = new ArrayList<ContoCorrente>();

		for (ContoCorrente contoCorrente : list) {

			ContoCorrente contoDTO = fromContoCorrenteToDTO(contoCorrente);
			modelList.add(contoDTO);

		}

		return modelList;

	}

	public Transazione fromTransazioneToDTO(Transazione transazione) {

		Transazione transazioneDTO = new Transazione();

		transazioneDTO.setId(transazione.getId());
		transazioneDTO.setDataTransazione(transazione.getDataTransazione());
		transazioneDTO.setImporto(transazione.getImporto());
		transazioneDTO.setContoCorrente(fromContoCorrenteToDTO(transazione.getContoCorrente()));
		if(transazione.getContoCorrenteBeneficiario() != null) {
			transazioneDTO.setContoCorrenteBeneficiario(fromContoCorrenteToDTO(transazione.getContoCorrenteBeneficiario()));
		}		
		transazioneDTO.setStatoTransazione(transazione.getStatoTransazione());
		transazioneDTO.setTipoTransazione(transazione.getTipoTransazione());
		transazioneDTO.setDescrizione(transazione.getDescrizione());

		return transazioneDTO;
	}

	public List<Transazione> fromListTransazioneToDTO(List<Transazione> transazioni) {

		List<Transazione> transazioniDTO = new ArrayList<Transazione>();

		for (Transazione transazione : transazioni) {

			Transazione transazioneDTO = fromTransazioneToDTO(transazione);

			transazioniDTO.add(transazioneDTO);
		}

		return transazioniDTO;
	}

}
