package monopoly;

import java.util.ArrayList;

public class Player implements Playable {
	
	private static final int STARTING_BALANCE = 1500;
	
	private String name;
	private int balance;
	private ArrayList<Rentable> properties = new ArrayList<Rentable>();
	
	Player(String name)
	{
		this.name = name;
		balance = STARTING_BALANCE;
	}

	@Override
	public String getIdentifier() 
	{
		return name;
	}
	
	public void buyProperty(Rentable property)
	{
		balance -= property.getPrice();
		
		property.setOwner(this);
		properties.add(property);
	}

	@Override
	public int getNetWorth() 
	{
		int netWorth = balance;
		
		for(Ownable p: properties)
		{
			netWorth += p.getPrice();
		}
		
		return netWorth;
	}

	@Override
	public int getBalance() 
	{
		return balance;
	}
	
	public boolean ownsGroup(Group group)
	{
		boolean isGroupOwner = true;
		
		for(Groupable property: group.getMembers())
		{
			if(!properties.contains(property))
			{
				isGroupOwner = false;
			}
		}
		
		return isGroupOwner;
	}
	
	public int ownedStations()
	{
		int stations = 0;
		
		for(Rentable p: properties)
		{
			if(p.getGroup().getIdentifier() == "stations")
			{
				stations++;
			}
		}
		
		return stations;
	}
	
	public int ownedUtilities()
	{
		int utilities = 0;
		
		for(Rentable p: properties)
		{
			if(p.getGroup().getIdentifier() == "utilities")
			{
				utilities++;
			}
		}
		
		return utilities;
	}
}
