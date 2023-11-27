package it.exobank.model;

import java.io.Serializable;

public class TipoTransazione implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String tipoTransazione;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipoTransazione() {
		return tipoTransazione;
	}

	public void setTipoTransazione(String tipoTransazione) {
		this.tipoTransazione = tipoTransazione;
	}
	
	

}
