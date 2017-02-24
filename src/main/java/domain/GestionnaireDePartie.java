package domain;

import java.util.HashMap;

public class GestionnaireDePartie {

	static HashMap<String, Partie> listePartie = new HashMap<String, Partie>(); 
	
	public Partie getPartie (String categorie){
		if(!listePartie.containsKey(categorie)){
			listePartie.put(categorie, new Partie());
		}
		return listePartie.get(categorie);
	}
	
	public Partie getPartie (){
		return this.getPartie("Facile");
	}
	
}
