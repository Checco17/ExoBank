package it.exobank.model;

import java.io.Serializable;

public class Ruolo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer ruoloId;

	private String nomeRuolo;

	public Integer getRuoloId() {
		return ruoloId;
	}

	public void setRuoloId(Integer ruoloId) {
		this.ruoloId = ruoloId;
	}

	public String getNomeRuolo() {
		return nomeRuolo;
	}

	public void setNomeRuolo(String nomeRuolo) {
		this.nomeRuolo = nomeRuolo;
	}

}
