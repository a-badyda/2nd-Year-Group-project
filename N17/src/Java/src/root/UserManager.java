package Java.src.root;

import java.util.*;

/**
 * Manages all the user in the database, such as adding and getting users by name or id.
 * @author Csoma Silhab
 *
 */
public class UserManager {

	private LinkedList<User> users;
	
	public UserManager(){
		users = new LinkedList<User>();
	}
	
	/**
	 * Fetches a user with the given id number.
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
	 * Fetches a user with the given username.
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
	
	/**
	 * Add a user to the users list.
	 * @param user User object to add
	 */
	public void addUser(User user){
		this.users.add(user);
	}
	
	/**
	 * Removes a user from the users list.
	 * @param user User object to remove
	 */
	public void removeUser(User user){
		this.users.remove(user);
	}
	
	/**
	 * Creates a new user with the given id, username and password.
	 * @param id Id of new user
	 * @param username Username of new user
	 * @param password Password of new user
	 */
	public void createUser(Integer id, String username, String password){
		User newbie = new User(username, password);
		newbie.setId(id);
	}
	
	/**
	 * Creates a new request between the given users and monsters.
	 * @param u1id User who made the request
	 * @param u2id User who accepted the request
	 * @param m1id Monster of the requester
	 * @param m2id Monster of the target user
	 * @param type The type of this request
	 * @return A request object with all the data inserted
	 */
	public Request newRequest(Integer u1id, Integer u2id, Integer m1id, Integer m2id, RequestType type){
		Request req = new Request(u1id, u2id, m1id, m2id, type);
		return req;
	}
	
}
