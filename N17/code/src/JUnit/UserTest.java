package JUnit;

import static org.junit.Assert.*;

import org.junit.*;

import root.Monster;
import root.User;

public class UserTest {
	
	User test;
	Monster dummy;

	@Before
	public void setup() {
		test = new User("tux", "password");
		dummy = new Monster();
	}
	
	@Test
	public void testSetAndGetId(){
		test.setId(64);
		assertEquals(64, (int)test.getId());
	}
	
	@Test
	public void testSetAndGetUsername(){
		test.setUsername("Alex");
		assertEquals("Alex", test.getUsername());
	}
	
	@Test
	public void testSetAndGetPassword(){
		test.setPassword("password");
		assertEquals("password", test.getPassword());
	}
	
	@Test
	public void testSetAndGetServerAdd(){
		test.setServerAdd("address");
		assertEquals("address", test.getServerAdd());
	}
	
	@Test
	public void testSetAndGetKey(){
		test.setKey("1234");
		assertEquals("1234", test.getKey());
	}

	@Test
	public void testSetAndGetCash(){
		test.setCash(2000);
		assertEquals(2000, (int)test.getCash());
	}
	
	@Test
	public void testAddAndGetMonsters(){
		test.addMonster(dummy);
		assertEquals(dummy, test.getMonsters().get(0));
	}
	
	@Test
	public void testAddAndGetFriends(){
		User friend = new User();
		test.addFriend(friend);
		assertEquals(friend, test.getFriends().get(0));
	}
	
	
	
	
}
