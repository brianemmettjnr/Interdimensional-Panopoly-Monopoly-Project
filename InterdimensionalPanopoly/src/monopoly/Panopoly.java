package monopoly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.Timer;
import interfaces.*;

public class Panopoly 
{
	private ArrayList<Player> players;
	private Player currentPlayer;
	private Board board;
	private GUI[] guiArray;
	private Dice dice = new Dice();
	private boolean clockwiseMovement = true;
	private Timer countdownTimer;
	private CardDeck deck = new CardDeck();
	private int TIME_LEFT = 300000;	//5 minutes
	private int AUCTION_TIME = 15000; //15 seconds
	private boolean inCountdown = false;
	private int bid;
	private Player highestBidder;
	private Locatable auctionProperty;
	private SimpleDateFormat df = new SimpleDateFormat("mm:ss");
	
	Panopoly(int numLocations)
	{
		board = new Board(numLocations);
		SetupGUI.PlayerCountGui(this);
	}
	public void createGUI(ArrayList<Player> playerArray)
	{
		players = playerArray;
		currentPlayer = players.get(0);
		int index=0;
		guiArray =new GUI[playerArray.size()];
		for(Player player: players)
		{
			guiArray[index]=new GUI(board.getNumLocations(),this,players,player);
			if(player instanceof GameBot)
			{
				((GameBot) player).setGUI(guiArray[index]);
			}
			index++;
		}
		updateGUI();
	}
	public void createNetworkedGUI(ArrayList<Player> playerArray)
	{
		players = playerArray;
		currentPlayer = players.get(0);
		int index=0;
		guiArray =new GUI[playerArray.size()];

	}
	public void setNetworkedPlayerGUI(int i){
		Player p = players.get(i);
		System.out.println("setNtworkdplayr gui");
		guiArray[i]=new GUI(board.getNumLocations(),this,players,p);

		updateGUI();
	}
	private void updateGUI() {
		for(GUI gui:this.guiArray) {
			gui.updateGUI();
		}
	}
	private void updateAction(String action)
	{
		for(GUI gui:this.guiArray)
		{
			gui.updateAction(action);
		}
	}
	private void resetCommands() {
		for(GUI gui:this.guiArray)
		{
			gui.resetCommands();
		}
	}
	private void leaveGameGui(Player currentPlayer) {
		for(GUI gui:this.guiArray)
		{
			gui.leaveGame(currentPlayer);
		}
	}
	private void guiEndGame() {
		for(GUI gui:this.guiArray)
		{
			gui.endGame();
		}
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
	
	String getSquareAction()
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
			ret = "\n" + currentPlayer.getIdentifier() + " has landed on Go to Jail and been sent to jail.";
			currentPlayer.sendToJail();
		}
		else if(square instanceof Chance || square instanceof CommunityChest)
		{
			Player player=currentPlayer;
			String card=deck.getCard(this);
			//if player sent to jail
			if(currentPlayer!=player)
				guiArray[player.getPlayerIndex()].displayCard(card);
			else
				guiArray[currentPlayer.getPlayerIndex()].displayCard(card);
		}

		return ret;
	}
	
	public void startCountdown()
	{
		inCountdown = true;
		countdownTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				TIME_LEFT -= 1000;
				
				for(GUI gui:guiArray)
					gui.updateDoomsdayClock(df.format(TIME_LEFT));

				if(TIME_LEFT <= 0)
				{	
					countdownTimer.stop();
					endGame();
				}
			}});
		
		countdownTimer.start();
		updateAction("COUNTDOWN STARTED");
	}
	
	public boolean isInCountdown()
	{
		return inCountdown;
	}

	public void callAuction(int onEndDo)
	{
		bid = 0;
		highestBidder = currentPlayer;
		auctionProperty=board.getLocation(currentPlayer.getPosition());
		for(GUI gui: guiArray)
		{
			gui.startAuction();
		}
		updateAction("An auction for " + auctionProperty.getIdentifier()+" ");
		//chloe stuff here
		Timer auctionTimer = new Timer(1000, null);
				
		ActionListener timerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				AUCTION_TIME -= 1000;
				
				for(GUI gui: guiArray)
				{
					gui.updateAuctionClock(df.format(AUCTION_TIME));;
				}
				
				if(AUCTION_TIME <= 0)
				{	
					auctionTimer.stop();
					if(onEndDo==0)
						roll();
					else
						startPlayerTurn(getNextPlayer());
					for(GUI gui: guiArray)
					{
						gui.endAuction();
					}
					updateAction(highestBidder.getIdentifier()+" wins with a bid of "+bid);
					highestBidder.buyProperty((Rentable)auctionProperty,bid);
					AUCTION_TIME = 15000;
				}
			}};
		
		auctionTimer.addActionListener(timerListener);
		auctionTimer.start();
	}

	public Locatable getAuctionProperty()
	{
		return auctionProperty;
	}

	public int getCurrentBid()
	{
		return bid;
	}

	public void updateBid(int bid,Player player)
	{
		this.bid=bid;
		highestBidder=player;
		updateAction(player.getIdentifier()+" is the highest bidder with "+GUI.symbol+bid);
	}

	public void startPlayerTurn(Player player)
	{
		if(player.getClass()==GameBot.class){
			currentPlayer.doubles = 0;
			currentPlayer.canRoll = true;
			currentPlayer.rollComplete = false;
			currentPlayer = player;

			updateGUI();
			updateAction(currentPlayer.getIdentifier() + "'s turn");
			//todo fix
			((GameBot) player).makeGameDecision();
		}else{
			currentPlayer.doubles = 0;
			currentPlayer.canRoll = true;
			currentPlayer.rollComplete = false;
			currentPlayer = player;

			resetCommands();
			updateGUI();

			updateAction(currentPlayer.getIdentifier() + "'s turn");
		}
	}


	public void roll()
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

		
		resetCommands();
		updateGUI();
		msg += getSquareAction();
		updateAction(msg);
		if(currentPlayer instanceof GameBot)
		//todo fix
		 ((GameBot) currentPlayer).makeGameDecision();
	}
	
	//PROPERTY METHODS
	public String buyProperty(Rentable property)
	{
		currentPlayer.buyProperty(property, property.getPrice());
		updateGUI();
		
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
	
	public void leaveGame(Player player)
	{
		if(players.size()==1)
			return;
		for(Rentable property: player.getProperties())
		{
			property.reset();
		}
		updateAction(player.getIdentifier() + " has left the game.");
		
		for(Rentable property: player.getProperties())
		{
			property.reset();
		}
		int index = players.indexOf(player);
		if(player==currentPlayer)
			currentPlayer=getNextPlayer();
		players.remove(player);
		leaveGameGui(player);
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
			updateAction(winners.get(0).getIdentifier() + " has won!");
		
		//if there is a draw
		else
		{
			String draw = "Draw between ";
			for(int i = 0; i <= winners.size() - 2; i++)
				draw += winners.get(i).getIdentifier() + ", ";

			draw = draw.substring(0, draw.length() - 2);
			draw += " and " + winners.get(winners.size() - 1).getIdentifier() + ".";
			updateAction(draw);

		}
		guiEndGame();
	}

}
