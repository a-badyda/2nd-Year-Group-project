package root;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class handles breeding between 2 monsters. A number of new monsters is produced
 * based on their average fertility, the user who accepted the request gets all the
 * children.
 */

public class Breeding {

	
	
	/**
	 * Initiates a breeding session between two monters.
	 * @param u1 The owner of monster 1.
	 * @param u2 The owner of monster 2.
	 * @param m1 The monster that was put up for breeding.
	 * @param m2 The monster that was accepted for breeding.
	 */

	public Breeding(){}
	
	public ArrayList<String> Breed(User u1, User u2, Monster m1, Monster m2){
		User from, to;
		Monster fromMon, toMon;
		
		Date date = new Date();
	    SimpleDateFormat ft = 
	    new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		
		from = u1;
		fromMon = m1;
		to = u2;
		toMon = m2;
		ArrayList<String> children = new ArrayList<String>();
		
		
		//number of children
		int numberOfChildren = (int)((m1.getFertility()+m2.getFertility())/2);
		
		for (int i =0; i<numberOfChildren;i++){
			//child stats
			int defence = (int)(45+(( (45-m1.getStrength())+(45-m2.getDefence()))/2 )+ (-20 +(int)(Math.random()*((20-(-20))+1))));
			int strength =(int)(45+(( (45-m1.getStrength())+(45-m2.getDefence()))/2 )+ (-20 +(int)(Math.random()*((20-(-20))+1))));
			int agretion   = (int)((m1.getAggression()+m2.getAggression())/2)+ (-3 +(int)(Math.random()*((3-(-3))+1)));
			int fretillity = (int)((m1.getFertility()+m2.getFertility())/2)+ (-3 +(int)(Math.random()*((3-(-3))+1)));
			int health = (int)((m1.getHealth()+m2.getHealth())/2) + (-10 +(int)(Math.random()*((10-(-10))+1)));
			
			//query 
			String child = "INSERT INTO `monsterdata`.`monsters` " +
					"(`ownerID`, `name`, `health`, `strength`, `defence`, `aggression`, `fertility`, `breed`, `status`, `cashPrize`, `wins`, `losses`, `birth`) " +
					"VALUES ('"+u2.getId()+"', 'baby', '"+health+"', '"+strength+"', '"+defence+"', '"+agretion+"', '"+fretillity+"', 'BEAST', 'NORMAL', '10', '0', '0', '"+ft.format(date)+"');";
		    
			children.add(child);
			
			
		}
		
		return children;
	}
	
}
