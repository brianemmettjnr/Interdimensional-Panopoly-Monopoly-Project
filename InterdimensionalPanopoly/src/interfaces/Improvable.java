package interfaces;

public interface Improvable extends Ownable{

	public int getBuildPrice();
	public boolean hasBuildings();

	int getNumBuildings();

	public String build();
	public String demolish();
	
	public int getNumHouses();
	public int getNumHotels();
}
