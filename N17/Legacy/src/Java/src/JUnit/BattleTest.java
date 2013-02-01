package JUnit;

import static org.junit.Assert.*;
import org.junit.*;

import root.Battle;
import root.Monster;
import root.User;

public class BattleTest {
	
	Battle battle;

	@Before
	public void setup() {
		User chris = new User("Chris", "passWd");
		Monster delegator = new Monster(1, "Delegator");
		delegator.setHealth(1000);
		delegator.setStrength(100);
		User tux = new User("Tux", "delegate");
		Monster derp = new Monster(2, "Derp");
		
		battle = new Battle();
		battle.doBattle(chris, tux, delegator, derp);
	}
	

	@Test
	public void testAttack(){
		Monster mon1 = new Monster(1, "A");
		Monster mon2 = new Monster(2, "B");
		float hp = mon2.getHealth();
		battle.attack(mon1, mon2);
		assertNotSame(hp, mon2.getHealth());
	}
	
	@Test
	public void testAtkSuccess(){
		Monster defMon = new Monster(3,"C");
		defMon.setDefence(0);
		boolean hit = battle.atkSuccess(defMon);
		assertTrue(hit);
	}
	
	
	

}
