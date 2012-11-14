package monstermashData;

import java.util.ArrayList;

public class User {
	
	private Integer id, cash, key;
	private String username, email, password, serverAdd;
	private ArrayList<Monster> monsters;
	private ArrayList<User> friends;
	private ArrayList<Request> requests;
	
	public User(){
		monsters = new ArrayList<Monster>();
		friends = new ArrayList<User>();
		requests = new ArrayList<Request>();
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
	
	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public void setMonsters(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}
	public ArrayList<User> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}
	public ArrayList<Request> getRequests() {
		return requests;
	}
	public void setRequests(ArrayList<Request> requests) {
		this.requests = requests;
	}

}
