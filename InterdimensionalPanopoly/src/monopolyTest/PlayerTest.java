package monopolyTest;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import monopoly.Player;
import monopoly.Station;

public class PlayerTest {

	Player test = new Player("testPlayer", 0, 0,null);
	Station prop1 = new Station("Test Station");
	
	@Test
	public void testBalance() {
		assertEquals(1500, test.getBalance());
		
		test.buyProperty(prop1, 100);
		assertEquals(1400, test.getBalance());
	}
	
	@Test
	public void testBuyProperty() {

		test.buyProperty(prop1, 100);
		assertEquals(prop1, test.getProperties().get(0));
	}
	
	@Test
	public void testIdentifier() {

		assertEquals("testPlayer", test.getIdentifier());
	}
	
	@Test
	public void testIndex() {

		assertEquals(0, test.getPlayerIndex());
	}
	
	@Test
	public void testNetWorth() {
		assertEquals(1500, test.getNetWorth());

		test.buyProperty(prop1, 100);
		
		assertEquals(1400 + prop1.getValue(), test.getNetWorth());
	}
}
