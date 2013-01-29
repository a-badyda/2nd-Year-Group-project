package root;

/**
 * Stores all the data related to requests, the users and monsters involved as well
 * as the type and state of the request.
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
	public Request(Integer u1id, Integer u2id, Integer m1id, Integer m2id, RequestType type){
		this.setFrom(u1id);
		this.setFromMon(m1id);
		this.setTo(u2id);
		this.setToMon(m2id);
		this.setType(type);
	}
	public Request(Integer u1id, Integer u2id, RequestType type){//for request type freind
		this.setFrom(u2id);
		this.setTo(u1id);
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
