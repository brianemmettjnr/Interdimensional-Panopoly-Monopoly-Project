package monopoly;

public class PrivateProperty extends NamedLocation implements Ownable{
	
	private Playable owner;
	private int price;

	public PrivateProperty(String name, int price) {
		super(name);
		this.price = price;
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
}
