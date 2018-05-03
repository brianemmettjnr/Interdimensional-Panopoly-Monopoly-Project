package interfaces;

public interface Rentable extends Ownable, Groupable
{
	public void reset();
	public int getRentalAmount();
}
