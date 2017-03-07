package servlet;

import infra.DataHandler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;

public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public Inscription() {
		super();
		DataHandler.createNewDatabase();
		DataHandler.initDatabaseConnection();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String password = request.getParameter("password");

		if (pseudo == null || password == null) {
			response.getWriter().write("Echec de l'inscription");
		} else {
			// Les parametres sont corectements saisis
			response.getWriter().write(pseudo + "\n" + password + "\n");
			if (!User.isUserExist(pseudo)) {
				// L'utilisateur a le droit de se connecter
				response.getWriter().write("Inscription autorisee");
				User.inscription(pseudo, password);

				request.getSession().setAttribute("pseudo", pseudo);
				
				response.sendRedirect("/BlindTest/SalleDeJeux");

			} else {
				// Le couple pseudo password n'est pas correcte
				response.getWriter().write("Echec de l'inscription");
			}
		}
	}
}
