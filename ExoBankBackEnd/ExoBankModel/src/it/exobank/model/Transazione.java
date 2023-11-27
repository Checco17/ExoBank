package it.exobank.model;

import java.io.Serializable;
import java.util.Date;

public class Transazione implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Date dataTransazione;
	
	private Double importo;
	
	private StatoTransazione statoTransazione;
	
	private TipoTransazione tipoTransazione;
	
	private ContoCorrente contoCorrente;
	
	private ContoCorrente contoCorrenteBeneficiario;
	
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataTransazione() {
		return dataTransazione;
	}

	public void setDataTransazione(Date dataTransazione) {
		this.dataTransazione = dataTransazione;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public StatoTransazione getStatoTransazione() {
		return statoTransazione;
	}

	public void setStatoTransazione(StatoTransazione statoTransazione) {
		this.statoTransazione = statoTransazione;
	}

	public TipoTransazione getTipoTransazione() {
		return tipoTransazione;
	}

	public void setTipoTransazione(TipoTransazione tipoTransazione) {
		this.tipoTransazione = tipoTransazione;
	}

	public ContoCorrente getContoCorrente() {
		return contoCorrente;
	}

	public void setContoCorrente(ContoCorrente contoCorrente) {
		this.contoCorrente = contoCorrente;
	}

	public ContoCorrente getContoCorrenteBeneficiario() {
		return contoCorrenteBeneficiario;
	}

	public void setContoCorrenteBeneficiario(ContoCorrente contoCorrenteBeneficiario) {
		this.contoCorrenteBeneficiario = contoCorrenteBeneficiario;
	}
	
	
	
	
	
}
