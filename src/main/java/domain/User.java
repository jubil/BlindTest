package domain;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pseudo, password;	
	private Stats stats;
	
	public User(String pseudo, String password) {
		super();
		this.pseudo = pseudo;
		this.password = password;
		this.stats = new Stats();
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
	//TODO isUserExist
	public boolean isUserExist(String pseudo){
		return false;
	}
}
