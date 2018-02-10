import java.util.ArrayList;

public class Player implements PlayerAPI {
	
	private static final int MAX_NUM_JAIL_EXIT_ATTEMPTS = 3;
	
	private String name;
	private int position;
	private int balance;
	private int amount;
	private String tokenName;
	private int tokenId;
	private boolean passedGo;
	private ArrayList<Property> properties = new ArrayList<Property>();
	private boolean inJail;
	private int numJailExitAttempts;
	private ArrayList<Card> cards = new ArrayList<Card>();
	
// CONSTRUCTORS
	
	Player (String name, String tokenName, int tokenId) {
		this.name = name;
		this.tokenName = tokenName;
		this.tokenId = tokenId;
		position = 0;
		balance = 0;
		passedGo = false;
		inJail = false;
		return;
	}
	
	Player (Player player) {   // copy constructor
		this(player.getName(), player.getTokenName(), player.getTokenId());
	}
	
// METHODS DEALING WITH PLAYER DATA
	
	public String getName () {
		return name;
	}
	
	public String getTokenName () {
		return tokenName;
	}
	
	public int getTokenId () {
		return tokenId;
	}
	
// METHODS DEALING WITH TOKEN POSITION
	
	public int getPosition () {
		return position;
	}

	public void move (int squares) {
		position = position + squares;
		if (position >= Board.NUM_SQUARES) {
			position = position - Board.NUM_SQUARES;
			passedGo = true;
		} else {
			passedGo = false;
		}
		if (position < 0) {
			position = position + Board.NUM_SQUARES;
		} 
		return;
	}
	
	public void moveTo (int square) {
		if (square < position) {
			passedGo = true;
		} else {
			passedGo = false;
		}
		position = square;
		return;
	}

	public boolean passedGo () {
		return passedGo;
	}
	
	// METHODS DEALING WITH JAIL
	
	public void goToJail () {
		position = Board.POS_JAIL;
		inJail = true;
		numJailExitAttempts = 0;
		return;
	}
	
	public void freeFromJail () {
		position = Board.POS_JAIL;
		inJail = false;
		return;
	}
	
	public boolean isInJail () {
		return inJail;
	}

	public void failedJailExitAttempt () {
		numJailExitAttempts++;
		return;
	}
	
	public boolean exceededJailExitAttempts () {
		return numJailExitAttempts >= MAX_NUM_JAIL_EXIT_ATTEMPTS;
	}
	
	public void addCard (Card card) {
		cards.add(card);
		return;
	}
	
	public boolean hasGetOutOfJailCard () {
		boolean hasCard = false;
		if (cards.size()> 0) {
			hasCard = cards.get(0).getAction() == CardDeck.ACT_GET_OUT_OF_JAIL;
		}
		return hasCard;
	}
	
	public Card getCard () {
		Card card = cards.get(0);
		cards.remove(0);
		return card;
	}

//  METHODS DEALING WITH MONEY
	
	public void doTransaction (int amount) {
		balance = balance + amount;
		this.amount = amount;
		return;
	}
		
	public int getTransaction () {
		return amount;
	}
	
	public int getBalance () {
		return balance;
	}
	
	public int getAssets () {
		int assets = balance;
		for (Property property: properties) {
			assets = assets + property.getPrice();
		}
		return assets;
	}
	
//  METHODS DEALING WITH PROPERTY
	
	public void addProperty (Property property) {
		property.setOwner(this);
		properties.add(property);
		return;
	}
	
	public int getNumProperties () {
		return properties.size();
	}
	
	public Property getLatestProperty () {
		return properties.get(properties.size()-1);
	}
	
	public ArrayList<Property> getProperties () {
		return properties;
	}
	
	public boolean isGroupOwner (Site site) {
		boolean ownsAll = true;
		ColourGroup colourGroup = site.getColourGroup();
		for (Site s : colourGroup.getMembers()) {
			if (!s.isOwned() || (s.isOwned() && s.getOwner() != this))
				ownsAll = false;
		}
		return ownsAll;
	}
	
	public int getNumStationsOwned () {
		int numOwned = 0;
		for (Property p : properties) {
			if (p instanceof Station) {
				numOwned++;
			}
		}
		return numOwned;
	}
	
	public int getNumUtilitiesOwned () {
		int numOwned = 0;
		for (Property p : properties) {
			if (p instanceof Utility) {
				numOwned++;
			}
		}
		return numOwned;
	}
	
	public int getNumHousesOwned () {
		int numHousesOwned = 0;
		for (Property p : properties) {
			if (p instanceof Site) {
				numHousesOwned = numHousesOwned + ((Site) p).getNumHouses();
			}
		}
		return numHousesOwned;
	}
	
	public int getNumHotelsOwned () {
		int numHotelsOwned = 0;
		for (Property p : properties) {
			if (p instanceof Site) {
				numHotelsOwned = numHotelsOwned + ((Site) p).getNumHotels();
			}
		}
		return numHotelsOwned;
	}	
	
// COMMON JAVA METHODS	
	
	public boolean equals (String name) {
		return this.name.toLowerCase() == name;
	}
	
	public String toString () {
		return name + " (" + tokenName + ")";
	}
	

}
