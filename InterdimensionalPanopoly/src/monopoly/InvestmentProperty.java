//InvestmentProperty: Properties that can be built upon

package monopoly;

import interfaces.Improvable;

public class InvestmentProperty extends RentalProperty implements Improvable{
	
	//0 houses and hotels at beginning
	int houses = 0, hotels = 0, buildPrice;
	final static int MAX_UNITS = 5;

	public InvestmentProperty(String name, int price, int[] rent, int mortgage, int buildPrice, Group group) 
	{
		super(name, price, rent, mortgage, group);
		this.buildPrice = buildPrice;
	}
	
	public int getBuildPrice() {
		return buildPrice;
	}
	
	@Override
	public int getRentalAmount() 
	{
		return rent[getNumHouses() + (getNumHotels()*5)];
	}
	
	public boolean hasBuildings()
	{
		if(getNumHouses() + getNumHotels() != 0)
			return true;
		else
			return false;
	}

	@Override
	public int getNumHouses() {
		return houses;
	}

	@Override
	public int getNumHotels() {
		return hotels;
	}

	//build houses one by one
	@Override
	public void build() {
		
		houses++;
			
		//once 5 houses are built, remove them and build hotel - allow multiple hotels
		if(houses == MAX_UNITS)
		{
			houses = 0;
			hotels++;
		}
	}

	//demolish houses one by one
	@Override
	public void demolish() {
		
		//demolish houses first
		if(houses > 0)
		{
			houses--;
		}
			
		//if hotels and no houses, demolish hotel, replace with 4 houses
		else if(hotels > 0)
		{
			hotels--;
			houses += 4;
		}
	}

}
