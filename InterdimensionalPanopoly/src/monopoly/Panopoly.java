package monopoly;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import interfaces.Locatable;
import interfaces.Taxable;

public class Panopoly 
{
	private ArrayList<Player> players;
	private Player currentPlayer;
	private Board board;
	private static GUI gui;
	private Dice dice = new Dice();
	private boolean clockwiseMovement = true;
	
	Panopoly(int numLocations)
	{
		board = new Board(numLocations);
		GUI.PlayerCountGui();
		players = GUI.getPlayersArray();
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
			currentPlayer.pay(((Taxable) square).getFlatAmount() + ((Taxable) square).getIncomePercentage() * currentPlayer.getBalance());
		
	}

	public static void createGUI() 
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		gui = new GUI(40, screenSize);		
	}
}
