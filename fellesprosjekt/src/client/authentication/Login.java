package client.authentication;

public class Login {
	String userName, passwordHash;
	
	public Login(String userName, String password){
		this.userName = userName;
		this.passwordHash = SHAHasher.SHAHash(password);
	}

	public String getUserName() {
		return userName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

}
