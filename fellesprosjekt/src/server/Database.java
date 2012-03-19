package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	public static final String COL_APPOINTMENTID = "AVTALEID";
	public static final String COL_TITLE = "TITTEL";
	public static final String COL_DESCRIPTION = "BESKRIVELSE";
	public static final String COL_FROM = "TIDSPUNKT";
	public static final String COL_TO = "SLUTTTIDSPUNKT";
	public static final String COL_PLACE = "STED";
	
	public static final String COL_ANSWER = "SVAR";
	public static final String COL_PERSONID = "ANSATTNR";
	public static final String COL_FORNAVN = "FORNAVN";
	public static final String COL_ETTERNAVN = "ETTERNAVN";
	public static final String COL_BRUKERNAVN = "BRUKERNAVN";
	public static final String COL_EPOST = "EPOST";
	public static final String COL_TLF = "TELEFONNR";
	
	
	
	private String mysqlDriver="com.mysql.jdbc.Driver"; 
	private String url="jdbc:mysql://mysql.stud.ntnu.no/martedl_kalender"; 
	private String user = "martedl_admin";
	private String password = "ntnu";
	private Connection connection;

	public Database() {
		connect();

		ResultSet result;
		String query = "SELECT FORNAVN FROM ANSATT;";

		ServerConstants.console.writeline(query);
		ServerConstants.console.writeline("resultat: ");
		try {		
			result = executeQuery(query);			
			while (result.next()) {
				String s = result.getString("fornavn");
				ServerConstants.console.writeline(s);
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
	
	

	public ResultSet executeQuery(String query) throws SQLException{
		return connection.createStatement().executeQuery(query);
	}

}
