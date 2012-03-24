package server.database;

import common.utilities.DateString;

public class Queries {

	public static String getAppointments(int personid){
		return "SELECT AVTALE. * " +
				"FROM AVTALE " +
				"WHERE AVTALE.LEDER = " + personid + " " +
				"AND NOT EXISTS ( " +
				"SELECT  * " + 
				"FROM DELTAKER " +
				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID" +
				"AND NOT DELTAKER.ANSATTNR = AVTALE.LEDER" +
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
				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID " +
				"AND NOT DELTAKER.ANSATTNR = AVTALE.LEDER" +
				");";
	}

	public static String getAppointmentsWithdatefilter(int personid){
		return 	"SELECT AVTALE. * " +
				"FROM AVTALE " +
				"WHERE AVTALE.LEDER = " + personid + " " +
				"AND NOT EXISTS ( " +
				"SELECT  * " +
				"FROM DELTAKER " +
				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID" +
				");";
	}


	public static String getMeetingsWithdatefilter(int personid){
		return 	"SELECT AVTALE.* " +
				"FROM AVTALE, DELTAKER " +
				"WHERE DELTAKER.ANSATTNR = " + personid + " " + 
				"AND DELTAKER.AVTALEID = AVTALE.AVTALEID " +
				"AND EXISTS( " +
				"SELECT * " +
				"FROM DELTAKER " +
				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID " +
				"AND NOT DELTAKER.ANSATTNR = AVTALE.LEDER" +
				");";
	}

	public static String getLeaderForMeeting(int avtaleid){
		return 	"SELECT ANSATT.* " +
				"FROM ANSATT, AVTALE " + 
				"WHERE AVTALE.AVTALEID = " + avtaleid + " " +
				"AND AVTALE.LEDER = ANSATT.ANSATTNR;";
	}

	public static String getParticipantsForMeeting(int avtaleid){
		return 	"SELECT * " +
				"FROM DELTAKER " + 
				"WHERE AVTALEID = " + avtaleid + ";";
	}

	public static String getAnsFromParticipants(int meetingid){
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

	public static String newNote(String title, int appId){
		return	"INSERT INTO VARSEL (TITTEL, AVTALEID) " +
				"VALUES '" + title + "', " + appId +";"; 
	}

	public static String getLastNote(){
		return  "SELECT * FROM VARSEL " +
				"ORDER BY VARSELID DESC LIMIT 1;";
	}

	public static String getNotes(int deltakerId){
		return	"SELECT VARSEL.* " +
				"FROM VARSEL, DELTAKER "+
				"WHERE VARSEL.AVTALEID = DELTAKER.AVTALEID "+
				"AND DELTAKER.ANSATTNR = " + deltakerId + 
				" ORDER BY VARSEL.TIDSENDT;";
	}	

	public static String getLastAppointment(){
		return	"SELECT * FROM AVTALE " +
				"ORDER BY AVTALEID DESC LIMIT 1;";
	}
	public static String getAppointmentById(int id){
		return	"SELECT * FROM AVTALE " +
				"WHERE AVTALEID = " + id + ";";
	}
	public static String getLastMeeting(){
		return	"SELECT * FROM AVTALE " +
				"ORDER BY AVTALEID DESC LIMIT 1;";
	}

	public static String getNote(int noteId){
		return	"SELECT * FROM VARSEL " +
				"WHERE VARSEL.VARSELID = " + noteId + ";";
	}

	public static String getAppointment(int appId){
		return	"SELECT * FROM AVTALE " +
				"WHERE AVTALE.AVTALEID = " + appId + ";";
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

	public static String getPersonsByFilter(String search){
		return 	"SELECT ANSATT.* FROM ANSATT " + 
				"WHERE (FORNAVN LIKE '%" + search + "%' " +
				"OR ETTERNAVN LIKE '%" + search + "%') " +
				"OR (MATCH(FORNAVN,ETTERNAVN) " +
				"AGAINST ('%" + search +"%' " +
				"IN BOOLEAN MODE));";
	}

	public static String createNewAppointment(String title, String description, DateString startTime, DateString endTime, String place, int leader){
		return  "INSERT INTO AVTALE (TITTEL, BESKRIVELSE, TIDSPUNKT, SLUTTIDSPUNKT, STED, LEDER) " +
				"VALUES ('" + title + "', '" + description + "', '" + startTime.toString() +
				"', '" + endTime.toString() + "', '" + place + "', '" + leader +"');";
	}
	public static String createNewMeeting(String title, String description, DateString startTime, DateString endTime, String place, String room, int leader){
		return  "INSERT INTO AVTALE (TITTEL, BESKRIVELSE, TIDSPUNKT, SLUTTIDSPUNKT, STED, LEDER, ROMNR) " +
				"VALUES ('" + title + "', '" + description + "', '" + startTime.toString() +
				"', '" + endTime.toString() + "', '" + place + "', '" + leader +"', '" + room + "');";
	}

	public static String addPersonToAttend(int personId, int appId){
		return	"INSERT INTO DELTAKER (ANSATTNR, AVTALEID, SVAR) " +
				"VALUES (" + personId + ", " + appId + ", 0);";
	}

	public static String getRoomsForTimeSlot(DateString start, DateString end, int capacity) {
		return "SELECT DISTINCT MOTEROM.ROMNR " +
				"FROM MOTEROM, AVTALE " +
				"JOIN AVTALE A ON " + start +  " <= B.SLUTTIDSPUNKT AND " + end + " >= B.TIDSPUNKT " + 
				"WHERE A.ROMNR != MOTEROM.ROMNR AND MOTEROM.KAPASITET >= " + capacity + ";";

	}

	public static String bookRoom(int appId, String roomId){
		return	"UPDATE AVTALE " +
				"SET ROMNR=" + roomId +
				" WHERE AVTALEID=" + appId + ";";
	}

	public static String updateAnswerToInvite(int attendant, int appointment, int answer){
		return 	"UPDATE DELTAKER " +
				"SET SVAR= " + answer +
				" WHERE AVTALEID=" + appointment + "AND ANSATTNR=" + attendant +";";
	}
	
	public static String deleteNote(int noteID){
		return "DELETE FROM VARSEL "+
				"WHERE VARSELID = " + noteID + ";" ;
	}
}
