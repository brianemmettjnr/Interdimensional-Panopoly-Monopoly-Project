package monopoly;

import interfaces.Actionable;
import interfaces.Cardable;

public class CommunityChest extends NamedLocation implements Cardable
{
	public CommunityChest() 
	{
		super("Community Chest");
	}

	@Override
	public Actionable getCardAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
