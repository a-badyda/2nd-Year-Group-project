package ROOT;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.sql.*;


public class images
{
  
  public static byte[] getPhoto (Connection conn, int iNumPhoto)
       throws Exception, SQLException
  {
    String req = "" ;
    Blob img ;
    byte[] imgData = null ;
    Statement stmt = conn.createStatement ();
    
    // Query
    req = "Select data From images Where ID = " + iNumPhoto ;
    
    ResultSet rset  = stmt.executeQuery ( req ); 
    
    while (rset.next ())
    {    
      img = rset.getBlob(1);
      imgData = img.getBytes(1,(int)img.length());
    }    
    
    rset.close();
    stmt.close();
    
    return imgData ;
  }
  
  
  
  
  
  
  
  
  
} 