//package Java.src.root;
package root;

import java.util.ArrayList;
import java.util.Random;

import Java.src.root.Status;

/**
 * This class handles battles done by 2 users. Their monsters try to inflict damage
 * each round and the first one to have 0 health is the loser.
 */

public class Battle {

	private User from, to;
	private Monster fromMon, toMon, defeated = null, winner;
	private ArrayList<String> messages;
	/**
	* Variation of the amount of damage done.
	*/
	int dmg_variation = 10;		
	/**
	* The percentage of extra damage done in aggression attacks.
	*/
	float extra_dmg = 20;

	
	public Battle(){}
	
	/**
	 * Initiates a battle between the given monsters.
	 * @param u1 The owner of monster 1.
	 * @param u2 The owner monster 2.
	 * @param m1 The monster of the user who made the request.
	 * @param m2 The monster of the user who received request.
	 */
	public ArrayList<String> doBattle(User u1, User u2, Monster m1, Monster m2){
		this.to = u1;
		this.from = u2;
		this.toMon = m1;
		this.fromMon = m2;
		System.out.println("It's "+toMon.getName()+" vs "+fromMon.getName()+"!\n");
		System.out.println(toMon.getName()+" has: "+toMon.getHealth()+" "+
		toMon.getStrength()+" "+toMon.getDefence()+" "+toMon.getAggression()+"\n");
		System.out.println(fromMon.getName()+" has: "+fromMon.getHealth()+" "+
				fromMon.getStrength()+" "+fromMon.getDefence()+" "+fromMon.getAggression()+"\n");
		while (defeated == null){
			attack(toMon, fromMon);
			if (defeated == null){
				attack(fromMon, toMon);
			}
		}
		
		return declareWinner();
	}
	
	/**
	 * Declares the winner of this battle and updates their status and records.
	 * @return A set of messages about the battle
	 */
	private ArrayList<String> declareWinner() {
		
		messages = new ArrayList<String>();
		
		winner.setWins(winner.getWins()+1);
		messages.add("UPDATE 'monsters' SET 'wins'='"+(winner.getWins()+1)+"' WHERE'monsterID'='"+winner.getId()+"'");
		winner.setStatus(Status.HAPPY);
		
		defeated.setLosses(defeated.getLosses()+1);
		defeated.setHealth(0);
		defeated.setStatus(Status.DEAD);
		messages.add("UPDATE 'monsters' SET 'losses'='"+(defeated.getLosses()+1)+"', 'health'='0', 'status'='DEAD' WHERE'monsterID'='"+defeated.getId()+"'");
		
		
		if(winner == toMon){
			to.setCash(to.getCash()+fromMon.getCashPrize());
			messages.add("UPDATE 'user' SET 'Cash'='"+(to.getCash()+winner.getCashPrize())+"' WHERE'UserID'='"+winner.getOwnerId()+"'");
			messages.add("INSERT INTO 'notifications'('type','UserID1',UserID2','MonsterID1','MonsterID2','state','cash','outcome') VALUES('battle_results','"+from.getId()+"','"+to.getId()+"','"+fromMon.getId()+"','"+toMon.getId()+"','pending','"+winner.getCashPrize()+"','you won'");
		} else {
			from.setCash(from.getCash()+toMon.getCashPrize());
			messages.add("UPDATE 'user' SET 'Cash'='"+(from.getCash()+winner.getCashPrize())+"' WHERE'UserID'='"+winner.getOwnerId()+"'");
			messages.add("INSERT INTO 'notifications'('type','UserID1',UserID2','MonsterID1','MonsterID2','state','cash','outcome') VALUES('battle_results','"+from.getId()+"','"+to.getId()+"','"+fromMon.getId()+"','"+toMon.getId()+"','pending','0','you lost'");
		}
		messages.add("INSERT INTO 'result'('type','userID1','userID2','monsterID1','monsterID2','userwon','monsterwon','winmessage','lostmessage','cash','baby1','baby2','baby3','baby4','baby5','baby6','baby7','baby8','baby9','baby10')" +
				"VALUES('battle_results','"+to.getId()+"','"+from.getId()+"','"+toMon.getId()+"','"+fromMon.getId()+"','"+winner.getOwnerId()+"','"+winner.getId()+"','congratulations you won the fight','sorry you lost the fight','"+winner.getCashPrize()+"','NULL','NULL','NULL','NULL','NULL','NULL','NULL','NULL','NULL','NULL'");
		
		return messages;
	}

	/**
	 * One monster attacks the other, checks if they reached 0 health.
	 * @param atkMon The attacking monster
	 * @param defMon The defending monster
	 */

	public void attack(Monster atkMon, Monster defMon) {
		System.out.println(atkMon.getName()+" attacks!\n");
		// Defence chance of evading
		if (atkSuccess(defMon)) {
			// Base damage with variation
			Random rnd = new Random();
			float dmg = atkMon.getStrength();
			dmg += rnd.nextInt(dmg_variation);
			System.out.println(dmg+" damage!");
			// Chance for extra damage
			float extra_atk = atkMon.getAggression();
			float more_dmg = 0;
			for (int i = 0; i < extra_atk; i++) {
				System.out.println(atkMon.getName()+" goes again!\n");
				if (atkSuccess(defMon)) {
					more_dmg += dmg * (extra_dmg / 100);
					System.out.println(more_dmg+" damage!\n");
				}
			}

			// Deal damage, check if defMon is dead
			float health = defMon.getHealth() - (dmg + more_dmg);
			defMon.setHealth(health);
			System.out.println(defMon.getName()+" has "+defMon.getHealth()+" HP left!\n");
			if (health <= 0) {
				System.out.println(defMon.getName()+" is KO!\n");
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
		System.out.println(defMon.getName()+" dodged!\n");
		return false;
	}
	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public Monster getFromMon() {
		return fromMon;
	}

	public void setFromMon(Monster fromMon) {
		this.fromMon = fromMon;
	}

	public Monster getToMon() {
		return toMon;
	}

	public void setToMon(Monster toMon) {
		this.toMon = toMon;
	}

	public Monster getDefeated() {
		return defeated;
	}

	public void setDefeated(Monster defeated) {
		this.defeated = defeated;
	}

	public Monster getWinner() {
		return winner;
	}

	public void setWinner(Monster winner) {
		this.winner = winner;
	}

}
