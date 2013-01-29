package root;

import java.util.Calendar;
import java.util.Random;

/**
 * A monster in the server, it has an owner, stats and multiple cash values.
 * @author Csoma Silhab
 *
 */
public class Monster {
	
	//The owner, its records and cash values of the monster
	private Integer id, ownerId;
	private String name;
	private Breed breed;	//extra
	private Status status;	//extra
	private int cashPrize = 500, cashSell = 0, cashBreed = 0, wins = 0, losses = 0;
	//Monster stat related variables
	private Calendar birth;
	private float health, strength, defence, aggression, fertility;
	private float max_health_bonus = 20;
	private float max_strDef_bonus = 30;
	private float max_fertAggr_bonus = 4;
	
	public Monster(){}
	
	/**
	 * Creates a monster with a given name and owner, birth date is set to the current time.
	 * @param owner Id of the owner of the monster
	 * @param name Name of the monster
	 */
	public Monster(Integer owner, String name){
		this.name = name;
		ownerId = owner;
		Calendar birth = Calendar.getInstance();
		generateStats(birth);
	}

	/**
	 * Generates the stats of a monster when it is created.
	 * @param birth 
	 * @param breed The breed type of the monster.
	 */
	public void generateStats(Calendar birth){
		this.birth = birth;
		generateBases();
		generateGenetics();
	}
	
	/**
	 * Generates the genetic stats ranging from 0 to the max_genetic_bonus.
	 */
	public void generateGenetics() {
		Random rn = new Random();
		health += rn.nextInt((int) max_health_bonus);
		rn = new Random();
		strength += rn.nextInt((int) max_strDef_bonus);
		rn = new Random();
		defence += rn.nextInt((int) max_strDef_bonus);
		rn = new Random();
		aggression += rn.nextInt((int) max_fertAggr_bonus);
		rn = new Random();
		fertility += rn.nextInt((int) max_fertAggr_bonus);
	}

	/**
	 * Generates the base stats based on the breed of the monster.
	 * @param breed The breed of the monster.
	 */
	public void generateBases(){
		health = 50;
		strength = 45;
		defence = 45;
		aggression = 0;
		fertility = 0;
		
	}
	
	/**
	 * Changes the health and strength of the monster depending on its age.
	 */
	public void ageHealth(){
		Calendar current = Calendar.getInstance();
		int age = (int) calculateAge(current, birth);
		float hp = this.health;
		hp = (float) (hp * ((10+2.7*age)*Math.exp(age*(-0.09)))/10);
		this.setHealth(hp);
		float str = this.strength;
		str = (float) (str * ((10+2.7*age)*Math.exp(age*(-0.09)))/10);
		this.setStrength(str);
	}
	
	/**
	 * 
	 * @param current Current time and date
	 * @param birth Birth date of the monster
	 * @return Monster's age
	 */
	public long calculateAge(Calendar current, Calendar birth){
		Calendar date = (Calendar) current.clone();
		long days = 0;
		while(date.before(birth)){
			date.add(Calendar.DAY_OF_MONTH, 1);
			days++;
		}
		return days;
	}
	
	public float getHealth(){
		return health;
	}
	
	public int getCashSell() {
		return cashSell;
	}

	public void setCashSell(int cashSell) {
		this.cashSell = cashSell;
	}

	public int getCashBreed() {
		return cashBreed;
	}

	public void setCashBreed(int cashBreed) {
		this.cashBreed = cashBreed;
	}
	
	public Calendar getBirth() {
		return birth;
	}
	public void setBirth(Calendar birth) {
		this.birth = birth;
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
