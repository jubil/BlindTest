package domain;

import infra.DataHandler;

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

	// isUserExist
	public static boolean isUserExist(String pseudo) {
		return DataHandler.isUserExist(pseudo);
	}

	// connection
	public static boolean connect(String pseudo, String password) {
		return DataHandler.connectUser(pseudo, password);
	}

	// inscription
	public static void inscription(String pseudo, String password) {
		// Verifier si le pseudo existe
		if (isUserExist(pseudo)) {
			System.out.println(pseudo + "User already exists");
			return;
		}
		DataHandler.storeUser(new User(pseudo, password));	
	}
}
