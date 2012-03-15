package data_objects;

public class Person {
	
	private String firstname;
	
	private String sirname;
	
	private String email;
	
	private String username;
	
	private String password;
	
	private String telephone;
	
	private int id;
	
	
	public Person(int id, String firstname, String sirname, String email, String username, String password,String telephone ) {
		this.id = id;
		this.firstname = firstname;
		this.sirname = sirname;
		this.email = email;
		this.username = username;
		this.password = password;
		this.telephone = telephone;
	}
	
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getSirname(){
		return sirname;
	}

	public String getEmail() {
		return email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getTelephone(){
		return telephone;
	}

	public int getId() {
		return id;
	}


	
	public String toString() {
		return getFirstname() + " " + getSirname();
	}
}
