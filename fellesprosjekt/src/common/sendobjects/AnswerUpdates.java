package common.sendobjects;

import java.util.HashMap;

import common.dataobjects.Appointment;
import common.dataobjects.Person;

public class AnswerUpdates {
	private HashMap<Person, Integer> personsWithAnswer;
	private Appointment appointment;

	public AnswerUpdates(HashMap<Person, Integer> personsWithAnswer, Appointment appointment){
		this.personsWithAnswer = personsWithAnswer;
		this.appointment = appointment;
	}

	public HashMap<Person, Integer> getPersonsWithAnswer() {
		return personsWithAnswer;
	}

	public Appointment getAppointment() {
		return appointment;
	}
}
