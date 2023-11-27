package it.exobank.crud;

import java.util.ArrayList;
import java.util.List;

import it.exobank.mapper.StatoContoCorrenteMapper;
import it.exobank.model.StatoContoCorrente;


public class StatoContoCorrenteCrud {

	private static StatoContoCorrenteCrud instance;

	public static StatoContoCorrenteCrud getInstance() {

		if (instance == null) {

			instance = new StatoContoCorrenteCrud();
			return instance;
		}
		return instance;
	}

	public List<StatoContoCorrente> findAllStatoContoCorrente(StatoContoCorrenteMapper mapper) throws Exception {

		List<StatoContoCorrente> conti = new ArrayList<StatoContoCorrente>();
		
		try{
			conti = mapper.findAllStatoContoCorrente();
			return conti;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findAllStatoContoCorrente nel StatoContoCorrenteCrud");
		}

		

	}

}
