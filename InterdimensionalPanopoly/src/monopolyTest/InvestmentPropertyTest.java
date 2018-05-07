package monopolyTest;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import monopoly.Player;
import monopoly.Group;
import monopoly.InvestmentProperty;

public class InvestmentPropertyTest {

	Group group = new Group("Group");
	InvestmentProperty prop1 = new InvestmentProperty("prop1", 10, new int[] {1, 2}, 5, 1, group);
	InvestmentProperty prop2 = new InvestmentProperty("prop2", 20, new int[] {1, 2}, 10, 2, group);
	InvestmentProperty prop3 = new InvestmentProperty("prop3", 30, new int[] {1, 2}, 15, 3, group);
	Player test = new Player("testPlayer", 0, 0,null);
	
	@Before
	public void before()
	{
		test.buyProperty(prop1, prop1.getPrice());
		test.buyProperty(prop2, prop2.getPrice());
	}

	@Test
	public void testGetRentalAmount() {
		assertEquals(1, prop1.getRentalAmount());
		
		assertEquals(1, prop2.getRentalAmount());
		
		test.buyProperty(prop3, prop3.getPrice());
		prop2.build();
		assertEquals(2, prop2.getRentalAmount());
	}
	
	@Test
	public void testBuildPrice() {

		assertEquals(1, prop1.getBuildPrice());
	}
	
	@Test
	public void testBuild() {

		assertEquals(0, prop1.getNumBuildings());
		
		prop1.build();
		assertEquals(1, prop1.getNumBuildings());
	}
	
	@Test
	public void testhasBuildings() {

		assertEquals(false, prop1.hasBuildings());
		
		prop1.build();
		assertEquals(true, prop1.hasBuildings());
	}
	
	@Test
	public void testValue() {
		assertEquals(prop1.getPrice(), prop1.getValue());
		
		prop1.build();
		
		assertEquals(prop1.getPrice() + prop1.getBuildPrice(), prop1.getValue());
	}
	
	@Test
	public void testDemolish() {

		prop1.build();
		assertEquals(1, prop1.getNumBuildings());
		
		prop1.demolish();
		assertEquals(0, prop1.getNumBuildings());
	}
}
