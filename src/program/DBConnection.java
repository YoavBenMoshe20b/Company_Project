package program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static DBConnection instance = null;
	private Connection connection = null;

	private DBConnection() {

		String url = "jdbc:mysql://127.0.0.1:3306/companydb";
		String user = "root";
		String password = "yoavnbm4321";
		try {
			connection = DriverManager.getConnection(url, user, password);
			if (connection != null) {
				System.out.println("Connected to the database!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance.connection;
	}

}
