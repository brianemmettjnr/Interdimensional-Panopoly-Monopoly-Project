package monopoly;

import java.util.ArrayList;

import interfaces.Groupable;
import interfaces.Ownable;
import interfaces.Playable;
import interfaces.Rentable;

public class Player implements Playable {
	
	private static final int STARTING_BALANCE = 200;
	private int playerIndex;

	private String name;
	private int balance;
	private ArrayList<Rentable> properties = new ArrayList<>();
	private int position;
	private int imageIndex=0;
	private Panopoly panopoly;
	boolean canRoll = true;
	boolean rollComplete = false;
	private boolean inJail = false;
	int doubles;
	
	public Player(String name, int imageIndex, int playerIndex,Panopoly panopoly)
	{
		this.panopoly=panopoly;
		this.playerIndex = playerIndex;
		this.imageIndex=imageIndex;
		this.name = name;
		balance = STARTING_BALANCE;
		position = 0;
		doubles = 0;
	}

	@Override
	public String getIdentifier() 
	{
		return name;
	}

	 int getPosition()
	{
		return position;
	}

	 int getImageIndex()
	{
		return imageIndex;
	}

	public boolean move(int squares, boolean clockwise)
	{
		boolean passedGO = false;
		
		if(clockwise)
		{
			position += squares;
			//if they pass GO
			if(position>=panopoly.getBoard().getNumLocations()) {
				position = position - panopoly.getBoard().getNumLocations();
				passedGO = true;
				earn(200);
			}
			
			if(panopoly.getBoard().getLocation(position).getIdentifier() == "Go to Jail")
			{
				sendToJail();
				panopoly.gui.updateAction(getIdentifier() + " has landed on Go to Jail and been sent to Jail");
			}
		}
		
		else
		{
			position -= squares;
			//if they pass GO
			if(position<0) {
				position = position + panopoly.getBoard().getNumLocations();
				passedGO = true;
				earn(200);
			}

		}
		
		return passedGO;
	}
	
	public boolean isInJail()
	{
		return inJail;
	}
	
	public void sendToJail()
	{
		position = panopoly.getBoard().getJailLocation();
		inJail = true;
	}
	
	public void releaseFromJail()
	{
		inJail = false;
	}
	
	 void pay(int payment)
	{
		balance -= payment;
	}
	
	 void earn(int earnings)
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

	//If game ends early - get winner by net worth
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
	
	 boolean hasProperty()
	{
		return !properties.isEmpty();
	}
	
	 int ownedStations()
	{
		int stations = 0;
		
		for(Rentable p: properties)
		{
			if(p instanceof Station)
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
			if(p instanceof Utility)
			{
				utilities++;
			}
		}
		
		return utilities;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public ArrayList<Rentable> getProperties() {
		return properties;
	}
}