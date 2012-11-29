package Java;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * The server class that communicates between the JavaScript and the Java classes. 
 * It also uses a SQL database for validating information like passwords.
 */

@WebServlet(name = "game", urlPatterns = {"/game"})
public class GameServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Database db;
	private UserManager users;
    
	/**
	 * Basic constructor that connects to the database and initialises UserManager.
	 */
    public GameServer() {
    	db = new Database();
    	db.connect();
    	users = new UserManager();
				
    }

    /**
     * Gets actions from the HTML such as log on and new user data.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		switch (action){
		}
		
		
	}
	
	/**
	 * Posts information to the HTML such as requests, battle and breed results.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		switch(request.getParameter("action")){
		}
	}
}
	
	