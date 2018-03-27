//PropertyTest: Testing methods and attributes of the property classes

package monopoly;

import interfaces.Groupable;

public class PropertyTest {

	public static void main(String[] args) {
		
		Group stations = new Group("Station");
		Group green = new Group("Green");
		Group lightBlue = new Group("Light Blue");
		
		//declaring properties
		NamedLocation go = new NamedLocation("Go");
		NamedLocation freeParking = new NamedLocation("Free Parking");
		
		RentalProperty marylebone = new RentalProperty("Marylebone Station", 500, new int[] {100}, 200, stations);
		RentalProperty liverpool = new RentalProperty("Liverpool St. Station", 1500, new int[] {300}, 500, stations);
		
		InvestmentProperty eustonRd = new InvestmentProperty("Euston Road", 400, new int[] {50}, 100, 30, green);
		InvestmentProperty piccadilly = new InvestmentProperty("Piccadilly", 450, new int[] {50}, 150, 40, green);
		InvestmentProperty leicester = new InvestmentProperty("Leicester Square", 70, new int[] {10}, 30, 10, lightBlue);
		
		//dummy player to test Set/Get Owner methods
		Player testPlayer = new Player("Test Player",1,0);
		
		testPlayer.buyProperty(leicester, leicester.getPrice());
		
		TaxableProperty superTax = new TaxableProperty("Super Tax", 200);
		TaxableProperty incomeTax = new TaxableProperty("Income Tax", 100);
		
		go.setRight(eustonRd);
		eustonRd.setRight(piccadilly);
		liverpool.setLeft(piccadilly);
		
		incomeTax.setRight(leicester);
		leicester.setLeft(incomeTax);
		
		//testing strings
		System.out.println("Name of Free Parking: " + freeParking.getIdentifier());
		System.out.println("\nProperty right of Go:\nExpected: Euston Road\tActual: " + go.goRight().getIdentifier());
		System.out.println("\nPrice of Piccadilly:\nExpected: 450\t\tActual: " + piccadilly.getPrice());
		System.out.println("\nOwner of Leicester Square:\nExpected: Test Player\tActual: " + leicester.getOwner().getIdentifier());
		System.out.println("\nRent of Marylebone:\nExpected: 100\t\tActual: " + marylebone.getRentalAmount());
		System.out.println("\nMortgage price of Piccadilly:\nExpected: 200\t\tActual: " + marylebone.getMortgageAmount());
		System.out.println("\nTax on Super Tax:\nExpected: 200\t\tActual: " + superTax.getFlatAmount());
		System.out.println("\nBuild price of Leicester Square:\nExpected: 10\t\tActual: " + leicester.getBuildPrice());
		System.out.println("\nHouses on Euston Road before build:\nExpected: 0\t\tActual: " + eustonRd.getNumHouses());
		System.out.println("\nHotels on Euston Road before build:\nExpected: 0   \t\tActual: " + eustonRd.getNumHotels());
		
		for(int i = 0; i < 9; i++)
		eustonRd.build();
		
		System.out.println("\nHouses on Euston Road after build:\nExpected: 4\t\tActual: " + eustonRd.getNumHouses());
		System.out.println("\nHotels on Euston Road after build:\nExpected: 1\t\tActual: " + eustonRd.getNumHotels());
		
		for(int i = 0; i < 6; i++)
		eustonRd.demolish();
		
		System.out.println("\nHouses on Euston Road after demolish:\nExpected: 3\t\tActual: " + eustonRd.getNumHouses());
		System.out.println("\nHotels on Euston Road after demolish:\nExpected: 0\t\tActual: " + eustonRd.getNumHotels());
		
		System.out.println("\nMortgage value of Liverpool St. Station:\nExpected: 500\t\tActual: " + liverpool.getMortgageAmount());
		System.out.println("\nMortgage state of Liverpool St. Station before mortgage:\nExpected: false\t\tActual: " + liverpool.isMortgaged());
		
		liverpool.mortgage();
		
		System.out.println("\nMortgage state of Liverpool St. Station after mortgage:\nExpected: true\t\tActual: " + liverpool.isMortgaged());

		liverpool.redeem();
		
		System.out.println("\nMortgage state of Liverpool St. Station after redeem:\nExpected: false\t\tActual: " + liverpool.isMortgaged());
		
		System.out.println("\nGroup of Euston Road:\nExpected: Green\t\tActual: " + eustonRd.getGroup().getIdentifier());
		
		String members = "";
		
		for(Groupable m: green.getMembers())
		{
			members += m.getIdentifier() + ", ";
		}
				
		System.out.println("\nMembers of Green\nExpected: Euston Road, Piccadilly\tActual: " + members);
	}

}
