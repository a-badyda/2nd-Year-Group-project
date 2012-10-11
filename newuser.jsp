<jsp:useBean id="user" class="classes.UserData" scope="session"/>
<jsp:setProperty name="user" property="username"/> 
<jsp:setProperty name="user" property="password"/> 
<jsp:setProperty name="user" property="age"/> 
<jsp:setProperty name="user" property="email"/> 
<jsp:setProperty name="user" property="firstName"/> 
<jsp:setProperty name="user" property="lastName"/> 
<%= user.newuser()%>