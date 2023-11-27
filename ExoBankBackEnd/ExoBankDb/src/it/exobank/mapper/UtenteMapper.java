package it.exobank.mapper;

import java.util.List;

import it.exobank.model.Utente;

public interface UtenteMapper {

	void insertUtente(Utente utente);

	Utente findUtenteById(Integer id);

	List<Utente> findAllUtente();
	
	Utente findByEmailPassword(Utente utenteLogin);

//    void updateUtente(Utente utente);
//
//    void deleteUtente(Integer id);
}
