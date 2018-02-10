
public class Utility extends Property {
	
	int[] rentTable;
	Dice dice;
	
	Utility (String name, String shortName, int price, int mortgageValue, int[] rentTable, Dice dice) {
		super(name, price, shortName, mortgageValue);
		this.rentTable = rentTable;
		this.dice = dice;
		return;
	}
	
	public int getRentMultiplier () {
		return rentTable[super.getOwner().getNumUtilitiesOwned()-1];
	}

	public int getRent () {
		return dice.getTotal() * getRentMultiplier();
	}
	
}
