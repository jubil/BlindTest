package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import domain.User;

public class DataHandler {

	private static final String DATABASE_URL = "jdbc:sqlite:BDD.db";
	private static Connection con = null;
	
	public void storeUser(User u){
		Statement statement;
		try {
			statement = con.createStatement();
			statement.execute("INSERT INTO User (pseudo,password) VALUES ('"+u.getPseudo()+"','"+u.getPassword()+"'); SELECT rowid as ROWID, * FROM User;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createNewDatabase() {
		if(con != null){
			return;
		}
		String url = "jdbc:sqlite:BDD.db";
		try {
			con = DriverManager.getConnection(url);
			
			Statement statement = con.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS User(pseudo VARCHAR PRIMARY KEY, password VARCHAR);");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	
	
}
