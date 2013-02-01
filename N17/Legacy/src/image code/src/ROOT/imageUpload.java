package ROOT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class imageUplaod
 */
@WebServlet(name = "imageUpload", urlPatterns = {"/imageUpload"})
@MultipartConfig
public class imageUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	Connection conn;

    /**
     * Default constructor. 
     */
    public imageUpload() {

      // JDBC driver name and database URL
       final String DB_URL="jdbc:mysql://localhost:3306/monsterdata";
       
       //  Database credentials
       final String USER = "root";
       final String PASS = "Samsung2233";
                
          
          
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
	        // Open a connection
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);
	        System.out.println("yay made a connection");
	        
          } catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//open the output writer
		PrintWriter out = response.getWriter();
		
		//get the collection of parts from the multi part data.
		 Collection<Part> parts = request.getParts();
	      if (parts.size() != 2) {
	         //can write error page saying all details are not entered
	    	  
 			out.println(parts.size());
	       }
	      
	      //get the part with name image and cram the contence into filePart
	       Part filePart = request.getPart("image");
	       InputStream imageInputStream = filePart.getInputStream();
	       //read imageInputStream
	       filePart.write("C:/imgtemp/tempimg.jpg");
	       //write the image to local temp storage

	       //get the part with name name and cram the contence into namePart
	       Part namePart = request.getPart("name");
	       InputStream nameInputStream = namePart.getInputStream();
	       //name , String type can also obtained using Request parameter
	       String nameParameter = request.getParameter("name");


	       
       		
	       //prepare the DB connection
       		Connection conn = null;
       		
   	       // JDBC driver name and database URL
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
					  
					
			        //load in the temp file
			        File file = new File("C:/imgtemp/tempimg.jpg");
			        FileInputStream fis = new FileInputStream(file);
					
					// prepare statment
					PreparedStatement pstmt;
		
		           int len = (int)file.length();
		           //set up query
		           String query = ("insert into images VALUES(ID,?,'"+nameParameter+"')");
		           pstmt = conn.prepareStatement(query);
		           
		
		           // Method used to insert a stream of bytes into remaingin ? 
		           pstmt.setBinaryStream(1, fis, len); 
		           //run the query and stor in DB
		           pstmt.executeUpdate();
					
					
					
					
   		        
   	          } catch (SQLException | ClassNotFoundException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   				
   				out.println(e.getMessage());
   		        
   			}
       			
       	    	
       	    
       
			
   			out.println("well that should be it");
            out.close();

            
	}

	
	
	
	
}
