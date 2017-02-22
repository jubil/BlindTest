package domain;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pseudo, password;	
	private StatsUser stats;
	
	public User(String pseudo, String password) {
		super();
		this.pseudo = pseudo;
		this.password = password;
		this.stats = new StatsUser();
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
	
	public StatsUser getStats() {
		return stats;
	}

	//TODO isUserExist
	public boolean isUserExist(String pseudo){
		return false;
	}

	//TODO connection
	public static boolean connect(String pseudo, String password) {
		if(pseudo.equals("admin")){
			return true;
		}
		return false;
	}
}
