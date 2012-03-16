package server;

public class Queries {

	public static String getAppointmentsAsPartitioner(int personid){
		return 	"SELECT AVTALE.* FROM AVTALE, DELTAKER " +
				"WHERE DELTAKER.ANSATTNR = " + personid + " "+
				"AND DELTAKER.AVTALEID = AVTALE.AVTALEID;";
	}
	public static String getAppointmentsAsLeader(int personid){
		return 	"SELECT AVTALE.* FROM AVTALE, LEDER " +
				"WHERE LEDER.ANSATTNR = " + personid + " " +
				"AND LEDER.AVTALEID = AVTALE.AVTALEID;";
	}
	public static String getPartitionersForMeeting(int meetingid){
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
