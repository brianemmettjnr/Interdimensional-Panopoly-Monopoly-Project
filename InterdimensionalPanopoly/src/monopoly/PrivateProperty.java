package monopoly;

import interfaces.Ownable;
import interfaces.Playable;

public abstract class PrivateProperty extends NamedLocation implements Ownable
{	
	private Playable owner;
	private int price;
	
	public PrivateProperty(String name, int price) {
		super(name);
		this.price = price;
		this.owner = null;
	}

	@Override
	public Playable getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Playable player) {
		owner = player;		
	}

	@Override
	public int getPrice() {
		return price;
	}
	
	public boolean isOwned() {
		if(owner != null)
			return true;
		
		else
			return false;
	}
}
