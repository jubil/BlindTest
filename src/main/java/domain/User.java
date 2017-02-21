package domain;

public class User {

	private String pseudo, password;
	
	public User(String pseudo, String password) {
		super();
		this.pseudo = pseudo;
		this.password = password;
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
	
}
