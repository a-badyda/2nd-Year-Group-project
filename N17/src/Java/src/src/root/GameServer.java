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
    Breeding breeding;
    Battle battle;
	
    public GameServer() {
    	db = new Database();
    	db.connect();
    	users = new UserManager();
    	breeding = new Breeding();
    	battle = new Battle();
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
			out.print("{\"isLoggedIn\": true, \"username\": \"" + user.getUsername() + "\", \"cash\": \""+user.getCash() + "\" }");
			out.flush();
			out.close();
		}else{
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.print("{\"isLoggedIn\": false }");
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
		case "newBuyRequest":newBuyRequest(request,response);break;
		case "getMonsters":getMonsters(request,response);break;
		case "getFriends":getFriends(request,response);break;
		case "getFriendsMonsters":getFriendsMonsters(request,response);break;
		case "getAllNotifications":getAllRequest(request,response);break;
		case "acceptRequest":acceptRequest(request,response);break;
		case "declineRequest":declineRequest(request,response);break;
		case "isLoggedIn": IsLoggedIn(request, response); break;
		case "getUserData": GetUserData(request, response); break;
		
		
		
		
		case "setBuyCost": setBuyCost(request, response); break;
		case "setBreedCost": setBreedCost(request, response); break;
		
		case "getRichList": getRichList(request, response); break;
		
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
    				user.setCash(rset.getInt("cash"));
    				users.addUser(user);
    				
    				
    				
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
						Friend.setCash(rset2.getInt("cash"));
						user.addFriend(Friend);
						//load in freinds monsters here
						
						String query3 = ("SELECT * FROM monsters WHERE ownerID='" +Friend.getId()+ "'");
						
						ResultSet rset3;
						rset3 = db.createQuery(query3);
						try {
							while(rset3.next()){
								
								Monster m = new Monster();
								m.setName(rset3.getString("name"));
								m.setId(rset3.getInt("monsterID"));
								m.setOwnerId(rset3.getInt("ownerID"));
								m.setHealth(rset3.getInt("health"));
								m.setAggression(rset3.getInt("aggression"));
								m.setStrength(rset3.getInt("strength"));
								m.setDefence(rset3.getInt("defence"));
								m.setFertility(rset3.getInt("fertility"));
								m.setWins(rset3.getInt("wins"));
								m.setLosses(rset3.getInt("losses"));
								m.setCashBreed(rset3.getInt("cashbreed"));
								m.setCashPrize(rset3.getInt("cashprize"));
								m.setCashSell(rset3.getInt("cashSell"));
								
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
					m.setHealth(rset2.getInt("health"));
					m.setAggression(rset2.getInt("aggression"));
					m.setStrength(rset2.getInt("strength"));
					m.setDefence(rset2.getInt("defence"));
					m.setFertility(rset2.getInt("fertility"));
					m.setWins(rset2.getInt("wins"));
					m.setLosses(rset2.getInt("losses"));
					m.setCashBreed(rset2.getInt("cashbreed"));
					m.setCashPrize(rset2.getInt("cashprize"));
					m.setCashSell(rset2.getInt("cashSell"));
					
					user.addMonster(m);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			// load in notifications
			String query3 = ("SELECT * FROM notifications WHERE UserID1='" +user.getId()+ "'");
			
			ResultSet rset3;
			rset3 = db.createQuery (query3);
			ArrayList<Request> requests = new ArrayList<Request>();
			try {
				while(rset3.next()){
					
					int u1=Integer.parseInt(rset3.getString("UserID1"));
					int u2=Integer.parseInt(rset3.getString("UserID2"));
					
					int m1=Integer.parseInt(rset3.getString("MonsterID1"));
					int m2=Integer.parseInt(rset3.getString("MonsterID2"));
					
					
					Request r = new Request(u1, u2, m1, m2,RequestType.valueOf(rset3.getString("type")));
					r.setId(rset3.getInt("ID"));
					r.setCash(rset3.getInt("cash"));
					r.setOutcome(rset3.getString("outcome"));
					requests.add(r);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			user.setRequests(requests);
			
			
			
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
		
		
		try {
			int Friend=0;
			String query = ("SELECT * FROM user WHERE UserName='" +(String)request.getParameter("username")+ "'");
			
			ResultSet rset;
			rset = db.createQuery(query);
			while(rset.next()){
				Friend = rset.getInt("UserID");
			}
			
			query = "INSERT INTO notifications (type, UserID1, UserID2, MonsterID1, MonsterID2, state) VALUES ('friend_request', '"+Friend+"', '"+user.getId()+"', 0, 0, 'PENDING')";
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
		
		
		int Friend = Integer.parseInt(request.getParameter("friendId"));
		int FriendMonster = Integer.parseInt(request.getParameter("monsterId"));
		int userMonster = Integer.parseInt(request.getParameter("userMonsterId"));
		
		try {
			
			
			String query = "INSERT INTO notifications (type, UserID1, UserID2, MonsterID1, MonsterID2, state) VALUES ('battle_request', '"+Friend+"', '"+user.getId()+"', '"+userMonster+"', '"+FriendMonster+"', 'PENDING')";
			db.execute(query);
			
		
			
			PrintWriter out = response.getWriter();
			out.print("Request sent");
			out.flush();
			out.close();
				
			
		} catch (IOException e) {
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
		try {
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		
		int Friend = (int)request.getAttribute("friendId");
		int FriendMonster = (int)request.getAttribute("monsterId");
		int userMonster = (int)request.getAttribute("userMonsterId");
		
		
		User u1=users.fetchUser(user.getId());
		User u2=users.fetchUser((int)request.getAttribute("friendId"));
		
		Monster m1=u1.getMonster((int)request.getAttribute("userMonsterId"));
		Monster m2=u2.getMonster((int)request.getAttribute("monsterId"));
		
		String query ="INSERT INTO 'result'('type','userID1','userID2','monsterID1','monsterID2','userwon','monsterwon','winmessage','lostmessage','cash','baby1','baby2','baby3','baby4','baby5','baby6','baby7','baby8','baby9','baby10')" +
				"VALUES('battle_results','"+u1.getId()+"','"+u2.getId()+"','"+m1.getId()+"','"+m2.getId()+"','"+u1.getId()+"','"+u1.getId()+"','congratulations you have baby monsters','some one breeded with your monster','"+m2.getCashBreed()+"','11899','NULL','NULL','NULL','NULL','NULL','NULL','NULL','NULL','NULL'";
		db.execute(query);
		
		
		ArrayList<String> querylist = breeding.Breed(u1,u2,m1,m2);
		
		
		String query2 = ("SELECT * FROM 'result' WHERE baby1='11899'");
	    int resultID=0;
	    ResultSet rset;
		rset = db.createQuery (query2);
		
			while (rset.next ())
			{ 
				resultID=rset.getInt("ID");
			}
			query2="UPDATE 'result' SET 'baby1'='null' WHERE 'ID'='"+resultID+"'";
			db.execute(query2);
			
			for (int i=0; i<querylist.size();i++){
				db.execute(querylist.get(i));
			}
			
			query2 = ("SELECT * FROM monster WHERE breed='11899'");
		    
			rset = db.createQuery (query2);
			int count =1;
			int babies[]=new int[10];
			while (rset.next ())
			{ 
				count++;
				babies[count-1]=rset.getInt("ID");
			}
			
			for(int i=1;i<count;i++){
				query="UPDATE 'result' SET 'baby"+i+"'='"+babies[i-1]+"' WHERE 'ID'='"+resultID+"'";
				db.execute(query);
				
				query="UPDATE 'monster' SET 'breed'='BEAST' WHERE 'ID'='"+babies[i-1]+"'";
				db.execute(query);
				
			}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//responce (monster array babies,stats) amount_paid: 
		
	}
	private void newBuyRequest(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		
		for(int i=0;i<user.getFriends().size();i++){
			if(user.getFriends().get(i).getId()==(int)request.getAttribute("friendId")){
				
				
				for(int j=0;j<user.getFriends().get(i).getMonsters().size();j++){
					if(user.getFriends().get(i).getMonsters().get(j).getId()==(int)request.getAttribute("monsterId")){
						user.setCash(user.getCash()+user.getFriends().get(i).getMonsters().get(j).getCashSell());
						user.getFriends().get(i).getMonsters().remove(j);
						//result
						String query="INSERT INTO 'result'('type','userID1','userID2','monsterID1','monsterID2','userwon','monsterwon','winmessage','lostmessage','cash','baby1','baby2','baby3','baby4','baby5','baby6','baby7','baby8','baby9','baby10')" +
								"VALUES('battle_results','"+user.getId()+"','','"+(int)request.getAttribute("monsterId")+"','','"+user.getId()+"','','congratulations you just bought a new monster','"+user.getCash()+user.getFriends().get(i).getMonsters().get(j).getCashSell()+"','NULL','NULL','NULL','NULL','NULL','NULL','NULL','NULL','NULL','NULL'";
						db.execute(query);	
						break;
					}
				}
				
				String query="UPDATE 'monsters' SET 'ownerID'='"+user.getId()+"' WHERE 'monsterID'='"+(int)request.getAttribute("monsterId")+"'";
				
				db.execute(query);
				
				//notification
				query="INSERT INTO `notifications` (`type`, `UserID1`, `UserID2`, `state`) VALUES ('buy_result', '"+user.getId()+"', '"+(int)request.getAttribute("friendId")+"', 'PENDING')";
				db.execute(query);
				
			}
		}
		user.getMonsters().clear();
		String query2 = ("SELECT * FROM monsters WHERE ownerID='" +user.getId()+ "'");
		
		ResultSet rset2;
		rset2 = db.createQuery (query2);
		try {
			while(rset2.next()){
				
				Monster m = new Monster();
				m.setName(rset2.getString("name"));
				m.setId(rset2.getInt("monsterID"));
				m.setOwnerId(rset2.getInt("ownerID"));
				m.setHealth(rset2.getInt("health"));
				m.setAggression(rset2.getInt("aggression"));
				m.setStrength(rset2.getInt("strength"));
				m.setDefence(rset2.getInt("defence"));
				m.setFertility(rset2.getInt("fertility"));
				m.setWins(rset2.getInt("wins"));
				m.setLosses(rset2.getInt("losses"));
				m.setCashBreed(rset2.getInt("cashbreed"));
				m.setCashPrize(rset2.getInt("cashprize"));
				m.setCashSell(rset2.getInt("cashSell"));
				
				user.addMonster(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//responce monster   amount_paid:
	}
	
	
	private void getMonsters(HttpServletRequest request, HttpServletResponse response){
		reloadmonsters(request,response);
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		ArrayList<Monster> requests = user.getMonsters();
		try {
			PrintWriter out = response.getWriter();
			out.print("{\"Monsters\":[");
			
			for (int i =0 ;i<requests.size();i++){
				
				String buy;
				String breed;
				if(requests.get(i).getCashSell()>0){buy="ture";}else{buy="false";}
				if(requests.get(i).getCashBreed()>0){breed="ture";}else{breed="false";}
				
				
				out.print("{\"monstername\":\""+requests.get(i).getName()+"\",");
				out.print("\"ID\":\""+requests.get(i).getId()+"\",");
				out.print("\"buy\":\""+buy+"\",");
				out.print("\"strength\":\""+requests.get(i).getStrength()+"\",");
				out.print("\"health\":\""+requests.get(i).getHealth()+"\",");
				out.print("\"fertility\":\""+requests.get(i).getFertility()+"\",");
				out.print("\"defense\":\""+requests.get(i).getDefence()+"\",");
				out.print("\"aggression\":\""+requests.get(i).getAggression()+"\",");
				out.print("\"breed\":\""+breed+"\"}");
				
				
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
	private void getFriends(HttpServletRequest request, HttpServletResponse response){
		reloadfreinds(request,response);
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
		reloadfreinds(request,response);
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		
		ArrayList<Monster> requests = users.fetchFriendsMonstersFromDatabase(Integer.parseInt(request.getParameter("friend_id")));
		
				
		
		try {
			PrintWriter out = response.getWriter();
			out.print("{\"friend_id\":"+Integer.parseInt(request.getParameter("friend_id"))+", \"Monsters\":[");
			
			for (int i =0 ;i<requests.size();i++){
				
				String buy;
				String breed;
				if(requests.get(i).getCashSell()>0){buy="'ture'";}else{buy="'false'";}
				if(requests.get(i).getCashBreed()>0){breed="'ture'";}else{breed="'false'";}
				
				
				out.print("{\"monstername\":\""+requests.get(i).getName()+"\",");
				out.print("\"ID\":\""+requests.get(i).getId()+"\",");
				out.print("\"buy\":\""+buy+"\",");
				out.print("\"strength\":\""+requests.get(i).getStrength()+"\",");
				out.print("\"health\":\""+requests.get(i).getHealth()+"\",");
				out.print("\"fertility\":\""+requests.get(i).getFertility()+"\",");
				out.print("\"defecnce\":\""+requests.get(i).getDefence()+"\",");
				out.print("\"aggrestion\":\""+requests.get(i).getAggression()+"\",");
				out.print("\"breed\":\""+breed+"\"}");
				
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
	
	private void getAllResults(HttpServletRequest request, HttpServletResponse response){
		
		
		
		try{
			PrintWriter out;
		
			out = response.getWriter();
		
			out.print("{\"Results\":[");
	    	HttpSession session = request.getSession(true);
			
			User user = users.fetchUser((String)session.getAttribute("username"));
			
		    String query = ("SELECT * FROM result WHERE userID1='"+user.getId()+"'");
		    
		    ResultSet rset;
    		rset = db.createQuery (query);
    		while (rset.next ())
    		{ 
    			
    			out.print("{\"Type\":\""+rset.getString("type")+"\",");
				out.print("\"ID\":\""+rset.getInt("ID")+"\",");
				out.print("\"From\":\""+users.fetchUser(rset.getInt("userID2")).getUsername()+"\"");
				
				
				String query2 = ("SELECT * FROM monsters WHERE monsterID='" +rset.getInt("monsterID1")+ "'");
				
				ResultSet rset2;
				rset2 = db.createQuery(query2);
				
			
				while (rset2.next ())
				{
					out.print("{\"my_monster\":[");
					out.print("{\"monstername\":\""+rset2.getString("name")+"\",");
					out.print("\"ID\":\""+rset2.getInt("monsterID")+"\",");
					out.print("\"strength\":\""+rset2.getInt("strength")+"\",");
					out.print("\"health\":\""+rset2.getInt("health")+"\",");
					out.print("\"fertility\":\""+rset2.getInt("fertility")+"\",");
					out.print("\"defense\":\""+rset2.getInt("defence")+"\",");
					out.print("\"aggression\":\""+rset2.getInt("aggression")+"\",");
					out.print("]}");
				}
			
				query2 = ("SELECT * FROM monsters WHERE monsterID='" +rset.getInt("monsterID2")+ "'");
				
				rset2 = db.createQuery (query2);
				
				while (rset2.next())
	    		{
					out.print("{\"friend_monster\":[");
					out.print("{\"monstername\":\""+rset2.getString("name")+"\",");
					out.print("\"ID\":\""+rset2.getInt("monsterID")+"\",");
					out.print("\"strength\":\""+rset2.getInt("strength")+"\",");
					out.print("\"health\":\""+rset2.getInt("health")+"\",");
					out.print("\"fertility\":\""+rset2.getInt("fertility")+"\",");
					out.print("\"defense\":\""+rset2.getInt("defence")+"\",");
					out.print("\"aggression\":\""+rset2.getInt("aggression")+"\",");
					out.print("]}");
	    		}
			
    			
				if(user.getId()==rset.getInt("userWon")){
					out.print("\"message\":\""+rset2.getString("winmessage")+"\",");
					out.print("\"cash\":\""+rset2.getInt("cash")+"\",");
				}else{
					out.print("\"message\":\""+rset2.getString("lostmessage")+"\",");
					out.print("\"cash\":\""+(0-rset2.getInt("cash"))+"\",");
				}
				
				out.print("\"babies\": [");
				for (int i =0; i<10;i++){
					query2 = ("SELECT * FROM monsters WHERE monsterID='" +rset.getInt("baby"+i)+ "'");
					
					rset2 = db.createQuery (query2);
					
					while (rset2.next())
		    		{
						out.print("{\"monstername\":\""+rset2.getString("name")+"\",");
						out.print("\"ID\":\""+rset2.getInt("monsterID")+"\",");
						out.print("\"strength\":\""+rset2.getInt("strength")+"\",");
						out.print("\"health\":\""+rset2.getInt("health")+"\",");
						out.print("\"fertility\":\""+rset2.getInt("fertility")+"\",");
						out.print("\"defense\":\""+rset2.getInt("defence")+"\",");
						out.print("\"aggression\":\""+rset2.getInt("aggression")+"\"");
						out.print("}");
		    		}
					
				}
				out.print("]");
    		}
	    }catch(Exception e){
	    	
	    	
	    }
		
	}
	
	private void getAllRequest(HttpServletRequest request, HttpServletResponse response){
		reloadnotifications(request,response);
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		ArrayList<Request> requests = user.getRequests();
		try {
			PrintWriter out = response.getWriter();
			out.print("{\"Notifications\":[");
			
			for (int i =0 ;i<requests.size();i++){
				
				
				String query3 = ("SELECT * FROM user WHERE UserID='" +requests.get(i).getFrom()+ "'");
				
				ResultSet rset3;
				rset3 = db.createQuery (query3);
				String fromname = null;
				while(rset3.next()){
					fromname=rset3.getString("UserName");
				}
				out.print("{\"Type\":\""+requests.get(i).getType()+"\",");
				out.print("\"ID\":\""+requests.get(i).getId()+"\",");
				out.print("\"From\":\""+fromname+"\"}");
				
				if(i<requests.size()-1){
					out.print(",");
				}
			}
			
			out.print("]}");
			out.flush();
			out.close();
			
		} catch (IOException | SQLException ex) {
			
		}
	
	}
	
	private void acceptRequest(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		int id = Integer.parseInt(request.getParameter("id"));
		
		Request r = null;
		for(int i=0;i< user.getRequests().size();i++){
			 r = user.getRequests().get(i);
			if(r.getId()==id){
				break;
			}
		}
		
		switch(r.getType()){
		
			case battle_request:acceptBattleRequest(request,response,r);
			break;
			
			case friend_request:acceptFriendRequest(request,response,r);
			break;
		}
	}
	private void acceptBattleRequest(HttpServletRequest request, HttpServletResponse response,Request r){
		int requestid = r.getId();
		
		String query = ("SELECT * FROM notifications WHERE ID='" +requestid+ "'");
		
		try {
				ResultSet rset;
				rset = db.createQuery (query);
				while (rset.next ())
				{
					
					
					User u1=users.fetchUserFromDatabase(rset.getInt("UserID1"));
					User u2=users.fetchUserFromDatabase(rset.getInt("UserID2"));
					
					Monster m1=users.fetchMonsterFromDatabase(rset.getInt("MonsterID1"));
					Monster m2=users.fetchMonsterFromDatabase(rset.getInt("MonsterID2"));
					
					
					ArrayList<String> querylist = battle.doBattle(u1,u2,m1,m2);
					
					for (int i=0; i<querylist.size();i++){
						db.execute(querylist.get(i));
					}
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		query = ("DELETE FROM notifications WHERE ID='" +requestid+ "'");
		db.execute(query);
		
		
	}
	
    private void acceptBreedRequest(HttpServletRequest request, HttpServletResponse response){//legesy
	
		HttpSession session = request.getSession(true);
		
		int myMonsterID = (int)request.getAttribute("myMonsterID");
		int friendMonsterID = (int)request.getAttribute("friendMonsterID");
		int friendID = (int)request.getAttribute("friendID");
		
		User u1 = users.fetchUser((String)session.getAttribute("username"));
		User u2 = users.fetchUser(friendID);
		
		Monster m1 = u1.getMonster(myMonsterID);
		Monster m2 = u2.getMonster(friendMonsterID);
		
		ArrayList<String> querylist = breeding.Breed(u1,u2,m1,m2);
		
		for (int i=0; i<querylist.size();i++){
			db.execute(querylist.get(i));
		}
		
	}
	private void acceptFriendRequest(HttpServletRequest request, HttpServletResponse response,Request r){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		int friendid = r.getFrom();
		String query = ("DELETE FROM `notifications` WHERE `ID`='"+id+"'");
		String query1 ="INSERT INTO `friends` (`userID`, `friendID`) VALUES ('"+user.getId()+"', '"+friendid+"')";
		String query2 ="INSERT INTO `friends` (`userID`, `friendID`) VALUES ('"+friendid+"', '"+user.getId()+"')";
		String query3 ="INSERT INTO notifications (type, UserID1, UserID2, MonsterID1, MonsterID2, state) VALUES ('friend_accepted', '"+friendid+"', '"+user.getId()+"', 0, 0, 'PENDING')";
		
		

		db.execute(query);
		db.execute(query1);
		db.execute(query2);
		db.execute(query3);
		
		
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
		int ID;
		ID =  Integer.parseInt(request.getParameter("id"));
		String query = "DELETE FROM notifications WHERE ID='"+ID+"'";
		db.execute(query);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print("{\"id\":"+ID+",\"message\":\"notification removed\"}");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setBreedCost(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		user.getMonster((int)session.getAttribute("ID")).setCashBreed((int)session.getAttribute("cash"));
		
		String query="UPDATE 'monsters' SET 'cashSell'='"+(int)session.getAttribute("cash")+"' WHERE 'monsterID'='"+(int)session.getAttribute("ID")+"'";
		
		db.execute(query);
		
	}
	private void setBuyCost(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		user.getMonster((int)session.getAttribute("ID")).setCashSell((int)session.getAttribute("cash"));
		
		String query="UPDATE 'monsters' SET 'cashbreed'='"+(int)session.getAttribute("cash")+"' WHERE 'monsterID'='"+(int)session.getAttribute("ID")+"'";
		
		
		db.execute(query);
	}
		
	private void getRichList(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		
		User freinds[] = new User[user.getFriends().size()];
		
		for(int i=0; i<user.getFriends().size(); i++){
			freinds[i]=user.getFriends().get(i);
		}
		
		
		int n = freinds.length;
		User temp;
       
        for(int i=0; i < n; i++){
                for(int j=1; j < (n-i); j++){
                       
                        if(freinds[j-1].getCash() < freinds[j].getCash()){
                                temp = freinds[j-1];
                                freinds[j-1] = freinds[j];
                                freinds[j] = temp;
                        }
                }
        }
        User richlist[] = new User[10];
		for(int i =0; i<10;i++){
			if(i==freinds.length){
				break;
			}
			richlist[i] = freinds[i];
		}
		
		PrintWriter out;
		try {
			out = response.getWriter();
		
		out.print("{\"RichList\":[");
		
		for (int i =0 ;(i<10);i++){
			
			if(i==freinds.length){
				break;
			}
			out.print("{\"username\":\""+richlist[i].getUsername()+"\",");
			out.print("\"cash\":\""+richlist[i].getCash()+"\"}");
			
			
			if(i<9){
				if(i==freinds.length-1){
					break;
				}
				out.print(",");
			}
		}
		
		out.print("]}");
		out.flush();
		out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void reloadfreinds(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		user.getFriends().clear();
		String query = ("SELECT * FROM friends WHERE UserID='" +users.fetchUser((String)session.getAttribute("username")).getId()+ "'");
		
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
					Friend.setCash(rset2.getInt("cash"));
					user.addFriend(Friend);
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
							m.setHealth(rset2.getInt("health"));
							m.setAggression(rset2.getInt("aggression"));
							m.setStrength(rset2.getInt("strength"));
							m.setDefence(rset2.getInt("defence"));
							m.setFertility(rset2.getInt("fertility"));
							m.setWins(rset2.getInt("wins"));
							m.setLosses(rset2.getInt("losses"));
							m.setCashBreed(rset2.getInt("cashbreed"));
							m.setCashPrize(rset2.getInt("cashprize"));
							m.setCashSell(rset2.getInt("cashSell"));
							
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
	}
	private void reloadmonsters(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		user.getMonsters().clear();
		String query2 = ("SELECT * FROM monsters WHERE ownerID='" +user.getId()+ "'");
		
		ResultSet rset2;
		rset2 = db.createQuery (query2);
		try {
			while(rset2.next()){
				
				Monster m = new Monster();
				m.setName(rset2.getString("name"));
				m.setId(rset2.getInt("monsterID"));
				m.setOwnerId(rset2.getInt("ownerID"));
				m.setHealth(rset2.getInt("health"));
				m.setAggression(rset2.getInt("aggression"));
				m.setStrength(rset2.getInt("strength"));
				m.setDefence(rset2.getInt("defence"));
				m.setFertility(rset2.getInt("fertility"));
				m.setWins(rset2.getInt("wins"));
				m.setLosses(rset2.getInt("losses"));
				m.setCashBreed(rset2.getInt("cashbreed"));
				m.setCashPrize(rset2.getInt("cashprize"));
				m.setCashSell(rset2.getInt("cashSell"));
				
				user.addMonster(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void reloadnotifications(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		user.getRequests().clear();
		
		String query3 = ("SELECT * FROM notifications WHERE UserID1='" +user.getId()+ "'");
		
		ResultSet rset3;
		rset3 = db.createQuery (query3);
		ArrayList<Request> requests = new ArrayList<Request>();
		try {
			while(rset3.next()){
				
				int u1=Integer.parseInt(rset3.getString("UserID1"));
				int u2=Integer.parseInt(rset3.getString("UserID2"));
				
				int m1=Integer.parseInt(rset3.getString("MonsterID1"));
				int m2=Integer.parseInt(rset3.getString("MonsterID2"));
				
				Request r = new Request(u2,u1,m2,m1,RequestType.valueOf(rset3.getString("type")));
				r.setId(rset3.getInt("ID"));
				r.setCash(rset3.getInt("cash"));
				r.setOutcome(rset3.getString("outcome"));
				requests.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		user.setRequests(requests);
		
	}
	
}
