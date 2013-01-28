package Java.src.root;

import java.util.Calendar;
import java.util.Date;

public class Monster {
	
	private Integer id, ownerId;
	private String name;
	private MonsterStats stats;
	private Breed breed;	//extra
	private Status status;	//extra
	private int cashPrize = 500, wins = 0, losses = 0;
	private Calendar birth;
	
	public Monster(){}
	
	public Monster(Integer owner, String name){
		this.name = name;
		ownerId = owner;
		Calendar birth = Calendar.getInstance();
		stats = new MonsterStats(birth);
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
	public MonsterStats getStats() {
		return stats;
	}

	public void setStats(MonsterStats stats) {
		this.stats = stats;
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
	public Calendar getBirth() {
		return birth;
	}
	public void setBirth(Calendar birth) {
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
