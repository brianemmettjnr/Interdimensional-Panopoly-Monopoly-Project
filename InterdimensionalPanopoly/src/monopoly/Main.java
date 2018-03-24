package monopoly;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

	public static void main(String[] args) 
	{
		createPanopoly();
	}
	
	public static void createPanopoly()
	{
		int numLocations = ThreadLocalRandom.current().nextInt(20, 40 + 1);
		Panopoly panopoly = new Panopoly(numLocations);
	}

}
