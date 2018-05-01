package interfaces;

public interface Mortgageable extends Ownable{
	public int getMortgageAmount();
	
	public String mortgage();
	public String redeem();
	
	public boolean isMortgaged();
}
