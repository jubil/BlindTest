package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Partie;

/**
 * Servlet implementation class GestionPartie
 */
public class GestionPartie_json extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Partie partieCourante;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionPartie_json() {
        super();
        partieCourante = new Partie();
        
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		// TODO : a supprimer une fois connexion operationnel
		//out.print("<br>Served at: " + request.getContextPath());
		//session.setAttribute("pseudo", "Matthieu");
		
		String pseudo = (String) session.getAttribute("pseudo");
		
		if(pseudo == null){
			pseudo = "Invité_" + new Date().getTime();
			session.setAttribute("pseudo", pseudo);
		}

		// TODO : a supprimé quand op
		//out.print("<br>Pseudo : " + pseudo);
		//out.print("<br>Date : " + new Date().getTime());
		//out.println("<br>" + partieCourante.getChanson(pseudo));
		
		out.println(partieCourante.getChanson(pseudo));
		
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
