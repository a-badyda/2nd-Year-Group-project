/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tux
 */
@WebServlet(name = "imageUploader", urlPatterns = {"/imageUploader"})
public class imageUploader extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // JDBC driver name and database URL
       // final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
        final String DB_URL="jdbc:mysql://localhost:3306/setionslookup";
        
        //  Database credentials
        final String USER = "root";
        final String PASS = "password";
       
         int len;
           String query;
           PreparedStatement pstmt;
           
           // Register JDBC driver
           try {
			Class.forName("com.mysql.jdbc.Driver");
		
           // Open a connection
           Connection conn;
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           System.out.println("yay made a connection");
           Statement stmt;
           // Execute SQL query
           stmt = conn.createStatement();
           String sql;
           sql = "SELECT setionID, name, email, age FROM data";
           ResultSet rs = stmt.executeQuery(sql);
        
           File file = new File("C:/xampp/tomcat/webapps/Dropbox/play/WebContent/WEB-INF/tiger.jpg");
           FileInputStream fis = new FileInputStream(file);
           len = (int)file.length();

           query = ("insert into profilePic VALUES(?,?,?)");
           pstmt = conn.prepareStatement(query);
           pstmt.setString(1,file.getName());
           pstmt.setInt(2, len);

           // Method used to insert a stream of bytes
           pstmt.setBinaryStream(3, fis, len); 
           pstmt.executeUpdate();
           
           } catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
