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
		mon.generateBases();
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
	public void testSetAndGetCashSell(){
		mon.setCashSell(100);
		assertEquals(100, mon.getCashSell());
	}
	
	@Test
	public void testSetAndGetCashBreed(){
		mon.setCashBreed(100);
		assertEquals(100, mon.getCashBreed());
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
	
	@Test
	public void testGenerateHealth() {
		assertEquals(50, mon.getHealth(), 10);
	}
	
	@Test
	public void testGenerateStrength(){
		assertEquals(45, mon.getStrength(), 10);
	}
	
	@Test
	public void testGenerateDefence(){
		assertEquals(45, mon.getDefence(), 10);
	}
	
	@Test
	public void testGenerateAggression(){
		assertEquals(0, mon.getAggression(), 10);
	}
	
	@Test
	public void testGenerateFertility(){
		assertEquals(0, mon.getFertility(), 10);
	}
	
	@Test
	public void testGenerateGenetics(){
		double prev = mon.getHealth();
		mon.generateGenetics();
		double modi = mon.getHealth();
		assertNotSame(prev, modi);
	}
	
	@Test
	public void testAgeHealthAndStrength(){
		float prev1 = mon.getHealth();
		float prev2 = mon.getStrength();
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 1, 0);
		mon.setBirth(cal);
		mon.ageHealth();
		System.out.println("Health Test:\n Original: "+
		prev1+" New: "+mon.getHealth());
		
		System.out.println("Strength Test:\n Original: "+
				prev2+" New: "+mon.getStrength());
		assertNotSame(prev1, mon.getHealth());
	}
	
	@Test
	public void testCalculateAge(){
		Calendar cu = Calendar.getInstance();
		int age = (int) mon.calculateAge(cu, mon.getBirth());
		assertEquals(0, age);
	}

	
}
