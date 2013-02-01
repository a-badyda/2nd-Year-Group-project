package JUnit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Java.src.root.*;

public class UserManagerTest {
	
	UserManager man;

	@Before
	public void setup() {
		man = new UserManager();
	}
	
	@Test
	public void testAddAndGetUserId(){
		User user = new User();
		user.setId(4);
		man.addUser(user);
		assertEquals(man.fetchUser(4), user);
	}
	
	@Test
	public void testAddAndGetUserNm(){
		User user = new User();
		user.setUsername("Chris");
		man.addUser(user);
		assertEquals(man.fetchUser("Chris"), user);
	}
	

	@Test
	public void testRemoveUser(){
		User user = new User();
		user.setUsername("Bad");
		man.addUser(user);
		man.removeUser(user);
		assertNull(man.fetchUser("Bad"));
	}
	
	@Test
	public void testNewRequest(){
		Request req = man.newRequest(10, 20, 5,2, RequestType.BATTLE);
		assertNull(req);
	}
}
