package data_objects;

public class Message {
	
	private String text;
	private Appointment appointment;
	
	
	public Message (String text, Appointment appointment){
		this.text = text;
		this.appointment = appointment;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public Appointment getAppointment() {
		return appointment;
	}


	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	
}
