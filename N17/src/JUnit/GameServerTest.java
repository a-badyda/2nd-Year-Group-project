package JUnit;

import static org.junit.Assert.*;

import org.junit.*;
import root.GameServer;

public class GameServerTest {
	
	GameServer server;

	@Before
	public void setup() {
		server = new GameServer();
		
	}

}
