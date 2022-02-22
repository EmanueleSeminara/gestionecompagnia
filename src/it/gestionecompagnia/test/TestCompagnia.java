package it.gestionecompagnia.test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.connection.MyConnection;
import it.gestionecompagnia.dao.Constants;
import it.gestionecompagnia.dao.compagnia.CompagniaDAO;
import it.gestionecompagnia.dao.compagnia.CompagniaDAOImpl;
import it.gestionecompagnia.dao.impiegato.ImpiegatoDAO;
import it.gestionecompagnia.dao.impiegato.ImpiegatoDAOImpl;
import it.gestionecompagnia.model.Compagnia;
import it.gestionecompagnia.model.Impiegato;

public class TestCompagnia {

	public static void main(String[] args) {
		CompagniaDAO compagniaDAOInstance = null;
		ImpiegatoDAO impiegatoDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);

			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testInsertCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testGetCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testUpdateCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testDeleteCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testFindByExampleCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testGetImpiegato(impiegatoDAOInstance);
			System.out.println("In tabella compagnia ci sono " + impiegatoDAOInstance.list().size() + " elementi.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ========== Test Compagnia ==========
	private static void testGetCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testGet inizio.............");
		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		if (elencoVociPresenti.size() < 1)
			throw new RuntimeException("testFindById : FAILED, non ci sono voci sul DB");

		Compagnia primoDellaLista = elencoVociPresenti.get(0);

		Compagnia elementoCheRicercoColDAO = compagniaDAOInstance.get(primoDellaLista.getId());
		if (elementoCheRicercoColDAO == null
				|| !elementoCheRicercoColDAO.getRagioneSociale().equals(primoDellaLista.getRagioneSociale()))
			throw new RuntimeException("testFindById : FAILED, le login non corrispondono");

		System.out.println(".......testGet fine: PASSED.............");
	}

	private static void testInsertCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testInsertCompagnia inizio.............");
		int quantiElementiInseriti = compagniaDAOInstance.insert(new Compagnia("pluto", (long) 5, new Date()));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertUser : FAILED");

		System.out.println(".......testInsertCompagnia fine: PASSED.............");
	}

	private static void testUpdateCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testUpdate inizio.............");
		List<Compagnia> elencoCompagniaPresenti = compagniaDAOInstance.list();
		if (elencoCompagniaPresenti.size() < 1)
			throw new RuntimeException("testUpdate : FAILED, non ci sono compagnie sul DB");
		Compagnia compagniaDaModificare = elencoCompagniaPresenti.get(0);
		compagniaDaModificare.setRagioneSociale("Pluto");

		int numeroModifiche = compagniaDAOInstance.update(compagniaDaModificare);
		if (numeroModifiche != 1) {
			throw new RuntimeException("testUpdate : FAILED, nessun elemento modificato nel DB");
		}

		Compagnia negozioDaVerificare = compagniaDAOInstance.get(compagniaDaModificare.getId());

		if (!negozioDaVerificare.getRagioneSociale().equals(compagniaDaModificare.getRagioneSociale())) {
			throw new RuntimeException("testUpdate : FAILED, i dati aggiornati non corrispondono");
		}

		System.out.println(".......testUpdate fine: PASSED.............");
	}

	private static void testDeleteCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testDeleteCompagnias inizio.............");
		int quantiElementiInseriti = compagniaDAOInstance.insert(new Compagnia("Giuseppe", (long) 4, new Date()));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteUser : FAILED, compagnia da rimuovere non inserito");

		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		int numeroElementiPresentiPrimaDellaRimozione = elencoVociPresenti.size();
		if (numeroElementiPresentiPrimaDellaRimozione < 1)
			throw new RuntimeException("testDeleteUser : FAILED, non ci sono voci sul DB");

		Compagnia ultimoDellaLista = elencoVociPresenti.get(numeroElementiPresentiPrimaDellaRimozione - 1);
		compagniaDAOInstance.delete(ultimoDellaLista);

		int numeroElementiPresentiDopoDellaRimozione = compagniaDAOInstance.list().size();
		if (numeroElementiPresentiDopoDellaRimozione != numeroElementiPresentiPrimaDellaRimozione - 1)
			throw new RuntimeException("testDeleteUser : FAILED, la rimozione non è avvenuta");

		System.out.println(".......testDeleteCompagnia fine: PASSED.............");
	}

	public static void testFindByExampleCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindByExampleCompagnia inizio.............");

		Date dataCreazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");

		Compagnia marioRossi = new Compagnia("Mario", (long) 5, dataCreazione);

		int quantiElementiInseriti = compagniaDAOInstance.insert(marioRossi);
		if (quantiElementiInseriti < 1) {
			throw new RuntimeException("testFindAllWhereDateCreatedGreaterThan : FAILED, user non inserito");
		}

		List<Compagnia> elencoVoci = compagniaDAOInstance.findByExample(marioRossi);
		for (Compagnia compagniaItem : elencoVoci) {
			if (!compagniaItem.getDataFondazione().equals(marioRossi.getDataFondazione())
					|| !compagniaItem.getFatturatoAnnuo().equals(marioRossi.getFatturatoAnnuo())
					|| !compagniaItem.getRagioneSociale().equals(marioRossi.getRagioneSociale())) {
				throw new RuntimeException(
						"testFindAllWhereDateCreatedGreaterThan : FAILED, compagnia con dati diversi diverso"
								+ compagniaItem.getId());
			}
		}
		System.out.println(".......testFindByExampleCompagnia fine: PASSED.............");
	}

	// ========== Test Impiegato ==========
	private static void testGetImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println(".......testGetImpiegato inizio.............");
		List<Impiegato> elencoVociPresenti = impiegatoDAOInstance.list();
		if (elencoVociPresenti.size() < 1)
			throw new RuntimeException("testGetImpiegato : FAILED, non ci sono voci sul DB");

		Impiegato primoDellaLista = elencoVociPresenti.get(0);

		Impiegato elementoCheRicercoColDAO = impiegatoDAOInstance.get(primoDellaLista.getId());
		if (elementoCheRicercoColDAO == null
				|| !elementoCheRicercoColDAO.getCodiceFiscale().equals(primoDellaLista.getCodiceFiscale())
				|| !elementoCheRicercoColDAO.getNome().equals(primoDellaLista.getNome())
				|| !elementoCheRicercoColDAO.getCognome().equals(primoDellaLista.getCognome()))
			throw new RuntimeException("testGetImpiegato : FAILED, le login non corrispondono");

		System.out.println(".......testGetImpiegato fine: PASSED.............");
	}
}
