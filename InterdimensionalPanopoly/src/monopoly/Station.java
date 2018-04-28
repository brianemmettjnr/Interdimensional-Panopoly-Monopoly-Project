package monopoly;

import java.awt.*;

public class Station extends RentalProperty
{
	public Color color=Color.gray;
	private static Group Stations = new Group("Stations");
	private Dice dice = new Dice();
	
	public Station(String name)
	{
		super(name, 200, null, 100, Stations);
	}
	
	@Override
	public int getRentalAmount() 
	{
		if(getOwner() != null)
			return (int) (Math.pow(2, ((((Player) getOwner()).ownedStations())-1)) * 25);
		
		else
			return 0;
	}
}
