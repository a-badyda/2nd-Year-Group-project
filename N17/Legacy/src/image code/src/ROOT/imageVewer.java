package ROOT;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class imageVewer
 */
@WebServlet(name = "imageVewer", urlPatterns = {"/imageVewer"})
public class imageVewer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public imageVewer() {
        super();
       
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();

   		
   		Connection conn;
   		Statement stmt;
   	    /**
   	     * Default constructor. 
   	     */
   	    
	      // JDBC driver name and database URL
	      // final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
	       final String DB_URL="jdbc:mysql://localhost:3306/monsterdata";
	       
	       //  Database credentials
	       final String USER = "root";
	       final String PASS = "Samsung2233";
	                
	          // Register JDBC driver
	          
			try {
				Class.forName("com.mysql.jdbc.Driver");
				// Open a connection
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				System.out.println("yay made a connection");
				  
				// Execute SQL query
				stmt = conn.createStatement();
				String sql;
				
				sql = "SELECT ID,imageName FROM images";
				ResultSet rs = stmt.executeQuery(sql);
	            
				out.println("<table>");
				out.println("<tr>");
				out.println("<td>  id  </td>");
				out.println("<td> Name </td>");
				out.println("<td> link </td>");
				out.println("</tr>");
				
				
				while(rs.next()){
					int id  = rs.getInt("ID");
					String Name = rs.getString("imageName");
					
					//Display values
					
					
					out.println("<tr>");
					out.println("<td>" + id + "</td>");
					out.println("<td>" + Name + "</td>");
					out.println("<td> <a href=\"http://www.monstermashgame.co.uk/image_DB_admin/image.jsp?ID="+id+"\">link</a> </td>");
					out.println("</tr>");
					
				}
				out.println("</table>");
				
				
				
		        
	          } catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				out.println(e.getMessage());
		        
			}
		
		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
