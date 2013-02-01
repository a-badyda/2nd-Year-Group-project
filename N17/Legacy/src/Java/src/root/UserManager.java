package root;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Manages all the users and monsters in the database, such as adding and getting users 
 * by name or id. It can also communicates with the database to update both user listings.
 * @author Csoma Silhab
 *
 */
public class UserManager {

	private LinkedList<User> users;
	private Database db;
	
	public UserManager(){
		users = new LinkedList<User>();
		db = new Database();
	}
	/**
	 * Fetches a user with the given id number from the database.
	 * @param id Id of the user to search for
	 * @return A user with the given id number
	 */
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
	/**
	 * Fetches a user with the given username from the database.
	 * @param name Name of the user to search for
	 * @return A user with the given username
	 */
	public User fetchUserFromDatabase(String name) {
		String query = "SELECT * FROM user WHERE UserName='" +name+ "'";
		try {
			ResultSet rset;
			rset = db.createQuery(query);
			while (rset.next ()){
				User user = new User();
				user.setUsername(rset.getString("UserName"));
				user.setCash(rset.getInt("Cash"));
				user.setId(rset.getInt("UserID"));	
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void cheakForUsersMonsterss(int id){
		
		boolean hasMonsters=false;
		String query = "SELECT * FROM monsters WHERE ownerID='" +id+ "'";
		try {
			
			ResultSet rset;
			rset = db.createQuery(query);
			while (rset.next ()){
				hasMonsters=true;
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(hasMonsters){
			
		}else{
			///generate monster
			genereate(id);
		}
	
		
	}

	/**
	 * Generates a new monster with the given id and cash prize of 100.
	 * @param id is of the monster
	 */
	public void genereate(int id){
		
		Calendar date = Calendar.getInstance();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		
		
		Monster m = new Monster();
		m.generateStats(date);
		
		
		
		String query ="INSERT INTO monsters (ownerID,name,health,strength,defence,aggression,fertility,breed,status,cashSell,wins,losses,birth,cashbreed,cashprize)" +
				" VALUES ('"+id+"', 'my new monster', '"+m.getHealth()+"', '"+m.getStrength()+"', '"+m.getDefence()+"', '"+m.getAggression()+"', '"+m.getFertility()+"', 'BEAST', 'NORMAL', '0', '0', '0', '"+ft.format(date.getTime())+"', '0', '100')";
		
		db.execute(query);

	}
	/**
	 * Fetches a monster from the database with the given id number.
	 * @param id the id of the monster
	 * @return the monster with the same id
	 */
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
	/**
	 * Fetches the monster list of a friend with the given id.
	 * @param id id of the friend
	 * @return list of the friend's monsters
	 */
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
	/**
	 * Fetches a user with the given id number from the user list.
	 * @param id Id of the user to search for
	 * @return A user with the given id number
	 */
	public User fetchUser(Integer id){
		for(int i=0; i<users.size(); i++){
			if (id.equals(users.get(i).getId())){
				return users.get(i);
			}
		}
		return null;
	}
	/**
	 * Fetches a user with the given username from the user list.
	 * @param name Name of the user to search for
	 * @return A user with the given username
	 */
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
	/**
	 * Creates a new user with the given data.
	 * @param id id number of the user
	 * @param username name of the user
	 * @param password password of the user
	 */
	public void createUser(Integer id, String username, String password){
		User newbie = new User(username, password);
		newbie.setId(id);
	}
	/**
	 * Creates a new request with the given data.
	 * @param u1id user 1
	 * @param u2id user 2
	 * @param m1id monster of user 1
	 * @param m2id monster of user 2
	 * @param type request type
	 * @return a request with the given data
	 */
	public Request newRequest(Integer u1id, Integer u2id, Integer m1id, Integer m2id, RequestType type){
		Request req = new Request(u1id, u2id, m1id, m2id, type);
		return req;
	}
	
	
	
}
