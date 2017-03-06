package infra;

import domain.CategorieChanson;

public class DataHandlerTest {

	public static void main(String[] args) {

		//DataHandler.createNewDatabase();
    	//DataHandler.initDatabaseConnection();
    	
//		DataHandler.storeChanson(new Chanson("titre", "auteur", "imgAlbum", "srcMusique"));
//		DataHandler.storeChanson(new Chanson("bbb", "gre", "ivem", "vaexz"));
//		DataHandler.storeChanson(new Chanson("titre2", "auteur2", "imgAlbum2", "srcMusique2"));
		
		System.out.println(DataHandler.getRandomChanson(CategorieChanson.FACILE).getTitre());
	}

}
