package it.exobank.crud;

import java.util.*;

import it.exobank.mapper.*;
import it.exobank.model.*;

public class UtenteCrud {

	private static UtenteCrud instance;

	public static UtenteCrud getInstance() {

		if (instance == null) {

			instance = new UtenteCrud();
			return instance;
		}
		return instance;
	}

	public List<Utente> findAllUtente(UtenteMapper mapper) throws Exception {

		List<Utente> users = new ArrayList<Utente>();
		try {
			users = mapper.findAllUtente();
			return users;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findAllUtente nel UtenteCrud");

		}

	}

	public Utente insertUtente(UtenteMapper mapper, Utente utente) throws Exception {

		Utente model = new Utente();

		try {
			mapper.insertUtente(utente);
			model = mapper.findByEmailPassword(utente);
			return model;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel insertUtente nel UtenteCrud");
		}

	}

	public Utente findUtenteById(UtenteMapper mapper, Integer id) throws Exception {

		Utente model = new Utente();

		try {
			model = mapper.findUtenteById(id);
			return model;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findUtenteById nel UtenteCrud");
		}

	}

	public Utente findByEmailPassword(UtenteMapper mapper, Utente utenteLogin) throws Exception {

		Utente model = new Utente();

		try {
			model = mapper.findByEmailPassword(utenteLogin);
			return model;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findByEmailPassword nel UtenteCrud");

		}

	}

}
