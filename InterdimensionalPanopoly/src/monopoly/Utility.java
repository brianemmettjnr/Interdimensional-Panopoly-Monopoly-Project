package monopoly;

public class Utility extends RentalProperty
{
	private static Group Utilities = new Group("Utilities");
	private Dice dice = new Dice();
	
	public Utility(String name)
	{
		super(name, 150, new int[] {4, 10}, 70, Utilities);
	}
	
	@Override
	public int getRentalAmount() 
	{
		if(getOwner() != null)
			return rent[((Player)getOwner()).ownedUtilities() - 1] * dice.rollDice(1, 6);
		else
			return 0;
	}
}