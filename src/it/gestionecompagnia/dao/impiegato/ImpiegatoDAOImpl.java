package it.gestionecompagnia.dao.impiegato;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import it.gestionecompagnia.dao.AbstractMySQLDAO;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {

	@Override
	public List<Impiegato> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<>();
		Impiegato impiegatoTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from compagnia")) {

			while (rs.next()) {
				impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("nome"));
				impiegatoTemp.setCognome(rs.getString("cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));

				impiegatoTemp.setDataAssunzione(rs.getDate("dataassunzione"));
				impiegatoTemp.setDataNascita(rs.getDate("datanascita"));

				impiegatoTemp.setId(rs.getLong("ID"));
				result.add(impiegatoTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Impiegato get(Long idInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Impiegato> findByExample(Impiegato input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countByDataFondazioneCompagniaGreaterThen(Data dataInput) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(long fatturatoInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Impiegato> findAllErroriAssunzioni() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
