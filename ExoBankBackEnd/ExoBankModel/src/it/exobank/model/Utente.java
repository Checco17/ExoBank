package it.exobank.model;

import java.io.Serializable;

public class Utente implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer utenteId;
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private Ruolo ruoloId;

	public Integer getUtenteId() {
		return utenteId;
	}

	public void setUtenteId(Integer utenteId) {
		this.utenteId = utenteId;
	}

	
	public Ruolo getRuoloId() {
		return ruoloId;
	}

	public void setRuoloId(Ruolo ruoloId) {
		this.ruoloId = ruoloId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
