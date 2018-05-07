package monopolyTest;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import monopoly.Player;
import monopoly.Utility;

public class UtilityTest {

	Utility utility1 = new Utility("utility1");
	Utility utility2 = new Utility("utility2");
	Player test = new Player("testPlayer", 0, 0,null);

	@Test
	public void testGetPrice() {
		assertEquals(150, utility1.getPrice());
	}

	@Test
	public void testUtilityGroup() {
		assertEquals("Utilities", utility1.getGroup().getIdentifier());
	}
	
	@Test
	public void testIsOwned() {
		assertEquals(false, utility1.isOwned());
		
		test.buyProperty(utility1, utility1.getPrice());
		assertEquals(true, utility1.isOwned());
	}
	
	@Test
	public void testOwnership() {
		assertEquals(null, utility1.getOwner());
		
		test.buyProperty(utility1, utility1.getPrice());
		assertEquals(test, utility1.getOwner());
	}
}
