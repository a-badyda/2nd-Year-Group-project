//package Java;
package root;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "game", urlPatterns = {"/game"})
public class GameServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	Database db;
	UserManager users;
    
    public GameServer() {
    	db = new Database();
    	db.connect();
    	users = new UserManager();
				
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action = request.getParameter("action");
		
		switch (action){
		
		case "isLoggedIn": IsLoggedIn(request, response); break;
		case "getUserData": GetUserData(request, response); break;
		}
		
		
	}
	
	private void IsLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		if (session.getAttribute("username") != null && session.getAttribute("key") != null){
			
			 out.print("{\"login\":true}");
		} else {
			 out.print("{\"login\":false}");
		}
		
		out.flush();
		out.close();
	}
	
	private void GetUserData(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		HttpSession session = request.getSession(true);
		
		User user=users.fetchUser((String)session.getAttribute("username"));
		
		if (user!=null && user.getKey().equals(session.getAttribute("key"))){

			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.print("{\"isLoggedIn\":\" true\" \"username\": \"" + user.getUsername() + "\", \"key\": \"" + user.getKey() + "\" ,\"cash\": \""+user.getCash() + "\" }");
			out.flush();
			out.close();
		}else{
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.print("{\"isLoggedIn\": \"false\" ");
			out.flush();
			out.close();
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		switch(request.getParameter("action")){
		
		case "login": LogIn(request,response);break;
		case "logout": LogOut(request,response);break;
		case "newuser": NewUser(request,response);break;
		case "addFriend":addFriend(request,response);break;
		case "newBattleRequest":newBattleRequest(request,response);break;
		case "newBreedRequest":newBreedRequest(request,response);break;
		case "getMonsters":getMonsters(request,response);break;
		case "getFriends":getFriends(request,response);break;
		case "getFriendsMonsters":getFriendsMonsters(request,response);break;
		case "getAllRequest":getAllRequest(request,response);break;
		case "acceptRequest":acceptRequest(request,response);break;
		case "declineRequest":declineRequest(request,response);break;
		case "isLoggedIn": IsLoggedIn(request, response); break;
		}
	}
	
		private void LogIn(HttpServletRequest request, HttpServletResponse response){
			
			String username = request.getParameter("username");
		    String password = request.getParameter("password");
			
		    
		    try{
		    	HttpSession session = request.getSession(true);
				
				User user = users.fetchUser((String)session.getAttribute("username"));
				
				session.setAttribute("key", " ");
				session.setAttribute("username", " ");
				session.invalidate();
				
			    String query = ("UPDATE user SET 'key'='' WHERE 'UserName'='"+user.getUsername()+"';");
				db.execute(query);
				
				
				users.removeUser(user);
		    	
		    }catch(Exception e){
		    	
		    	
		    }
		    
		    
			int ID;
			int cash;
			
			String query = ("SELECT * FROM user WHERE UserName='" +username+ "'");
        	
			try {
				PrintWriter out;
			    out = response.getWriter();
			    
	    		ResultSet rset;
	    		rset = db.createQuery (query);
	    		while (rset.next ())
	    		{    
	    			if(rset.getString("password").equalsIgnoreCase(password)){
	    				
	    				Random rand = new Random();
	    				String key = "";
	    				for(int i=0;i<100;i++){
	    					int whatever = rand.nextInt(5);
	    					char temp = (char) whatever;
	    					key += temp;
	    				}
	    				
	    				User user = new User(username,password);
	    				user.setKey(key);
	    				user.setId(rset.getInt("UserID"));
	    				users.addUser(user);
	    				user.setCash(rset.getInt("cash"));
	    				
	    				
	    				HttpSession session = request.getSession(true);
	    				session.setAttribute("key", key);
	    				session.setAttribute("username", user.getUsername());
	    				
	    				
	    			    query = ("UPDATE user SET 'key'='"+key+"' WHERE 'UserName'='"+username+"';");
	 		            //run the query and stor in DB
	 		            db.execute(query);
	    				
	 		            out.print("{\"login\":true}");
	    				out.flush();
	    				out.close();
	 		            
	    				break;
	    			}
        		}
    		} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			User user = users.fetchUser(username);
			
			if(user != null){
				//load freinds
				query = ("SELECT * FROM friends WHERE UserID='" +users.fetchUser(username).getId()+ "'");
				
				try{
					ResultSet rset;
					rset = db.createQuery (query);
					
					while (rset.next ())
		    		{
						User Friend = new User();
						Friend.setId(rset.getInt("friendID"));
						String query2 = ("SELECT * FROM user WHERE UserID='" +Friend.getId()+ "'");
						ResultSet rset2;
						rset2 = db.createQuery (query2);
						
						while (rset2.next())
			    		{
							Friend.setUsername(rset2.getString("UserName"));
							user.addFriends(Friend);
							//load in freinds monsters here
							
							String query3 = ("SELECT * FROM monsters WHERE ownerID='" +Friend.getId()+ "'");
							
							ResultSet rset3;
							rset2 = db.createQuery (query2);
							try {
								while(rset2.next()){
									
									Monster m = new Monster();
									m.setName(rset2.getString("name"));
									m.setId(rset2.getInt("monsterID"));
									m.setOwnerId(rset2.getInt("ownerID"));
									
									Friend.addMonster(m);
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
			    		}
		    		}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				


				//load in your monsters here
				
				String query2 = ("SELECT * FROM monsters WHERE ownerID='" +user.getId()+ "'");
				
				ResultSet rset2;
				rset2 = db.createQuery (query2);
				try {
					while(rset2.next()){
						
						Monster m = new Monster();
						m.setName(rset2.getString("name"));
						m.setId(rset2.getInt("monsterID"));
						m.setOwnerId(rset2.getInt("ownerID"));
						user.addMonster(m);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				// load in notifications
				
				//do a redirect
				//~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~~*~**~~*~*~*~*~*~*~*~*~*~*~*~~*~*~*~*~*~*~*~*~*~*~*~*~*~*~
			}
			else{
				try {
					PrintWriter out = response.getWriter();
					out.print("sorry invalid login");
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			

		}
		
		private void LogOut(HttpServletRequest request, HttpServletResponse response){
			
			
			HttpSession session = request.getSession(true);
			
			User user = users.fetchUser((String)session.getAttribute("username"));
			
			
			session.setAttribute("key", " ");
			session.setAttribute("username", " ");
			session.invalidate();
			
		    String query = ("UPDATE user SET 'key'='' WHERE 'UserName'='"+user.getUsername()+"';");
			db.execute(query);
			
			
			users.removeUser(user);
			
			//redirect
			
		}
		
		private void NewUser(HttpServletRequest request, HttpServletResponse response){
			
			String username = request.getParameter("username");
		    String password = request.getParameter("password");
		    
		    
		    String query = ("INSERT INTO `user` (`UserName`, `Email`, `Password`, `Cash`) VALUES ('"+username+"', ' ', '"+password+"', '200');");
			//run the query and store in DB
		    db.execute(query);
		    
		    
			
		    
			Date date = new Date();
		    SimpleDateFormat ft = 
		    new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		    
		    LogIn(request,response);
		    
			int ID=0;
			try {
				query = ("SELECT * FROM user WHERE UserName='" +(String)request.getParameter("username")+ "'");
				
				ResultSet rset;
	    		rset = db.createQuery (query);
				
				while(rset.next()){
					ID=rset.getInt("UserID");
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		    // give them there first monster
			
			
			
			query = "INSERT INTO `monsterdata`.`monsters` (`ownerID`, `name`, `health`, `strength`, `defence`, `aggression`, `fertility`, `breed`, `status`, `cashPrize`, `wins`, `losses`, `birth`) VALUES ('"+ID+"', 'my first monster', '10', '10', '10', '10', '10', 'BEAST', 'NORMAL', '10', '0', '0', '"+ft.format(date)+"');";
		    
			//run the query and store in DB
		    db.execute(query);
		    
		    try {
				PrintWriter out = response.getWriter();
				out.print("new user created");
				out.print(ft.format(date));
				out.flush();
				out.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void addFriend(HttpServletRequest request, HttpServletResponse response){
			
			HttpSession session = request.getSession(true);
			User user = users.fetchUser((String)session.getAttribute("username"));
			int Friend;
			
			
			try {
				String query = ("SELECT * FROM user WHERE UserName='" +(String)request.getAttribute("username")+ "'");
				Friend = db.createQuery(query).getInt("UserID");
				query = "INSERT INTO `notifications` (`type`, `UserID1`, `UserID2`, `state`) VALUES ('FRIEND', '"+user.getId()+"', '"+Friend+"', 'PENDING')";
				db.execute(query);
		        try {
					PrintWriter out = response.getWriter();
					out.print("added sucsefully");
					out.flush();
					out.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				try {
					PrintWriter out = response.getWriter();
					out.print("error could not add");
					out.flush();
					out.close();
					
				} catch (IOException ex) {
				}
				
			}
		}
		
		private void newBattleRequest(HttpServletRequest request, HttpServletResponse response){
			
			HttpSession session = request.getSession(true);
			User user = users.fetchUser((String)session.getAttribute("username"));
			
			int Friend = (int)request.getAttribute("friendId");
			int FriendMonster = (int)request.getAttribute("monsterId");
			int userMonster = (int)request.getAttribute("userMonsterId");
			
			String query = ("SELECT * FROM user WHERE UserName='" +(String)session.getAttribute("username")+ "'");
			
			try {
				Friend = db.createQuery(query).getInt("UserID");
				query = "INSERT INTO `notifications` (`type`, `UserID1`, `UserID2`, `MonsterID1`, `MonsterID2`, `state`) VALUES ('BATTLE', '"+user.getId()+"', '"+Friend+"', '"+userMonster+"', '"+FriendMonster+"', 'PENDING')";
				db.execute(query);
				
			
				try {
					PrintWriter out = response.getWriter();
					out.print("Request sent");
					out.flush();
					out.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				try {
					PrintWriter out = response.getWriter();
					out.print("Request faild to send");
					out.flush();
					out.close();
					
				} catch (IOException ex) {
				}
				
			}
			
		}

		private void newBreedRequest(HttpServletRequest request, HttpServletResponse response){
			
			HttpSession session = request.getSession(true);
			User user = users.fetchUser((String)session.getAttribute("username"));
			
			int Friend = (int)request.getAttribute("friendId");
			int FriendMonster = (int)request.getAttribute("monsterId");
			int userMonster = (int)request.getAttribute("userMonsterId");
			
			String query = ("SELECT * FROM user WHERE UserName='" +(String)request.getAttribute("username")+ "'");
			
			try {
				Friend = db.createQuery(query).getInt("UserID");
				query = "INSERT INTO `notifications` (`type`, `UserID1`, `UserID2`, `MonsterID1`, `MonsterID2`, `state`) VALUES ('BREED', '"+user.getId()+"', '"+Friend+"', '"+userMonster+"', '"+FriendMonster+"', 'PENDING')";
				db.execute(query);
				
				try {
					PrintWriter out = response.getWriter();
					out.print("Request sent");
					out.flush();
					out.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				try {
					PrintWriter out = response.getWriter();
					out.print("Request faild to send");
					out.flush();
					out.close();
					
				} catch (IOException ex) {
				}
			}
		}

		private void getMonsters(HttpServletRequest request, HttpServletResponse response){
			HttpSession session = request.getSession(true);
			User user = users.fetchUser((String)session.getAttribute("username"));
			ArrayList<Monster> requests = user.getMonsters();
			try {
				PrintWriter out = response.getWriter();
				out.print("{\"Monsters\":[");
				
				for (int i =0 ;i<requests.size();i++){
					
					
					out.print("{\"Name\":\""+requests.get(i).getName()+"\",");
					out.print("\"ID\":\""+requests.get(i).getId()+"\"}");
					
				}
				
				out.print("]}");
				out.flush();
				out.close();
			} catch (IOException ex) {
			}
		}
		private void getFriends(HttpServletRequest request, HttpServletResponse response){
			HttpSession session = request.getSession(true);
			User user = users.fetchUser((String)session.getAttribute("username"));
			ArrayList<User> requests = user.getFriends();
			try {
				PrintWriter out = response.getWriter();
				out.print("{\"Friends\":[");
				
				for (int i =0 ;i<requests.size();i++){
					out.print("{\"username\":\""+requests.get(i).getUsername()+"\",");
					out.print("\"id\":\""+requests.get(i).getId()+"\",");
					out.print("\"ServerAddress\":\""+requests.get(i).getServerAdd()+"\"}");	
					
					if(i<requests.size()-1){
						out.print(",");
					}
				}
				
				out.print("]}");
				out.flush();
				out.close();
			} catch (IOException ex) {
			}
		}
		private void getFriendsMonsters(HttpServletRequest request, HttpServletResponse response){
			HttpSession session = request.getSession(true);
			User user = users.fetchUser((String)session.getAttribute("username"));
			ArrayList<User> requests1 = user.getFriends();
			ArrayList<Monster> requests = user.getFriendsMonsters((int)session.getAttribute("FriendID"));
			try {
				PrintWriter out = response.getWriter();
				out.print("{\"FreindsMonsters\":\"[\"");
				
				for (int i =0 ;i<requests.size();i++){
					
					out.print("{\"Name\":\""+requests.get(i).getName()+"\"");
					out.print("{\"ID\":\""+requests.get(i).getId()+"\"");
				}
				
				out.print("\"]}\"");
				out.flush();
				out.close();
				
				
				
				
			} catch (IOException ex) {
			}
		}
		private void getAllRequest(HttpServletRequest request, HttpServletResponse response){
			
			HttpSession session = request.getSession(true);
			User user = users.fetchUser((String)session.getAttribute("username"));
			ArrayList<Request> requests = user.getRequests();
			try {
				PrintWriter out = response.getWriter();
				out.print("{\"Notifications\":\"[\"");
				
				for (int i =0 ;i<requests.size();i++){
					
					
					out.print("{\"Type\":\""+requests.get(i).getType()+"\"");
					out.print("{\"ID\":\""+requests.get(i).getId()+"\"");
					out.print("{\"From\":\""+requests.get(i).getFrom()+"\"");
					
				}
				
				out.print("\"]}\"");
				out.flush();
				out.close();
				
			} catch (IOException ex) {
			}
		
		}
		
		private void acceptRequest(HttpServletRequest request, HttpServletResponse response){
			HttpSession session = request.getSession(true);
			User user = users.fetchUser((String)session.getAttribute("username"));
			Request r =	user.getRequests().get((int)session.getAttribute("ID"));
			
			
			switch(r.getType()){
			
				case BATTLE:acceptBattleRequest(request,response);
				break;
				
				case BREED:acceptBreedRequest(request,response);
				break;
				
				case FRIEND:acceptFriendRequest(request,response);
				break;
			}
		}
		private void acceptBattleRequest(HttpServletRequest request, HttpServletResponse response){
			
		}
		private void acceptBreedRequest(HttpServletRequest request, HttpServletResponse response){
			
		}
		private void acceptFriendRequest(HttpServletRequest request, HttpServletResponse response){
			HttpSession session = request.getSession(true);
			User user = users.fetchUser((String)session.getAttribute("username"));
			
			
			String query = ("DELETE FROM `notifications` WHERE `ID`='"+(int)request.getAttribute("id")+"'");
			String query1 ="INSERT INTO `friends` (`userID`, `friendID`) VALUES ('"+user.getId()+"', '"+(int)session.getAttribute("FriendID")+"')";
			String query2 ="INSERT INTO `friends` (`userID`, `friendID`) VALUES ('"+(int)session.getAttribute("FriendID")+"', '"+user.getId()+"')";
			
			

			db.execute(query);
			db.execute(query1);
			db.execute(query2);
			
			
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("friend added");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		private void declineRequest(HttpServletRequest request, HttpServletResponse response){
			String query = ("DELETE FROM `notifications` WHERE `ID`='"+(int)request.getAttribute("id")+"'");
			db.execute(query);
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("request declined");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
}
