package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;

public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Connection() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String password = request.getParameter("password");
		
		if(pseudo == null || password == null){
			response.getWriter().write("Echec de connection");
		}else {
			//Les paramètres sont corectements saisis
			response.getWriter().write(pseudo+"\n"+password+"\n");
			if(User.connect(pseudo, password)){
				//L'utilisateur a le droit de se connecter
				response.getWriter().write("Connection autorisée");
				request.getSession().setAttribute("pseudo", pseudo);
				
			}else {
				//Le couple pseudo password n'est pas correcte
				response.getWriter().write("Echec de connection");
			}
			
		}
		
	}

}
