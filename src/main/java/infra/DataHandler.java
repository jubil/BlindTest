package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import domain.CategorieChanson;
import domain.Chanson;
import domain.User;

public class DataHandler {

	// private static final String DATABASE_URL = "jdbc:sqlite:BDD.db";
	private static final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\Matthieu\\BDD.db";
	private static Connection con = null;

	public DataHandler(){
		super();
		initDatabaseConnection();
	}
	
	public static Chanson getRandomChanson() {
		if (con == null) {
			initDatabaseConnection();
		}
		int nbChansons = 1;
		try{
			//Calcul du nombre de chansons
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT count(*) as c FROM Music;");
			while (rs.next()) {
				nbChansons = rs.getInt("c");
			}
			int idAlea = new Random().nextInt(nbChansons)+1;
			statement = con.createStatement();
			rs = statement.executeQuery("SELECT * FROM Music WHERE id='"+idAlea+"';");
			
			while (rs.next()) {
				return new Chanson(rs.getString("titre"), rs.getString("auteur"), CategorieChanson.intToCategorie(rs.getInt("categorie")), rs.getString("image_album"), rs.getString("fichier_musique"));
			}			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		//Normalement jamais atteint
		return null;
		
	}
	
	public static Chanson getRandomChanson(int categorie) {
		if (con == null) {
			initDatabaseConnection();
		}
		try{
			//Calcul du nombre de chansons
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Music WHERE categorie="+categorie+" ORDER BY RANDOM() LIMIT 1;");
			
			while (rs.next()) {
				return new Chanson(rs.getString("titre"), rs.getString("auteur"), CategorieChanson.intToCategorie(rs.getInt("categorie")), rs.getString("image_album"), rs.getString("fichier_musique"));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		//Normalement jamais atteint
		return null;
	}
	
	public static Chanson getRandomChanson(CategorieChanson categorie) {
		return getRandomChanson(categorie.getIdCategorie());
	}

	public static void storeChanson(Chanson c) {
		if (con == null) {
			initDatabaseConnection();
		}

		try {
			Statement statement = con.createStatement();
			statement
					.execute("INSERT INTO Music (titre,auteur,categorie,fichier_musique,image_album) VALUES ('"
							+ c.getTitre()
							+ "','"
							+ c.getAuteur()
							+ "','"
							+ c.getCategorie().getIdCategorie()
							+ "','"
							+ c.getSrcMusique()
							+ "','"
							+ c.getImgAlbum()
							+ "'); SELECT rowid as ROWID, * FROM Music; VALUES ('"
							+ c.getTitre()
							+ "','"
							+ c.getAuteur()
							+ "','"
							+ c.getSrcMusique()
							+ "','"
							+ c.getImgAlbum()
							+ "'); SELECT rowid as ROWID, * FROM Music;");
		} catch (SQLException e) {
			System.err.println("La chanson ne peut pas être ajouté à la base");
			// e.printStackTrace();
		}

	}

	// OK
	public static void storeUser(User u) {
		if (con == null) {
			initDatabaseConnection();
		}

		try {
			Statement statement = con.createStatement();
			statement.execute("INSERT INTO User (pseudo,password) VALUES ('"
					+ u.getPseudo() + "','" + u.getPassword()
					+ "'); SELECT rowid as ROWID, * FROM User;");
		} catch (SQLException e) {
			System.err.println(u.getPseudo()
					+ " ne peut pas être ajouté à la base");
		}

	}

	// OK
	public static void initDatabaseConnection() {
		try {
			con = DriverManager.getConnection(DATABASE_URL);
		} catch (SQLException e) {
			createNewDatabase();
			//e.printStackTrace();
		}
	}

	// OK
	public static void createNewDatabase() {
		if (con != null) {
			return;
		}
		try {
			try {
				// Chargement du Driver
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			con = DriverManager.getConnection(DATABASE_URL);
			System.out.println(con);
			Statement statement = con.createStatement();
			statement
					.execute("CREATE TABLE IF NOT EXISTS User(pseudo VARCHAR PRIMARY KEY, password VARCHAR);");

			statement = con.createStatement();
			statement
					.execute("CREATE TABLE IF NOT EXISTS Music(id INTEGER PRIMARY KEY AUTOINCREMENT, titre VARCHAR NOT NULL, auteur VARCHAR NOT NULL, best_score INT, best_user VARCHAR, categorie INT, fichier_musique VARCHAR NOT NULL, image_album VARCHAR NOT NULL);");

			AddDefaultValues();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void AddDefaultValues(){
		storeUser(new User("admin", "admin"));
		storeUser(new User("val", "123"));
		
		storeChanson(new Chanson("Hangover","Alestorm","https://images.genius.com/2b948edc89085b00d2b46e3a27984fcd.960x960x1.jpg","/BlindTest/chansons/Alestorm_Hangover.mp3"));
	}

	// A VERIFIER
	public static boolean isUserExist(String pseudo) {
		if (con == null) {
			initDatabaseConnection();
		}

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT * FROM User WHERE pseudo='" + pseudo
							+ "'");

			if (!rs.next()) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean connectUser(String pseudo, String password) {
		if (con == null) {
			initDatabaseConnection();
		}

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT * FROM User WHERE pseudo='" + pseudo
							+ "' AND password='" + password + "'");

			if (!rs.next()) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

}
