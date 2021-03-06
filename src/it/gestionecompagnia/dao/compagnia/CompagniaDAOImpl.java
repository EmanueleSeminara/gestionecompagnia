package it.gestionecompagnia.dao.compagnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.dao.AbstractMySQLDAO;
import it.gestionecompagnia.model.Compagnia;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Compagnia> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<>();
		Compagnia compagniaTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from compagnia")) {

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturatoannuo"));
				compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
				compagniaTemp.setId(rs.getLong("ID"));
				result.add(compagniaTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Compagnia get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Compagnia result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setRagioneSociale(rs.getString("ragionesociale"));
					result.setFatturatoAnnuo(rs.getLong("fatturatoannuo"));
					result.setDataFondazione(rs.getDate("datafondazione"));
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
	public int update(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE compagnia SET datafondazione=?, fatturatoannuo=?, ragionesociale=? where id=?;")) {
			ps.setDate(1, new java.sql.Date(input.getDataFondazione().getTime()));
			ps.setLong(2, input.getFatturatoAnnuo());
			ps.setString(3, input.getRagioneSociale());
			ps.setLong(4, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO compagnia (datafondazione, fatturatoannuo, ragionesociale) VALUES (?, ?, ?);")) {
			ps.setDate(1, new java.sql.Date(input.getDataFondazione().getTime()));
			ps.setLong(2, input.getFatturatoAnnuo());
			ps.setString(3, input.getRagioneSociale());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM compagnia WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findByExample(Compagnia example) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (example == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Compagnia> result = new ArrayList<>();
		Compagnia compagniaTemp = null;

		String query = "select * from compagnia where 1=1 ";
		if (example.getDataFondazione() != null) {
			query += " and datafondazione like '" + new java.sql.Date(example.getDataFondazione().getTime()) + "' ";
		}
		if (example.getFatturatoAnnuo() != null) {
			query += " and fatturatoannuo like '" + example.getFatturatoAnnuo() + "%' ";
		}

		if (example.getRagioneSociale() != null && !example.getRagioneSociale().isBlank()) {
			query += " and ragionesociale like '" + example.getRagioneSociale() + "%' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturatoannuo"));
				compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
				compagniaTemp.setId(rs.getLong("ID"));
				result.add(compagniaTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(Date dataInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<>();
		Compagnia compagniaTemp = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select distinct(c.id), c.ragionesociale, c.fatturatoannuo, c.datafondazione from compagnia c inner join impiegato i on c.id=i.compagnia_id where dataassunzione > ?;")) {
			{
				ps.setDate(1, new java.sql.Date(dataInput.getTime()));
				try (ResultSet rs = ps.executeQuery()) {

					while (rs.next()) {
						compagniaTemp = new Compagnia();
						compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
						compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturatoannuo"));
						compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
						compagniaTemp.setId(rs.getLong("ID"));
						result.add(compagniaTemp);
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
	public List<Compagnia> findAllByRagioneSocialeContiene(String ragioneSocialeInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<>();
		Compagnia compagniaTemp = null;

		try (PreparedStatement ps = connection
				.prepareStatement("select * from compagnia where ragionesociale like ?;")) {
			{
				ps.setString(1, "%" + ragioneSocialeInput + "%");
				try (ResultSet rs = ps.executeQuery()) {

					while (rs.next()) {
						compagniaTemp = new Compagnia();
						compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
						compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturatoannuo"));
						compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
						compagniaTemp.setId(rs.getLong("ID"));
						result.add(compagniaTemp);
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
	public List<Compagnia> findAllByCodFissImpiegatoContiene(String tokeCodiceFiscaleInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<>();
		Compagnia compagniaTemp = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select distinct(c.id), c.ragionesociale, c.fatturatoannuo, c.datafondazione from compagnia c inner join impiegato i on c.id=i.compagnia_id where codicefiscale like ?;")) {
			{
				ps.setString(1, "%" + tokeCodiceFiscaleInput + "%");
				try (ResultSet rs = ps.executeQuery()) {

					while (rs.next()) {
						compagniaTemp = new Compagnia();
						compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
						compagniaTemp.setFatturatoAnnuo(rs.getLong("fatturatoannuo"));
						compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
						compagniaTemp.setId(rs.getLong("ID"));
						result.add(compagniaTemp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}
