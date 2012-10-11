<%@ page import = "java.io.*" %>
<%@ page import = "java.sql.*" %>
<jsp:useBean id="photo" class="classes.images" scope="session" />
<%
  //the variable for image ID in the sql server 
  int iNumPhoto ;
  //the variable for connection to the SQL server 
  Connection conn = null;
  //if the ID value is set to something 
  if ( request.getParameter("imgID") != null )
  {
   // get the parameter"imgID" and cram it into the variable 
   // the parameter "imgID" is the bit after the ? 
   // so in this case image.jsp?imgID=<what goes in here will be put into the ID variable>
    iNumPhoto = Integer.parseInt(request.getParameter("imgID")) ;   
  
    try
    {  
    Class.forName("com.mysql.jdbc.Driver");
       conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/setionslookup","root","password");
       
  
       
       byte[] imgData = photo.getPhoto( conn, iNumPhoto) ;   
       
       response.setContentType("image/gif");
       OutputStream o = response.getOutputStream();
       o.write(imgData);
       o.flush(); 
       o.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw e;
    }
     
  }
%>