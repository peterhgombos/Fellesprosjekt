package common.sendobjects;

import java.util.ArrayList;

import common.dataobjects.Appointment;
import common.dataobjects.Person;

public class AppointmentInvites {
	private ArrayList<Person> invited;
	private Appointment appointment;
	
	public ArrayList<Person> getInvited() {
		return invited;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public AppointmentInvites(ArrayList<Person> invited, Appointment appointment) {
		this.invited = invited;
		this.appointment = appointment;
	}

}
