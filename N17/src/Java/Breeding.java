package root;

import java.util.ArrayList;

/**
 * This class handles breeding between 2 monsters. A number of new monsters is produced
 * based on their average fertility, the user who accepted the request gets all the
 * children.
 */

public class Breeding {

	private User from, to;
	private Monster fromMon, toMon;
	private ArrayList<Monster> children;
	
	/**
	 * Initiates a breeding session between two monters.
	 * @param u1 The owner of monster 1.
	 * @param u2 The owner of monster 2.
	 * @param m1 The monster that was put up for breeding.
	 * @param m2 The monster that was accepted for breeding.
	 */
	public Breeding(User u1, User u2, Monster m1, Monster m2){
		this.from = u1;
		this.fromMon = m1;
		this.to = u2;
		this.toMon = m2;
		children = new ArrayList<Monster>();
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
