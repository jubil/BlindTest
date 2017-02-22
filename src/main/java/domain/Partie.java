package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.plaf.metal.MetalBorders.PaletteBorder;

import infra.DataHandler;

public class Partie {

	private ArrayList<Chanson> chansons;
	private int indexChansonCourante;
	private HashMap<String, Integer> scores;
	private long startPartieTime;
	private static final int NB_CHANSON = 10;
	//new Date().getTime();
	
	public Partie() {
		super();
		System.out.println("Creation d'une nouvelle partie");
		this.chansons = this.generationPlaylist();
		System.out.println("nb Chanson : " + chansons.size());
		this.indexChansonCourante = 0;
		System.out.println("indexChansonCourante : " + indexChansonCourante);
		this.scores = new HashMap<String, Integer>();
		System.out.println("nb scores : " + scores.size());
		this.startPartieTime = new Date().getTime();
		System.out.println("Nouvelle partie : " + this.startPartieTime);
	}
		
	
	private ArrayList<Chanson> generationPlaylist() {
		ArrayList<Chanson> playlist = new ArrayList<Chanson>();
		Chanson chanson;
		boolean isIn;
//		while(playlist.size() < NB_CHANSON){
//			chanson = new DataHandler().getRandomChanson();
//			isIn = false;
//			for(int i = 0 ; i < playlist.size(); i ++){
//				if( chanson.getTitre().equals(playlist.get(i).getTitre())){
//					isIn = true;
//					break;
//				}
//			}
//			if(!isIn){
//				playlist.add(chanson);
//			}
//		}
		
		//TODO : a supprimer quand foncitonnel
		playlist.add(new DataHandler().getRandomChanson());
		
		return playlist;
	}


	public String getChanson(String pseudo) {
		// TODO verifier que le pseudo est bien enregistrer dans les joueurs;
		return "{indexPlaylist : " + this.indexChansonCourante +
				", chanson  : [ " + this.chansons.get(this.indexChansonCourante).toJson() +
				"]" +
				"}";
	}



}
