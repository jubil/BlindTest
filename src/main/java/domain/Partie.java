package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Partie {

	private ArrayList<Chanson> chansons;
	private int indexChansonCourante;
	private HashMap<String, Integer> scores;
	private long startPartieTime;
	private static final int NB_CHANSON = 10;
	//new Date().getTime();
	
	public Partie() {
		super();
		this.chansons = this.generationPlaylist(NB_CHANSON);
		this.indexChansonCourante = 0;
		this.scores = new HashMap<String, Integer>();
		this.startPartieTime = new Date().getTime();
	}
		
	
	private ArrayList<Chanson> generationPlaylist(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	public String getChanson(String pseudo) {
		// TODO Auto-generated method stub
		return "{Musique}";
	}



}
