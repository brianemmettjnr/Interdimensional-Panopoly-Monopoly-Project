
public class Site extends Property {
	
	private static int MAX_NUM_UNITS=5;

	private int[] rentTable;
	private ColourGroup colourGroup;
	private int numBuildings;
	private int buildPrice;
	
	Site (String name, String shortName, int price, int mortgageValue, int[] rentTable, ColourGroup colourGroup, int buildPrice) {
		super(name, price, shortName, mortgageValue);
		this.rentTable = rentTable;
		this.colourGroup = colourGroup;
		this.buildPrice = buildPrice;
		numBuildings = 0;
		colourGroup.addMember(this);
		return;
	}	
	
// METHODS DEALING WITH BUILDING UNITS (HOUSES AND HOTELS)
	
	public boolean canBuild (int numToBuild) {
		 return (numBuildings+numToBuild)<=MAX_NUM_UNITS;
	}
	
	public void build (int numToBuild) {
		if (canBuild(numToBuild)) {
			numBuildings = numBuildings + numToBuild;
		}
		return;
	}
	
	public boolean canDemolish (int numToDemolish) {
		return (numBuildings-numToDemolish)>=0;
	}
	
	public void demolish (int numToDemolish) {
		if (canDemolish(numToDemolish)) {
			numBuildings = numBuildings - numToDemolish;
		}
	}
	
	public void demolishAll () {
		numBuildings = 0;
		return;
	}
	
	public int getNumBuildings () {
		return numBuildings;
	}
	
	public int getBuildingPrice () {
		return buildPrice;
	}
	
	public boolean hasBuildings () {
		return numBuildings > 0;
	}
	
	public int getNumHouses () {
		int numHouses;
		if (numBuildings < 5) {
			numHouses = numBuildings;
		} else {
			numHouses = 0;
		}
		return numHouses;
	}
	
	public int getNumHotels () {
		int numHotels;
		if (numBuildings == 5) {
			numHotels = 1;
		} else {
			numHotels = 0;
		}
		return numHotels;
	}

// METHODS DEALING WITH COLOUR GROUPS
	
	public ColourGroup getColourGroup () {
		return colourGroup;
	}
	
// METHODS DEALING WITH RENT
		
	public int getRent () {
		int rent;
		if (numBuildings==0 && super.getOwner().isGroupOwner(this)) {
			rent = rentTable[0];
		} else if (numBuildings==0 && super.getOwner().isGroupOwner(this)) {
			rent = 2*rentTable[0]; 
		} else {
			rent = rentTable[numBuildings];
		}
		return rent;
	}
	
// COMMON JAVA METHODS
	
	public String toString () {
		return super.toString();
	}
}
