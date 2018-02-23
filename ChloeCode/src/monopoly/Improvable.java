package monopoly;

public interface Improvable extends Ownable{

	public int getBuildPrice();
	
	public void build();
	public void demolish();
	
	public int getNumHouses();
	public int getNumHotels();
}
