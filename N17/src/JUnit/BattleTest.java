package JUnit;

import static org.junit.Assert.*;
import org.junit.*;

import Java.src.root.Battle;
import Java.src.root.Monster;
import Java.src.root.User;

public class BattleTest {
	
	Battle battle;

	@Before
	public void setup() {
		User chris = new User("Chris", "passWd");
		Monster delegator = new Monster(1, "Delegator");
		delegator.getStats().setStrength(100);
		User tux = new User("Tux", "delegate");
		Monster derp = new Monster(2, "Derp");
		
		battle = new Battle(chris, tux, delegator, derp);
	}
	
	@Test
	public void testSetAndGetTo(){
		User test = new User();
		battle.setTo(test);
		assertEquals(battle.getTo(), test);
	}
	
	@Test
	public void testSetAndGetFrom(){
		User test = new User();
		battle.setFrom(test);
		assertEquals(battle.getFrom(), test);
	}
	
	@Test
	public void testSetAndGetToMon(){
		Monster test = new Monster();
		battle.setToMon(test);
		assertEquals(battle.getToMon(), test);
	}
	
	@Test
	public void testSetAndGetFromMon(){
		Monster test = new Monster();
		battle.setFromMon(test);
		assertEquals(battle.getFromMon(), test);
	}
	
	@Test
	public void testSetAndGetWinner(){
		Monster test = new Monster();
		battle.setWinner(test);
		assertEquals(battle.getWinner(), test);
	}
	
	@Test
	public void testSetAndGetDefeated(){
		Monster test = new Monster();
		battle.setDefeated(test);
		assertEquals(battle.getDefeated(), test);
	}
	
	@Test
	public void testAttack(){
		assertTrue(battle.getDefeated().getStats().getHealth() <= 0);
	}
	
	@Test
	public void testDeclareWinner(){
		assertEquals("Delegator", battle.getWinner().getName());
	}
	
	
	

}
