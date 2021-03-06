package it.gestionecompagnia.dao.impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.AbstractMySQLDAO;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {

	public ImpiegatoDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Impiegato> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<>();
		Impiegato impiegatoTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from impiegato")) {

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
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Impiegato result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Impiegato();
					result.setNome(rs.getString("nome"));
					result.setCognome(rs.getString("cognome"));
					result.setCodiceFiscale(rs.getString("codicefiscale"));
					result.setDataAssunzione(rs.getDate("dataassunzione"));
					result.setDataNascita(rs.getDate("datanascita"));
					result.setId(rs.getLong("ID"));
					result.setId(rs.getLong("ID"));
				} else {
					result = null;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE impiegato SET codicefiscale=?, cognome=?, nome=?, compagnia_id=?, dataassunzione=?, datanascita=? where id=?;")) {
			ps.setString(1, input.getCodiceFiscale());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getNome());
			ps.setLong(4, input.getCompagnia().getId());
			ps.setDate(5, new java.sql.Date(input.getDataAssunzione().getTime()));
			ps.setDate(6, new java.sql.Date(input.getDataNascita().getTime()));
			ps.setLong(7, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO impiegato (codicefiscale, cognome, compagnia_id, dataassunzione, datanascita, nome) VALUES (?, ?, ?, ?, ?, ?);")) {
			ps.setString(1, input.getCodiceFiscale());
			ps.setString(2, input.getCognome());
			ps.setLong(3, input.getCompagnia().getId());
			ps.setDate(4, new java.sql.Date(input.getDataAssunzione().getTime()));
			ps.setDate(5, new java.sql.Date(input.getDataNascita().getTime()));
			ps.setString(6, input.getNome());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM impiegato WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findByExample(Impiegato example) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (example == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Impiegato> result = new ArrayList<>();
		Impiegato impiegatoTemp = null;

		String query = "select * from impiegato where 1=1 ";
		if (example.getCodiceFiscale() != null && !example.getCodiceFiscale().isBlank()) {
			query += " and codicefiscale like '" + example.getCodiceFiscale() + "%' ";
		}
		if (example.getCognome() != null && !example.getCognome().isBlank()) {
			query += " and cognome like '" + example.getCognome() + "%' ";
		}
		if (example.getCompagnia() != null && example.getCompagnia().getId() > 0) {
			query += " and compagnia_id like '" + example.getCompagnia().getId() + "%' ";
		}

		if (example.getDataAssunzione() != null) {
			query += " and dataassunzione like '" + new java.sql.Date(example.getDataAssunzione().getTime()) + "' ";
		}

		if (example.getDataNascita() != null) {
			query += " and dataassunzione like '" + new java.sql.Date(example.getDataNascita().getTime()) + "' ";
		}

		if (example.getNome() != null && !example.getNome().isBlank()) {
			query += " and nome like '" + example.getNome() + "%' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

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
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<>();
		Impiegato impiegatoTemp = null;

		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where compagnia_id=?;")) {
			{
				ps.setLong(1, compagniaInput.getId());
				try (ResultSet rs = ps.executeQuery()) {

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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int countByDataFondazioneCompagniaGreaterThan(Date dataInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		int result = 0;

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) as totale from impiegato i inner join compagnia c on c.id=i.compagnia_id where c.datafondazione > ?;")) {
			{
				ps.setDate(1, new java.sql.Date(dataInput.getTime()));
				try (ResultSet rs = ps.executeQuery()) {

					if (rs.next()) {
						result = rs.getInt("total");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(long fatturatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<>();
		Impiegato impiegatoTemp = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from impiegato i inner join compagnia c on c.id=i.compagnia_id where c.fatturatoannuo > ?;")) {
			{
				ps.setLong(1, fatturatoInput);
				try (ResultSet rs = ps.executeQuery()) {

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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findAllErroriAssunzioni() throws Exception {
		if (isNotActive()) {
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		}

		ArrayList<Impiegato> result = new ArrayList<>();
		Impiegato impiegatoTemp = null;

		try (Statement ps = connection.createStatement();
				ResultSet rs = ps.executeQuery(
						"select * from impiegato i inner join compagnia c on c.id=i.compagnia_id where c.datafondazione > i.dataassunzione;")) {

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

}
