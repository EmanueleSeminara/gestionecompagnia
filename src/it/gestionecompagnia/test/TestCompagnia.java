package it.gestionecompagnia.test;

import java.sql.Connection;

import it.gestionecompagnia.connection.MyConnection;
import it.gestionecompagnia.dao.Constants;
import it.gestionecompagnia.dao.compagnia.CompagniaDAO;
import it.gestionecompagnia.dao.compagnia.CompagniaDAOImpl;

public class TestCompagnia {

	public static void main(String[] args) {
		CompagniaDAO compagniaDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			compagniaDAOInstance = new CompagniaDAOImpl(connection);

			System.out.println("In tabella user ci sono " + compagniaDAOInstance.list().size() + " elementi.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
