package classes;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserData {
    int age;
	String username;
	String email;
    String password;
    String firstName;
    String lastName;
    
    
    
    String login;
    
    
    
    
    Connection conn;
    int len;
    String query;
    PreparedStatement pstmt;
    
    public UserData(){
    	
    	
    	// JDBC driver name and database URL
        // final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
         final String DB_URL="jdbc:mysql://localhost:3306/setionslookup";
         
         //  Database credentials
         final String USER = "root";
         final String PASS = "password";
        
            // Register JDBC driver
 			try {
				Class.forName("com.mysql.jdbc.Driver");
            // Open a connection
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
            } catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    }
    
    

    public void setUsername( String value )
    {
        username = value;
    }

    public void setPassword( String value )
    {
        password = value;
    }

    public void setAge( int value )
    {
        age = value;
    }

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	
	public String getEmail() {
		if(logedin()){
			return email;
		}
		else
		{
			return null;
		}
	}
	
	public String getFirstName() {
		if(logedin()){
			return firstName;
		}
		else
		{
			return null;
		}
	}
	
	public String getLastName() {
		if(logedin()){
			return lastName;
		}
		else
		{
			return null;
		}
	}

	public String getUsername() {
		if(logedin()){
			return username; 
		}
		else
		{
			return null;
		}
	}

    public String getPassword() {
    	if(logedin()){
    		return password; 
    	}
    	else{
    		return null;
    	}
    }

    public int getAge() {
    	if(logedin()){
    		return age; 
    	}
    	else{
    		return 0;
    	}
    }
    
    
    public Boolean logedin(){
    	   // Register JDBC driver
            try {
            Statement stmt;
            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT userName, email, password, age,firstName,lastName FROM data";
            ResultSet rs = stmt.executeQuery(sql);
            
           
            while(rs.next()){
    	
		    	
		    	String user = rs.getString("userName");
		    	String pass = rs.getString("password");
		    	
		    	
		    	if((user.equalsIgnoreCase(username)&&(pass.equalsIgnoreCase(password)))){
		    		
		    		age= rs.getInt("age");
		    		email= rs.getString("email");
		    	    firstName= rs.getString("firstName");
		    	    lastName= rs.getString("lastName");
		    		
		    		return true;
		    	} else {
		    		
		    		age = 0;
		    		email= "";
		    	    firstName= "";
		    	    lastName= "";
		    	}
		    	
            }
            return false;
            
            
            
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return false;
    }

    
    public String newuser(){
    	
    	try {
            Statement stmt;
            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            sql = "INSERT INTO data (age, userName, email, password, firstName, lastName) VALUES ( '" + age + "' , '"+username+"', '"+email+"', '"+password+"', '"+firstName+"', '"+lastName+"')";
            stmt.executeUpdate(sql);
            
            
            
            
            
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return "done";
    }
}





//while(rs.next()){
//
//int id  = rs.getInt("setionID");
//int age = rs.getInt("age");
//String first = rs.getString("name");
//String last = rs.getString("email");
//
////Display values
//out.println("ID: " + id + "<br>");
//out.println(", Age: " + age + "<br>");
//out.println(", First: " + first + "<br>");
//out.println(", Last: " + last + "<br>");
//}