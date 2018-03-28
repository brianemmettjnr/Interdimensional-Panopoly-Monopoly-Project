package monopoly;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import interfaces.Locatable;

public class Board 
{
	private ArrayList<Locatable> locations = new ArrayList<Locatable>();
	private Group brown = new Group("brown");
	
	Board(int numLocations)
	{
		for(int i = 0; i < numLocations; i++)
		{
			if(i == 0)
				locations.add(new NamedLocation("GO"));
			else if(i == numLocations/4)
				locations.add(new NamedLocation("Jail/Just Visiting"));
			else if(i == numLocations/4 + 2)
				locations.add(new Utility("Utility1"));
			else if(i == (numLocations*3)/4)
				locations.add(new NamedLocation("Go to Jail"));
			else if(i == ((numLocations*3)/4)-2)
				locations.add(new Utility("Utility2"));
			else if(i == numLocations/2)
				locations.add(new NamedLocation("Free Parking"));
			else if(i == 4)
				locations.add(new TaxableProperty("Income Tax", 200));
			else if(i == numLocations-2)
				locations.add(new TaxableProperty("Super Tax", 100));
			else
			{
				if(ThreadLocalRandom.current().nextInt(0, 5 + 1) == 5)
					locations.add(new Station("Surname Station"));
				else
					locations.add(new InvestmentProperty("Investment", 200, new int[] {1, 2}, 100, 10, brown));
			}
		}
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
