package Java;

import java.util.Date;

public class Monster {
	
	private Integer id, ownerId;
	private String name;
	private float health, strength, defence, aggression, fertility;
	private Breed breed;
	private Status status;
	private int cashPrize, wins, losses;
	private Date birth;
	
	Monster(){
		
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
		return health;
	}
	public void setHealth(float health) {
		this.health = health;
	}
	public float getStrength() {
		return strength;
	}
	public void setStrength(float strength) {
		this.strength = strength;
	}
	public float getDefence() {
		return defence;
	}
	public void setDefence(float defence) {
		this.defence = defence;
	}
	public float getAggression() {
		return aggression;
	}
	public void setAggression(float aggression) {
		this.aggression = aggression;
	}
	public float getFertility() {
		return fertility;
	}
	public void setFertility(float fertility) {
		this.fertility = fertility;
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
