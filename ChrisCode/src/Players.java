import java.util.ArrayList;

public class Players {

	public static final int MAX_NUM_PLAYERS = 6;

	private ArrayList<Player> players = new ArrayList<Player>();
	private boolean playerWasRemoved = false;
	
	Players () {
		return;
	}
	
	Players (Players players) {
		for (Player p : players.get()) {
			this.players.add(p);
		}
		return;
	}
	
	public void add (Player player) {
		players.add(player);
		return;
	}
	
	public ArrayList<Player> get () {
		return players;
	}
	
	public ArrayList<Player> getOtherPlayers (Player player) {
		ArrayList<Player> otherPlayers = new ArrayList<Player>(players);
		otherPlayers.remove(player);
		return otherPlayers;
	}
	
	public void clear () {
		players.clear();
		return;
	}
	
	public int indexOf (Player player) {
		return players.indexOf(player);
	}
	
	public boolean canAddPlayer () {
		return players.size() < MAX_NUM_PLAYERS;
	}
	
	public boolean isPlayerName (String name) {
		boolean found = false;
		for (Player p : players) {
			if (p.equals(name)) {
				found = true;
			}
		}
		return found;
	}

	public Player get (int playerId) {
		return players.get(playerId);
	}
	
	public Player get (String name) {
		Player player = null;
		for (Player p : players) {
			if (p.equals(name)) {
				player = p;
			}
		}
		return player;
	}
	
	public void remove (Player player) {
		for (Property p : player.getProperties()) {
			if (p instanceof Site) {
				((Site) p).demolishAll();
			}
			p.releaseOwnership();
		}
		players.remove(player);
		playerWasRemoved = true;
		return;
	}
	
	public Player getNextPlayer (Player currPlayer) {
		Player nextPlayer;
		if (playerWasRemoved) {
			nextPlayer = currPlayer;
			playerWasRemoved = false;
		} else {
			nextPlayer = get((players.indexOf(currPlayer) + 1) % players.size());
		}
		return nextPlayer;
	}
	
	public int numPlayers () {
		return players.size();
	}	

}
