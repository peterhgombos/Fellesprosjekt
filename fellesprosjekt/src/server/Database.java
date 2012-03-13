package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Database {
	private String mysqlDriver="com.mysql.jdbc.Driver"; 
	private String url="jdbc:mysql://mysql.stud.ntnu.no/martedl_kalender"; 
	private String user = "martedl_admin";
	private String password = "ntnu";
	private Connection connection;

	public Database() {
		connect();

		ResultSet result;
		String query = "SELECT FORNAVN FROM ANSATT;";

		Console.writeline(query);
		Console.writeline("resultat: ");
		try {		
			result = executeQuery(query);			
			while (result.next()) {
				String s = result.getString("fornavn");
				Console.writeline(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		

	}

	private void connect(){
		try {
			Class.forName(mysqlDriver).newInstance();
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
	} 

	private ResultSet executeQuery(String query) throws SQLException{
		return connection.createStatement().executeQuery(query);
	}

}
