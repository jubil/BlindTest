package domain;

import java.util.HashMap;

public class GestionnaireDePartie {

	static final String DEFAULT_CATEGORIE = "Facile";
	
	static HashMap<String, Partie> listePartie = new HashMap<String, Partie>(); 
	
	public Partie getPartie (String categorie){
		if((categorie.equals("null"))){
			categorie = DEFAULT_CATEGORIE;
		}
		
		if(!listePartie.containsKey(categorie)){
			listePartie.put(categorie, new Partie());
		}
		return listePartie.get(categorie);
	}
	
}
