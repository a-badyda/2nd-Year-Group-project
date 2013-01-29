package Java.src.root;

import java.sql.*;
import java.util.ArrayList;

public class Database {
	
	Connection conn;

	Database(){
		
	}
	
	public void connect(){
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
	
	public ResultSet createQuery(String q){
		try {
			Statement st = conn.createStatement();
			return st.executeQuery(q);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public void execute(String q){
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(q);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
}
