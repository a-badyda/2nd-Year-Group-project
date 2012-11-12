package root;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class game extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private Connection conn;
    
    public ArrayList<User> activeUsers;
    
    
    public game() {
    	
		// JDBC driver name and database URL
       final String DB_URL="jdbc:mysql://localhost:3306/monsterdata";
       //  Database credentials
       final String USER = "root";
       final String PASS = "Samsung2233";
       activeUsers = new ArrayList<User>();
       
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        // Open a connection
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);
	        
	        
	        System.out.println("yay made a connection");
          } catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
				
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
			
			if (user.username.equals(session.getAttribute("username")) && user.key.equals(session.getAttribute("key"))){

				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.print("{\"username\": \"" + user.username + "\", \"key\": \"" + user.key + "\" ,\"cash\": \""+user.cash + "\" }");
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
        	
    		Statement stmt;
			try {
				stmt = conn.createStatement ();
			
	    		ResultSet rset;
	    		rset = stmt.executeQuery ( query );
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
	    				user.SetKey(key);
	    				activeUsers.add(user);
	    				user.SetCash(rset.getInt("cash")); 
	    				
	    				HttpSession session = request.getSession(true);
	    				session.setAttribute("key", key);
	    				session.setAttribute("username", user.username);
	    				
	    				
	    				PreparedStatement pstmt;
	    			    query = ("UPDATE user SET 'key'='"+key+"' WHERE 'UserName'='"+username+"';");
	 		            pstmt = conn.prepareStatement(query);
	 		            //run the query and stor in DB
	 		            pstmt.executeUpdate();
	    				
	 		            
	    				
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
				
				if (user.username.equals(session.getAttribute("username"))){

					toBeRemoved = user;
					break;
				}
			}
			
			if (toBeRemoved != null){
				activeUsers.remove(toBeRemoved);
				
				session.setAttribute("key", "");
				session.setAttribute("username", "");
				session.invalidate();
				
				PreparedStatement pstmt;
			    String query = ("UPDATE user SET 'key'='' WHERE 'UserName'='"+toBeRemoved.username+"';");
		        try {
					pstmt = conn.prepareStatement(query);
					//run the query and stor in DB
					pstmt.executeUpdate();
		        } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		    
		    
		    PreparedStatement pstmt;
		    String query = ("INSERT INTO `user` (`UserName`, `Email`, `Password`, `Cash`) VALUES ('"+username+"', ' ', '"+password+"', '200');");
	        try {
				pstmt = conn.prepareStatement(query);
				//run the query and stor in DB
				pstmt.executeUpdate();
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
		
}
