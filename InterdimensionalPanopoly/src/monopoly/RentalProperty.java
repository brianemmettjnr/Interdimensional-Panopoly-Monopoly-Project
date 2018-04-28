//RentalProperty: Train stations, Utilities

package monopoly;

import interfaces.Groupable;
import interfaces.Mortgageable;
import interfaces.Rentable;

public abstract class RentalProperty extends PrivateProperty implements Rentable, Mortgageable, Groupable
{
	protected int[] rent;
	private int mortgage;
	private Group group;
	private boolean mortgaged = false;
	
	public RentalProperty(String name, int price, int[] rent, int mortgageCost, Group group) {
		super(name, price);
		this.rent = rent;
		mortgage = mortgageCost;
		this.group = group;
		group.addMember(this);
	}

	//returns cost to mortgage
	@Override
	public int getMortgageAmount() 
	{
		return mortgage;
	}

	//returns if property is currently mortgaged
	@Override
	public boolean isMortgaged() 
	{
		return mortgaged;
	}

	@Override
	public String mortgage()
	{
		mortgaged = true;
		return "";
	}

	@Override
	public String redeem()
	{
		mortgaged = false;
		return "";
	}

	@Override
	public Group getGroup() 
	{
		return group;
	}

}
