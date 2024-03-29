package root;

public class Request {
	
	private Integer id, from, to, fromMon, toMon;
	private RequestType type;
	private RequestState state;
	
	public Request(Integer u1id, Integer u2id, Integer m1id, Integer m2id, RequestType type){
		this.setFrom(u1id);
		this.setFromMon(m1id);
		this.setTo(u2id);
		this.setToMon(m2id);
		this.setType(type);
	}
	public Request(Integer u1id, Integer u2id, RequestType type){//for request type friend
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
