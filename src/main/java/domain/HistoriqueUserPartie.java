package domain;

import java.util.ArrayList;

public class HistoriqueUserPartie {
	
	private ArrayList<Integer> listTempsDeReponse;
	private ArrayList<Integer> listFindDeReponse;
	private int points;
	private int indexDerniereReponse;
	
	public HistoriqueUserPartie() {
		super();
		this.listFindDeReponse = new ArrayList<Integer>();
		this.listTempsDeReponse = new ArrayList<Integer>();
		this.points = 0 ;
		this.indexDerniereReponse = 0;
	}

	//public void ajout
	
}
