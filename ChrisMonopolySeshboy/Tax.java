
public class Tax extends Square {
	
	int amount;
	
	Tax (String name, int amount) {
		super(name);
		this.amount = amount;
		return;
	}

	public int getAmount () {
		return amount;
	}
	
}
