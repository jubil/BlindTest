package infra;

import domain.Chanson;

public class DataHandlerTest {

	public static void main(String[] args) {

		DataHandler dh = new DataHandler();
		dh.createNewDatabase();
//		dh.AddDefaultValues();
//		dh.storeChanson(new Chanson("titre", "auteur", "imgAlbum", "srcMusique"));
//		dh.storeChanson(new Chanson("titre2", "auteur", "imgAlbum", "srcMusique"));
//		dh.storeChanson(new Chanson("titre3", "auteur", "imgAlbum", "srcMusique"));
//		dh.storeChanson(new Chanson("titre4", "auteur", "imgAlbum", "srcMusique"));
		
		//System.out.println(dh.getRandomChanson().getTitre());
		dh.getRandomChanson();
		
	}

}
