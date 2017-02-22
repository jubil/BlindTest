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
	private static final int TEMPS_PAR_MUSIQUE = 30000; //ms
	private static final int DECALAGE_MAX_ENTRE_JOUEURS = 1000; //ms : le decalage max entre chaque joueur
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
		
		long time = new Date().getTime();
		long index = (time - this.startPartieTime) / TEMPS_PAR_MUSIQUE;
		
		// TODO verifier que le pseudo est bien enregistrer dans les joueurs;
		return "{indexPlaylist : " + index +
				", chanson  : [ " + this.chansons.get(this.indexChansonCourante).toJson() +
				"]" +
				"}";		
	}


	/*
	 * Attend le temps qu'il faut pour repondre a tous en meme temps;
	 * si la partie est fini, alors réinitialise la partie
	 */
	public void synchronisation() {
		
		// test s'il faut attendre la prochaine vague de chanson
		long time = new Date().getTime();
		long tempsDepuisDebutDeLaChanson = (time - this.startPartieTime) % TEMPS_PAR_MUSIQUE;
		
		if( tempsDepuisDebutDeLaChanson > DECALAGE_MAX_ENTRE_JOUEURS){
			// si pas au debut : on attend
			System.out.println("Attente pour syncro");
			System.out.println("declage : " + tempsDepuisDebutDeLaChanson);
			System.out.println("Attente : " + (TEMPS_PAR_MUSIQUE - tempsDepuisDebutDeLaChanson));
			try {
				Thread.sleep(TEMPS_PAR_MUSIQUE - tempsDepuisDebutDeLaChanson);
			} catch (InterruptedException e) {e.printStackTrace();}
			System.out.println("Reprise");
		}
		System.out.println("Synchro");
		
		// on verifi si la partie est fini et qu'on demande une nouvelle chanson :
		// on réinitialise la partie
		if(this.indexChansonCourante >= NB_CHANSON){
			this.reinitialisation();
		}
	}


	private void reinitialisation() {
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



}
