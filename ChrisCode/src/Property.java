
public class Property extends Square {

	private static final float MORTGAGE_PREMIUM = 1.1f;
	
	private boolean isOwned;
	private int price;
	private Player owner;
	private String shortName;
	private boolean mortgaged;
	private int mortgageValue;
	
	Property (String name, int price, String shortName, int mortgageValue) {
		super(name);
		this.price = price;
		this.shortName = shortName;
		isOwned = false;
		owner = null;
		mortgaged = false;
		this.mortgageValue = mortgageValue;
		return;
	}
		
// METHODS DEALING WITH NAMES
	
	public String getShortName () {
		return shortName;
	}
	
// METHODS DEALING WITH PRICE

	public int getPrice () {
		return price;
	}
	
// METHODS DEALING WITH RENT : 	

	public int getRent () { // this method is overloaded by the subclasses
		return 0;  
	}
	
// METHODS DEALING WITH OWNERSHIP
	
	public Player getOwner () {
		return owner;
	}
	
	public boolean isOwned () {
		return isOwned;
	}
	
	public void setOwner (Player player) {
		owner = player;
		isOwned = true;
		return;
	}
	
	public void releaseOwnership () {
		isOwned = false;
		owner = null;
		mortgaged = false;
		return;
	}
	
// METHODS DEALING WITH MORTGAGES
	
	public void setMortgaged() {
		mortgaged = true;
		return;
	}
	
	public boolean isMortgaged() {
		return mortgaged;
	}
	
	public void setNotMortgaged() {
		mortgaged = false;
		return;
	}
	
	public int getMortgageValue() {
		return mortgageValue;
	}
	
	public int getMortgageRemptionPrice () {
		return (int) (((float) mortgageValue) * MORTGAGE_PREMIUM);
	}
	
// COMMON JAVA METHODS	
	
	public boolean equals (String string) {
		return shortName.equals(string);
	}
	
	public String toString () {
		return super.toString();
	}
}
