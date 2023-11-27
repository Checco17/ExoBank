package it.exobank.model;

import java.io.Serializable;

public class StatoContoCorrente implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer statoContoCorrenteId;

	private String nomeStato;

	public Integer getStatoContoCorrenteId() {
		return statoContoCorrenteId;
	}

	public void setStatoContoCorrenteId(Integer statoContoCorrenteId) {
		this.statoContoCorrenteId = statoContoCorrenteId;
	}

	public String getNomeStato() {
		return nomeStato;
	}

	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}

	
}
