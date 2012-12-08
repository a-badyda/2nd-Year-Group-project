package Java;

import java.util.Random;

/**
 * Holds all of the statistics of a monster. All stats have a base value depending on their
 * breed and a genetic value that is randomly assigned when they are born.
 */

public class MonsterStats {
	
	private float base_health, base_strength, base_defence, base_aggression, base_fertility;
	private float gen_health, gen_strength, gen_defence, gen_aggression, gen_fertility;
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
		return (base_health + gen_health);
	}
	
	public float getStrength(){
		return (base_strength + gen_strength);
	}
	
	public float getDefence(){
		return (base_defence + gen_defence);
	}
	
	public float getAggression(){
		return (base_aggression + gen_aggression);
	}
	
	public float getFertility(){
		return (base_fertility + gen_fertility);
	}
	
	/**
	 * Generates the genetic stats ranging from 0 to the max_genetic_bonus.
	 */
	public void generateGenetics() {
		Random rn = new Random();
		gen_health = rn.nextInt((int) max_genetic_bonus);
		rn = new Random();
		gen_strength = rn.nextInt((int) max_genetic_bonus);
		rn = new Random();
		gen_defence = rn.nextInt((int) max_genetic_bonus);
		rn = new Random();
		gen_aggression = rn.nextInt((int) max_genetic_bonus);
		rn = new Random();
		gen_fertility = rn.nextInt((int) max_genetic_bonus);
	}

	/**
	 * Generates the base stats based on the breed of the monster.
	 * @param breed The breed of the monster.
	 */
	public void generateBases(Breed breed){
		switch (breed){
			case SLIME: 
				base_health=50;
				base_strength=10;
				base_defence=20;
				base_aggression=50;
				base_fertility=80;
				break;
				
			case BEAST: 
				base_health=100;
				base_strength=20;
				base_defence=5;
				base_aggression=80;
				base_fertility=60;
				break;
			
			case DEMON: 
				base_health=80;
				base_strength=25;
				base_defence=5;
				base_aggression=100;
				base_fertility=10;
				break;
				
			case SERPENT: 
				base_health=60;
				base_strength=20;
				base_defence=20;
				base_aggression=50;
				base_fertility=70;
				break;
				
			case DRAGON: 
				base_health=100;
				base_strength=15;
				base_defence=15;
				base_aggression=60;
				base_fertility=40;
				break;
				
			case GHOST: 
				base_health=100;
				base_strength=5;
				base_defence=30;
				base_aggression=70;
				base_fertility=50;
				break;
		}
	}
}
