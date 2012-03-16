package utilities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class XmlUtilities {
	
	public static String appointmentsToXml(ResultSet rs) throws SQLException {
		while (rs.next()) {
			int id = rs.getInt(DatabaseColumns.AVTALEID);
			String tittel = rs.getString(DatabaseColumns.TITTEL);
			String beskrivelse = rs.getString(DatabaseColumns.BESKRIVELSE);
			Date tidspunkt = rs.getDate(DatabaseColumns.TIDSPUNKT);
			Date sluttidspunkt = rs.getDate(DatabaseColumns.SLUTTIDSPUNKT);
			String sted = rs.getString(DatabaseColumns.STED);
			
			System.out.println(id + tittel + beskrivelse + tidspunkt + sluttidspunkt + sted);
		}
		return null;
		
	}
	
}