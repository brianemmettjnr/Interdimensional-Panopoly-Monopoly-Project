public interface Locatable extends Identifiable{
	
	public void setLeft(Locatable left);
	public void setRight(Locatable right);
	
	public Locatable goLeft();
	public Locatable goRight();
}
