package monsterServer;

/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
*/
import java.sql.SQLException;
import java.sql.ResultSet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


@WebServlet(name = "game", urlPatterns = {"/game"})
public class GameServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	Database db;
    public ArrayList<User> activeUsers;
    
    public GameServer() {
    	db = new Database();
    	db.connect();
    	activeUsers = db.getActiveUsers();
				
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		
		switch (action){
		
		case "isLoggedIn": IsLoggedIn(request, response); break;
		case "getUserData": GetUserData(request, response); break;
		}
		
		
	}
	
	public void IsLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		if (session.getAttribute("username") != null && session.getAttribute("key") != null){
			
			out.print("true");
		} else {
			
			out.print("false");
		}
		
		out.flush();
		out.close();
	}
	
	public void GetUserData(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		HttpSession session = request.getSession(true);
		
		for (User user: activeUsers){
			
			if (user.getUsername().equals(session.getAttribute("username")) && user.getKey().equals(session.getAttribute("key"))){

				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("{\"username\": \"" + user.getUsername() + "\", \"key\": \"" + user.getKey() + "\" ,\"cash\": \""+user.getCash() + "\" }");
				out.flush();
				out.close();
				
				break;
			}
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		switch(request.getParameter("action")){
		
		case "login": LogIn(request,response);break;
		case "logout": LogOut(request,response);break;
		case "newuser": NewUser(request,response);break;
		}
	}
	
		public void LogIn(HttpServletRequest request, HttpServletResponse response){
			
			String username = request.getParameter("username");
		    String password = request.getParameter("password");
			
			int ID;
			int cash;
			
			String query = ("SELECT * FROM user WHERE UserName='" +username+ "'");
        	
			try {
			
	    		ResultSet rset;
	    		rset = db.createQuery (query);
	    		while (rset.next ())
	    		{    
	    			if(rset.getString("password").equalsIgnoreCase(password)){
	    				
	    				Random rand = new Random();
	    				String key = "";
	    				for(int i=0;i<100;i++){
	    					int whatever = rand.nextInt(5);
	    					char temp = (char) whatever;
	    					key += temp;
	    				}
	    				
	    				User user = new User(username,password);
	    				user.setKey(key);
	    				activeUsers.add(user);
	    				user.setCash(rset.getInt("cash")); 
	    				
	    				HttpSession session = request.getSession(true);
	    				session.setAttribute("key", key);
	    				session.setAttribute("username", user.getUsername());
	    				
	    				
	    			    query = ("UPDATE user SET 'key'='"+key+"' WHERE 'UserName'='"+username+"';");
	 		            //run the query and stor in DB
	 		            db.execute(query);
	    				
	    				break;
	    			}
        		}
    		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				PrintWriter out = response.getWriter();
					
				out.print("");
				out.flush();
				out.close();
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		public void LogOut(HttpServletRequest request, HttpServletResponse response){
			
			
			HttpSession session = request.getSession(true);
			
			User toBeRemoved = null;
			
			for (User user: activeUsers){
				
				if (user.getUsername().equals(session.getAttribute("username"))){

					toBeRemoved = user;
					break;
				}
			}
			
			if (toBeRemoved != null){
				activeUsers.remove(toBeRemoved);
				
				session.setAttribute("key", "");
				session.setAttribute("username", "");
				session.invalidate();
				
			    String query = ("UPDATE user SET 'key'='' WHERE 'UserName'='"+toBeRemoved.getUsername()+"';");
					//run the query and stor in DB
					db.execute(query);

			}
			try {
				PrintWriter out = response.getWriter();
				out.print("");
				out.flush();
				out.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void NewUser(HttpServletRequest request, HttpServletResponse response){
			
			String username = request.getParameter("username");
		    String password = request.getParameter("password");
		    
		    
		    String query = ("INSERT INTO `user` (`UserName`, `Email`, `Password`, `Cash`) VALUES ('"+username+"', ' ', '"+password+"', '200');");
				//run the query and stor in DB
		    	db.execute(query);
	        
	        try {
				PrintWriter out = response.getWriter();
				out.print("");
				out.flush();
				out.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	        
	        
		}
		
}
