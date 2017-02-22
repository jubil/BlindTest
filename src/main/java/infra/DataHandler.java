package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Chanson;
import domain.User;

public class DataHandler {

	private static final String DATABASE_URL = "jdbc:sqlite:BDD.db";
	private static Connection con = null;
	
	public Chanson getRandomChanson(){
		//TODO : renvoie une musique aleatoire
		return new Chanson("Titre", "auteur", "imgAlbum", "srcMusique");
	}
	
	//OK
	public static void storeUser(User u){
		if(con == null){
			initDatabaseConnection();
		}
		
		Statement statement;
		try {
			statement = con.createStatement();
			statement.execute("INSERT INTO User (pseudo,password) VALUES ('"+u.getPseudo()+"','"+u.getPassword()+"'); SELECT rowid as ROWID, * FROM User;");
		} catch (SQLException e) {
			System.err.println(u.getPseudo() + " ne peut pas être ajouté à la base");
		}
		
	}
	
	//OK
	private static void initDatabaseConnection() {
		try {
			con = DriverManager.getConnection(DATABASE_URL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//OK
	public static void createNewDatabase() {
		if(con != null){
			return;
		}
		try {
			con = DriverManager.getConnection(DATABASE_URL);
			
			Statement statement = con.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS User(pseudo VARCHAR PRIMARY KEY, password VARCHAR);");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	//A VERIFIER
	public static boolean isUserExist(String pseudo) {
		if(con == null){
			initDatabaseConnection();
		}
		
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM User WHERE pseudo='"+ pseudo +"'");
		
			if(!rs.next()){
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	
	
}
