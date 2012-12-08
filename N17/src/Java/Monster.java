package Java;

import java.util.Calendar;
import java.util.Date;

/**
 * Defines all monsters in the MonsterMash game, a monster can age as well as
 * update its own battle record. Its statistics may change as it ages and battles.
 */

public class Monster {
	
	//Ids are for linking tables in the database and are not used elsewhere.
	private Integer id, ownerId;
	private String name;
	private MonsterStats stats;
	private Breed breed;
	private Status status;
	//Cash prize is the amount a user wins when they defeat this monster.
	private int cashPrize, wins, losses;
	
	public Monster(){}
	
	/**
	 * Creates a new monster with the given parameters.
	 * @param name The name of the monster.
	 * @param breed The breed type of the monster.
	 * @param birth The birth date of the monster.
	 */
	public Monster(String name, Breed breed, Date birth){
		this.name=name;
		this.breed=breed;
		stats = new MonsterStats(breed);
		Calendar now = Calendar.getInstance();
		stats.setBirth(now);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setStats(MonsterStats stats){
		this.stats = stats;
	}
	
	public MonsterStats getStats(){
		return stats;
	}
	
	public Breed getBreed() {
		return breed;
	}
	public void setBreed(Breed breed) {
		this.breed = breed;
	}
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getCashPrize() {
		return cashPrize;
	}
	public void setCashPrize(int cashPrize) {
		this.cashPrize = cashPrize;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}

	
}
