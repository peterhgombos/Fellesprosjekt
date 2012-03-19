package dataobjects;

import client.connection.XMLElements;

public class Person {
	
	private String firstname;
	private String surname;
	private String email;
	private String username;
	private String telephone;
	private int personID;
	
	public Person(int id, String firstname, String surname, String email, String username, String password,String telephone ) {
		this.personID = id;
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.username = username;
		this.telephone = telephone;
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
}
