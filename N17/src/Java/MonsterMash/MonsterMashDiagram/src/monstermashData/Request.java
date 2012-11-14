package monstermashData;

public class Request {
	
	private User from, to;
	private Monster fromMon, toMon;
	private RequestType type;
	private RequestState state;
	
	public Request(){
		
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
