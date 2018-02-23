package monopoly;

public interface Mortgageable extends Ownable{
	public int getMortgageAmount();
	
	public void mortgage();
	public void redeem();
	
	public boolean isMortgaged();
}
