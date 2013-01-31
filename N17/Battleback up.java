package root;

import java.util.ArrayList;
import java.util.Random;

import root.Monster;

/**
 * This class handles battles done by 2 users. Their monsters try to inflict damage
 * each round and the first one to have 0 health is the loser.
 */

public class Battle {

	private User from, to;
	private Monster fromMon, toMon, defeated = null, winner;
	private ArrayList<String> messages;
	private int winnerID;
	/**
	* Variation of the amount of damage done.
	*/
	int dmg_variation = 10;		
	/**
	* The percentage of extra damage done in aggression attacks.
	*/
	float extra_dmg = 20;
	
	/**
	 * Initiates a battle between the given monsters.
	 * @param u1 The owner of monster 1.
	 * @param u2 The owner monster 2.
	 * @param m1 The monster of the user who made the request.
	 * @param m2 The monster of the user who received request.
	 */
	
	public Battle(){
		
	}
	
	public void clean(){
		to = null;
		from = null;
		toMon = null;
		fromMon = null;
		winner = null;
		defeated = null;
		messages = new ArrayList<String>();
		
		
	}
	
	
	public ArrayList<String> doBattle(User u1, User u2, Monster m1, Monster m2){
		clean();
		this.to = u1;
		this.from = u2;
		this.toMon = m1;
		this.fromMon = m2;
		
		
		while (defeated == null){
			attack(toMon, fromMon);
			if (defeated == null){
				attack(fromMon, toMon);
			}
		}
		
		return declareWinner();
	}
	
	private ArrayList<String> declareWinner() {
		
		messages = new ArrayList<String>();
		
		winner.setWins(winner.getWins()+1);
		messages.add("UPDATE monsters SET wins='"+(winner.getWins()+1)+"' WHERE monsterID='"+winner.getId()+"'");
		winner.setStatus(Status.HAPPY);
		
		defeated.setLosses(defeated.getLosses()+1);
		defeated.setHealth(0);
		defeated.setStatus(Status.DEAD);
		messages.add("UPDATE monsters SET losses='"+(defeated.getLosses()+1)+"', health='0', status='DEAD', ownerID='102' WHERE monsterID='"+defeated.getId()+"'");
		
		
		if(winner == toMon){
			messages.add("INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES ('battle_results','"+from.getId()+"','"+to.getId()+"','"+fromMon.getId()+"','"+toMon.getId()+"','pending','"+winner.getCashPrize()+"','you won')");
			messages.add("INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES ('battle_results','"+to.getId()+"','"+from.getId()+"','"+toMon.getId()+"','"+fromMon.getId()+"','pending','0','you lost')");
			
		} else {
			messages.add("INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES('battle_results','"+from.getId()+"','"+to.getId()+"','"+fromMon.getId()+"','"+toMon.getId()+"','pending','0','you lost')");
			messages.add("INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES ('battle_results','"+to.getId()+"','"+from.getId()+"','"+toMon.getId()+"','"+fromMon.getId()+"','pending','"+winner.getCashPrize()+"','you won')");
		}
		
		messages.add("INSERT INTO result(type,userID1,userID2,monsterID1,monsterID2,userwon,monsterwon,winmessage,lostmessage,cash,baby1,baby2,baby3,baby4,baby5,baby6,baby7,baby8,baby9,baby10)" +
				" VALUES ('battle_results','"+to.getId()+"','"+from.getId()+"','"+toMon.getId()+"','"+fromMon.getId()+"','"+winner.getOwnerId()+"','"+winner.getId()+"','congratulations you won the fight','sorry you lost the fight','"+winner.getCashPrize()+"', 0,0,0,0,0,0,0,0,0,0)");
		messages.add("INSERT INTO result(type,userID1,userID2,monsterID1,monsterID2,userwon,monsterwon,winmessage,lostmessage,cash,baby1,baby2,baby3,baby4,baby5,baby6,baby7,baby8,baby9,baby10)" +
				" VALUES ('battle_results','"+from.getId()+"','"+to.getId()+"','"+fromMon.getId()+"','"+toMon.getId()+"','"+winner.getOwnerId()+"','"+winner.getId()+"','congratulations you won the fight','sorry you lost the fight','"+winner.getCashPrize()+"', 0,0,0,0,0,0,0,0,0,0)");
		
		winnerID = winner.getOwnerId();
		
		return messages;
	}
	
	public int getwinnerMonsterID(){
		return winner.getId();
	}

	public int getWinnerID() {
		return winnerID;
	}

	public void attack(Monster atkMon, Monster defMon) {
		// Defence chance of evading
		if (atkSuccess(defMon)) {
			// Base damage with variation
			Random rnd = new Random();
			float dmg = atkMon.getStrength();
			dmg += rnd.nextInt(dmg_variation);

			// Chance for extra damage
			float extra_atk = atkMon.getAggression();
			int more_dmg = 0;
			for (int i = 0; i < extra_atk; i++) {
				if (atkSuccess(defMon)) {
					more_dmg += dmg * (extra_dmg / 100);
				}
			}

			// Deal damage, check if defMon is dead
			float health = defMon.getHealth() - (dmg + more_dmg);
			defMon.setHealth(health);
			if (health <= 0) {
				defeated = defMon;
				winner = atkMon;
			}
		}
	}

	/**
	 * A defending monster has a defence chance of evading an attack.
	 * 
	 * @param defMon The defending monster
	 * @return True if the attack connected
	 */
	public boolean atkSuccess(Monster defMon) {
		Random rnd = new Random();
		int rn = rnd.nextInt(100);
		float chance = defMon.getDefence();
		if (rn <= chance) {
			return true;
		}
		return false;
	}

}
