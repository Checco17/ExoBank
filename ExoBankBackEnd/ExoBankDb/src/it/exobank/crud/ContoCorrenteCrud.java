package it.exobank.crud;

import java.util.*;

import it.exobank.mapper.*;
import it.exobank.model.*;

public class ContoCorrenteCrud {

	private static ContoCorrenteCrud instance;

	private ContoCorrenteCrud() {

	}

	public static ContoCorrenteCrud getInstance() {

		if (instance == null) {

			instance = new ContoCorrenteCrud();
			return instance;
		}
		return instance;
	}

	public ContoCorrente insertContoCorrente(ContoCorrenteMapper mapper, ContoCorrente contoCorrente) throws Exception {

		ContoCorrente model = new ContoCorrente();
		try {
			mapper.insertContoCorrente(contoCorrente);
			model = mapper.findContoCorrenteByNumbConto(contoCorrente);
			return model;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'insertContoCorrente nel ContoCorrenteCrud");

		}

	}

	public List<ContoCorrente> findAllContoCorrente(ContoCorrenteMapper mapper) throws Exception {

		List<ContoCorrente> conti = new ArrayList<ContoCorrente>();

		try {
			conti = mapper.findAllContoCorrente();
			return conti;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findAlContoCorrente nel ContoCorrenteCrud");
		}

	}

	public ContoCorrente findContoCorrenteByUtenteId(ContoCorrenteMapper mapper, Integer i) throws Exception {

		ContoCorrente conto = new ContoCorrente();

		try {
			conto = mapper.findContoCorrenteByUtenteId(i);
			return conto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel findContoCorrenteByUtentId nel ContoCorrenteCrud");
		}

	}

	public ContoCorrente updateContoCorrente(ContoCorrenteMapper mapper, ContoCorrente contoCorrente) throws Exception {

		ContoCorrente conto = new ContoCorrente();

		try {
			mapper.updateContoCorrente(contoCorrente);
			conto = mapper.findContoCorrenteByUtenteId(contoCorrente.getUtente().getUtenteId());
			return conto;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel updateContoCorrente nel ContoCorrenteCrud");
		}

	}
}
