package monopoly;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

	public static void main(String[] args) 
	{
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (UnsupportedLookAndFeelException e) { e.printStackTrace(); }*/
		createPanopoly();

	}
	
	public static void createPanopoly()
	{	
		//allows for number of squares between 32 and 56 that are divisible by 4
		int numLocations = 4*ThreadLocalRandom.current().nextInt(8, 12 + 1);
		new Panopoly(numLocations);
	}

}
