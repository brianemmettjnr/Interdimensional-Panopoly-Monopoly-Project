package monopoly;

import java.util.Random;

import interfaces.Rollable;

public class Dice implements Rollable 
{
	private final static Random RND = new Random();
	private final int NORMAL_NUM_SIDES = 6;
	private boolean doubles = false;
	
	public int rollDice(int numDice, int numSides) 
	{
		int sum = 0;
		
		for (int i = 0; i < numDice; i++)
			sum += RND.nextInt(numSides)+1;
		
		return sum;
	}
	
	//roll 2 dice with 6 sides and check for doubles
	public int rollNormalDice()
	{
		int firstValue = RND.nextInt(NORMAL_NUM_SIDES)+1;
		int secondValue = RND.nextInt(NORMAL_NUM_SIDES)+1;
		
		if(firstValue == secondValue)
			doubles = true;
		else
			doubles = false;
		
		return firstValue + secondValue;
	}
	
	public boolean getDoubles()
	{
		return doubles;
	}
}
