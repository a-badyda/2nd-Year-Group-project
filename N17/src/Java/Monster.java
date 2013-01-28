package Java;

import java.util.Date;

public class Monster {
	
	private Integer id, ownerId;
	private String name;
	private MonsterStats stats;
	private Breed breed;	//extra
	private Status status;	//extra
	private int cashPrize = 500, wins = 0, losses = 0;
	private Date birth;
	
	public Monster(){}
	
	Monster(Integer owner, String name, Date birth){
		this.name = name;
		ownerId = owner;
		this.birth = birth;
		stats = new MonsterStats(breed);
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
	public float getHealth() {
		return stats.getHealth();
	}
	public float getStrength() {
		return stats.getStrength();
	}
	public float getDefence() {
		return stats.getDefence();
	}
	public float getAggression() {
		return stats.getAggression();
	}
	public float getFertility() {
		return stats.getFertility();
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
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
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
