package Java;

/**
 * This class stores the requests made by users. Each request has a type and state, 
 * along with information about the users and monsters involved.
 */

public class Request {
	
	private Integer id;
	private User from, to;
	private Monster fromMon, toMon;
	private RequestType type;
	private RequestState state;
	
	/**
	 * Creates a battle or breed request with the given parameters.
	 * @param u1 The user who made the request.
	 * @param u2 The user targeted by the request.
	 * @param m1 The monster of the user who made the request.
	 * @param m2 The monster of the user who received the request.
	 * @param type The type of the request.
	 * @return The request that was created.
	 */
	public Request(User u1, User u2, Monster m1, Monster m2, RequestType type){
		this.setFrom(u1);
		this.setFromMon(m1);
		this.setTo(u2);
		this.setToMon(m2);
		this.setType(type);
	}
	/**
	 * Creates a friend request with the given parameters.
	 * @param u1 The user who made the request.
	 * @param u2 The user targeted by the request.
	 * @param type
	 */
	public Request(User u1, User u2, RequestType type){
		this.setFrom(u1);
		this.setTo(u2);
		this.setType(type);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
