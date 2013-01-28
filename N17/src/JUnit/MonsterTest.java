package JUnit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.*;

import root.Breed;
import root.Monster;
import root.Status;

public class MonsterTest {

	Monster mon;
	
	@Before
	public void setup() {
		mon = new Monster();
	}
	
	@Test
	public void testSetAndGetId(){
		mon.setId(12);
		assertEquals(12, (int) mon.getId());
	}

	@Test
	public void testSetAndGetOwnerId(){
		mon.setOwnerId(10);
		assertEquals(10, (int) mon.getOwnerId());
	}
	
	@Test
	public void testSetAndGetName(){
		mon.setName("Dummy");
		assertEquals("Dummy", mon.getName());
	}
	
	@Test
	public void testSetAndGetBreed(){
		mon.setBreed(Breed.GHOST);
		assertEquals(Breed.GHOST, mon.getBreed());
	}
	
	@Test
	public void testSetAndGetStatus(){
		mon.setStatus(Status.HAPPY);
		assertEquals(Status.HAPPY, mon.getStatus());
	}
	
	@Test
	public void testSetAndGetCashPrize(){
		mon.setCashPrize(100);
		assertEquals(100, mon.getCashPrize());
	}
	
	@Test
	public void testSetAndGetWins(){
		mon.setWins(3);
		assertEquals(3, mon.getWins());
	}
	
	@Test
	public void testSetAndGetLosses(){
		mon.setLosses(1);
		assertEquals(1, mon.getLosses());
	}
	
	@Test
	public void testSetAndGetBirth(){
		Calendar date = Calendar.getInstance();
		mon.setBirth(date);
		assertEquals(date, mon.getBirth());
	}
	
}
