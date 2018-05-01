package monopoly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import interfaces.*;

public class Panopoly 
{
	private ArrayList<Player> players;
	private Player currentPlayer;
	private Board board;
	public GUI gui;
	private Dice dice = new Dice();
	private boolean clockwiseMovement = true;
	private long startTime;
	private Timer countdownTimer;
	
	Panopoly(int numLocations)
	{
		board = new Board(numLocations);
		SetupGUI.PlayerCountGui(this);
		
		startTime = System.currentTimeMillis();

		countdownTimer = new Timer(10 * 60 * 10, null);
		
		countdownTimer.setRepeats(false);
		countdownTimer.start();
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
		
		String msg = currentPlayer.getIdentifier() + " has rolled " + movePositions + ".";
		
		msg += currentPlayer.move(movePositions, clockwiseMovement);
		
		if(dice.getDoubles())
		{
			currentPlayer.canRoll = true;
			msg += "\n"+currentPlayer.getIdentifier() + " has rolled doubles and can roll again.";
			currentPlayer.doubles++;
			
			if(currentPlayer.doubles == 3)
			{
				currentPlayer.sendToJail();
				msg = currentPlayer.getIdentifier() + " has rolled 3 doubles in a row and been sent to Jail";
			}
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
		gui.leaveGame(currentPlayer);
		
		if(players.size() == 1)
			endGame(players);
		
		else
			gui.updateAction(startPlayerTurn(players.get(index % players.size())));
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
			for(int i = 0; i <= winners.size() - 2; i++)
				draw += winners.get(i).getIdentifier() + ", ";

			draw = draw.substring(0, draw.length() - 2);
			draw += " and " + winners.get(winners.size() - 1).getIdentifier() + ".";
			gui.updateAction(draw);

		}
		gui.endGame();
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
	}
	
	public void startCountdown()
	{
		ActionListener timerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				endGame(decideWinner());
			}};
			
		countdownTimer.addActionListener(timerListener);
		countdownTimer.restart();
		gui.updateAction("COUNTDOWN STARTED");
		gui.updateAction("Elapsed Time in secs: " + (System.currentTimeMillis() - startTime) / 1000);
	}

	public void createGUI(ArrayList<Player> playerArray)
	{
		players = playerArray;
		currentPlayer = players.get(0);
		gui = new GUI(board.getNumLocations(),this,players);
		gui.updateGUI();
	}
	
	public String startPlayerTurn(Player player)
	{
		currentPlayer.doubles = 0;
		currentPlayer.canRoll = true;
		currentPlayer.rollComplete = false;
		currentPlayer = player;
		
		gui.resetCommands();
		gui.updateGUI();
		
		return currentPlayer.getIdentifier() + "'s turn";
	}

	public Player getCurrentPlayer() 
	{
		return currentPlayer;
	}
	
	public Player getNextPlayer()
	{
		return players.get((players.indexOf(currentPlayer)+1)%players.size());
	}
}
