//package Java;
package root;

import java.util.ArrayList;

public class User {
	
	private Integer id, cash;
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
	
	public void setMonsters(ArrayList<Monster> mons) {
		this.monsters=mons;
	}
	public ArrayList<Monster> getMonsters() {
		return monsters;
	}
	public void addMonster(Monster monster) {
		this.monsters.add(monster);
	}
	
	public Monster getMonster(Integer ownerId){
		for (int i=0; i<monsters.size(); i++){
			if(monsters.get(i).getOwnerId().equals(ownerId)){
				return monsters.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<User> getFriends() {
		return friends;
	}
	public void addFriends(User friend) {
		this.friends.add(friend);
	}
	
	public void addRequest(Request req){
		requests.add(req);
	}
	
	public void removeRequest(Request req){
		requests.remove(req);
	}
	
	public ArrayList<Request> getRequests() {
		return requests;
	}
	public void setRequests(ArrayList<Request> requests) {
		this.requests = requests;
	}

}
