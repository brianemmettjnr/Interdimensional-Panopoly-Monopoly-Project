package monopolyTest;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import monopoly.Panopoly;
import org.junit.Test;

import monopoly.Player;
import monopoly.Station;

public class StationTest {

	Station station1 = new Station("station1");
	Station station2 = new Station("station2");
	Station station3 = new Station("station3");
	Station station4 = new Station("station4");
	Player test = new Player("testPlayer", 0, 0,null);

	@Test
	public void testGetRentalAmount() {
		test.buyProperty(station1, station1.getPrice());
		assertEquals(25, station1.getRentalAmount());
		
		test.buyProperty(station2, station2.getPrice());
		assertEquals(50, station1.getRentalAmount());
		
		test.buyProperty(station3, station3.getPrice());
		assertEquals(100, station1.getRentalAmount());
		
		test.buyProperty(station4, station4.getPrice());
		assertEquals(200, station1.getRentalAmount());
	}

	@Test
	public void testStationGroup() {
		assertEquals("Stations", station1.getGroup().getIdentifier());
	}
	
	@Test
	public void testIsOwned() {
		assertEquals(false, station1.isOwned());
		
		test.buyProperty(station1, station1.getPrice());
		assertEquals(true, station1.isOwned());
	}
	
	@Test
	public void testOwnership() {
		assertEquals(null, station1.getOwner());
		
		test.buyProperty(station1, station1.getPrice());
		assertEquals(test, station1.getOwner());
	}
}
