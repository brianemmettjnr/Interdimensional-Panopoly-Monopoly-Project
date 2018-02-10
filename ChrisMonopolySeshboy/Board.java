
public class Board implements BoardAPI {
	
	// Board data from http://monopoly.wikia.com/wiki/Monopoly
	// Note, a property is a site, utility or station
	
	public static final int NUM_SQUARES = 40;
	
	public static final int POS_GO = 0;
	public static final int POS_OLD_KENT_RD = 1;
	public static final int POS_JAIL = 10;
	public static final int POS_PALL_MALL = 11;
	public static final int POS_MARYLEBONE_STATION = 15;
	public static final int POS_TRAFALGAR_SQ = 24;
	public static final int POS_MAYFAIR = 39;
	
	private Square[] squares = new Square[NUM_SQUARES];
	private ColourGroup brown = new ColourGroup("brown");
	private ColourGroup lightBlue = new ColourGroup("light blue");
	private ColourGroup pink = new ColourGroup("pink");
	private ColourGroup orange = new ColourGroup("orange");
	private ColourGroup red = new ColourGroup("red");
	private ColourGroup yellow = new ColourGroup("yellow");
	private ColourGroup green = new ColourGroup("green");
	private ColourGroup darkBlue = new ColourGroup("dark blue");
	
	Board (Dice dice) {
			squares[0] = new Square("Go");
			squares[1] = new Site("Old Kent Rd", "kent", 60, 50, new int[] {2,10,30,90,160,250}, brown, 50);
			squares[2] = new CommunityChest();
			squares[3] = new Site("Whitechapel Rd", "whitechapel", 60, 50, new int[] {4,20,60,180,320,450}, brown, 50);
			squares[4] = new Tax("Income Tax",200);
			squares[5] = new Station("King's Cross Station", "kings", 200, 100, new int[] {25,50,100,200});
			squares[6] = new Site("The Angel Islington", "angel", 100, 50, new int[] {6,30,90,270,400,550}, lightBlue, 50);
			squares[7] = new Chance();
			squares[8] = new Site("Euston Rd", "euston", 100, 50, new int[] {6,30,90,270,400,550}, lightBlue, 50);
			squares[9] = new Site("Pentonville Rd", "pentonville", 120, 60, new int[] {8,40,100,300,450,600}, lightBlue, 50);
			squares[10] = new Square("Jail");
			squares[11] = new Site("Pall Mall", "mall", 140, 70, new int[] {10,50,150,450,625,750}, pink, 100);
			squares[12] = new Utility("Electric Co", "electric", 150, 75, new int[] {4,10}, dice);
			squares[13] = new Site("Whitehall", "whitehall", 140, 70, new int[] {10,50,150,450,625,750}, pink, 100);
			squares[14] = new Site("Northumberland Ave", "northumberland", 160, 80, new int[] {12,60,180,500,700,900}, pink, 100);
			squares[15] = new Station("Marylebone Station", "maryledone", 200, 100, new int[] {25,50,100,200});
			squares[16] = new Site("Bow St", "bow", 180, 90, new int[] {14,70,200,550,750,950}, orange, 100);
			squares[17] = new CommunityChest();
			squares[18] = new Site("Marlborough St", "marlborough", 180, 90, new int[] {14,70,200,550,750,950}, orange, 100);
			squares[19] = new Site("Vine St", "vine", 200, 100, new int[] {16,80,220,600,800,1000}, orange, 100);
			squares[20] = new Square("Free Parking");
			squares[21] = new Site("Strand", "strand", 220, 110, new int[] {18,90,250,700,875,1050}, red, 150);
			squares[22] = new Chance();
			squares[23] = new Site("Fleet St", "fleet", 220, 110, new int[] {18,90,250,700,875,1050}, red, 150);
			squares[24] = new Site("Trafalgar Sq", "trafalgar", 240, 120, new int[] {20,100,300,750,925,1100}, red, 150);
			squares[25] = new Station("Fenchurch St Station", "fenchurch", 200, 100, new int[] {25,50,100,200});
			squares[26] = new Site("Leicester Sq", "leicester", 260, 150, new int[] {22,110,330,800,975,1150}, yellow, 150);
			squares[27] = new Site("Coventry St", "coventry", 260, 150, new int[] {22,110,330,800,975,1150}, yellow, 150);
			squares[28] = new Utility("Water Works", "water", 150, 75, new int[] {4,10}, dice);
			squares[29] = new Site("Piccadilly", "piccadilly", 280, 150, new int[] {22,120,360,850,1025,1200}, yellow, 150);
			squares[30] = new GoToJail();
			squares[31] = new Site("Regent St", "regent", 300, 200, new int[] {26,130,390,900,1100,1275}, green, 200);
			squares[32] = new Site("Oxford St", "oxford", 300, 200, new int[] {26,130,390,900,1100,1275}, green, 200);
			squares[33] = new CommunityChest();
			squares[34] = new Site("Bond St", "bond", 320, 200, new int[] {28,150,450,1000,1200,1400}, green, 200);
			squares[35] = new Station("Liverpool St Station", "liverpool", 200, 100, new int[] {25,50,100,200});
			squares[36] = new Chance();
			squares[37] = new Site("Park Lane", "park", 350, 175, new int[] {35,175,500,1100,1300,1500}, darkBlue, 200);
			squares[38] = new Tax("Super Tax",100);
			squares[39] = new Site("Mayfair", "mayfair", 400, 200, new int[] {50,200,600,1400,1700,2000}, darkBlue, 200);
			return;
		}
		
		public Square getSquare (int index) {
			return squares[index];
		}

		public Property getProperty (int index) {
			return (Property) squares[index];
		}
		
		public Property getProperty (String shortName) {
			Property property = null;
			for (Square s : squares) {
				if (s instanceof Property) {
					Property p = (Property) s;
					if (p.equals(shortName)) {
						property = p;
					}
				}
			}
			return property;
		}

		public boolean isProperty (int index) {
			return squares[index] instanceof Property;
		}

		public boolean isProperty (String shortName) {
			boolean found = false;
			for (Square s :squares) {
				if (s instanceof Property) {
					Property p = (Property) s;
					if (p.equals(shortName)) {	
						found = true;
					}
				}
			}
			return found;
		}
	
		public boolean isSite (String shortName) {
			return isProperty(shortName) && getProperty(shortName) instanceof Site;
		}
	
		public boolean isStation (String shortName) {
			return isProperty(shortName) && getProperty(shortName) instanceof Station;
		}
	
		public boolean isUtility (String shortName) {
			return isProperty(shortName) && getProperty(shortName) instanceof Utility;
		}
}
