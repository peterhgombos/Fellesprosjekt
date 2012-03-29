package common.dataobjects;

import java.io.Serializable;


public class Person implements Serializable {
	
	private static final long serialVersionUID = -4926065805520400335L;
	
	private String firstname;
	private String surname;
	private String email;
	private String username;
	private String telephone;
	private int id;
	
	public Person(int id, String firstname, String surname, String email, String username,String telephone ) {
		this.id = id;
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.username = username;
		this.telephone = telephone;
	}
	
	@Override
	public int hashCode(){
		return id;
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

	public int getId() {
		return id;
	}
	
	public String toString() {
		return firstname + " " + surname;
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
		this.id = personID;
	}
}
