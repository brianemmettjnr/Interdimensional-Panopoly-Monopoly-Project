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
		SetupGUI.PlayerCountGui(this);
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
		
		if (currentPlayer.move(movePositions, clockwiseMovement))
			 gui.updateAction(currentPlayer.getIdentifier() + " has passed GO and earned 200.");
		
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
	
	public void leaveGame()
	{
		gui.updateAction(currentPlayer.getIdentifier() + " has left the game.");
		
		int index = players.indexOf(currentPlayer);
		players.remove(currentPlayer);
		currentPlayer = players.get(index % players.size());
		
		if(players.size() == 1)
			endGame(players);
	}
	
	public ArrayList<Player> decideWinner()
	{
		 int winningWorth = players.get(0).getNetWorth();
		 ArrayList<Player> winners = new ArrayList<Player>();
		 
		 for(Player p: players)
		 {
			 if(p.getNetWorth() > winningWorth)
			 {
				 winners.clear();
				 winners.add(p);
				 winningWorth = p.getNetWorth();
			 }
			 
			 else if(p.getNetWorth() == winningWorth)
			 {
				 winners.add(p);
			 }
		 }
		 
		 return winners;
	}
	
	public void endGame(ArrayList<Player> winners)
	{
		if(winners.size() == 1)
			gui.updateAction(winners.get(0).getIdentifier() + " has won!");
		
		else
		{
			String draw = "Draw between ";
			for(int i = 0; i < winners.size() - 2; i++)
				draw += winners.get(i).getIdentifier() + ", ";
			
			draw += "and " + winners.get(winners.size() - 1) + ".";
			gui.updateAction(draw);
		}
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
		else if((square instanceof RentalProperty) && (((Rentable) square).getOwner()!=null) && (((Rentable) square).getOwner()!=currentPlayer) && !(((RentalProperty) square).isMortgaged()))
		{
			int rent = ((Rentable) square).getRentalAmount();
			
			if(!(currentPlayer.hasProperty() || currentPlayer.getBalance() >= rent))
			{
				rent = currentPlayer.getBalance();
			}
			
			currentPlayer.pay(rent);
			((Player) ((Rentable) square).getOwner()).earn(rent);
			gui.updateAction(currentPlayer.getIdentifier() + " has paid " + rent + " to " + ((Rentable) square).getOwner().getIdentifier());
		}
		else if (square instanceof Chance || square instanceof CommunityChest)
			leaveGame();
		
	}
	
	//buy, roll, drawCard, endTurn
	public void setPossibleCommands()
	{
		Locatable square = board.getLocation(currentPlayer.getPosition());
		
		gui.rollCommand = currentPlayer.canRoll;
		//unowned property and player has rolled at least once
		gui.endCommand = (!gui.rollCommand && currentPlayer.getBalance() >= 0);
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
