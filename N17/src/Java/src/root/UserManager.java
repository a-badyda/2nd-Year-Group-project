package root;

import java.util.*;

public class UserManager {

	private LinkedList<User> users;
	
	public UserManager(){
		users = new LinkedList<User>();
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
