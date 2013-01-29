package root;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class handles battles done by 2 users. Their monsters try to inflict damage
 * each round and the first one to have 0 health is the loser.
 */

public class Battle {

	private User from, to;
	private Monster fromMon, toMon, defeated = null, winner;
	private ArrayList<String> messages;
	int variation = 10;
	
	/**
	 * Initiates a battle between the given monsters.
	 * @param u1 The owner of monster 1.
	 * @param u2 The owner monster 2.
	 * @param m1 The monster of the user who made the request.
	 * @param m2 The monster of the user who received request.
	 */
	
	public Battle(){
		
	}
	
	public ArrayList<String> doBattle(User u1, User u2, Monster m1, Monster m2){
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
		messages.add("UPDATE monsters SET losses='"+(defeated.getLosses()+1)+"', health='0', status='DEAD' WHERE monsterID='"+defeated.getId()+"'");
		
		
		if(winner == toMon){
			to.setCash(to.getCash()+fromMon.getCashPrize());
			messages.add("UPDATE user SET Cash='"+(to.getCash()+winner.getCashPrize())+"' WHERE UserID='"+winner.getOwnerId()+"'");
			messages.add("INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES ('battle_results','"+from.getId()+"','"+to.getId()+"','"+fromMon.getId()+"','"+toMon.getId()+"','pending','"+winner.getCashPrize()+"','you won')");
		} else {
			from.setCash(from.getCash()+toMon.getCashPrize());
			messages.add("UPDATE user SET Cash='"+(from.getCash()+winner.getCashPrize())+"' WHERE UserID='"+winner.getOwnerId()+"'");
			messages.add("INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES('battle_results','"+from.getId()+"','"+to.getId()+"','"+fromMon.getId()+"','"+toMon.getId()+"','pending','0','you lost')");
		}
		messages.add("INSERT INTO result(type,userID1,userID2,monsterID1,monsterID2,userwon,monsterwon,winmessage,lostmessage,cash,baby1,baby2,baby3,baby4,baby5,baby6,baby7,baby8,baby9,baby10)" +
				" VALUES ('battle_results','"+to.getId()+"','"+from.getId()+"','"+toMon.getId()+"','"+fromMon.getId()+"','"+winner.getOwnerId()+"','"+winner.getId()+"','congratulations you won the fight','sorry you lost the fight','"+winner.getCashPrize()+"', 0,0,0,0,0,0,0,0,0,0)");
		
		return messages;
	}

	public void attack(Monster atkMon, Monster defMon){
		Random rnd = new Random();
		int rn = rnd.nextInt(50);
		double chance = atkMon.getAggression() + rnd.nextInt(variation);
		if(rn <= chance){
			float dmg = (atkMon.getStrength() - defMon.getDefence() + 1);
			dmg += rnd.nextInt(variation);
			float health = defMon.getHealth() - dmg;
			defMon.setHealth(health);
			if (health <= 0) {
				defeated = defMon;
				winner = atkMon;
			}
		}
	}

}
