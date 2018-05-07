package monopoly;

import java.awt.*;

public class Station extends RentalProperty
{
	public Color color=Color.gray;
	private static Group Stations = new Group("Stations");
	
	public Station(String name)
	{
		super(name, 200, null, 100, Stations);
	}
	
	@Override
	public int getRentalAmount() 
	{
		return (int) (Math.pow(2, ((((Player) getOwner()).ownedStations())-1)) * 25);
	}
}
