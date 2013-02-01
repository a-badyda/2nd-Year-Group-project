package JUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import root.Breeding;
import root.Monster;
import root.User;

public class BreedingTest {

	Breeding br = new Breeding();
	
	@Test
	public void testBreed() {
		User u1 = new User("A", "Code");
		Monster m1 = new Monster(1,"Pok");
		User u2 = new User("B", "Edoc");
		Monster m2 = new Monster(2, "Kop");
		ArrayList<String> children = br.doBreed(u1, u2, m1, m2);
		System.out.println(children);
		assertNotNull(children);
	}

}
