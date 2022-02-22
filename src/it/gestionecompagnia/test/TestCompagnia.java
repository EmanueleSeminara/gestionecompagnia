package it.gestionecompagnia.test;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import it.gestionecompagnia.connection.MyConnection;
import it.gestionecompagnia.dao.Constants;
import it.gestionecompagnia.dao.compagnia.CompagniaDAO;
import it.gestionecompagnia.dao.compagnia.CompagniaDAOImpl;
import it.gestionecompagnia.model.Compagnia;

public class TestCompagnia {

	public static void main(String[] args) {
		CompagniaDAO compagniaDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			compagniaDAOInstance = new CompagniaDAOImpl(connection);

			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testInsertCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testGetCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testUpdateCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testDeleteCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

}
