package base;

import monopoly.Card;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PersonOfInterest
{
	static Random DICE = new Random();
	
	private KnowledgeBaseModule NOC			= null;
	private KnowledgeBaseModule NOC1       = null;
	private KnowledgeBaseModule NOC2		= null;
	private KnowledgeBaseModule NOC3		= null;
	private KnowledgeBaseModule NOC4		= null;
	private KnowledgeBaseModule NOC5		= null;
	private KnowledgeBaseModule NOC6		= null;
	private KnowledgeBaseModule NOC7		= null;
	private KnowledgeBaseModule WORLDS     = null;

	private static Vector<String> People			= null;
	private static Vector<String> allPeople			= null;
	private static Vector<String> checkers			= null;
	private static Vector<String> criminals 		= null;
	private static Vector<String> criminalsCars	= null;
	private static Vector<String> enemies			= null;
	private static Vector<String> opponents 		= null;
	private static Vector<String> murder 			= null;
	private static Vector<String> wealthy			= null;
	private static Vector<String> blackmail			= null;
	private static Vector<String> blackmailBritish	= null;
	private static Vector<String> weapons 			= null;
	private static Vector<String> cars				= null;
	private static Vector<String> inheritance		= null;
	private static Vector<String> actor				= null;
	private static Vector<String> wedding			= null;
	private static Vector<String> scam				= null;
	private static Vector<String> lecture			= null;
	private static Vector<String> villain			= null;
	private static Vector<String> terrorist			= null;
	private static Vector<String> royalty			= null;
	private static Vector<String> sportstar			= null;
	private static Vector<String> tests				= null;
	private static Vector<String> magic				= null;
	private static Vector<String> placeholder		= null;
	private static Vector<String> questions			= null;
	private static Vector<String> answers			= null;
	private static Vector<String> wrongs			= null;
	private static Vector<String> stations			= null;

	public static ArrayList<String> locations = new ArrayList<String>();
	public static ArrayList<String> stationNames = new ArrayList<String>();
	Set<String> removes = new HashSet<>();
	public static ArrayList<String> places = new ArrayList<String>();

	public PersonOfInterest()
	{
		NOC 		  = new KnowledgeBaseModule("Veale's The NOC List.txt", 0);
		NOC1		  = new KnowledgeBaseModule("Veale's The NOC List.txt", 3);
		NOC2		  = new KnowledgeBaseModule("Veale's The NOC List.txt", 10);
		NOC3 		  = new KnowledgeBaseModule("Veale's The NOC List.txt", 8);
		NOC4 		  = new KnowledgeBaseModule("Veale's The NOC List.txt", 11);
		NOC5		  = new KnowledgeBaseModule("Veale's The NOC List.txt", 4);
		NOC6		  = new KnowledgeBaseModule("Veale's The NOC List.txt", 17);
		NOC7		  = new KnowledgeBaseModule("Veale's The NOC List.txt", 16);
		WORLDS        = new KnowledgeBaseModule("Veale's domains.txt", 0);
		
		criminals 	= NOC.getAllKeysWithFieldValueNon("Genres", "crime");
		enemies 	= NOC.getAllKeysWithoutFieldValue("Opponent", "");
		checkers 	= WORLDS.getAllKeys("Specific Domains");
		murder		= NOC.getAllKeysWithFieldValueNon("Negative Talking Points", "evil");
		wealthy 	= NOC.getAllKeysWithFieldValueNon("Positive Talking Points", "wealthy");
		blackmail 	= NOC.getAllKeysWithFieldValueNon("Domains", "American politics");
		blackmailBritish = NOC.getAllKeysWithFieldValueNon("Domains", "British politics");
		weapons 	= NOC.getAllKeysWithoutFieldValue("Weapon of Choice", "");
		cars 		= NOC.getAllKeysWithoutFieldValue("Vehicle of Choice", "");
		inheritance = NOC.getAllKeysWithFieldValueNon("Category", "Novelist");
		actor		= NOC.getAllKeysWithFieldValueNon("Category", "Actor");
		wedding 	= NOC.getAllKeysWithFieldValueNon("Marital Status", "single");
		scam 		= NOC.getAllKeysWithFieldValueNon("Negative Talking Points", "devious");
		lecture 	= NOC.getAllKeysWithFieldValueNon("Category", "Intellectual");
		villain		= NOC.getAllKeysWithFieldValueNon("Category", "Villain");
		terrorist	= NOC.getAllKeysWithFieldValueNon("Category", "Terrorist");
		royalty 	= NOC.getAllKeysWithFieldValueNon("Domains", "British royalty");
		sportstar 	= NOC.getAllKeysWithFieldValueNon("Category", "Athlete");
		tests 		= NOC2.getAllKeys("Vehicle of Choice");
		magic 		= NOC.getAllKeysWithFieldValueNon("Category", "Wizard");


		for(int q = 0; q <= checkers.size()-1; q++)
		{
			String world = (String) checkers.get(q);
			allPeople = NOC1.getAllKeysWithFieldValue("Domains", world);
			
			int rands = DICE.nextInt(1)+2; // change to get number of in world properties 
			if(allPeople.size() >= 2)	// must match number in () line above
			{		
				for(int z = 0; z < rands; z++)
				{
					int n = DICE.nextInt(allPeople.size());
					locations.add((String) allPeople.get(n));
					allPeople.remove(n);
				}
			}
		}		
		
		removes.addAll(locations);
		locations.clear();
		locations.addAll(removes); // removes duplicates
		locations.remove(0);
	}

	public String Surnames()
	{
		stations 	= NOC.getAllKeys("Character");
		int rand = 0;
		String stationName = "";
		do {
			rand = DICE.nextInt(stations.size() - 2) + 1;
			String hold[] = stations.get(rand).split(" ");
			stationName = hold[hold.length-1];
		}	while(stationNames.contains(stationName));
		
		stationNames.add(stationName);
		return stationName;
	}

	public String TransportLinks()
	{
		Surnames();
		int rand2 = 0;
		String[] statNames = {"Hyperloop", "Teleportation Station", "Monorail", "Metro Station", "Jetpack Station", "Hovercar Depot", "Portal Gun Rental", "Bifrost Gate"};
		rand2 = DICE.nextInt(statNames.length);
		String TranLink = Surnames() + " " + statNames[rand2];
		return TranLink;

	}

	public String utility()
	{
		String[] utilityNames = {"Cryptocurrency Exchange", "Data Bank", "Hydroelectric Dam", "Wind Turbines", "Nuclear Power Station", "Lightning Farm", "Solar Panels"};
		int rand = 0;
		rand = DICE.nextInt(utilityNames.length);
		String utility = Surnames() + " " + utilityNames[rand];
		return utility;
	}

	public ArrayList<String> placenames (ArrayList<String> locations)
	{
		ArrayList<String> places = new ArrayList<String>();
		places.addAll(locations);
		return places;
	}
	
	public Card CriminalCardsBalancePos()
	{
		int rands = DICE.nextInt(criminals.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String robbedLoc[] = {"a bank", "a safe", "a cryptocurrency exchange", "a mansion", "the CIA", "the FBI", "the White House", "Trump Tower"};
		int locs = DICE.nextInt(robbedLoc.length);		
		String output = "You help " + criminals.get(rands) + " rob " + robbedLoc[locs] + ", gain Q" + moneyValues[mons];
		return new Card(Card.BAL_PLUS, output, moneyValues[mons]);
	}
	
	public Card CriminalCardsPosition()
	{
		int rands = DICE.nextInt(criminals.size()-1);
		int moves = DICE.nextInt(14)+1;
		criminalsCars = NOC2.getAllKeysWithFieldValueNon("Character", criminals.get(rands));
		String output = "You are in a police chase with " + criminals.get(rands) + " in their " + criminalsCars + ", move forward " + moves + " space(s)"; 
		output = output.replace("]", "");
		output = output.replace("[", "");
		return new Card(Card.POSITION_PLUS, output, moves);
	}
	
	public Card CriminalCardsBalanceNeg()
	{
		int rands = DICE.nextInt(criminals.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String output = "You get busted while with " + criminals.get(rands) + ", pay Q" + moneyValues[mons] + " in bail money";  
		return new Card(Card.BAL_MINUS, output, moneyValues[mons]); // possible to go to jail otherwise
	}
	
	public Card Opponents()
	{
		int rands = DICE.nextInt(enemies.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		opponents = NOC3.getAllKeysWithFieldValueNon("Character", enemies.get(rands));
		String output = "You help " + enemies.get(rands) + " defeat their opponent(s) " + opponents + ", receive Q" + moneyValues[mons];
		output = output.replace("]", "");
		output = output.replace("[", "");
		return new Card(Card.BAL_PLUS, output, moneyValues[mons]);
	}
	
	public Card Murderer()
	{
		int rands = DICE.nextInt(murder.size());
		String output = "You kill the evil " + murder.get(rands) + " go to jail";
		return  new Card(Card.GO_TO_JAIL, output);
	}
	
	public Card Wealth()
	{
		int rands = DICE.nextInt(wealthy.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String output = "You aid " + wealthy.get(rands) + ", they reward you with Q" + moneyValues[mons];
		return new Card(Card.BAL_PLUS, output, moneyValues[mons]);
	}
	
	public Card Blackmailer()
	{
		int rands = DICE.nextInt(blackmail.size());
		opponents = NOC3.getAllKeysWithFieldValueNon("Character", blackmail.get(rands));
		
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String output = "";
		
		if (opponents.equals(""))
			output = "You catch " + blackmail.get(rands) + " in bed with Donald Trump; you blackmail them, receive Q" + moneyValues[mons];
		else
			output = "You catch " + blackmail.get(rands) + " in bed with " + opponents + "; you blackmail them, receive Q" + moneyValues[mons];
		
		output = output.replace("]", "");
		output = output.replace("[", "");
		return new Card(Card.BAL_PLUS, output, moneyValues[mons]);

	}
	
	public Card BlackmailerBr()
	{
		int rands = DICE.nextInt(blackmailBritish.size());
		opponents = NOC3.getAllKeysWithFieldValueNon("Character", blackmailBritish.get(rands));
		String output = "You catch " + blackmailBritish.get(rands) + " in bed with " + opponents + ", receive a free pardon from prison";
		output = output.replace("]", "");
		output = output.replace("[", "");
		return new Card(Card.GET_OUT_OF_JAIL, output);
	}
	
	public Card Weapons()
	{
		int rands = DICE.nextInt(weapons.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		People = NOC4.getAllKeysWithFieldValueNon("Character", weapons.get(rands));
		String output = "You purchase " + weapons.get(rands) + "'s " + People + " at auction, pay Q" + moneyValues[mons];
		output = output.replace("]", "");
		output = output.replace("[", "");
		return new Card(Card.BAL_MINUS, output, moneyValues[mons]);
	}
	
	public Card Pawns()
	{
		int rands = DICE.nextInt(weapons.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		People = NOC4.getAllKeysWithFieldValueNon("Character", weapons.get(rands));
		String output = "You pawn " + weapons.get(rands) + "'s " + People + ", recieve Q" + moneyValues[mons];
		output = output.replace("]", "");
		output = output.replace("[", "");
		return new Card(Card.BAL_PLUS, output, moneyValues[mons]);
	}
	
	public Card CarCrash()
	{
		int rands = DICE.nextInt(cars.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		People = NOC2.getAllKeysWithFieldValueNon("Character", cars.get(rands));
		String output = "You total " + cars.get(rands) + "'s " + People + " while test driving it, pay Q" + moneyValues[mons];
		output = output.replace("]", "");
		output = output.replace("[", "");
		return new Card(Card.BAL_MINUS, output, moneyValues[mons]);
	}
	
	public Card Inherit()
	{
		int rands = DICE.nextInt(inheritance.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String output = "You inherit the right to " + inheritance.get(rands) + "'s novels, receive Q" + moneyValues[mons];
		return new Card(Card.BAL_PLUS, output, moneyValues[mons]);
	}
	
	public Card Parties()
	{
		int rands = DICE.nextInt(actor.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String family[] = {"niece", "nephew", "cousin", "son", "daughter"};
		int fams = DICE.nextInt(family.length);
		String output = "You pay Q" + moneyValues[mons] + " for " + actor.get(rands) + " to attend your " + family[fams] +"'s birthday party";
		return new Card(Card.BAL_MINUS, output, moneyValues[mons]);
	}
	
	public Card Wedding()
	{
		int rands = DICE.nextInt(wedding.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String output = "You attend " + wedding.get(rands) + "'s wedding, pay Q" + moneyValues[mons] + " for the wedding gift";
		return new Card(Card.BAL_MINUS, output, moneyValues[mons]);
	}
	
	public Card Scammed()
	{
		int rands = DICE.nextInt(scam.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String output = "You get caught in " + scam.get(rands) + "'s pyramid scheme scam, pay Q" + moneyValues[mons];
		return new Card(Card.BAL_MINUS, output, moneyValues[mons]);
	}
	
	public Card Lectured()
	{
		int rands = DICE.nextInt(lecture.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String output = "You are attending a lecture presided over by " + lecture.get(rands) + ", pay Q" + moneyValues[mons] + " for tickets";
		return new Card(Card.BAL_MINUS, output, moneyValues[mons]);
	}
	
	public Card Villainy()
	{
		int rands = DICE.nextInt(villain.size());
		String output = "You are caught aiding " + villain.get(rands) + ", go to jail"; 
		return new Card(Card.GO_TO_JAIL, output);
	}
	
	public Card Terrorism()
	{
		int rands = DICE.nextInt(terrorist.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String output = "You punch the known terrorist " + terrorist.get(rands) + " in the face, pay Q" + moneyValues[mons] + " for a doctor's vist";
		return new Card(Card.BAL_MINUS, output, moneyValues[mons]);
	}
	
	public Card Royalty()
	{
		int rands = DICE.nextInt(royalty.size());
		String output = "You are knighted by " + royalty.get(rands) + " for your services to business, recieve a royal pardon for future offences";
		return new Card(Card.GET_OUT_OF_JAIL, output);
	}
	
	public Card Sports()
	{
		int rands = DICE.nextInt(sportstar.size());
		int moneyValues[] = {50,100,150,200,250};
		int mons = DICE.nextInt(moneyValues.length);
		String sportTypes[] = {"American Football franchise", "Football club", "Hurling club", "Basketball franchise", "Baseball franchise", "Lacrosse franchise", "Hockey franchise", "Ice Hockey franchise", "Golf club"};
		int spts = DICE.nextInt(sportTypes.length);
		String output = "You invest in a new " + sportTypes[spts] + " with " + sportstar.get(rands) + ", pay Q" + moneyValues[mons];
		return new Card(Card.BAL_MINUS, output, moneyValues[mons]);
	}
	
	public Card TestDrive()
	{
		int rands = DICE.nextInt(tests.size());
		int moves = DICE.nextInt(14)+1;
		String output = "You test drive the new " + tests.get(rands) + " advance " + moves + " space(s) forward";
		return new Card(Card.POSITION_PLUS, output, moves);
	}
	
	public Card TimeTravel()
	{
		int rands = DICE.nextInt(magic.size());
		int moves = DICE.nextInt(14)+1;
		String output = "You time travel with " + magic.get(rands) + " move back " + moves + " space(s)";
		return new Card(Card.POSITION_MINUS, output, moves);
	}

	public Card DoomsDay()
	{
		int rands = DICE.nextInt(villain.size());
		String endWorld[] = {"zombie apocalypse", "nuclear weapons", "one-inch punch", "virus", "infinity gauntlet", "elderwand", "asteroid", "plague", "Death Star", "robot killing machines", "terminators", "alien invaders", "left-on immersion"};
		int ends = DICE.nextInt(endWorld.length);
		String output = "The end of the world is nigh, " + villain.get(rands) + " and their " + endWorld[ends] + " have brought about armageddon in t-minus 5 minutes, spend your remaining time wisely";
		placeholder = NOC2.getAllKeysWithFieldValueNon("Characters", cars.get(rands));
		return new Card(Card.DOOMSDAY, output);
	}

	public String[] Question()
	{
		int chance = 0;
		chance = DICE.nextInt(4);
		String qandA[] = new String[5];

		if(chance == 0)
		{
			questions = NOC.getAllKeys("Opponent");
			int rand = 0;
			rand = DICE.nextInt(questions.size() - 2) + 1;
			qandA[0] = "Which of the following is an opponent of " + questions.get(rand) + "?";
			answers = NOC3.getAllKeysWithFieldValueNon("Character", questions.get(rand));
			String[] possibles = answers.firstElement().split(", ");
			qandA[1] = "" + possibles[ThreadLocalRandom.current().nextInt(possibles.length)].replace(",", "");
			wrongs = NOC3.getAllKeysWithoutFieldValue("Character", questions.get(rand));
			int rand2 = 0;
			for (int i = 2; i < 5; i++) {
				rand2 = DICE.nextInt(wrongs.size() - 2) + 1;
				possibles = wrongs.get(rand2).split(", ");
				String temp = possibles[ThreadLocalRandom.current().nextInt(possibles.length)].replace(",", "");
				qandA[i] = temp;
			}
		}

		else if(chance == 1)
		{
			questions = NOC.getAllKeys("Vehicle of Choice");
			int rand = 0;
			rand = DICE.nextInt(questions.size()-2)+1;
			qandA[0] = questions.get(rand) + " is associated with which of the following modes of transport?";
			answers = NOC2.getAllKeysWithFieldValueNon("Character", questions.get(rand));
			String[] possibles = answers.firstElement().split(", ");
			qandA[1] = "" + possibles[ThreadLocalRandom.current().nextInt(possibles.length)].replace(",", "");
			wrongs = NOC2.getAllKeysWithoutFieldValue("Character", questions.get(rand));
			int rand2 = 0;
			for (int i = 2; i < 5; i++)
			{
				rand2 = DICE.nextInt(wrongs.size() - 2) + 1;
				possibles = wrongs.get(rand2).split(", ");
				String temp = possibles[ThreadLocalRandom.current().nextInt(possibles.length)].replace(",", "");
				qandA[i] = temp;
			}
		}

		else if(chance == 2)
		{
			questions = NOC.getAllKeys("Creator");
			int rand = 0;
			rand = DICE.nextInt(questions.size()-2)+1;
			qandA[0] = questions.get(rand) + " is a creation of which of the following?";
			answers = NOC6.getAllKeysWithFieldValueNon("Character", questions.get(rand));
			String[] possibles = answers.firstElement().split(", ");
			qandA[1] = "" + possibles[ThreadLocalRandom.current().nextInt(possibles.length)].replace(",", "");
			wrongs = NOC6.getAllKeysWithoutFieldValue("Character", questions.get(rand));
			int rand2 = 0;
			for (int i = 2; i < 5; i++)
			{
				rand2 = DICE.nextInt(wrongs.size() - 2) + 1;
				possibles = wrongs.get(rand2).split(", ");
				String temp = possibles[ThreadLocalRandom.current().nextInt(possibles.length)].replace(",", "");
				qandA[i] = temp;
			}
		}
		else if(chance == 3)
		{
			questions = NOC.getAllKeys("Portrayed By");
			int rand = 0;
			rand = DICE.nextInt(questions.size()-2)+1;
			qandA[0] = questions.get(rand) + " is portrayed by which of the following?";
			answers = NOC7.getAllKeysWithFieldValueNon("Character", questions.get(rand));
			String[] possibles = answers.firstElement().split(", ");
			qandA[1] = "" + possibles[ThreadLocalRandom.current().nextInt(possibles.length)].replace(",", "");
			wrongs = NOC7.getAllKeysWithoutFieldValue("Character", questions.get(rand));
			int rand2 = 0;
			for (int i = 2; i < 5; i++)
			{
				rand2 = DICE.nextInt(wrongs.size() - 2) + 1;
				possibles = wrongs.get(rand2).split(", ");
				String temp = possibles[ThreadLocalRandom.current().nextInt(possibles.length)].replace(",", "");
				qandA[i] = temp;
			}
		}

		return qandA;
	}
}