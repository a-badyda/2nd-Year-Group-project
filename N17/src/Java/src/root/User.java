package Java.src.root;

import java.util.ArrayList;

/**
 * This class describes a user in the server, it stores their credentials and their list
 * of monsters, friends and requests.
 * @author Csoma Silhab
 *
 */
public class User{
	
	private Integer id, cash = 100;
	private String key, username, password, serverAdd;
	private ArrayList<Monster> monsters;
	private ArrayList<User> friends;
	private ArrayList<Request> requests;
	
	
	public User(String username, String password){
		this.username=username;
		this.password=password;
		monsters = new ArrayList<Monster>();
		friends = new ArrayList<User>();
		requests = new ArrayList<Request>();
	}
	public User(){
		monsters = new ArrayList<Monster>();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getCash() {
		return cash;
	}
	public void setCash(Integer cash) {
		this.cash = cash;
	}
	
	public String getServerAdd(){
		return serverAdd;
	}
	
	public void setServerAdd(String serverAdd){
		this.serverAdd=serverAdd;
	}
	
	public ArrayList<Monster> getMonsters() {
		return monsters;
	}
	public void addMonster(Monster monster) {
		this.monsters.add(monster);
	}
	
	public Monster getMonster(Integer monsterId){
		for (int i=0; i<monsters.size(); i++){
			if(monsters.get(i).getId().equals(monsterId)){
				return monsters.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<User> getFriends() {
		return friends;
	}
	public void addFriend(User friend) {
		this.friends.add(friend);
	}
	public ArrayList<Request> getRequests() {
		return requests;
	}
	public void setRequests(ArrayList<Request> requests) {
		this.requests = requests;
	}
	public ArrayList<Monster> getFriendsMonsters(int ID) {
		return friends.get(ID).monsters;
	}

}
