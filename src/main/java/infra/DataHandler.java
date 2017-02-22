package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Chanson;
import domain.User;

public class DataHandler {

	private static Connection con;
	
	public void storeUser(User u){
		
	}
	
	public Chanson getRandomChanson(){
		//TODO : renvoie une musique aleatoire
		return new Chanson("Titre", "auteur", "imgAlbum", "srcMusique");
	}
	
	public void showAllUsers() throws SQLException{
		if(con == null){
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT pseudo, password FROM user");
		
		//Affichage console
		
	}

	private void getConnection() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con = DriverManager.getConnection("jdbc:sqlite:SQLiteTest.db");
		initialise();
	}

	private void initialise() throws SQLException {
		// TODO Auto-generated method stub
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
	
		if(!res.next()){
			System.out.println("Initialisation de la BD");
		}
	}
	
	
}
