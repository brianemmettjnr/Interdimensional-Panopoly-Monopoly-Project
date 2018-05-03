package monopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import base.PersonOfInterest;
import interfaces.Locatable;

public class Board 
{
	private ArrayList<Locatable> locations = new ArrayList<Locatable>();
	private ArrayList<String> currentSet;
	private Group currentGroup;

	HashMap<Group, ArrayList<String>> possibleLocations = new HashMap<Group, ArrayList<String>>();
	private int index = 0;
	
	private int minPrice = 50;
	private int maxPrice = 100;
	
	Board(int numLocations)
	{
		int priceIncrease = 44/ numLocations;
		int setIndex = 0;

		
		generateLocations();
		currentGroup = getNextSet();
		
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
					
					int buildCost = 0;
					//first side
					if(locations.size() < numLocations/4)
						buildCost = 50;
					else if(locations.size() < numLocations/2)
						buildCost = 100;
					else if(locations.size() < (numLocations*3)/4)
						buildCost = 150;
					else
						buildCost = 200;					
					
					if(setIndex == currentSet.size() || setIndex == 3)
					{
						currentGroup = getNextSet();
						//System.out.print(currentGroup.getIdentifier());
						//System.out.print(setIndex);
						setIndex = 0;
					}
					
					locations.add(new InvestmentProperty(currentSet.get(setIndex), price, generateRentArray(price), (price/2), buildCost, currentGroup));
					
					setIndex++;

					minPrice += priceIncrease * 10;
					maxPrice += priceIncrease * 15;
				}
			}
		}
	}
	
	private void generateLocations()
	{
		HashMap<String, String> temp = new HashMap<String, String>();
		ArrayList<String> locs = new PersonOfInterest().locations;
		String[] splitLoc = null;
		
		for(String loc : locs)
		{
			System.out.println(loc);
			splitLoc = loc.split(" \\(");
			if(!splitLoc[0].equals("") && splitLoc[1] != null)
			{
				//key = address, value = group
				temp.put(splitLoc[0], splitLoc[1].replaceAll("\\)", ""));
			}
		}		
		
		System.out.println(temp);
		
		ArrayList<String> addresses = new ArrayList<String>();
		
		for(String group : temp.values())
		{
			for(String address : temp.keySet())
			{
				if(temp.get(address).equals(group))
				{
					System.out.print(group + " " + address);
					addresses.add(address);
				}
			}
			
			System.out.print("\n");
			possibleLocations.put(new Group(group), addresses);
			addresses = new ArrayList<String>();
		}
	}
	
	private Group getNextSet()
	{
		Group nextKey = (Group) possibleLocations.keySet().toArray()[index];
		currentSet = possibleLocations.get(nextKey);
		index++;
		return nextKey;
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
		return ThreadLocalRandom.current().nextInt(minPrice, maxPrice + 1);
		
	}
	
	//REFERENCE: http://www.jdawiseman.com/papers/trivia/monopoly-rents.html
	public int[] generateRentArray(int price)
	{
		int rent = price/10 - 4;
		int oneHouseRent = (price/2) - 20;

		int[] array = {rent, oneHouseRent, 3*oneHouseRent, 6*oneHouseRent, 210 + 7*oneHouseRent, 300 + 5*oneHouseRent};
		
		if(price >= 150)
		{
			array[5] += 300;
		}
		
		return array;
	}
	
	public int getNumLocations()
	{
		return locations.size();
	}
	
	public int getJailLocation()
	{
		return getNumLocations()/4;
	}
}
