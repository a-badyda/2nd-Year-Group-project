package JUnit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.*;

import Java.src.root.Breed;
import Java.src.root.MonsterStats;

public class MonsterStatsTest {
	
	MonsterStats stats;
	
	@Before
	public void setup(){
		Calendar date = Calendar.getInstance();
		stats = new MonsterStats(date);
		stats.generateBases();
	}
	
	@Test
	public void testGenerateHealth() {
		assertEquals(60, stats.getHealth(), 10);
	}
	
	@Test
	public void testGenerateStrength(){
		assertEquals(10, stats.getStrength(), 10);
	}
	
	@Test
	public void testGenerateDefence(){
		assertEquals(10, stats.getDefence(), 10);
	}
	
	@Test
	public void testGenerateAggression(){
		assertEquals(10, stats.getAggression(), 10);
	}
	
	@Test
	public void testGenerateFertility(){
		assertEquals(10, stats.getFertility(), 10);
	}
	
	@Test
	public void testGenerateGenetics(){
		double prev = stats.getHealth();
		stats.generateGenetics();
		double modi = stats.getHealth();
		assertNotSame(prev, modi);
	}
	
	@Test
	public void testAgeHealth(){
		float prev = stats.getHealth();
		stats.ageHealth();
		System.out.println("Health Test:\n Original: "+
		prev+" New: "+stats.getHealth());
		assertNotSame(prev, stats.getHealth());
	}
	
	@Test
	public void testCalculateAge(){
		Calendar cu = Calendar.getInstance();
		int age = (int) stats.calculateAge(cu, stats.getBirth());
		assertEquals(0, age);
	}

}
