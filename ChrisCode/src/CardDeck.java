import java.util.*;

public class CardDeck {
	
	public static final int ACT_GO_FORWARD = 0;
	public static final int ACT_GO_BACKWARD = 1;
	public static final int ACT_MOVE = 2;
	public static final int ACT_GOTO_JAIL = 3;
	public static final int ACT_GET_OUT_OF_JAIL = 4;
	public static final int ACT_PAY_HOUSES = 5;
	public static final int ACT_PAY = 6;
	public static final int ACT_RECEIVE = 7;
	public static final int ACT_PAY_OR_CHANCE = 8;
	public static final int ACT_GIFTS = 9;
	
	ArrayList<Card> cards = new ArrayList<Card>();
	
	public void add (Card card) {
		cards.add(card);
		return;
	}

	public void shuffle () {
		Collections.shuffle(cards);
		return;
	}
	
	public Card get () {
		Card card = cards.get(0);
		cards.remove(0);
		return card;
	}
	
	public Card put (Card card) {
		cards.add(card);
		return card;
	}

}
