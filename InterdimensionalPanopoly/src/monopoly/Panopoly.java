package monopoly;

import java.util.ArrayList;

import interfaces.*;

public class Panopoly 
{
	private ArrayList<Player> players;
	private Player currentPlayer;
	private Board board;
	private GUI gui;
	private Dice dice = new Dice();
	private boolean clockwiseMovement = true;
	
	Panopoly(int numLocations)
	{
		board = new Board(numLocations);
		GUI.PlayerCountGui(this);
	}
	
	public Board getBoard()
	{
		return this.board;
	}
	
	public String roll()
	{
		currentPlayer.canRoll = false;
		currentPlayer.rollComplete = true;
		int movePositions = dice.rollNormalDice();
		
		String msg = currentPlayer.getIdentifier() + " has rolled " + movePositions + ". ";
		
		currentPlayer.move(movePositions, clockwiseMovement);
		
		if(dice.getDoubles())
		{
			currentPlayer.canRoll = true;
			msg += currentPlayer.getIdentifier() + " has rolled doubles and can roll again.";
		}
		
		getSquareAction();
		
		gui.resetCommands();
		gui.updateGUI();
		
		return msg;
	}
	
	public String buyProperty(Rentable property)
	{
		currentPlayer.buyProperty(property, property.getPrice());
		gui.buyCommand = false;
		gui.updateGUI();
		
		return currentPlayer.getIdentifier() + " has bought " + property.getIdentifier() + " for " + property.getPrice() + ".";
	}
	
	public String buildUnit(InvestmentProperty property)
	{
		property.build();
		
		currentPlayer.pay(property.buildPrice);
		
		if(property.getNumHotels() == 1)
			return currentPlayer.getIdentifier() + " has built a hotel on " + property.getIdentifier() + ".";
		else
			return currentPlayer.getIdentifier() + " has built a house on " + property.getIdentifier() + ".";
	}
	
	public String demolishUnit(InvestmentProperty property)
	{
		property.demolish();
		
		currentPlayer.earn(property.buildPrice / 2);
		
		if(property.getNumHouses() == InvestmentProperty.MAX_UNITS - 1)
			return currentPlayer.getIdentifier() + " has demolished a hotel on " + property.getIdentifier() + ".";
		else
			return currentPlayer.getIdentifier() + " has demolished a house on " + property.getIdentifier() + ".";
	}

	public String mortgage(RentalProperty mortgageProperty) 
	{
		mortgageProperty.mortgage();
		
		currentPlayer.earn(mortgageProperty.getMortgageAmount());
		
		return currentPlayer.getIdentifier() + " has mortgaged " + mortgageProperty.getIdentifier() + " for " + mortgageProperty.getMortgageAmount() + ".";
	}
	
	public String redeem(RentalProperty redeemProperty) 
	{
		redeemProperty.redeem();
		
		currentPlayer.pay(redeemProperty.getRedeemAmount());
		
		return currentPlayer.getIdentifier() + " has redeemed " + redeemProperty.getIdentifier() + " for " + redeemProperty.getRedeemAmount() + ".";
	}

	//TO DO: DRAW CARD
	private void getSquareAction() 
	{
		Locatable square = board.getLocation(currentPlayer.getPosition());
		
		if(square instanceof TaxableProperty)
		{
			currentPlayer.pay(((Taxable) square).getFlatAmount());
			gui.updateAction(currentPlayer.getIdentifier() + " has paid " + ((Taxable) square).getFlatAmount() + " in tax.");
		}
		//rental property owned by another player
		else if((square instanceof RentalProperty) && (((Rentable) square).getOwner()!=null) && (((Rentable) square).getOwner()!=currentPlayer))
		{
			int rent = ((Rentable) square).getRentalAmount();
			currentPlayer.pay(rent);
			((Player) ((Rentable) square).getOwner()).earn(rent);
			gui.updateAction(currentPlayer.getIdentifier() + " has paid " + rent + " to " + ((Rentable) square).getOwner().getIdentifier());
		}
		
		
	}
	
	//buy, roll, drawCard, endTurn
	public void setPossibleCommands()
	{
		Locatable square = board.getLocation(currentPlayer.getPosition());
		
		gui.rollCommand = currentPlayer.canRoll;
		//unowned property and player has rolled at least once
		if(square instanceof Rentable && ((Rentable) square).getOwner() == null && currentPlayer.rollComplete)
			gui.buyCommand = true;
				
		gui.endCommand = !gui.rollCommand;
	}

	public void createGUI() 
	{
		players = GUI.getPlayersArray();
		currentPlayer = players.get(0);
		gui = new GUI(board.getNumLocations());
		gui.updateGUI();
	}
	
	public String nextPlayer()
	{
		currentPlayer.canRoll = true;
		currentPlayer.rollComplete = false;
		currentPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());
		
		gui.resetCommands();
		gui.updateGUI();
		
		return currentPlayer.getIdentifier() + "'s turn";
	}

	public Player getCurrentPlayer() 
	{
		return currentPlayer;
	}
}
