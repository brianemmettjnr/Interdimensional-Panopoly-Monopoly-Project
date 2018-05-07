package monopolyTest;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import monopoly.Player;
import monopoly.Group;
import monopoly.InvestmentProperty;

public class RentalPropertyTest {

	Group group = new Group("Group");
	InvestmentProperty prop1 = new InvestmentProperty("prop1", 10, new int[] {1, 2}, 5, 1, group);
	InvestmentProperty prop2 = new InvestmentProperty("prop2", 20, new int[] {1, 2}, 10, 2, group);
	InvestmentProperty prop3 = new InvestmentProperty("prop3", 30, new int[] {1, 2}, 15, 3, group);
	Player test = new Player("testPlayer", 0, 0,null);

	@Test
	public void testGetPrice() {
		assertEquals(10, prop1.getPrice());
	}
	
	@Test
	public void testGetMortgagePrice() {
		assertEquals(5, prop1.getMortgageAmount());
	}
	
	@Test
	public void testGetRedeemPrice() {
		assertEquals(5, prop1.getRedeemAmount());
	}
	
	@Test
	public void testIsMortgaged() {
		assertEquals(false, prop1.isMortgaged());
		
		test.buyProperty(prop1, 0);
		prop1.mortgage();
		assertEquals(true, prop1.isMortgaged());
		
		prop1.redeem();
		assertEquals(false, prop1.isMortgaged());
	}
	
	@Test
	public void testValue() {
		assertEquals(10, prop1.getValue());
		
		test.buyProperty(prop1, 0);
		prop1.mortgage();
		assertEquals(5, prop1.getValue());
		
		prop1.redeem();
		assertEquals(10, prop1.getValue());
	}
		
	@Test
	public void testGetRentalAmount() {
		test.buyProperty(prop1, prop1.getPrice());
		assertEquals(1, prop1.getRentalAmount());
		
		test.buyProperty(prop2, prop2.getPrice());
		assertEquals(1, prop1.getRentalAmount());
		
		test.buyProperty(prop3, prop3.getPrice());
		prop2.build();
		assertEquals(2, prop2.getRentalAmount());
	}

	@Test
	public void testGroup() {
		assertEquals("Group", prop1.getGroup().getIdentifier());
	}
	
	@Test
	public void testIsOwned() {
		assertEquals(false, prop1.isOwned());
		
		test.buyProperty(prop1, prop1.getPrice());
		assertEquals(true, prop1.isOwned());
		
		prop1.reset();
		assertEquals(false, prop1.isOwned());
	}
	
	@Test
	public void testOwnership() {
		assertEquals(null, prop1.getOwner());
		
		test.buyProperty(prop1, prop1.getPrice());
		assertEquals(test, prop1.getOwner());
	}
}
