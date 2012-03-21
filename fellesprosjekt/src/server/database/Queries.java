package server.database;

import java.sql.Date;


public class Queries {

	public static String getAppointments(int personid){
		return 	"SELECT AVTALE.* " +
				"FROM AVTALE, DELTAKER " +
				"WHERE DELTAKER.ANSATTNR = " + personid + " " + 
				"AND DELTAKER.AVTALEID = AVTALE.AVTALEID " +
				"AND NOT EXISTS( " +
				"SELECT * " +
				"FROM DELTAKER " +
				"WHERE DELTAKER.ANSATTNR = " + personid + " " +
				");";
	}

	public static String getMeetings(int personid){
		return 	"SELECT AVTALE.* " +
				"FROM AVTALE, DELTAKER " +
				"WHERE DELTAKER.ANSATTNR = " + personid + " " + 
				"AND DELTAKER.AVTALEID = AVTALE.AVTALEID " +
				"AND EXISTS( " +
				"SELECT * " +
				"FROM DELTAKER " +
				"WHERE DELTAKER.ANSATTNR = " + personid + " " +
				");";
	}

	public static String getParticipantsForMeeting(int meetingid){
		return 	"SELECT DELTAKER.SVAR, ANSATT.* " +
				"FROM ANSATT, DELTAKER " +
				"WHERE DELTAKER.AVTALEID = " + meetingid + " " +
				"AND DELTAKER.ANSATTNR = ANSATT.ANSATTNR";
	}
	
	public static String loginAuthentication(String username, String passwordHash){
		return 	"SELECT ANSATT.* " +
				"FROM ANSATT " +
				"WHERE ANSATT.BRUKERNAVN = \"" + username + "\" " +
				"AND ANSATT.PASSORD = \"" + passwordHash + "\";";
	}
	//	
	//
	//	public static String getAppointmentsAsLeader(int personid){
	//		return 	"SELECT AVTALE.* FROM AVTALE, LEDER " +
	//				"WHERE LEDER.ANSATTNR = " + personid + " " +
	//				"AND LEDER.AVTALEID = AVTALE.AVTALEID " +
	//				"AND NOT EXISTS( " +
	//				"SELECT * " +
	//				"FROM DELTAKER " +
	//				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID " +
	//				");";
	//	}
	//	
	//	public static String getMeetingsAsLeader(int personid){
	//		return 	"SELECT AVTALE.* FROM AVTALE, LEDER " +
	//				"WHERE LEDER.ANSATTNR = " + personid + " " +
	//				"AND LEDER.AVTALEID = AVTALE.AVTALEID " +
	//				"AND EXISTS( " +
	//				"SELECT * " +
	//				"FROM DELTAKER " +
	//				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID " +
	//				");";
	//	}
	//
	//
	////	public static String getParticipantsForMeeting(int motenr) {
	////		return "SELECT * " +
	////				"FROM ANSATT " +
	////				"JOIN DELTAKER ON DELTAKER.ANSATTNR = ANSATT.ANSATTNR "+
	////				"WHERE DELTAKER.AVTALEID = " + motenr;
	////		
	////	}
	//	
	//	public static String getMeetingsAsParticipant(int personid){
	//		return 	"SELECT AVTALE.* FROM AVTALE, DELTAKER " +
	//				"WHERE DELTAKER.ANSATTNR = " + personid + " " +
	//				"AND DELTAKER.AVTALEID = AVTALE.AVTALEID;";
	//	}
	//
	//	public static String getParticipantsForMeeting(int meetingid){
	//		return 	"SELECT ANSATT.* FROM ANSATT, DELTAKER " +
	//				"WHERE DELTAKER.AVTALEID = " + meetingid + " " +
	//				"AND DELTAKER.ANSATTNR = ANSATT.ANSATTNR";
	//	}
	//	public static String getLeaderForMeeting(int meetingid){
	//		return 	"SELECT ANSATT.* FROM ANSATT, LEDER " +
	//				"WHERE LEDER.AVTALEID = " + meetingid + " " +
	//				"AND LEDER.ANSATTNR = ANSATT.ANSATTNR;";
	//	}
	//
	//	public static String getPersonsByFilter(String search){
	//		return 	"SELECT ANSATT.* FROM ANSATT" + 
	//				"WHERE (FORNAVN LIKE '%" + search + "%' " +
	//				"OR ETTERNAVN LIKE '%" + search + "%') " +
	//				"OR (MATCH(FORNAVN,ETTERNAVN) " +
	//				"AGAINST ('%" + search +"%' " +
	//				"IN BOOLEAN MODE));";
	//	}
	//	
		public static String createNewAppointment(String title, String description, Date startTime, Date endTime, String place, int leader){
			return  "INSERT INTO AVTALE (TITTEL, BESKRIVELSE, TIDSPUNKT, SLUTTIDSPUNKT, STED, LEDER) " +
					"VALUES ('" + title + "', '" + description + "', " + startTime +
					", " + endTime + ",'" + place + "', " + leader +");";
					
		}
	//
	//
	//
	//
}
