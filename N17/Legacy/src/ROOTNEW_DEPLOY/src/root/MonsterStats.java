package root;

import java.util.Calendar;
import java.util.Random;

/**
 * Holds all of the statistics of a monster. All stats have a base value depending on their
 * breed and a genetic value that is randomly assigned when they are born.
 */

public class MonsterStats {
	
	private float health, strength, defence, aggression, fertility;
	private float max_health_bonus = 20;
	private float max_strDef_bonus = 30;
	private float max_fertAggr_bonus = 4;
	private Calendar birth;
	
	/**
	 * Generates the stats of a monster when it is created.
	 * @param birth 
	 * @param breed The breed type of the monster.
	 */
	public MonsterStats(Calendar birth){
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
	
	public Calendar getBirth() {
		return birth;
	}
	public void setBirth(Calendar birth) {
		this.birth = birth;
	}

	public float getHealth(){
		return health;
	}
	
	public void ageHealth(){
		Calendar current = Calendar.getInstance();
		int age = (int) calculateAge(current, birth);
		float hp = health;
		hp = (float) (hp * ((10+2.7*age)*Math.exp(age*(-0.09))));
		setHealth(hp);
	}
	
	public long calculateAge(Calendar current, Calendar birth){
		Calendar date = (Calendar) current.clone();
		long days = 0;
		while(date.before(birth)){
			date.add(Calendar.DAY_OF_MONTH, 1);
			days++;
		}
		return days;
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
	

}
