package it.gestionecompagnia.dao.impiegato;

import java.util.List;

import javax.xml.crypto.Data;

import it.gestionecompagnia.dao.IBaseDAO;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public interface ImpiegatoDAO extends IBaseDAO<Impiegato> {

	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception;

	public int countByDataFondazioneCompagniaGreaterThen(Data dataInput) throws Exception;

	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(long fatturatoInput) throws Exception;

	public List<Impiegato> findAllErroriAssunzioni() throws Exception;

}
