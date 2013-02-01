package root;

import RequestState;
import RequestType;

/**
 * This class stores all data regarding requests; the users and monster 
 * involved and the type of the request.
 * @author Csoma Silhab
 *
 */

public class Request {
	
	private Integer id, from, to, fromMon, toMon;
	private RequestType type;
	private RequestState state;
	private String outcome;
	private int cash;
	
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	/**
	 * Creates a new battle or breed request between 2 users and monsters.
	 * @param u1id user 1
	 * @param u2id user 2
	 * @param m1id monster of user 1
	 * @param m2id monster of user 2
	 * @param type type of the request
	 */
	public Request(Integer u1id, Integer u2id, Integer m1id, Integer m2id, RequestType type){
		this.setFrom(u1id);
		this.setFromMon(m1id);
		this.setTo(u2id);
		this.setToMon(m2id);
		this.setType(type);
	}
	/**
	 * Creates a new friend request between 2 users.
	 * @param u1id requesting user
	 * @param u2id target user
	 * @param type request type
	 */
	public Request(Integer u1id, Integer u2id, RequestType type){//for request type freind
		this.setFrom(u1id);
		this.setTo(u2id);
		this.setType(type);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public Integer getFromMon() {
		return fromMon;
	}

	public void setFromMon(Integer fromMon) {
		this.fromMon = fromMon;
	}

	public Integer getToMon() {
		return toMon;
	}

	public void setToMon(Integer toMon) {
		this.toMon = toMon;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	public RequestState getState() {
		return state;
	}

	public void setState(RequestState state) {
		this.state = state;
	}
	

}
