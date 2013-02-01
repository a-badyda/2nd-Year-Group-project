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

/**
 * A class responsible fo handling all the requests and responses to the database and the server. 
 */

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

    /**
     * @method doGet responsible for preforming actions in response to requests
     * @param HttpServletRequest the request recived
     * @param HttpServletResponse the response to be supplied
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action = request.getParameter("action");
		
		switch (action){
		
		case "isLoggedIn": IsLoggedIn(request, response); break;
		case "getUserData": GetUserData(request, response); break;
		}
		
		
	}
	/**
	 * @method IsLoggedIn checks wether user is logged in and prints the output for later use
	 */
	
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
	
	/**
	 * @method GetUserData requests the user's name and key id to then output the username and cash values  
	 */
	
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
	
	/**
	 * @method doPost recives all the requests from the client side 
	 * - the major function that everything is put through. Responsible for passing the data from one place to another
	 */
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
			case "changeName": changeName(request, response); break;
			case "setBuyCost": setBuyCost(request, response); break;
			case "setBreedCost": setBreedCost(request, response); break;	
			case "getRichList": getRichList(request, response); break;
			case "getAllResults": getAllResults(request, response); break;
			
		}
	}
	
	/**
	 * @method LogIn  loads in the user into the server and validates all of it- 
	 * loads in all of the monsters, friends, and friends' monsters on lists
	 * and all the notifications - plus redirecting it all around where needed. 
	 * if the user has no monsters, a new one is generated for him, stored in a query and added to the database
	 * 
	 */
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
    				
 		            out.print("{\"login\":true,\"message\":\"login success\"}");
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
				out.print("{\"login\":false,\"message\":\"sorry invalid login\"}");
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
		 boolean exists= false;
	    
		String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    try {
	    	String query ="SELECT * FROM user WHERE UserName='"+(String)request.getParameter("username")+"'";
	    	ResultSet rset = db.createQuery(query);
			while(rset.next()){
				exists=true;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	    
	    if(!exists){
	    
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
				
	    		ResultSet rset = db.createQuery(query);
				
				while(rset.next()){
					ID=rset.getInt("UserID");
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		    // give them there first monster
			
			users.genereate(ID);
			
	//		query = "INSERT INTO `monsterdata`.`monsters` (`ownerID`, `name`, `health`, `strength`, `defence`, `aggression`, `fertility`, `breed`, `status`, `cashPrize`, `wins`, `losses`, `birth`) VALUES ('"+ID+"', 'my first monster', '10', '10', '10', '10', '10', 'BEAST', 'NORMAL', '10', '0', '0', '"+ft.format(date)+"');";
	//	    
	//		//run the query and store in DB
	//	    db.execute(query);
		    
		    try {
				PrintWriter out = response.getWriter();
				out.print("new user created");
				out.print(ft.format(date));
				out.flush();
				out.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }else{
	    	
	    	try {
				PrintWriter out = response.getWriter();
				out.print("{\"login\":false,\"message\":\"Sorry that user already exists\"}");
				out.flush();
				out.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * @method addFriend a method tries to find the matching string in database and if it is found, the match is sent a friend request
	 * if the friend is not found, an error message is returned
	 * when friend request is accepted/declined by the user, the method either creates the link between players or not. 
	 */
	private void addFriend(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		
		boolean viable=true;
		
		try {
		String query = "SELECT * FROM friends WHERE userID='" +user.getId()+ "' AND friendID='"+users.fetchUserFromDatabase((String)request.getParameter("username")).getId()+"'";
		
		ResultSet rset;
		rset = db.createQuery(query);
			while(rset.next()){
				viable=false;
			}
			
		} catch (SQLException|NullPointerException e1) {
			try {
				PrintWriter out = response.getWriter();
				out.print("sorry that user dosn't exist");
				out.flush();
				out.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			String query = "SELECT * FROM user WHERE UserName='" +users.fetchUserFromDatabase((String)request.getParameter("username")).getId()+"'";
			
			ResultSet rset;
			rset = db.createQuery(query);
				while(rset.next()){
					viable=false;
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
		
		
		try {
			String query = "SELECT * FROM notifications WHERE UserID2='" +user.getId()+ "' AND UserID1='"+users.fetchUserFromDatabase((String)request.getParameter("username")).getId()+"' AND type='friend_request'";
			
			ResultSet rset;
			rset = db.createQuery(query);
				while(rset.next()){
					viable=false;
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		if((user.getUsername().equalsIgnoreCase((String)request.getParameter("username")))||(!viable)){
		}else{
		
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
					out.print("Added successfully");
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
	}
	/**
	 * @method newBattleRequest Sends out the request to marked user with a marked user's monster that is required for battle
	 * if failed or passed and appropriate response is printed out. 
	 */
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
				out.print("Request failed to send");
				out.flush();
				out.close();
				
			} catch (IOException ex) {
			}
			
		}
		
		
	}
	/**
	 * @method the request is sent out, the user who accepted is first looses cash, then babies are generated and added into his monster list 
	 * Lastly, money is added to the user who set out his monster out for breeding
	 */
	private void newBreedRequest(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		
		if(users.fetchUserFromDatabase((String)session.getAttribute("username")).getCash()>=users.fetchMonsterFromDatabase(Integer.parseInt(request.getParameter("monsterId"))).getCashBreed()){
		
			try {
			User user = users.fetchUser((String)session.getAttribute("username"));
			
					//u1 lose cash
			User u1=users.fetchUserFromDatabase(user.getId());
			User u2=users.fetchUserFromDatabase(Integer.parseInt(request.getParameter("friendId")));
			
			Monster m1=users.fetchMonsterFromDatabase(Integer.parseInt(request.getParameter("userMonsterId")));
			Monster m2=users.fetchMonsterFromDatabase(Integer.parseInt(request.getParameter("monsterId")));
					
			
			String query="UPDATE user SET Cash='"+(u1.getCash()-m2.getCashBreed())+"' WHERE UserID='"+u1.getId()+"'";
			db.execute(query);
			query="UPDATE user SET Cash='"+(u2.getCash()+m2.getCashBreed())+"' WHERE UserID='"+u2.getId()+"'";
			db.execute(query);
			
			ArrayList<String> querylist = breeding.doBreed(u1,u2,m1,m2);
			
				for (int i=0; i<querylist.size();i++){
					db.execute(querylist.get(i));
				}
				
				String query2 ="SELECT * FROM monsters WHERE breed="+u1.getId()+"";
				ResultSet rset;
				rset = db.createQuery(query2);
				int count =1;
				int babies[]=new int[10];
				while ((rset.next())&&(count<10))
				{ 
					String query3="UPDATE monsters SET breed='BEAST' WHERE monsterID='"+rset.getInt("monsterID")+"'";
					db.execute(query3);
					babies[count-1]=rset.getInt("monsterID");
					count++;
				}
				
				query ="INSERT INTO result(type,userID1,userID2,monsterID1,monsterID2,userwon,monsterwon,winmessage,lostmessage,cash,baby1,baby2,baby3,baby4,baby5,baby6,baby7,baby8,baby9,baby10)" +
					"VALUES('breed_result','"+u1.getId()+"','"+u2.getId()+"','"+m1.getId()+"','"+m2.getId()+"','"+u1.getId()+"','"+u1.getId()+"','congratulations you have baby monsters','some one breeded with your monster','"+m2.getCashBreed()+"',"+babies[0]+","+babies[1]+","+babies[2]+","+babies[3]+","+babies[4]+","+babies[5]+","+babies[6]+","+babies[7]+","+babies[8]+","+babies[9]+")";
			
				db.execute(query);
				
				query ="INSERT INTO result(type,userID1,userID2,monsterID1,monsterID2,userwon,monsterwon,winmessage,lostmessage,cash,baby1,baby2,baby3,baby4,baby5,baby6,baby7,baby8,baby9,baby10)" +
						"VALUES('breed_result','"+u2.getId()+"','"+u1.getId()+"','"+m1.getId()+"','"+m2.getId()+"','"+u1.getId()+"','"+u1.getId()+"','congratulations you have baby monsters','some one breeded with your monster','"+m2.getCashBreed()+"',"+babies[0]+","+babies[1]+","+babies[2]+","+babies[3]+","+babies[4]+","+babies[5]+","+babies[6]+","+babies[7]+","+babies[8]+","+babies[9]+")";
				
				db.execute(query);
				
				query ="INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES ('breed_result','"+u2.getId()+"','"+u1.getId()+"','"+m1.getId()+"','"+m2.getId()+"','PENDING','0','')";
				
				db.execute(query);
				
				
				
				PrintWriter out = response.getWriter();
				out.print("Request sent");
				out.flush();
				out.close();
				
			} catch (SQLException | IOException e) {
				e.printStackTrace();
				try {
					PrintWriter out = response.getWriter();
					out.print("Request failed to send");
					out.flush();
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		}else{
			
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("Insufficient funds");
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//responce (monster array babies,stats) amount_paid: 
		
	}
	/**
	 * @method 
	 */
	private void newBuyRequest(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		if(users.fetchUserFromDatabase((String)session.getAttribute("username")).getCash()>=users.fetchMonsterFromDatabase(Integer.parseInt(request.getParameter("monsterId"))).getCashSell()){
			
			User user = users.fetchUser((String)session.getAttribute("username"));
			
			int userID = user.getId();
			int friendID = Integer.parseInt(request.getParameter("friendId"));
			int monsterID = Integer.parseInt(request.getParameter("monsterId"));
			
			Monster monster = users.fetchMonsterFromDatabase(monsterID);
			
			String query="UPDATE user SET Cash='"+(user.getCash()-monster.getCashSell())+"' WHERE UserID='"+user.getId()+"'";
			db.execute(query);
			user.setCash(user.getCash()-monster.getCashSell());
			query="UPDATE user SET Cash='"+(users.fetchUserFromDatabase(friendID).getCash()+monster.getCashSell())+"' WHERE UserID='"+friendID+"'";
			db.execute(query);
			query="INSERT INTO result(type,userID1,userID2,monsterID1,monsterID2,userwon,monsterwon,winmessage,lostmessage,cash,baby1,baby2,baby3,baby4,baby5,baby6,baby7,baby8,baby9,baby10)" +
					"VALUES('buy_result','"+userID+"','"+friendID+"','"+monsterID+"','"+monsterID+"','"+userID+"','"+monsterID+"','congratulations you just bought a new monster',' ','"+monster.getCashSell()+"',0,0,0,0,0,0,0,0,0,0)";
			db.execute(query);	
			query="UPDATE monsters SET cashSell='0' WHERE monsterID='"+monsterID+"'";
			db.execute(query);
			query="UPDATE monsters SET ownerID='"+userID+"' WHERE monsterID='"+monsterID+"'";
			db.execute(query);
	
			//notification
			query="INSERT INTO notifications (type, UserID1, UserID2, MonsterID1, MonsterID2, state) VALUES ('buy_result', '"+friendID+"', '"+userID+"', '"+monsterID+"', '"+monsterID+"', 'PENDING')";
			db.execute(query);
			
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("Request sent");
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * @method getMonsters Prints all monsters 1 at a time with all their stats in a set order
	 */
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
				out.print("\"cost_buy\":"+requests.get(i).getCashSell()+",");
				out.print("\"cost_breed\":"+requests.get(i).getCashBreed()+",");
				out.print("\"strength\":\""+requests.get(i).getStrength()+"\",");
				out.print("\"health\":\""+requests.get(i).getHealth()+"\",");
				out.print("\"fertility\":\""+requests.get(i).getFertility()+"\",");
				out.print("\"defence\":\""+requests.get(i).getDefence()+"\",");
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
	
	/**
	 * @method getFriends  prints the list of user's friends + the cash values for friends 
	 */
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
	
	/**
	 * @method getFriendsMonsters prints monsters of friends - adds button to allow selling//buying a monster if it was activated via breedRequest method
	 */
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
				if(requests.get(i).getCashSell()>0){buy="true";}else{buy="false";}
				if(requests.get(i).getCashBreed()>0){breed="true";}else{breed="false";}
				
				
				out.print("{\"monstername\":\""+requests.get(i).getName()+"\",");
				out.print("\"ID\":\""+requests.get(i).getId()+"\",");
				out.print("\"buy\":"+buy+",");
				out.print("\"cost_buy\":"+requests.get(i).getCashSell()+",");
				out.print("\"cost_breed\":"+requests.get(i).getCashBreed()+",");
				out.print("\"strength\":\""+requests.get(i).getStrength()+"\",");
				out.print("\"health\":\""+requests.get(i).getHealth()+"\",");
				out.print("\"fertility\":\""+requests.get(i).getFertility()+"\",");
				out.print("\"defence\":\""+requests.get(i).getDefence()+"\",");
				out.print("\"aggression\":\""+requests.get(i).getAggression()+"\",");
				out.print("\"breed\":"+breed+"}");
				
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
	
	/**
	 * @method changeName allows user to change the name of their monsters
	 */
	private void changeName(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("monster_id"));
		String name = request.getParameter("new_name");
		
		String query = "UPDATE monsters SET name='"+name+"' WHERE monsterID='"+id+"'";
		db.execute(query);
	}
	
	/**
	 * @method getAllResults prints the results of breeding/battle in a set form
	 */
	private void getAllResults(HttpServletRequest request, HttpServletResponse response){
	
		try{
			PrintWriter out;
		
			out = response.getWriter();
		
			out.print("{\"Results\":[");
	    	HttpSession session = request.getSession(true);
			
			User user = users.fetchUserFromDatabase((String)session.getAttribute("username"));
			
		   
		    //"+user.getId()+"
		    String query = ("SELECT COUNT(*) AS total FROM result WHERE userID1='"+user.getId()+"'");
		    ResultSet rset;
    		
		    rset = db.createQuery(query);
		    int norows=0;
		    
	    	rset.next();
	    	norows = rset.getInt("total");
		    
    		query = ("SELECT * FROM result WHERE userID1='"+user.getId()+"'");
    		rset = db.createQuery(query);
    		int count =0;
    		
    		while (rset.next ())
    		{ 
    			
    			out.print("{\"Type\":\""+rset.getString("type")+"\",");
				out.print("\"ID\":\""+rset.getInt("ID")+"\",");
				out.print("\"From\":\""+users.fetchUserFromDatabase(rset.getInt("userID2")).getUsername()+"\",");
				
				
				String query2 = ("SELECT * FROM monsters WHERE monsterID='" +rset.getInt("monsterID1")+ "'");
				
				ResultSet rset2;
				rset2 = db.createQuery(query2);
				
			
				while (rset2.next ())
				{
					out.print("\"my_monster\":");
					out.print("{\"monstername\":\""+rset2.getString("name")+"\",");
					out.print("\"ID\":\""+rset2.getInt("monsterID")+"\",");
					out.print("\"strength\":\""+rset2.getInt("strength")+"\",");
					out.print("\"health\":\""+rset2.getInt("health")+"\",");
					out.print("\"fertility\":\""+rset2.getInt("fertility")+"\",");
					out.print("\"defence\":\""+rset2.getInt("defence")+"\",");
					out.print("\"aggression\":\""+rset2.getInt("aggression")+"\"");
					out.print("},");
				}
			
				query2 = ("SELECT * FROM monsters WHERE monsterID='" +rset.getInt("monsterID2")+ "'");
				
				rset2 = db.createQuery (query2);
				
				while (rset2.next())
	    		{
					out.print("\"friend_monster\":");
					out.print("{\"monstername\":\""+rset2.getString("name")+"\",");
					out.print("\"ID\":\""+rset2.getInt("monsterID")+"\",");
					out.print("\"strength\":\""+rset2.getInt("strength")+"\",");
					out.print("\"health\":\""+rset2.getInt("health")+"\",");
					out.print("\"fertility\":\""+rset2.getInt("fertility")+"\",");
					out.print("\"defence\":\""+rset2.getInt("defence")+"\",");
					out.print("\"aggression\":\""+rset2.getInt("aggression")+"\"");
					out.print("},");
	    		}
			
    			
				if(user.getId()==rset.getInt("userWon")){
					out.print("\"message\":\""+rset.getString("winmessage")+"\",");
					out.print("\"cash\":\""+rset.getInt("cash")+"\",");
				}else{
					out.print("\"message\":\""+rset.getString("lostmessage")+"\",");
					out.print("\"cash\":\"0\",");
				}
				
				
				
				out.print("\"babies\": [");
				for (int i =0; i<10;i++){
					//baby 1 is the colomb 12 starting counting from 1
					if(rset.getInt(12+i)!=0){
						query2 = ("SELECT * FROM monsters WHERE monsterID='" +rset.getInt(12+i)+ "'");
						
						rset2 = db.createQuery (query2);
						
						while (rset2.next())
			    		{
							out.print("{\"monstername\":\""+rset2.getString("name")+"\",");
							out.print("\"ID\":\""+rset2.getInt("monsterID")+"\",");
							out.print("\"strength\":\""+rset2.getInt("strength")+"\",");
							out.print("\"health\":\""+rset2.getInt("health")+"\",");
							out.print("\"fertility\":\""+rset2.getInt("fertility")+"\",");
							out.print("\"defence\":\""+rset2.getInt("defence")+"\",");
							out.print("\"aggression\":\""+rset2.getInt("aggression")+"\"");
							out.print("}");
							
			    		}
						if((i<9)&&(rset.getInt(12+i+1)!=0)){
								out.print(",");
							}
					}
				}
				
				out.print("]}");
				
				if(count<norows-1){
					out.print(",");
				}
				count++;
    		}
    		
    		out.print("]}");
    		out.flush();
			out.close();
	    }catch(SQLException | IOException e){
	    	e.printStackTrace();
	    }
		
		
	}
	
	/**
	 * @method getAllRequest prints all current requests aka notifications for the user that are avaliable/new
	 */
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
	/**
	 * @method acceptRequest This method is long and I don't want to do it now. 
	 * I'm just looking at it and thinking 'how do i computer'
	 */
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
	
	/**
	 * @method acceptBattleRequest confirms the request sent from one user to another asking for battle (gasp! how unexpected!)
	 */
	private void acceptBattleRequest(HttpServletRequest request, HttpServletResponse response,Request r){
		int requestid = r.getId();
		battle = new Battle();
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
					
					String query2="DELETE FROM notifications WHERE ID='"+rset.getInt("ID")+"'";
					db.execute(query2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	/**
	 * @method acceptFriendRequest self-explanatory
	 */
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
	
	/**
	 * @method declineRequest allows the user to reject and 'drop/fail' the requests sent to him, deleting them completly
	 */
	private void declineRequest(HttpServletRequest request, HttpServletResponse response){
		int ID;
		ID =  Integer.parseInt(request.getParameter("id"));
		
		if(request.getParameter("result").equalsIgnoreCase("true")){
			String query = "DELETE FROM result WHERE ID='"+ID+"'";
			db.execute(query);
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("{\"id\":"+ID+",\"message\":\"result removed\"}");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
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
		
		
		
		
		
	}
	
	
	private void setBreedCost(HttpServletRequest request, HttpServletResponse response){
		
		String query="UPDATE monsters SET cashbreed='"+Integer.parseInt(request.getParameter("cost"))+"' WHERE monsterID='"+Integer.parseInt(request.getParameter("id"))+"'";
		db.execute(query);
		
	}
	
	private void setBuyCost(HttpServletRequest request, HttpServletResponse response){
		
		String query="UPDATE monsters SET cashSell='"+Integer.parseInt(request.getParameter("cost"))+"' WHERE monsterID='"+Integer.parseInt(request.getParameter("id"))+"'";
		db.execute(query);
	}
	
	/**
	 * @method getRichList generates a list of all the users' friends (+user) and sorts them from lowest to highiest cash value
	 */
	private void getRichList(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		
		User freinds[] = new User[user.getFriends().size()+1];
		
		
		for(int i=0; i<user.getFriends().size(); i++){
			freinds[i]=user.getFriends().get(i);
		}
		freinds[freinds.length-1]=user;
		
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
	
	/**
	 * @method reloadFriends method allowing you to update the friend list it seems
	 */
	private void reloadfreinds(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		user.getFriends().clear();
		
		
		try {
			String query="SELECT * FROM user WHERE UserID='"+user.getId()+"'";
			ResultSet rset;
			rset = db.createQuery (query);
				while(rset.next()){
					user.setCash(rset.getInt("Cash"));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		
		
		
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
					rset3 = db.createQuery (query3);
					try {
						while(rset3.next()){
							
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
	
	/**
	 * @method reloadmonsters allows monsters to be updated during one session without shutting the server down and turning it on again
	 */
	private void reloadmonsters(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		users.cheakForUsersMonsterss(user.getId());
		
		try {
		String query="SELECT * FROM user WHERE UserID='"+user.getId()+"'";
		ResultSet rset;
		rset = db.createQuery (query);
			while(rset.next()){
				user.setCash(rset.getInt("Cash"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
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
	}
	/**
	 * @method reloadnotifications same case like other reloads
	 */
	private void reloadnotifications(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(true);
		User user = users.fetchUser((String)session.getAttribute("username"));
		user.getRequests().clear();
		
		
		try {
			String query="SELECT * FROM user WHERE UserID='"+user.getId()+"'";
			ResultSet rset;
			rset = db.createQuery (query);
				while(rset.next()){
					user.setCash(rset.getInt("Cash"));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		
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
