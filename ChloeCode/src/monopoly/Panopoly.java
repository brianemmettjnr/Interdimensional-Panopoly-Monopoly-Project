package monopoly;

import java.util.ArrayList;

public class Panopoly 
{
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player currentPlayer;
	private Board board;
	
	Panopoly(int numLocations)
	{
		board = new Board(numLocations);
	}
	
	public void createPlayers()
	{
		players.add(new Player("Player 1"));
	}
	
}
