package monopoly;

import java.util.ArrayList;

import interfaces.Locatable;

public class Board 
{
	private ArrayList<Locatable> locations = new ArrayList<Locatable>();
	private Group brown = new Group("brown");
	private Group stations = new Group("stations");
	
	Board(int numLocations)
	{
		for(int i = 0; i < numLocations; i++)
		{
			if(i == 0)
				locations.add(new NamedLocation("GO"));
			else if(i == numLocations/4)
				locations.add(new NamedLocation("Jail/Just Visiting"));
			else if(i == (numLocations*3)/4)
				locations.add(new NamedLocation("Go to Jail"));
			else if(i == numLocations/2)
				locations.add(new NamedLocation("Free Parking"));
			else if(i == 4)
				locations.add(new TaxableProperty("Income Tax", 200));
			else if(i == numLocations-3)
				locations.add(new TaxableProperty("Super Tax", 100));
			else
				locations.add(new NamedLocation(""));
		}
//		locations.add(new NamedLocation("GO"));
//		locations.add(new InvestmentProperty("Old Kent Rd", 60, new int[] {2,10,30,90,160,250}, 50, 50, brown));
//		locations.add(new NamedLocation("CommunityChest"));
//		locations.add(new InvestmentProperty("Whitechapel Rd", 60, new int[] {4,20,60,180,320,450}, 50, 50, brown));
//		locations.add(new TaxableProperty("Income Tax", 0, 200));
//		locations.add(new RentalProperty("King's Cross Station", 200, new int[] {25, 50, 100, 200}, 100, stations));
	}
	
	public Locatable getLocation(int squareLocation)
	{
		return locations.get(squareLocation);
	}

	public ArrayList<Locatable> getLocations()
	{
		return locations;
	}

	private Locatable newLocation() 
	{
		// TODO generate new location?
		String name = newPropertyName();
		return null;
	}
	
	private String newPropertyName()
	{
		//TODO generate property names?
		return null;
	}
	
	public int getNumLocations()
	{
		return locations.size();
	}
}
