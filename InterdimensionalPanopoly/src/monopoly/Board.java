package monopoly;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import interfaces.Locatable;

public class Board 
{
	private ArrayList<Locatable> locations = new ArrayList<Locatable>();
	private Group brown = new Group("brown");
	
	private int minPrice = 50;
	private int maxPrice = 100;
	
	Board(int numLocations)
	{
		//Fill in locations for board - hard-coded Named Locations/Tax and Utilities
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
			//one in 8 chance that property is station, 1 in 4 card or 5 in 8 investment
			else
			{
				int rnd = ThreadLocalRandom.current().nextInt(1, 8 + 1);
				if(rnd == 1)
					locations.add(new Station("Surname Station"));
				else if(rnd == 2)
					locations.add(new Chance());
				else if(rnd == 3)
					locations.add(new CommunityChest());
				else
				{
					int price = generatePrice();
					locations.add(new InvestmentProperty("Investment", price, generateRentArray(price), (price/2), 10, brown));
					minPrice += 10;
					maxPrice += 15;
				}
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
	
	public int generatePrice()
	{
		return maxPrice;
		
	}
	
	public int[] generateRentArray(int price)
	{
		int rent = price/10;
		int[] array = {rent, (int) (rent*1.1), (int) (rent*1.2), (int) (rent*1.3), (int) (rent*1.4), (int) (rent*1.5)};
		return array;
	}
	
	public int getNumLocations()
	{
		return locations.size();
	}
}
