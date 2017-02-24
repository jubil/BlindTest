package domain;

public enum CategorieChanson {
	
	FACILE(0, "Facile"), DIFFICILE(1, "Difficile"), INTERNET(2, "Internet"), ANNEE80(3, "Années 80");
	
	private int idCategorie;
	private String intitule;
	
	private CategorieChanson(int idCategorie, String intitule) {
		this.idCategorie = idCategorie;
		this.intitule = intitule;
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public String getIntitule() {
		return intitule;
	}
	
	public static CategorieChanson intToCategorie(int i){
		for(CategorieChanson cc : CategorieChanson.values()){
			if(cc.getIdCategorie() == i){
				return cc;
			}
		}
		//Valeur par défaut
		return FACILE;
	}
	
}
