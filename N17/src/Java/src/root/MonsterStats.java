package root;

import java.util.Random;

/**
 * Holds all of the statistics of a monster. All stats have a base value depending on their
 * breed and a genetic value that is randomly assigned when they are born.
 */

public class MonsterStats {
	
	private float health, strength, defence, aggression, fertility;
	private float max_genetic_bonus = 20;

	/**
	 * Generates the stats of a monster when it is created.
	 * @param breed The breed type of the monster.
	 */
	public MonsterStats(Breed breed){
		generateBases(breed);
		generateGenetics();
	}
	
	public float getHealth(){
		return (health);
	}
	
	public float getStrength(){
		return (strength);
	}
	
	public float getDefence(){
		return (defence);
	}
	
	public float getAggression(){
		return (aggression);
	}
	
	public float getFertility(){
		return (fertility);
	}
	
	/**
	 * Generates the genetic stats ranging from 0 to the max_genetic_bonus.
	 */
	public void generateGenetics() {
		Random rn = new Random();
		health += rn.nextInt((int) max_genetic_bonus);
		rn = new Random();
		strength += rn.nextInt((int) max_genetic_bonus);
		rn = new Random();
		defence += rn.nextInt((int) max_genetic_bonus);
		rn = new Random();
		aggression += rn.nextInt((int) max_genetic_bonus);
		rn = new Random();
		fertility += rn.nextInt((int) max_genetic_bonus);
	}

	/**
	 * Generates the base stats based on the breed of the monster.
	 * @param breed The breed of the monster.
	 */
	public void generateBases(Breed breed){
		health = 20;
		strength = 10;
		defence = 10;
		aggression = 10;
		fertility = 10;
		
	}
}
