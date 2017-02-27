package domain;

import java.util.ArrayList;

public class HistoriqueUserPartie {
	
	private ArrayList<Integer> listTempsDeReponse;
	private ArrayList<Integer> listDeReponse;
	private int points;
	private int indexDerniereReponse;
	
	public HistoriqueUserPartie() {
		super();
		this.listDeReponse = new ArrayList<Integer>();
		this.listTempsDeReponse = new ArrayList<Integer>();
		this.points = 0 ;
		this.indexDerniereReponse = -1;
	}

	public void ajouterNouvelleReponse(int index, int resultat, int tempsDeReponse){
		if(index > indexDerniereReponse && (resultat < 4)){
			indexDerniereReponse = index;
			this.listDeReponse.add(resultat);
			this.listTempsDeReponse.add(tempsDeReponse);
			
			if(resultat > 1){ resultat = resultat -1;}
			this.points += resultat;
		}	
	}

	public ArrayList<Integer> getListTempsDeReponse() {
		return listTempsDeReponse;
	}
	
	public Integer getDernierTempsDeReponse(){
		if(listDeReponse.size()>0){
			return listTempsDeReponse.get(listDeReponse.size()-1);
		}
		return -1;
	}

	public ArrayList<Integer> getListDeReponse() {
		return listDeReponse;
	}
	
	public int getDernierResultat(){
		if(listDeReponse.size()>0){
			return listDeReponse.get(listDeReponse.size()-1);
		}
		return -1;
	}

	public int getPoints() {
		return points;
	}

	public int getIndexDerniereReponse() {
		return indexDerniereReponse;
	}
	
	
	
}
