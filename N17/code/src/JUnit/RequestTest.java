package JUnit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import root.Request;
import root.RequestState;
import root.RequestType;

public class RequestTest {
	
	Request req;
	
	@Before
	public void setup(){
		req = new Request(1,12,14,2,RequestType.BREED);
	}

	@Test
	public void testSetAndGetId() {
		req.setId(0);
		assertEquals(0, (int) req.getId());
	}
	
	@Test
	public void testSetAndGetFrom() {
		req.setFrom(1);
		assertEquals(1, (int) req.getFrom());
	}
	
	@Test
	public void testSetAndGetTo() {
		req.setTo(2);
		assertEquals(2, (int) req.getTo());
	}
	
	@Test
	public void testSetAndGetFromMon() {
		req.setFromMon(14);
		assertEquals(14, (int) req.getFromMon());
	}
	
	@Test
	public void testSetAndGetToMon() {
		req.setToMon(15);
		assertEquals(15, (int) req.getToMon());
	}
	
	@Test
	public void testSetAndGetType() {
		req.setType(RequestType.BATTLE);
		assertEquals(RequestType.BATTLE, req.getType());
	}
	
	@Test
	public void testSetAndGetStatus() {
		req.setState(RequestState.ACCEPTED);
		assertEquals(RequestState.ACCEPTED, req.getState());
	}

}
