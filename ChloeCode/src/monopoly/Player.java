package monopoly;

public class Player implements Playable {
	
	private static final int STARTING_BALANCE = 1500;
	
	private String name;
	private int balance;
	
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

	@Override
	public int getNetWorth() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBalance() 
	{
		return balance;
	}
}
