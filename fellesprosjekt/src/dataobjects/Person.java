package dataobjects;

public class Person {
	
	private String firstname;
	private String surname;
	private String email;
	private String username;
	private String telephone;
	private int id;
	
	
	public Person(int id, String firstname, String surname, String email, String username, String password,String telephone ) {
		this.id = id;
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

	public int getId() {
		return id;
	}


	
	public String toString() {
		return getFirstname() + " " + getSurname();
	}
}
