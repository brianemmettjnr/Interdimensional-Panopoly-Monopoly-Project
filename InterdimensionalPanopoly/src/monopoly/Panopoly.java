package monopoly;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import interfaces.*;

public class Panopoly 
{
	private ArrayList<Player> players;
	private Player currentPlayer;
	private static Board board;
	private static GUI gui;
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
	private void roll()
	{
		currentPlayer.move(dice.rollDice(1, 6), clockwiseMovement);
		getSquareAction();
	}

	//TO DO: COMPLETE ALL POSSIBILITIES
	private void getSquareAction() 
	{
		Locatable square = board.getLocation(currentPlayer.getPosition());
		
		if(square instanceof TaxableProperty)
			currentPlayer.pay(((Taxable) square).getFlatAmount());
		//rental property owned by another player
		else if((square instanceof RentalProperty) && (((Rentable) square).getOwner()!=null) && (((Rentable) square).getOwner()!=currentPlayer))
			currentPlayer.pay(((Rentable) square).getRentalAmount());
		
	}
	
	private void setPossibleCommands()
	{
		Locatable square = board.getLocation(currentPlayer.getPosition());
		
		//unowned property
		if(square instanceof Rentable && ((Rentable) square).getOwner() == null)
			GUI.rentCommand = true;
	}

	public void createGUI() 
	{
		players = GUI.getPlayersArray();
		currentPlayer = players.get(0);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		gui = new GUI(40, screenSize);		
	}
	
	public static int getNumLocations()
	{
		return board.getNumLocations();
	}
}
