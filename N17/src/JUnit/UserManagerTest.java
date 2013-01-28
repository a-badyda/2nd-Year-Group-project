package JUnit;

import static org.junit.Assert.*;

import org.junit.*;

import Java.src.root.User;
import Java.src.root.UserManager;

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
	public void testRemovehUser(){
		User user = new User();
		user.setUsername("Bad");
		man.addUser(user);
		man.removeUser(user);
		assertNull(man.fetchUser("Bad"));
	}
}
