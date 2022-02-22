package it.gestionecompagnia.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

public class Compagnia {
	private Long id;
	private String ragioneSociale;
	private Long fatturatoAnnuo;
	private Data dataFondazione;
	private List<Impiegato> impiegati = new ArrayList<>();

	public Compagnia() {
		super();
	}

	public Compagnia(Long id, String ragioneSociale, long fatturatoAnnuo, Data dataFondazione) {
		super();
		this.id = id;
		this.ragioneSociale = ragioneSociale;
		this.fatturatoAnnuo = fatturatoAnnuo;
		this.dataFondazione = dataFondazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public Long getFatturatoAnnuo() {
		return fatturatoAnnuo;
	}

	public void setFatturatoAnnuo(Long fatturatoAnnuo) {
		this.fatturatoAnnuo = fatturatoAnnuo;
	}

	public Data getDataFondazione() {
		return dataFondazione;
	}

	public void setDataFondazione(Data dataFondazione) {
		this.dataFondazione = dataFondazione;
	}

	public List<Impiegato> getImpiegati() {
		return impiegati;
	}

	public void setImpiegati(List<Impiegato> impiegati) {
		this.impiegati = impiegati;
	}

	@Override
	public String toString() {
		return "Compagnia [id=" + id + ", ragioneSociale=" + ragioneSociale + ", fatturatoAnnuo=" + fatturatoAnnuo
				+ ", dataFondazione=" + dataFondazione + "]";
	}

}
