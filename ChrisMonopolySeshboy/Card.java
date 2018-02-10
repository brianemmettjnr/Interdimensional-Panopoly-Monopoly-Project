
public class Card {

	private int type;
	private String message;
	private int actionId;
	private int parameter;
	private int[] parameters;
	
	Card () {
		message = "";
		return;
	}
	
	Card (int type, String message, int actionId) {
		this.type = type;
		this.message = message;
		this.actionId = actionId;
		return;
	}
	
	Card (int type, String message, int actionId, int parameter) {
		this.type = type;
		this.message = message;
		this.actionId = actionId;
		this.parameter = parameter;
		return;
	}
	
	Card (int type, String message, int actionId, int[] parameters) {
		this.type = type;
		this.message = message;
		this.actionId = actionId;
		this.parameters = parameters;
		return;
	}
	
	public int getType () {
		return type;
	}
	
	public int getAction () {
		return actionId;
	}
	
	public int getDestination () {
		return parameter;
	}
	
	public int getNumSpaces() {
		return parameter;
	}
	
	public int getAmount() {
		return parameter;
	}
	
	public int getHouseCost () {
		return parameters[0];
	}
	
	public int getHotelCost () {
		return parameters[1];
	}
	
	public String toString () {
		return message;
	}
	
}
