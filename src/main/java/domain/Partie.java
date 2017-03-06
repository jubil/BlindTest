package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import infra.DataHandler;

public class Partie {

	private ArrayList<Chanson> chansons;
	private long indexChansonCourante;
	private HashMap<String, HistoriqueUserPartie> scores;
	private long startPartieTime;
	private static final int NB_CHANSON = 15;
	private static final int TEMPS_PAR_MUSIQUE = 15000; //ms //TODO : up to 30000
private static final int TEMPS_ADDITIONNEL_PREMIERE_CHANSON = 5000;
	//new Date().getTime();
	
	public Partie() {
		super();
		System.out.println("Creation d'une nouvelle partie");
		this.chansons = this.generationPlaylist();
		System.out.println("nb Chanson : " + chansons.size());
		this.indexChansonCourante = 0;
		System.out.println("indexChansonCourante : " + indexChansonCourante);
		this.scores = new HashMap<String, HistoriqueUserPartie>();
		System.out.println("nb scores : " + scores.size());
		this.startPartieTime = new Date().getTime();
		System.out.println("Nouvelle partie : " + this.startPartieTime);
	}



	public int getIndexChansonCourante() {
		return (int) this.indexChansonCourante;
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
		for(int i = 0 ; i < NB_CHANSON ; i ++){
			playlist.add(DataHandler.getRandomChanson());
		}
		
		
		return playlist;
	}


	public String getChanson() {
		
		long time = new Date().getTime();
		long tempsDepuisDebutDeLaChanson = (time - (this.startPartieTime + TEMPS_ADDITIONNEL_PREMIERE_CHANSON)) % TEMPS_PAR_MUSIQUE;
		System.out.println("tttt : " + tempsDepuisDebutDeLaChanson);
		System.out.println("index : " + this.indexChansonCourante);
		if((time - this.startPartieTime) < (TEMPS_PAR_MUSIQUE + TEMPS_ADDITIONNEL_PREMIERE_CHANSON)){
			this.indexChansonCourante = 0 ;
		}else{
			this.indexChansonCourante = (time - TEMPS_ADDITIONNEL_PREMIERE_CHANSON - this.startPartieTime ) / TEMPS_PAR_MUSIQUE;
		}
		
		// on verifi si la partie est fini et qu'on demande une nouvelle chanson :
		// on réinitialise la partie
		if(this.indexChansonCourante >= NB_CHANSON){
			this.reinitialisation();
		}
		
		this.getWaitingTime();
		
		System.out.println("indexCourant : " + this.indexChansonCourante);
		
		// TODO verifier que le pseudo est bien enregistrer dans les joueurs;
		return "{\"indexPlaylist\" : \"" + this.indexChansonCourante +
				"\", \"waitingTime\" : \"" + (this.getWaitingTime() ) +
				"\", \"chanson\" : " + this.chansons.get((int)this.indexChansonCourante).toJson() +
				"}";		
	}


	/*
	 * Attend le temps qu'il faut pour repondre a tous en meme temps;
	 * si la partie est fini, alors réinitialise la partie
	 */
	public long getWaitingTime() {
		
		// test s'il faut attendre la prochaine vague de chanson
		long time = new Date().getTime();
		long tempsDepuisDebutDeLaChanson = (time - this.startPartieTime) % TEMPS_PAR_MUSIQUE;
		
		if(this.indexChansonCourante == 0 ){
			System.out.println("premiere chanson");
			return TEMPS_PAR_MUSIQUE - tempsDepuisDebutDeLaChanson + TEMPS_ADDITIONNEL_PREMIERE_CHANSON;
		}
		return TEMPS_PAR_MUSIQUE - tempsDepuisDebutDeLaChanson;
	}


	private void reinitialisation() {
		System.out.println("Creation d'une nouvelle partie");
		this.chansons = this.generationPlaylist();
		System.out.println("nb Chanson : " + chansons.size());
		this.indexChansonCourante = 0;
		System.out.println("indexChansonCourante : " + indexChansonCourante);
		this.scores = new HashMap<String, HistoriqueUserPartie>();
		System.out.println("nb scores : " + scores.size());
		this.startPartieTime = new Date().getTime();
		System.out.println("Nouvelle partie : " + this.startPartieTime);
		
	}


	public String getStatistiqueCourant(String pseudo) {
		String json = "{\"yourClassement\":";
			json += "{";
			json += "\"pseudo\" : \"" + pseudo + "\",";
			json += "\"point\" : \"" + scores.get(pseudo).getPoints() + "\",";
			json += "\"dernierResultat\" : \"" + scores.get(pseudo).getDernierResultat() + "\",";
			json += "\"dernierTempsDeReponse\" : \"" + scores.get(pseudo).getDernierTempsDeReponse() + "\"";
			json += "},";
		
		 json += "\"classement\" : [ ";
		System.out.println("keySet : " + this.scores.keySet().size());
		for (String key : this.scores.keySet()){
			 json += "{";
			 json += "\"pseudo\" : \"" + key + "\",";
			 json += "\"point\" : \"" + scores.get(key).getPoints() + "\",";
			 json += "\"dernierResultat\" : \"" + scores.get(key).getDernierResultat() + "\",";
			 json += "\"dernierTempsDeReponse\" : \"" + scores.get(key).getDernierTempsDeReponse() + "\"";
			 json += "},";
		}
		
		json = json.substring(0, json.length()-1) +  "]}";
		
		return json;
	}


	public void addResponseUser(String pseudo, int index, int resultat, int tempsDeReponse) {
		// TODO Ajouter la verification que l'utilisateur n'a pas déjà jouer le coup
		
		if(!this.scores.containsKey(pseudo)){
			this.addUserToPartie(pseudo);
		}
		this.scores.get(pseudo).ajouterNouvelleReponse(index, resultat, tempsDeReponse);
	}



	public void addUserToPartie(String pseudo) {
		this.scores.put(pseudo, new HistoriqueUserPartie());
		System.out.println("Nouvelle Utilisateur dans la partie : " + pseudo);
	}


	

}
