//package Java;
package root;

import java.util.*;

/**
 * Manages users for the server. User information is passed over from JavaScript.
 */

public class UserManager {

	private LinkedList<User> users;
	
	public UserManager(){
		users = new LinkedList<User>();
	}
	
	/**
	 * Gets a user based on their database ID.
	 * @param id The id of the user.
	 * @return The user with the corresponding ID.
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
	 * Gets a user based on their name.
	 * @param name The name of the user.
	 * @return The user with the corresponding name.
	 */
	public User fetchUser(String name){
		for(int i=0; i<users.size(); i++){
			if (name.equals(users.get(i).getUsername())){
				return users.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Adds a new user to the users list.
	 * @param user The user to add to the list.
	 */
	public void addUser(User user){
		this.users.add(user);
	}
	
	/**
	 * Removes a user from the users list.
	 * @param user The user to remove from the list.
	 */
	public void removeUser(User user){
		this.users.remove(user);
	}
	
	/**
	 * Creates a new user with the given parameters and add them to users.
	 * @param key The key of the user.
	 * @param username The username of the user.
	 * @param password The password of the user.
	 * @param cash The starting cash of the user.
	 * @return The newly created user object.
	 */
	public User createUser(String key, String username, String password, Integer cash){
		User newbie = new User(username, password);
		newbie.setKey(key);
		users.add(newbie);
		return newbie;
	}
	
	/**
	 * Creates a request with the given parameters.
	 * @param u1 The user who made the request.
	 * @param u2 The user targeted by the request.
	 * @param m1 The monster of the user who made the request.
	 * @param m2 The monster of the user who received the request.
	 * @param type The type of the request.
	 * @return The request that was created.
	 */
	public Request newRequest(User u1, User u2, Monster m1, Monster m2, RequestType type){
		Request req;
		if (type == RequestType.FRIEND){
			req = new Request(u1, u2, type);
		}
		else{req = new Request(u1, u2, m1, m2, type);}
		u1.addRequest(req);
		u2.addRequest(req);
		return req;
	}
	
}
