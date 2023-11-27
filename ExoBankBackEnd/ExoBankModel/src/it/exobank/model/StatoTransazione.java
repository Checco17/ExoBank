package it.exobank.model;

import java.io.Serializable;

public class StatoTransazione implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String statoTransazione;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatoTransazione() {
		return statoTransazione;
	}

	public void setStatoTransazione(String statoTransazione) {
		this.statoTransazione = statoTransazione;
	}
	
	

}
