package monopoly;

public class Card 
{
	public static final int BAL_PLUS = 0;
	public static final int BAL_MINUS = 1;
	public static final int POSITION_PLUS = 2;
	public static final int POSITION_MINUS = 3;
	public static final int GO_TO_JAIL = 4;
	public static final int GET_OUT_OF_JAIL = 5;
	public static final int DOOMSDAY = 6;
	
	String message;
	int consequence;
	int type;
	
	public Card(int type, String message, int consequence)
	{
		this.type = type;
		this.message = message;
		this.consequence = consequence;
	}
	
	public Card(int type, String message)
	{
		this.type = type;
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	void performConsequence(Panopoly panopoly)
	{
		switch(type) 
		{
			case Card.BAL_PLUS: 		panopoly.getCurrentPlayer().earn(consequence);
			break;
			case Card.BAL_MINUS:		panopoly.getCurrentPlayer().pay(consequence);
			break;
			case Card.POSITION_PLUS: 	panopoly.getCurrentPlayer().move(consequence, true);
										panopoly.getSquareAction();
			break;
			case Card.POSITION_MINUS:	panopoly.getCurrentPlayer().move(consequence, false);
										panopoly.getSquareAction();
			break;
			case Card.GO_TO_JAIL:		panopoly.getCurrentPlayer().sendToJail();
			break;
			case Card.GET_OUT_OF_JAIL:	panopoly.getCurrentPlayer().addGOOJFree();
			break;
			default:					panopoly.startCountdown();
			break;
		}
	}
}
