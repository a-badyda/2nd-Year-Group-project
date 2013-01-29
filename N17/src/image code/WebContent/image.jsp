<%@ page import = "java.io.*" %>
<%@ page import = "java.sql.*" %>
<jsp:useBean id="photo" class="ROOT.images" scope="session" />
<%
 

  int iNumPhoto ;
  Connection conn = null;
  
  if ( request.getParameter("ID") != null )
  {
   
    iNumPhoto = Integer.parseInt(request.getParameter("ID")) ;   
  
    try
    {  
    Class.forName("com.mysql.jdbc.Driver");
       conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/monsterdata","root","Samsung2233");
       
  
       
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