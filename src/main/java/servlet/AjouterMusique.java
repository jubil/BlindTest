package servlet;

import infra.DataHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.CategorieChanson;
import domain.Chanson;

public class AjouterMusique extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AjouterMusique() {
        super();
        DataHandler.createNewDatabase();
    	DataHandler.initDatabaseConnection();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titre = request.getParameter("titre");
		String auteur = request.getParameter("auteur");
		String categorie = request.getParameter("categorie");
		String musique = request.getParameter("musique");
		String album = request.getParameter("album");
		
		if(titre == null || auteur == null || categorie == null || musique == null || album == null){
			response.getWriter().write("Paramètres à utiliser : titre, auteur, categorie, musique et album");
		}else {
			//Si tous les paramètres son remplis
			int cat;
			try{
				cat = Integer.valueOf(categorie);
			}catch(Exception e){
				response.getWriter().write("categorie doit être un nombre");
				return;
			}
			DataHandler.storeChanson(new Chanson(titre, auteur, CategorieChanson.intToCategorie(cat), album, musique));
		}
	}

}
