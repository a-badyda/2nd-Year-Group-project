//package Java;
package root;
import java.sql.*;

/**
 * A MySQL Database, it can validate and process data faster 
 * and more efficiently than the Java classes would.
 */

public class Database {
	
	Connection conn;
	
	/**
	 * Connects to the database located in the URL and logs in with
	 * user information that is already given.
	 */
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
	
	/**
	 * Creates a new SQL statement and executes the query given by the server.
	 * @param query The SQL statement that is sent to the database.
	 * @return Data that was produced by the query.
	 */
	public ResultSet query(String query){
		try {
			Statement st = conn.createStatement();
			return st.executeQuery(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Changes information in the database in a parameter=value format.
	 * @param query The parameterised SQL statement that is sent to the database.
	 */
	public void update(String query){
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
