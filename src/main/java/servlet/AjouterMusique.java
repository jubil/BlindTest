package servlet;

import infra.DataHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		int categorie = Integer.valueOf(request.getParameter("cat"));
		String musique = request.getParameter("musique");
		String album = request.getParameter("album");
	}

}
