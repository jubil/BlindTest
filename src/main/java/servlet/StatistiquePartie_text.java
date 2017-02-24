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
 * Servlet implementation class StatistiquePartie
 */
public class StatistiquePartie_text extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatistiquePartie_text() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		Partie partieCourante = (Partie) session.getAttribute("partie");
		
		out.println("pseudo : " + request.getParameter("pseudo"));
		out.println("<br>findingTime : " + request.getParameter("findingTime"));
		out.println("<br>find : " + request.getParameter("find") + "<br>");
		
		//TODO : test authenticité user
		
		try {
			partieCourante.addResponseUser(
					request.getParameter("pseudo"),
					new Integer(request.getParameter("find")),
					new Integer(request.getParameter("findingTime")));		
			out.println(partieCourante.getStatistiqueCourant());			
		} catch (Exception e) {
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
