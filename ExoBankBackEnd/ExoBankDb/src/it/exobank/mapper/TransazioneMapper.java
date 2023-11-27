package it.exobank.mapper;

import java.util.List;

import it.exobank.model.Transazione;

public interface TransazioneMapper {

    void insertTransazione(Transazione transazione);

    Transazione findTransazioneById(Integer id);

    List<Transazione> findAllTransazione();

    void updateTransazione(Transazione transazione);
    
    Transazione findLastTransazione();
    
    List<Transazione> findTransazioneByUtenteId(Integer i);

   
}
