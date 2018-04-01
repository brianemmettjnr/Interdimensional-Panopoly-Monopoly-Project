package monopoly;

public class Station extends RentalProperty
{
	private static Group Stations = new Group("Stations");
	private Dice dice = new Dice();
	
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