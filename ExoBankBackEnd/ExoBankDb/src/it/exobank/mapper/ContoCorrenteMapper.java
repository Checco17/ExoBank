package it.exobank.mapper;

import java.util.List;

import it.exobank.model.ContoCorrente;

public interface ContoCorrenteMapper {

    void insertContoCorrente(ContoCorrente contoCorrente);

    ContoCorrente findContoCorrenteByUtenteId(Integer id);
    
    ContoCorrente findContoCorrenteByNumbConto(ContoCorrente contoCorrente);

    List<ContoCorrente> findAllContoCorrente();

    void updateContoCorrente(ContoCorrente contoCorrente);
//
//    void deleteContoCorrente(Integer id);
}
