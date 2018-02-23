//RentalProperty: Train stations, Utilities

package monopoly;

public class RentalProperty extends PrivateProperty implements Rentable, Mortgageable, Groupable
{
	private int rent, mortgage;
	private Group group;
	private boolean mortgaged = false;
	
	public RentalProperty(String name, int price, int rent, int mortgageCost, Group group) {
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

	@Override
	public int getRentalAmount() 
	{
		return rent;
	}

	//returns if property is currently mortgaged
	@Override
	public boolean isMortgaged() 
	{
		return mortgaged;
	}

	@Override
	public void mortgage() 
	{
		mortgaged = true;
	}

	@Override
	public void redeem() 
	{
		mortgaged = false;
	}

	@Override
	public Group getGroup() 
	{
		return group;
	}

}
