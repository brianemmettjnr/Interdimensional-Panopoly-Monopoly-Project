package monopoly;

import java.util.ArrayList;

public class Player implements Playable {
	
	private static final int STARTING_BALANCE = 1500;
	
	private String name;
	private int balance;
	private ArrayList<Ownable> properties = new ArrayList<Ownable>();
	
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
	
	public void buyProperty(Ownable property)
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
}
