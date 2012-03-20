package client.authentication;

import java.io.Serializable;

public class Login implements Serializable{
	
	private static final long serialVersionUID = 2968544862788609989L;
	
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
