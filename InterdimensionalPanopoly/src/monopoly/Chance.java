package monopoly;

import interfaces.Actionable;
import interfaces.Cardable;

public class Chance extends NamedLocation implements Cardable
{

	public Chance() 
	{
		super("Chance");
	}

	@Override
	public Actionable getCardAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
