package domain;

import java.io.Serializable;
import java.util.Date;

public class StatsUser implements Serializable{
	private static final long serialVersionUID = 1L;

	private int chansonsTrouvees, chansonsNonTrouvees;
	private Date dateInscription;
	
	public StatsUser() {
		init();
	}

	public void init() {
		dateInscription = new Date();
		chansonsTrouvees = 0;
		chansonsNonTrouvees = 0;		
	}

	public int getChansonsTrouvees() {
		return chansonsTrouvees;
	}

	public void setChansonsTrouvees(int chansonsTrouvees) {
		this.chansonsTrouvees = chansonsTrouvees;
	}

	public int getChansonsNonTrouvees() {
		return chansonsNonTrouvees;
	}

	public void setChansonsNonTrouvees(int chansonsNonTrouvees) {
		this.chansonsNonTrouvees = chansonsNonTrouvees;
	}

	public Date getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}
	
	/**
	 * Incrémente les statistiques en fonction de si le joueur trouve une bonne réponse.
	 * @param trouve Mettre true si le joueur trouve la bonne réponse.
	 */
	public void trouverChanson(boolean trouve){
		if(trouve){
			chansonsTrouvees++;
		}else {
			chansonsNonTrouvees++;
		}
	}
		
}
