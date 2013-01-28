package JUnit;

import static org.junit.Assert.*;

import org.junit.*;

import Java.User;

public class UserTest {
	
	User test;

	@Before
	public void setup() {
		test = new User("tux", "password");
	}

}
