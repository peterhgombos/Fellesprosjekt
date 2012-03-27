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
				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID " +
				"AND NOT DELTAKER.ANSATTNR = AVTALE.LEDER" +
				");";
	}

	public static String getRoom(String id){
		return "SELECT MOTEROM.* " +
				"FROM MOTEROM " +
				"WHERE MOTEROM.ROMNR = '" + id + "';";
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
				"AND NOT DELTAKER.ANSATTNR = AVTALE.LEDER " +
				");";
	}

	public static String getMeetingsByDate(int personid, DateString startDate, DateString endDate){
		return 	"SELECT AVTALE. * " +  
				"FROM AVTALE, DELTAKER " + 
				"WHERE DELTAKER.ANSATTNR = " + personid + " " +
				"AND DELTAKER.AVTALEID = AVTALE.AVTALEID AND AVTALE.TIDSPUNKT " +
				"BETWEEN  '" + startDate + "' AND '"+ endDate + "' " +
				"AND EXISTS ( " + 
				"SELECT  * " +
				"FROM DELTAKER " +
				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID " +
				"AND NOT DELTAKER.ANSATTNR = AVTALE.LEDER " +
				");";
	}
	
	public static String getAppointmentsByDate(int personid, DateString startDate, DateString endDate){
		return	"SELECT AVTALE.* " +
				"FROM AVTALE " +
				"WHERE AVTALE.LEDER = " + personid + " " + 
				"AND AVTALE.TIDSPUNKT " +
				"BETWEEN '" + startDate + "' AND '" + endDate + "' " + 
				"AND NOT EXISTS( " +
				"SELECT * " +
				"FROM DELTAKER " +
				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID " +
				"AND NOT DELTAKER.ANSATTNR = AVTALE.LEDER " +
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
	public static String updateNote(int appId){
		return	"UPDATE VARSEL " +
				"SET TIDSENDT = NULL " +
				"WHERE VARSEL.AVTALEID =" + appId + ";";
	}

	public static String getLastNote(){
		return  "SELECT * FROM VARSEL " +
				"ORDER BY TIDSENDT DESC LIMIT 1;";
	}

	public static String getNotes(int deltakerId, String filter){
		return	"SELECT VARSEL. * , HAR_MOTTATT.HAR_LEST " +
				"FROM VARSEL, DELTAKER, HAR_MOTTATT "+
				"WHERE " +
				"VARSEL.AVTALEID = DELTAKER.AVTALEID " +
				"AND DELTAKER.ANSATTNR = HAR_MOTTATT.ANSATTNR " +
				"AND VARSEL.VARSELID = HAR_MOTTATT.VARSELID " +
				"AND DELTAKER.ANSATTNR = " + deltakerId + " " +
				"AND VARSEL.TITTEL LIKE '%" + filter + "%'" +
				"ORDER  BY VARSEL.TIDSENDT;";
	}
	public static String getNotesAvlyst(int deltakerId, String filter){
		return	"SELECT VARSEL. * , HAR_MOTTATT.HAR_LEST " +
				"FROM VARSEL, HAR_MOTTATT "+
				"WHERE " +
				"VARSEL.AVTALEID = -1 " +
				"AND VARSEL.VARSELID = HAR_MOTTATT.VARSELID " +
				"AND HAR_MOTTATT.ANSATTNR = " + deltakerId + " " +
				"AND VARSEL.TITTEL LIKE '%" + filter + "%'" +
				"ORDER  BY VARSEL.TIDSENDT;";
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
	
	public static String createNote(String title, int appID){
		return "INSERT INTO VARSEL VALUES(NULL, '" + title + "', NULL, " + appID + 
				" );";
	}
	
	public static String addNoteToPerson(int personID, int noteID){
		return "INSERT INTO HAR_MOTTATT VALUES( " + personID + ", " + noteID + ", " + " 0);";
	}
	
	public static String upDateNoteToPerson(int personID, int noteID){
		return "UPDATE HAR_MOTTATT SET HAR_LEST = 1 "+
				"WHERE ANSATTNR = " + personID  + 
				" AND VARSELID = " + noteID + " ;";
	}
	public static String resetNoteToPerson(int personID, int noteID){
		return "UPDATE HAR_MOTTATT " +
				"SET HAR_LEST = 0 "+
				"WHERE ANSATTNR = " + personID  + " " + 
				"AND VARSELID = " + noteID + ";";
	}

	public static String getPersonsByFilter(String search){
		return 	"SELECT ANSATT.* FROM ANSATT " + 
				"WHERE (FORNAVN LIKE '%" + search + "%' " +
				"OR ETTERNAVN LIKE '%" + search + "%') " +
				"OR (MATCH(FORNAVN,ETTERNAVN) " +
				"AGAINST ('%" + search +"%' " +
				"IN BOOLEAN MODE));";
	}

//
//	public static String getNotesByFilter(String search){
//		return 	"SELECT VARSEL.* " +
//				"FROM VARSEL " + 
//				"WHERE VARSEL.TITTEL LIKE '%" + search + "%'";
//	}

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
	public static String updatePersonToAttend(int personId, int appId){
		return	"UPDATE DELTAKER " +
				"SET " +
				"SVAR = " + "0 " +
				"WHERE DELTAKER.ANSATTNR = " + personId + " " +
				"AND DELTAKER.AVTALEID = " + appId + " " +
				";";
	}

	public static String getRoomsForTimeSlot(DateString start, DateString end, int capacity) {
		return 	"SELECT DISTINCT MOTEROM .  * " +
				"FROM MOTEROM, AVTALE " +
				"WHERE MOTEROM.KAPASITET >= " + capacity + 
				" AND " +
				"NOT EXISTS ( "+
				"SELECT  * " +
				"FROM AVTALE AS B " +
				"WHERE '" + end + "' <= B.SLUTTIDSPUNKT " +
				"AND  ' " + start + "' >= B.TIDSPUNKT AND B.ROMNR = MOTEROM.ROMNR ); ";
	}

	public static String bookRoom(int appId, String roomId){
		return	"UPDATE AVTALE " +
				"SET ROMNR = '" + roomId + "' " +
				"WHERE AVTALEID = '" + appId + "';";
	}

	public static String updateAnswerToInvite(int attendant, int appointment, int answer){
		return 	"UPDATE DELTAKER " +
				"SET SVAR= " + answer + " " +
				"WHERE AVTALEID = " + appointment + " AND ANSATTNR = " + attendant +";";
	}

	public static String deleteNoteForPerson(int noteID){
		return "DELETE FROM HAR_MOTTATT "+
				"WHERE VARSELID = " + noteID + ";" ;
	}

	public static String updateAppointment(int appID, String title, String description, DateString start, DateString end, String place){
		return 	"UPDATE AVTALE " +
				"SET TITTEL = '" + title + "', BESKRIVELSE =  '" + description + "', TIDSPUNKT = '" + start + 
				"', SLUTTIDSPUNKT= '" + end + "', STED= '" + place + "' " +
				"WHERE AVTALE.AVTALEID = " + appID + ";";
	}
	
	public static String updateMeeting(int appID, String title, String description, DateString start, DateString end, String place, String room){
		return 	"UPDATE AVTALE " + 
				"SET TITTEL = '" + title + "', BESKRIVELSE =  '" + description + "', TIDSPUNKT = '" + start + 
				"', SLUTTIDSPUNKT= '" + end + "', STED= '" + place + "' " +
				"WHERE AVTALE.AVTALEID = " + appID + ";";
	}
	
	public static String deleteAppointment(int appID){
		return  "DELETE AVTALE. * , "+
				"DELTAKER. *  FROM AVTALE," +
				"DELTAKER WHERE AVTALE.AVTALEID = " + appID +
				" AND AVTALE.AVTALEID = DELTAKER.AVTALEID;";
	}
	
	public static String oldNewNotes(int personID){
		return "SELECT VARSEL.* " +
				"FROM VARSEL, HAR_MOTTATT " +
				"WHERE VARSEL.VARSELID = HAR_MOTTATT.VARSELID " +
				"AND HAR_MOTTATT.HAR_LEST = 0 " +
				"AND HAR_MOTTATT.ANSATTNR = " + personID + ";";
	}
	
	public static String deleteParticipant(int personID, int avtaleID) {
		return "DELETE " +
				"FROM DELTAKERE " +
				"WHERE DELTAKERE.ANSATTNR = " + personID + " " +
				"AND DELTAKERE.AVTALEID = " + avtaleID;
		
	}

	public static String getNoteByAppId(int id){
		return "SELECT * FROM VARSEL WHERE AVTALEID = id ;";
	}
}
