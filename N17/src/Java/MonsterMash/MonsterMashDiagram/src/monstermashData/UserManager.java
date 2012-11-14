package monstermashData;

import java.util.*;

public class UserManager {

	private MySQLDatabase db;
	private LinkedList<User> users;
	
	public UserManager(){
		users = new LinkedList<User>();
		db = new MySQLDatabase();
	}
	
	public User fetchUser(Integer id){
		for(int i=0; i<users.size(); i++){
			if (id.equals(users.get(i).getId())){
				return users.get(i);
			}
		}
		return null;
	}
	
	public User getUser(String name){
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
	
	public void createUser(Integer id, String username, String email, String password){
		User newbie = new User();
		newbie.setId(id);
		newbie.setUsername(username);
		newbie.setEmail(email);
		newbie.setPassword(password);
	}
	
	
}
