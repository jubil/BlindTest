package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSpinnerUI;

import domain.GestionnaireDePartie;
import domain.Partie;

/**
 * Servlet implementation class StatistiquePartie
 */
public class StatistiquePartie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatistiquePartie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		Partie partieCourante = new GestionnaireDePartie().getPartie(""+session.getAttribute("categorie"));

		//Partie partieCourante =  session.getAttribute("partie");
		
		if((session.getAttribute("pseudo")+"").equals("null")){
			session.setAttribute("pseudo", "Invité" + new Date().getTime());
		}
		
		try {
			partieCourante.addResponseUser(
					""+session.getAttribute("pseudo"),
					new Integer(request.getParameter("find")),
					new Integer(request.getParameter("findingTime")));
		} catch (Exception e) {
			System.out.println("Aucune infos user");
			}
		
		try {
			out.println(partieCourante.getStatistiqueCourant());			
		} catch (Exception e) {
			System.out.println("Error lors de la recuperation des states général");
			e.printStackTrace();
			out.println("{\"classement\" : []} ");;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
