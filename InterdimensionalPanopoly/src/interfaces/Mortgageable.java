package interfaces;

public interface Mortgageable extends Ownable{
	public int getMortgageAmount();
	public int getRedeemAmount();
	
	public String mortgage();
	public String redeem();
	
	public boolean isMortgaged();
}
