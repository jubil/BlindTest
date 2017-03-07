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

	private static final String DATABASE_URL = "jdbc:sqlite:BDD.db";
	//private static final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\Matthieu\\BDD.db";
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
		
		storeChanson(new Chanson("Hangover","Alestorm","https://images.genius.com/2b948edc89085b00d2b46e3a27984fcd.960x960x1.jpg","chansons/0000.mp3"));
		storeChanson(new Chanson("Thunderstruck","AC DC","http://img.cdandlp.com/2013/04/imgL/115931111.jpg","chansons/0001.mp3"));
		storeChanson(new Chanson("Do I Wanna Know","Arctic Monkeys","https://upload.wikimedia.org/wikipedia/en/d/df/Arctic_Monkeys_-_Do_I_Wanna_Know.png","chansons/0002.mp3"));
		storeChanson(new Chanson("Song2","Blur","https://images.rapgenius.com/4d0b298356323707fa4332f568623a5f.600x600x1.jpg","chansons/0003.mp3"));
		storeChanson(new Chanson("It s My Life","Bon Jovi","http://remixpacks.at.ua/_ld/8/86344392.jpg","chansons/0004.mp3"));
		storeChanson(new Chanson("Safe And Sound","Capital Cities","https://upload.wikimedia.org/wikipedia/en/e/ee/Capital-Cities-Safe-and-Sound.jpg","chansons/0005.mp3"));
		storeChanson(new Chanson("Paradise","Coldplay","https://upload.wikimedia.org/wikipedia/en/2/22/Coldplay_-_Paradise.JPG","chansons/0006.mp3"));
		storeChanson(new Chanson("Viva La Vida","Coldplay","http://www.coldplay.com/recordings/art_vivalavida.jpg","chansons/0007.mp3"));
		storeChanson(new Chanson("Gangster s Paradise","Coolio","http://www.booska-p.com/up/images/news/1375728487_samples%20coolio%20gangstas%20paradise%20large.jpg","chansons/0008.mp3"));
		storeChanson(new Chanson("Insane In The Brain","Cypress Hill","https://upload.wikimedia.org/wikipedia/en/0/05/Cypress_Hill_-_Insane_in_the_Brain.jpg","chansons/0009.mp3"));
		storeChanson(new Chanson("Aerodynamic","Daft Punk","http://www.dancerebels.com/wp-content/uploads/2013/08/Download-Daft-Punk-Aerodynamic-Jackson-Days-Remix.jpg","chansons/0010.mp3"));
		storeChanson(new Chanson("One More Time","Daft Punk","http://cdn.ticketfly.com/i/00/01/45/16/93-atlg.jpg","chansons/0011.mp3"));
		storeChanson(new Chanson("Cake By The Ocean","DNCE","https://images.genius.com/ac198dea4aaf034177f1c5054ff05f98.1000x1000x1.jpg","chansons/0012.mp3"));
		storeChanson(new Chanson("Still DRE","Dr Dre et Snoop Dogg","https://images.genius.com/364f9186775b4cb86f062d460690fb7e.822x822x1.jpg","chansons/0013.mp3"));
		storeChanson(new Chanson("Lose Yourself","Eminem","https://1.bp.blogspot.com/-KoPRN4rYQ8E/VuFwB7tKTyI/AAAAAAAACo4/sfD-isKqzh8/s1600/cover.jpg","chansons/0014.mp3"));
		storeChanson(new Chanson("The Real Slim Shady","Eminem","https://upload.wikimedia.org/wikipedia/en/thumb/6/69/Eminem_-_The_Real_Slim_Shady_CD_cover.jpg/220px-Eminem_-_The_Real_Slim_Shady_CD_cover.jpg","chansons/0015.mp3"));
		storeChanson(new Chanson("Dance Dance","Fall Out Boys","https://i.ytimg.com/vi/6rDG8C3oB18/hqdefault.jpg","chansons/0016.mp3"));
		storeChanson(new Chanson("Feel Good Inc","Gorillaz","https://upload.wikimedia.org/wikipedia/en/thumb/d/df/CDR6663.jpg/220px-CDR6663.jpg","chansons/0017.mp3"));
		storeChanson(new Chanson("The Pretender","Foo Fighters","https://s3.amazonaws.com/rapgenius/1395352909_84a6d36dcb_b.jpg","chansons/0018.mp3"));
		storeChanson(new Chanson("Clint Eastwood","Gorillaz","https://upload.wikimedia.org/wikipedia/en/b/b5/ClintEastwood.Gorillaz.single.jpg","chansons/0019.mp3"));
		storeChanson(new Chanson("Just Jammin","Gramatik","http://s2.hulkshare.com/song_images/original/6/e/2/6e29d4b905fcb1476e54faf04f8f260c.jpg?dd=1388552400","chansons/0020.mp3"));
		storeChanson(new Chanson("J ai demandé à la lune","Indochine","http://www.zicabloc.com/wp-content/uploads/2010/04/indochine_jai_demande_a_la_lune.jpg","chansons/0021.mp3"));
		storeChanson(new Chanson("I Was Made For Loving You","KISS","http://djbrecord.free.fr/kiss027.jpg","chansons/0022.mp3"));
		storeChanson(new Chanson("Sorry For Party Rocking","LMFAO","https://upload.wikimedia.org/wikipedia/en/a/a2/Party_Rock_Anthem_(feat._Lauren_Bennet_%26_GoonRock)_-_Single.jpeg","chansons/0023.mp3"));
		storeChanson(new Chanson("J t emmène au vent","Louise Attaque","https://pmcdn.priceminister.com/photo/Louise-Attaque-J-t-emmene-Au-Vent-Cd-Hors-Commerce-Cassettes-Mini-disques-Laser-disques-297207981_ML.jpg","chansons/0024.mp3"));

		
		storeChanson(new Chanson("Thrift shop","Macklemore","http://www.thegroundmag.com/wp-content/uploads/Macklemore.jpg","chansons/0025.mp3"));
		storeChanson(new Chanson("Clandestino","Manu Chao","https://images-na.ssl-images-amazon.com/images/I/618BD96hvcL.jpg","chansons/0026.mp3"));
		storeChanson(new Chanson("Kids","MGMT","https://upload.wikimedia.org/wikipedia/en/9/98/MGMT_Kids.jpg","chansons/0027.mp3"));
		storeChanson(new Chanson("Time to pretend","MGMT","https://upload.wikimedia.org/wikipedia/en/c/cb/Time_to_Pretend_(MGMT_EP)_coverart.jpg","chansons/0028.mp3"));
		storeChanson(new Chanson("Beat it","Mickael Jackson","http://a133.idata.over-blog.com/1/90/86/29/autres2/Michael-Jackson---Beat-it.jpg","chansons/0029.mp3"));
		storeChanson(new Chanson("Respire","Mickey 3D","https://upload.wikimedia.org/wikipedia/en/7/7f/Respire_(Mickey_3D_single_-_cover_art).jpg","chansons/0030.mp3"));
		storeChanson(new Chanson("Hysteria","Muse","http://muse-france.com//wp-content/uploads/sites/6657/2016/09/absolution.jpg","chansons/0031.mp3"));
		storeChanson(new Chanson("Uprising","Muse","http://www.muse-france.fr/wp-content/uploads/2013/05/muse_the_resistance.jpg","chansons/0032.mp3"));
		storeChanson(new Chanson("Smells Like Teen Spirit","Nirvana","http://img.cdandlp.com/2014/04/imgL/116591642.jpg","chansons/0033.mp3"));
		storeChanson(new Chanson("hey Ya","OutKast","https://upload.wikimedia.org/wikipedia/en/e/e4/OutkastHeyYa.jpg","chansons/0034.mp3"));
		storeChanson(new Chanson("All Night","Parov Stelar","http://img1.vmuzike.net/artist/gallery/3de/98e/171047_big.jpg","chansons/0035.mp3"));
		storeChanson(new Chanson("Californiacation","Red hot Chili Pepper","https://upload.wikimedia.org/wikipedia/en/d/df/RedHotChiliPeppersCalifornication.jpg","chansons/0036.mp3"));
		storeChanson(new Chanson("Can t Stop","Red hot Chili Pepper","https://upload.wikimedia.org/wikipedia/en/thumb/8/8e/RedHotChiliPeppersCantStop.jpg/220px-RedHotChiliPeppersCantStop.jpg","chansons/0037.mp3"));
		storeChanson(new Chanson("Jeune et Con","Saez","http://s.mxmcdn.net/images-storage/albums/6/6/8/3/8/4/11483866_500_500.jpg","chansons/0038.mp3"));
		storeChanson(new Chanson("Alors on Danse","Stromae","http://streamd.hitparade.ch/cdimages/stromae-alors_on_danse_s_2.jpg","chansons/0039.mp3"));
		storeChanson(new Chanson("Stupeflip Vite","Stupeflip","https://images.genius.com/da6c0d3dec2ef3ac9e7f12d5bfc7d1b4.600x600x1.jpg","chansons/0040.mp3"));
		storeChanson(new Chanson("Ca","Telephone","https://i.ytimg.com/vi/MIEu5RHjMlc/hqdefault.jpg","chansons/0041.mp3"));
		storeChanson(new Chanson("Un Autre Monde","Telephone","https://i.ytimg.com/vi/MIEu5RHjMlc/hqdefault.jpg","chansons/0042.mp3"));
		storeChanson(new Chanson("Pump It","The Black Eyes Peas","http://cdn.pianosheetmusiconline.com/wp-content/uploads/2012/08/Pump-It-by-Black-Eyed-Peas.jpg","chansons/0043.mp3"));
		storeChanson(new Chanson("I Can t Get No","The Rolling Stones","http://static.fnac-static.com/multimedia/Images/FR/NR/1c/f0/6c/7139356/1540-1/tsp20150622124402/I-can-t-get-no-satisfaction-50th-anniversary.jpg","chansons/0044.mp3"));
		storeChanson(new Chanson("Seven Nation Army","The White Stripes","https://c1.staticflickr.com/5/4142/4809763419_5d9f46f1a7_z.jpg","chansons/0045.mp3"));
		storeChanson(new Chanson("L hymne de nos campagnes","Tryo","http://static.qobuz.com/images/covers/85/58/0886443475885_600.jpg","chansons/0046.mp3"));
		storeChanson(new Chanson("Ride","Twenty One Pilots","http://streamd.hitparade.ch/cdimages/twenty_one_pilots-ride_s.jpg","chansons/0047.mp3"));
		storeChanson(new Chanson("Stressed Out","Twenty One Pilots","http://streamd.hitparade.ch/cdimages/twenty_one_pilots-ride_s.jpg","chansons/0048.mp3"));
		
		
		
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
