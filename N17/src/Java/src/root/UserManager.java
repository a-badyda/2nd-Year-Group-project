package root;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserManager {

	private LinkedList<User> users;
	private Database db;
	
	public UserManager(){
		users = new LinkedList<User>();
		db = new Database();
	}
	
	public User fetchUserFromDatabase(int id) {
		String query = "SELECT * FROM user WHERE UserID='" +id+ "'";
		try {
			ResultSet rset;
			rset = db.createQuery(query);
			while (rset.next ()){
				User user = new User();
				user.setUsername(rset.getString("UserName"));
				user.setCash(rset.getInt("Cash"));
				user.setId(id);	
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Monster fetchMonsterFromDatabase(int id){
		String query = "SELECT * FROM monsters WHERE monsterID='" +id+ "'";
		try {
			ResultSet rset;
			rset = db.createQuery(query);
			while (rset.next ()){
				Monster monster = new Monster();
				monster.setName(rset.getString("name"));
				monster.setId(rset.getInt("monsterID"));
				monster.setOwnerId(rset.getInt("ownerID"));
				monster.setHealth(rset.getInt("health"));
				monster.setAggression(rset.getInt("aggression"));
				monster.setStrength(rset.getInt("strength"));
				monster.setDefence(rset.getInt("defence"));
				monster.setFertility(rset.getInt("fertility"));
				monster.setWins(rset.getInt("wins"));
				monster.setLosses(rset.getInt("losses"));
				monster.setCashBreed(rset.getInt("cashbreed"));
				monster.setCashPrize(rset.getInt("cashprize"));
				monster.setCashSell(rset.getInt("cashSell"));
				return monster;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ArrayList<Monster> fetchFriendsMonstersFromDatabase(int id){
		
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		
		String query = "SELECT * FROM monsters WHERE ownerID='" +id+ "'";
		try {
			ResultSet rset;
			rset = db.createQuery(query);
			while (rset.next ()){
				Monster monster = new Monster();
				monster.setName(rset.getString("name"));
				monster.setId(rset.getInt("monsterID"));
				monster.setOwnerId(rset.getInt("ownerID"));
				monster.setHealth(rset.getInt("health"));
				monster.setAggression(rset.getInt("aggression"));
				monster.setStrength(rset.getInt("strength"));
				monster.setDefence(rset.getInt("defence"));
				monster.setFertility(rset.getInt("fertility"));
				monster.setWins(rset.getInt("wins"));
				monster.setLosses(rset.getInt("losses"));
				monster.setCashBreed(rset.getInt("cashbreed"));
				monster.setCashPrize(rset.getInt("cashprize"));
				monster.setCashSell(rset.getInt("cashSell"));
				
				monsters.add(monster);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return monsters;
	}
	
	public User fetchUser(Integer id){
		for(int i=0; i<users.size(); i++){
			if (id.equals(users.get(i).getId())){
				return users.get(i);
			}
		}
		return null;
	}
	
	public User fetchUser(String name){
		for(int i=0; i<users.size(); i++){
			if (name.equals(users.get(i).getUsername())){
				return users.get(i);
			}
		}
		return null;
	}
	
	public void addUser(User user){
		this.users.add(user);
	}
	
	public void removeUser(User user){
		this.users.remove(user);
	}
	
	public void createUser(Integer id, String username, String password){
		User newbie = new User(username, password);
		newbie.setId(id);
	}
	
	public Request newRequest(Integer u1id, Integer u2id, Integer m1id, Integer m2id, RequestType type){
		Request req = new Request(u1id, u2id, m1id, m2id, type);
		return req;
	}
	
	
	
}
