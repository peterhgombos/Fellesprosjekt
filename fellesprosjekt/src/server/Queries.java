package server;

public class Queries {

	public static String getAppointmentsAsLeader(int personid){
		return 	"SELECT AVTALE.* FROM AVTALE, LEDER " +
				"WHERE LEDER.ANSATTNR = " + personid + " " +
				"AND LEDER.AVTALEID = AVTALE.AVTALEID " +
				"AND NOT EXISTS( " +
				"SELECT * " +
				"FROM DELTAKER " +
				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID " +
				");";
	}
	public static String getMeetingsAsLeader(int personid){
		return 	"SELECT AVTALE.* FROM AVTALE, LEDER " +
				"WHERE LEDER.ANSATTNR = " + personid + " " +
				"AND LEDER.AVTALEID = AVTALE.AVTALEID " +
				"AND EXISTS( " +
				"SELECT * " +
				"FROM DELTAKER " +
				"WHERE DELTAKER.AVTALEID = AVTALE.AVTALEID " +
				");";
	}
	public static String getMeetingsAsParticipant(int personid){
		return 	"SELECT AVTALE.* FROM AVTALE, LEDER " +
				"WHERE LEDER.ANSATTNR = " + personid + " " +
				"AND LEDER.AVTALEID = AVTALE.AVTALEID;";
	}

	public static String getParticipantsForMeeting(int meetingid){
		return 	"SELECT ANSATT.* FROM ANSATT, DELTAKER " +
				"WHERE DELTAKER.AVTALEID = " + meetingid + " " +
				"AND DELTAKER.ANSATTNR = ANSATT.ANSATTNR";
	}
	public static String getLeaderForMeeting(int meetingid){
		return 	"SELECT ANSATT.* FROM ANSATT, LEDER " +
				"WHERE LEDER.AVTALEID = " + meetingid + " " +
				"AND LEDER.ANSATTNR = ANSATT.ANSATTNR;";
	}

	public static String getPersonsByFilter(String search){
		return 	"SELECT ANSATT.* FROM ANSATT" + 
				"WHERE (FORNAVN LIKE '%" + search + "%' " +
				"OR ETTERNAVN LIKE '%" + search + "%') " +
				"OR (MATCH(FORNAVN,ETTERNAVN) " +
				"AGAINST ('%" + search +"%' " +
				"IN BOOLEAN MODE));";
	}




}
