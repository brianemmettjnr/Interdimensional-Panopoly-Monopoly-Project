package monopoly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import interfaces.*;

public class Panopoly 
{
	private ArrayList<Player> players;
	private Player currentPlayer;
	private Board board;
	private GUI gui;
	private Dice dice = new Dice();
	private boolean clockwiseMovement = true;
	private long startTime;
	private Timer countdownTimer;
	private CommunityChest deck = new CommunityChest();
	
	Panopoly(int numLocations)
	{
		board = new Board(numLocations);
		SetupGUI.PlayerCountGui(this);
		
		startTime = System.currentTimeMillis();

		countdownTimer = new Timer(10 * 60 * 10, null);
		countdownTimer.setRepeats(false);
	}
	
	public void createGUI(ArrayList<Player> playerArray)
	{
		players = playerArray;
		currentPlayer = players.get(0);
		gui = new GUI(board.getNumLocations(),this,players);
		gui.updateGUI();
	}
	
	//GETTER METHODS
	public Board getBoard()
	{
		return this.board;
	}

	public Player getCurrentPlayer() 
	{
		return currentPlayer;
	}
	
	public Player getNextPlayer()
	{
		return players.get((players.indexOf(currentPlayer)+1)%players.size());
	}
	
	//TO DO: DRAW CARD
	private String getSquareAction()
	{
		Locatable square = board.getLocation(currentPlayer.getPosition());
		String ret = "";
		
		if(square instanceof TaxableProperty)
		{
			currentPlayer.pay(((Taxable) square).getFlatAmount());
			ret = "\n" + currentPlayer.getIdentifier() + " has paid " + ((Taxable) square).getFlatAmount() + " in tax.";
		}
		//rental property owned by another player
		else if((square instanceof RentalProperty) && (((Rentable) square).getOwner()!=null) && (((Rentable) square).getOwner()!=currentPlayer) && !(((RentalProperty) square).isMortgaged()))
		{
			int rent = ((Rentable) square).getRentalAmount();

			currentPlayer.pay(rent);
			((Player) ((Rentable) square).getOwner()).earn(rent);
			ret = "\n" + currentPlayer.getIdentifier() + " has paid " + rent + " to " + ((Rentable) square).getOwner().getIdentifier();
		}
		
		else if(square.getIdentifier() == "Go to Jail")
		{
			currentPlayer.sendToJail();
			ret = "\n" + currentPlayer.getIdentifier() + " has landed on Go to Jail and been sent to jail.";
			//ret += startPlayerTurn(getNextPlayer());
		}
		
		else if(square instanceof CommunityChest || square instanceof Chance)
		{
			ret += deck.getCard(this);
		}

		return ret;
	}
	
	public void startCountdown()
	{
		ActionListener timerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				endGame();
			}};
			
		countdownTimer.addActionListener(timerListener);
		countdownTimer.start();
		gui.updateAction("COUNTDOWN STARTED");
		gui.updateAction("Elapsed Time in secs: " + (System.currentTimeMillis() - startTime) / 1000);
	}
	
	public void startPlayerTurn(Player player)
	{
		if(player.getClass()==GameBot.class){
			System.out.println(player.getIdentifier());
			currentPlayer.doubles = 0;
			currentPlayer.canRoll = true;
			currentPlayer.rollComplete = false;
			currentPlayer = player;

			gui.updateGUI();
			gui.updateAction(currentPlayer.getIdentifier() + "'s turn");
			((GameBot) player).makeGameDecision(gui);
		}else{
			currentPlayer.doubles = 0;
			currentPlayer.canRoll = true;
			currentPlayer.rollComplete = false;
			currentPlayer = player;

			gui.resetCommands();
			gui.updateGUI();

			gui.updateAction(currentPlayer.getIdentifier() + "'s turn");
		}
	}
	
	public String roll()
	{
		currentPlayer.canRoll = false;
		currentPlayer.rollComplete = true;
		int movePositions = dice.rollNormalDice();
		
		String msg = currentPlayer.getIdentifier() + " has rolled " + movePositions + ".";

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

		//pass GO message
		msg += currentPlayer.move(movePositions, clockwiseMovement);

		msg += getSquareAction();
		
		gui.resetCommands();
		gui.updateGUI();
		if(currentPlayer instanceof GameBot)
			((GameBot) currentPlayer).makeGameDecision(gui);
		return msg;
	}
	
	//PROPERTY METHODS
	public String buyProperty(Rentable property)
	{
		currentPlayer.buyProperty(property, property.getPrice());
		gui.updateGUI();
		
		return currentPlayer.getIdentifier() + " has bought " + property.getIdentifier() + " for " + property.getPrice() + ".";
	}
	
	public String buildUnit(InvestmentProperty property)
	{
		currentPlayer.pay(property.buildPrice);
		return property.build();		
	}
	
	public String demolishUnit(InvestmentProperty property)
	{
		currentPlayer.earn(property.buildPrice / 2);
		return property.demolish();
	}

	public String mortgage(RentalProperty mortgageProperty) 
	{
		currentPlayer.earn(mortgageProperty.getMortgageAmount());
		return mortgageProperty.mortgage();
	}
	
	public String redeem(RentalProperty redeemProperty) 
	{
		currentPlayer.pay(redeemProperty.getRedeemAmount());
		return redeemProperty.redeem();	
	}
	
	public void leaveGame()
	{
		gui.updateAction(currentPlayer.getIdentifier() + " has left the game.");
		
		for(Rentable property: currentPlayer.getProperties())
		{
			property.reset();
		}
		
		int index = players.indexOf(currentPlayer);
		players.remove(currentPlayer);
		gui.leaveGame(currentPlayer);
		
		//if one player left, they win automatically and game ends
		if(players.size() == 1)
			endGame();
		
		else
			startPlayerTurn(players.get(index % players.size()));
	}
	
	public ArrayList<Player> decideWinners()
	{
		 int winningWorth = 0;
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
	
	public void endGame()
	{
		ArrayList<Player> winners = decideWinners();
		
		//if one distinct winner
		if(winners.size() == 1)
			gui.updateAction(winners.get(0).getIdentifier() + " has won!");
		
		//if there is a draw
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
}
