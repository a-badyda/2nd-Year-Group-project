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
	public Battle(User u1, User u2, Monster m1, Monster m2){
		this.to = u1;
		this.from = u2;
		this.toMon = m1;
		this.fromMon = m2;
		messages = new ArrayList<String>();
		
		while (defeated == null){
			attack(toMon, fromMon);
			if (defeated == null){
				attack(fromMon, toMon);
			}
		}
		declareWinner();
		
	}
	
	private void declareWinner() {
		winner.setWins(winner.getWins()+1);
		winner.setStatus(Status.HAPPY);
		
		defeated.setLosses(defeated.getLosses()+1);
		defeated.setHealth(0);
		defeated.setStatus(Status.DEAD);
		
		if(winner == toMon){
			to.setCash(to.getCash()+fromMon.getCashPrize());
		} else {
			from.setCash(from.getCash()+toMon.getCashPrize());
		}
	}

	public void attack(Monster atkMon, Monster defMon){
		Random rnd = new Random();
		int rn = rnd.nextInt(50);
		double chance = atkMon.getAggression() 
				+ rnd.nextInt(variation);
		if(rn <= chance){
			float dmg = (atkMon.getStrength() 
					- defMon.getDefence() + 1);
			dmg += rnd.nextInt(variation);
			float health = defMon.getHealth() - dmg;
			defMon.setHealth(health);
			if (health <= 0) {
				defeated = defMon;
				winner = atkMon;
			}
		}
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

	public ArrayList<String> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
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
}
