package root;

import java.util.ArrayList;
import java.util.Random;

import Java.src.root.Monster;

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
	
	public Battle(){
		
	}
	
	
	/**
	 * Initiates a battle between the given monsters.
	 * @param u1 The owner of monster 1.
	 * @param u2 The owner monster 2.
	 * @param m1 The monster of the user who made the request.
	 * @param m2 The monster of the user who received request.
	 */
	public ArrayList<String> doBattle(User u1,User u2,Monster m1,Monster m2){
		
		ArrayList <String> messages = new ArrayList<String>();
		User user1 = u1;
		User user2 = u2;
		Monster user1Monster = m1;
		Monster user2Monster = m2;
		String query;
		//do while alive
		//(user1Monster.getHealth()>0)&&(user2Monster.getHealth()>0)
		while(true){
			
			//fight
			attack(user2Monster,user1Monster);
			//see if u1m dead ;
			if(user1Monster.getHealth()<=0){
				
				//set user2 cash = user2 cash +100
				query="UPDATE user SET Cash='"+Integer.toString((user1.getCash()+100))+"' WHERE UserID='"+user1.getId()+"'";			
				messages.add(query);
				//set moster1 dead+change to grave yard
				query="UPDATE monsters SET health='0', ownerID='102' WHERE monsterID='"+user1Monster.getId()+"'";
				messages.add(query);
				//set notifications
				query="INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES ('battle_results','"+user2.getId()+"','"+user1.getId()+"','"+user2Monster.getId()+"','"+user1Monster.getId()+"','pending','"+user2Monster.getCashPrize()+"','you won')";
				messages.add(query);
				query="INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES ('battle_results','"+user1.getId()+"','"+user2.getId()+"','"+user1Monster.getId()+"','"+user2Monster.getId()+"','pending','0','you lost')";
				messages.add(query);
				//set results
				query="INSERT INTO result(type,userID1,userID2,monsterID1,monsterID2,userwon,monsterwon,winmessage,lostmessage,cash,baby1,baby2,baby3,baby4,baby5,baby6,baby7,baby8,baby9,baby10)" +
						" VALUES ('battle_results','"+user1.getId()+"','"+user2.getId()+"','"+user1Monster.getId()+"','"+user2Monster.getId()+"','"+user1.getId()+"','"+user1Monster.getId()+"','congratulations you won the fight','sorry you lost the fight','"+user2Monster.getCashPrize()+"', 0,0,0,0,0,0,0,0,0,0)";
				messages.add(query);
						
				query="INSERT INTO result(type,userID1,userID2,monsterID1,monsterID2,userwon,monsterwon,winmessage,lostmessage,cash,baby1,baby2,baby3,baby4,baby5,baby6,baby7,baby8,baby9,baby10)" +
						" VALUES ('battle_results','"+user2.getId()+"','"+user1.getId()+"','"+user2Monster.getId()+"','"+user1Monster.getId()+"','"+user1.getId()+"','"+user1Monster.getId()+"','congratulations you won the fight','sorry you lost the fight','"+user2Monster.getCashPrize()+"', 0,0,0,0,0,0,0,0,0,0)";
				messages.add(query);
				break;
			}
			
			
			//fight
			attack(user1Monster,user2Monster);
			//see if u2m dead if break;
			if(user2Monster.getHealth()<=0){
				
				//set user1 cash = user1 cash +100
				query="UPDATE user SET Cash='"+Integer.toString((user2.getCash()+100))+"' WHERE UserID='"+user2.getId()+"'";			
				messages.add(query);
				//set monster2 dead+change to grave yard
				query="UPDATE monsters SET health='0', ownerID='102' WHERE monsterID='"+user2Monster.getId()+"'";
				messages.add(query);
				//set notifications
				query="INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES ('battle_results','"+user1.getId()+"','"+user2.getId()+"','"+user1Monster.getId()+"','"+user2Monster.getId()+"','pending','"+user1Monster.getCashPrize()+"','you won')";
				messages.add(query);
				query="INSERT INTO notifications(type,UserID1,UserID2,MonsterID1,MonsterID2,state,cash,outcome) VALUES ('battle_results','"+user2.getId()+"','"+user1.getId()+"','"+user2Monster.getId()+"','"+user1Monster.getId()+"','pending','0','you lost')";
				messages.add(query);
				//set results
				query="INSERT INTO result(type,userID1,userID2,monsterID1,monsterID2,userwon,monsterwon,winmessage,lostmessage,cash,baby1,baby2,baby3,baby4,baby5,baby6,baby7,baby8,baby9,baby10)" +
				" VALUES ('battle_results','"+user1.getId()+"','"+user2.getId()+"','"+user1Monster.getId()+"','"+user2Monster.getId()+"','"+user2.getId()+"','"+user2Monster.getId()+"','congratulations you won the fight','sorry you lost the fight','"+user1Monster.getCashPrize()+"', 0,0,0,0,0,0,0,0,0,0)";
				messages.add(query);
				query="INSERT INTO result(type,userID1,userID2,monsterID1,monsterID2,userwon,monsterwon,winmessage,lostmessage,cash,baby1,baby2,baby3,baby4,baby5,baby6,baby7,baby8,baby9,baby10)" +
				" VALUES ('battle_results','"+user2.getId()+"','"+user1.getId()+"','"+user2Monster.getId()+"','"+user1Monster.getId()+"','"+user2.getId()+"','"+user2Monster.getId()+"','congratulations you won the fight','sorry you lost the fight','"+user1Monster.getCashPrize()+"', 0,0,0,0,0,0,0,0,0,0)";
				messages.add(query);
				break;
			}
			
		}
		
		return messages;
	}
	
	/**
	 * Declares the winner of this battle and updates their status and records.
	 * @return A set of messages about the battle
	 */
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
	
	/**
	 * One monster attacks the other, checks if they reached 0 health.
	 * @param atkMon The attacking monster
	 * @param defMon The defending monster
	 */
	public void attack(Monster atkMon, Monster defMon) {
		// Defence chance of evading
		if (atkSuccess(defMon)) {
			// Base damage with variation
			Random rnd = new Random();
			float dmg = atkMon.getStrength();
			dmg += rnd.nextInt(10);

			// Chance for extra damage
			float extra_atk = atkMon.getAggression();
			int more_dmg = 0;
			for (int i = 0; i < extra_atk; i++) {
				if (atkSuccess(defMon)) {
					more_dmg += dmg * (20 / 100);
				}
			}
			// Deal damage, check if defMon is dead
			float health = defMon.getHealth() - (dmg + more_dmg);
			defMon.setHealth(health);
			
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
		if (chance > 100){
			chance = 100;
		}
		if (rn >= chance) {
			return true;
		}
		return false;
	}

}
