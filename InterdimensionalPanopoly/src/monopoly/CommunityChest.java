package monopoly;

import java.util.concurrent.ThreadLocalRandom;

import base.PersonOfInterest;
import interfaces.Actionable;
import interfaces.Cardable;

public class CommunityChest extends NamedLocation implements Cardable
{
	PersonOfInterest ps = new PersonOfInterest();
	
	public CommunityChest() 
	{
		super("Community Chest");
	}

	@Override
	public Actionable getCardAction() 
	{
		return null;
	}
	
	public String getCard(Panopoly panopoly)
	{
		Card card = drawCard(ThreadLocalRandom.current().nextInt(0, 6 + 1));
		card.performConsequence(panopoly);
		return card.getMessage();
	}
	
	private Card drawCard(int type)
	{
		switch(type) 
		{
			case Card.BAL_PLUS: 			return drawBalPlus();
			case Card.BAL_MINUS:			return drawBalMinus();
			case Card.POSITION_PLUS: 		return drawPositionPlus();
			case Card.POSITION_MINUS:		return drawPositionMinus();
			case Card.GO_TO_JAIL:			return drawGoToJail();
			case Card.GET_OUT_OF_JAIL:		return drawGetOutOfJail();
			default:						return drawDoomsday();
		}
	}
	
	private Card drawBalPlus()
	{
		int rand = ThreadLocalRandom.current().nextInt(0, 6 + 1);
		
		switch(rand) 
		{
			case 0: return ps.CriminalCardsBalancePos();
			case 1: return ps.Opponents();
			case 2: return ps.Wealth();
			case 3: return ps.Blackmailer();
			case 4: return ps.BlackmailerBr();
			case 5: return ps.Pawns();
			default: return ps.Inherit();
		}
	}
	
	private Card drawBalMinus()
	{
		int rand = ThreadLocalRandom.current().nextInt(6 + 1);
		
		switch(rand)
		{
			case 0: return ps.Weapons();
			case 1: return ps.CarCrash();
			case 2: return ps.Wedding();
			case 3: return ps.Scammed();
			case 4: return ps.Lectured();
			case 5: return ps.Terrorism();
			default: return ps.Sports();
		}
	}
	
	private Card drawPositionPlus()
	{
		int rand = ThreadLocalRandom.current().nextInt(2);
		
		switch(rand)
		{
			case 0: return ps.CriminalCardsPosition();
			default: return ps.TestDrive();
		}
	}

	private Card drawPositionMinus()
	{
		return ps.TimeTravel();
	}
	
	private Card drawGoToJail()
	{
		int rand = ThreadLocalRandom.current().nextInt(2);
		
		switch(rand)
		{
			case 0: return ps.Murderer();
			default: return ps.Villainy();
		}
	}
	
	private Card drawGetOutOfJail()
	{
		int rand = ThreadLocalRandom.current().nextInt(2);
		
		switch(rand)
		{
			case 0: return ps.BlackmailerBr();
			default: return ps.Royalty();
		}
	}
	
	private Card drawDoomsday()
	{
		return ps.DoomsDay();
	}
}
