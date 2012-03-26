package common.dataobjects;

import java.io.Serializable;

import xml.XMLElements;

public class Person implements Serializable {
	
	private static final long serialVersionUID = -4926065805520400335L;
	
	private String firstname;
	private String surname;
	private String email;
	private String username;
	private String telephone;
	private int personID;
	
	public Person(int id, String firstname, String surname, String email, String username,String telephone ) {
		this.personID = id;
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.username = username;
		this.telephone = telephone;
	}
	
	@Override
	public int hashCode(){
		return personID;
	}
	
	public Person() {
		
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getSurname(){
		return surname;
	}

	public String getEmail() {
		return email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getTelephone(){
		return telephone;
	}

	public int getPersonID() {
		return personID;
	}
	
	public String toString() {
		return firstname + " " + surname;
	}
	
	public String toXML(){
		StringBuilder xml = new StringBuilder();
		xml.append(XMLElements.openXML(XMLElements.PERSON));
		xml.append(XMLElements.openXML(XMLElements.PERSON_ID) + getPersonID() + XMLElements.closeXML(XMLElements.PERSON_ID));
		xml.append(XMLElements.openXML(XMLElements.FIRSTNAME) + getFirstname() + XMLElements.closeXML(XMLElements.FIRSTNAME));
		xml.append(XMLElements.openXML(XMLElements.SURNAME) + getSurname() + XMLElements.closeXML(XMLElements.SURNAME));
		xml.append(XMLElements.openXML(XMLElements.EMAIL) + getEmail() + XMLElements.closeXML(XMLElements.EMAIL));
		xml.append(XMLElements.openXML(XMLElements.USERNAME) + getUsername() + XMLElements.closeXML(XMLElements.USERNAME));
		xml.append(XMLElements.openXML(XMLElements.TELEPHONE) + getTelephone() + XMLElements.closeXML(XMLElements.TELEPHONE));
		xml.append(XMLElements.closeXML(XMLElements.PERSON));
		return xml.toString();
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}
}
