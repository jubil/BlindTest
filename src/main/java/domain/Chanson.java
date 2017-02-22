package domain;

public class Chanson {

	private String titre, auteur, imgAlbum, srcMusique;
	
	//Meilleur Score
	private User meilleurJoueur;
	private int record;
	
	public Chanson(String titre, String auteur, String imgAlbum, String srcMusique) {
		super();
		this.titre = titre;
		this.auteur = auteur;
		this.imgAlbum = imgAlbum;
		this.srcMusique = srcMusique;
		this.meilleurJoueur = null;
		this.record = (Integer) null;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getImgAlbum() {
		return imgAlbum;
	}

	public void setImgAlbum(String imgAlbum) {
		this.imgAlbum = imgAlbum;
	}

	public String getSrcMusique() {
		return srcMusique;
	}

	public void setSrcMusique(String srcMusique) {
		this.srcMusique = srcMusique;
	}

	public String toJson(){
		return "{ adresseChanson : " + this.getSrcMusique() + 
				", auteur : " + this.getAuteur() + 
				", titre : " + this.getTitre() +
				", imgAlbum : " + this.getImgAlbum() +
				"}";
	}
	
	
}
