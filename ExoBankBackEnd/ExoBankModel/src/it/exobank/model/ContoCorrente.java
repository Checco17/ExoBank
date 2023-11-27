package it.exobank.model;

import java.io.Serializable;

public class ContoCorrente implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String numeroConto;

	private Double saldo;

	private StatoContoCorrente statoContoCorrente;

	private Utente utente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroConto() {
		return numeroConto;
	}

	public void setNumeroConto(String numeroConto) {
		this.numeroConto = numeroConto;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public StatoContoCorrente getStatoContoCorrente() {
		return statoContoCorrente;
	}

	public void setStatoContoCorrente(StatoContoCorrente statoContoCorrente) {
		this.statoContoCorrente = statoContoCorrente;
	}

}
