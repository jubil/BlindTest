package domain;

import java.util.HashMap;

public class GestionnaireDePartie {

	static final CategorieChanson DEFAULT_CATEGORIE = CategorieChanson.FACILE;
	
	static HashMap<String, Partie> listePartie = new HashMap<String, Partie>(); 
	
	public Partie getPartie (String categorie){
		CategorieChanson cc;
		if((categorie.equals("null"))){
			cc = DEFAULT_CATEGORIE;
		}else{
			cc = CategorieChanson.StringToCategorie(categorie);
		}
		
		if(!listePartie.containsKey(categorie)){
			listePartie.put(categorie, new Partie(cc));
		}
		return listePartie.get(categorie);
	}
	
}
