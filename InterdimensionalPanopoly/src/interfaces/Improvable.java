package interfaces;

public interface Improvable extends Ownable{

	public int getBuildPrice();
	public boolean hasBuildings();

	int getNumBuildings();

	public void build();
	public void demolish();
	
	public int getNumHouses();
	public int getNumHotels();
}
