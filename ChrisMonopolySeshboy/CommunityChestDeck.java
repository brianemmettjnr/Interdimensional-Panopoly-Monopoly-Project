
public class CommunityChestDeck extends CardDeck {
	
	public static final int COMMUNITY_CHEST = 1;
	
	CommunityChestDeck () {

		// Movement
		cards.add(new Card(COMMUNITY_CHEST,"Advance to Go",ACT_GO_FORWARD,Board.POS_GO));
		cards.add(new Card(COMMUNITY_CHEST,"Go back to Old Kent Road",ACT_GO_BACKWARD,Board.POS_OLD_KENT_RD));

		// Taxes, Fines, etc.
		cards.add(new Card(COMMUNITY_CHEST,"Pay hospital " +  UI.CURRENCY_SYMBOL + "100.",ACT_PAY,100));
		cards.add(new Card(COMMUNITY_CHEST,"Doctor's fee. Pay " + UI.CURRENCY_SYMBOL + "50.",ACT_PAY,50));
		cards.add(new Card(COMMUNITY_CHEST,"Pay your insurance premium. " + UI.CURRENCY_SYMBOL + "50.",ACT_PAY,50));
		cards.add(new Card(COMMUNITY_CHEST,"Pay a  " + UI.CURRENCY_SYMBOL + "10 fine or take a Chance.",ACT_PAY_OR_CHANCE,10));

		// Receipts
		cards.add(new Card(COMMUNITY_CHEST,"Bank error in your favour. Collect " + UI.CURRENCY_SYMBOL + "200.",ACT_RECEIVE,200));
		cards.add(new Card(COMMUNITY_CHEST,"Annuity matures. Collect " + UI.CURRENCY_SYMBOL + "100.",ACT_RECEIVE,100));
		cards.add(new Card(COMMUNITY_CHEST,"You inherit " + UI.CURRENCY_SYMBOL + "100.",ACT_RECEIVE,100));
		cards.add(new Card(COMMUNITY_CHEST,"From sale of stock you get " + UI.CURRENCY_SYMBOL + "50.",ACT_RECEIVE,50));
		cards.add(new Card(COMMUNITY_CHEST,"Receive interest on 7% preference shares: " + UI.CURRENCY_SYMBOL + "25.",ACT_RECEIVE,25));
		cards.add(new Card(COMMUNITY_CHEST,"Income tax refund. Collect " + UI.CURRENCY_SYMBOL + "20.",ACT_RECEIVE,20));
		cards.add(new Card(COMMUNITY_CHEST,"You have won second prize in a beauty contest. Collect  " + UI.CURRENCY_SYMBOL + "10.",ACT_RECEIVE,10));
		cards.add(new Card(COMMUNITY_CHEST,"It is your birthday. Collect  " + UI.CURRENCY_SYMBOL + "10 from each player",ACT_GIFTS,10));

		// Jail
		cards.add(new Card(COMMUNITY_CHEST,"Go to jail. Move directly to jail. Do not pass Go. Do not collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GOTO_JAIL));
		cards.add(new Card(COMMUNITY_CHEST,"Get out of jail free. This card may be kept until needed or sold",ACT_GET_OUT_OF_JAIL));
		
		return;
	}

}
