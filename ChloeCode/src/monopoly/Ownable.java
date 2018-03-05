package monopoly;

public interface Ownable 
{
	public Playable getOwner();
	public void setOwner(Playable player);
	
	public int getPrice();
}
