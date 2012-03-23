package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	public static final String COL_APPOINTMENTID = "AVTALEID";
	public static final String COL_LEADER = "LEDER";
	public static final String COL_TITLE = "TITTEL";
	public static final String COL_DESCRIPTION = "BESKRIVELSE";
	public static final String COL_FROM = "TIDSPUNKT";
	public static final String COL_TO = "SLUTTIDSPUNKT";
	public static final String COL_PLACE = "STED";
	
	public static final String COL_ANSWER = "SVAR";
	public static final String COL_PERSONID = "ANSATTNR";
	public static final String COL_FORNAVN = "FORNAVN";
	public static final String COL_ETTERNAVN = "ETTERNAVN";
	public static final String COL_BRUKERNAVN = "BRUKERNAVN";
	public static final String COL_EPOST = "EPOST";
	public static final String COL_TLF = "TELEFONNR";
	public static final String COL_ROMID = "ROMNR";
	public static final String COL_ROMKAPASITET = "KAPASITET";
	public static final String COL_EXTERNAL = "EKSTERNE";
	
	
	
	private String mysqlDriver="com.mysql.jdbc.Driver"; 
	private String url="jdbc:mysql://mysql.stud.ntnu.no/martedl_kalender"; 
	private String user = "martedl_admin";
	private String password = "ntnu";
	private Connection connection;

	public void connect(){
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
	
	public int updateDB(String query) throws SQLException{
		return connection.createStatement().executeUpdate(query);
	}

}
