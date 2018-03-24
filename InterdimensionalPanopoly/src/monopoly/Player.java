package monopoly;

import java.util.ArrayList;

import interfaces.Groupable;
import interfaces.Ownable;
import interfaces.Playable;
import interfaces.Rentable;

import javax.swing.*;

public class Player implements Playable {
	
	private static final int STARTING_BALANCE = 1500;
	
	private String name;
	private int balance;
	private ArrayList<Rentable> properties = new ArrayList<Rentable>();
	private int position;
	private JLabel icon=null;
	
	Player(String name,JLabel icon)
	{
		this.icon=icon;
		this.name = name;
		balance = STARTING_BALANCE;
		position = 0;
	}

	@Override
	public String getIdentifier() 
	{
		return name;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public void move(int squares, boolean clockwise)
	{
		if(clockwise)
		{
			position += squares;
			
			//if passed GO
			if(position > Panopoly.getNumLocations())
			{
				earn(200);
				position = position % Panopoly.getNumLocations();
			}
		}
		
		else
		{
			position -= squares;
			
			//if passed GO
			if(position < 0)
			{
				earn(200);
				position = position % Panopoly.getNumLocations();
			}
		}
	}
	
	public void pay(int payment)
	{
		balance -= payment;
	}
	
	public void earn(int earnings)
	{
		balance += earnings;
	}
	
	public void buyProperty(Rentable property, int price)
	{
		pay(price);
		
		property.setOwner(this);
		properties.add(property);
	}
	
	//sell property to other player
	public void sellProperty(Rentable property, int sellPrice, Player newOwner)
	{
		balance += sellPrice;
		properties.remove(property);
		
		newOwner.buyProperty(property, sellPrice);
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
