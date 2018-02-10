public class seshBot implements Bot {
	
	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	
	
	//creating instances of the APIs
	BoardAPI boardbot;			
	PlayerAPI playerbot;
	DiceAPI dicebot;
	
	boolean mortgage=false;
	
	int increment=0;
	int Mortgagepos=0;
	int commandcount=0;
	
	Player otherplayer=null; //creating an instance of player to make decision based on them
	
	int[] Array={1,3,6,8,9,11,13,14,16,18,19,21,23,24,26,27,29,31,32,34,37,39}; //array of squares that can be bought
	
	//constructor for seshBot
	seshBot (BoardAPI board, PlayerAPI player, DiceAPI dice) {
		
		boardbot = board;		
		playerbot = player;
		dicebot = dice;
		
		return;
	}
	
	public String getName () {
		
		return "seshBot";
	}
	
	public String getCommand () 
	{	// Add your code here
		
		//checks if a player is in jail and makes decision based on that
		if(jailCheck()!=null)
		{
			return jailCheck();
		}
		
		//checks if seshBot must declare bankruptcy
		if(playerbot.getBalance()<0)
		{
			return "bankrupt";
		}
		
		//checks if the square can be bought
		if(buy()!=null)
		{
			return "buy";
		}
		
		//if bought square has a mortgage mortgage the property immediately to get more in total player net worth 
		if(mortgage==true)
		{
			mortgage=false;
			return "mortgage " + boardbot.getProperty(Mortgagepos).getShortName();
		}
		
		//checking if seshBot has more of a net worth than the other player and if so will quit the game
		if(Assetcheck()==true)
		{
			return "quit";
		}
		
		//use of a binary semaphore to switch between "roll" and "done" the primary operations
		//shoutout to Felix Ballado for the inspiration
		//done will be returned when increments value is initially 0 and once called will be changed to 1 to operate with binary semaphore
		if(increment==0)
		{
			increment++;
			return "done";
		}
		//roll will be returned when increments value is initially 1 and once called will be changed to 0 to operate with binary semaphore
		if(increment==1)
		{
			increment--;
			return "roll";
		}
		
		return null;	
	}
	
	private boolean Assetcheck() //this function checks if seshBot is ahead of the other player
	{
		if(otherplayer==null)	//other player has already been initialized as null
		{
			commandcount++;
			if(otherplayer==null&&commandcount>100)	//other player has already been initialized as null
			{
				return true;
			}
			for(int i=0;i<Array.length;i++)
			{
				String shortName = boardbot.getProperty(Array[i]).getShortName();
				
				//finding and storing if a property is owned by another player
				if((boardbot.isSite(shortName) || boardbot.isStation(shortName) || boardbot.isUtility(shortName)))
				{
					if(boardbot.getProperty(Array[i]).getOwner()!=null&&boardbot.getProperty(Array[i]).getOwner()!=playerbot)
					{
						otherplayer = boardbot.getProperty(Array[i]).getOwner();
					}
				}
			}
			return false; //return false because seshBot is not in a position to quit the game yet
		}
		else
		{
			int other = otherplayer.getAssets();		//other player
			int me = playerbot.getAssets();				//seshBot
			
			if(me > other)								//if our overall assets(net worth) is more than the other players
			{											//return true to quit the game
				return true;							
			}
			else										//if our overall assets(net worth) are less than the other players
			{											//return false to continue playing the game
				return false;
			}
		}
	}
	private String jailCheck()
	{
		//if player is in jail after their role what to do
		if(playerbot.isInJail())
		{
			if(playerbot.hasGetOutOfJailCard())
			{
				
				/*
				 * if seshBot has a get out of jail card it will not be used 
				 * unless the balance is below 500
				 */
				if(playerbot.getBalance() < 500)
				{
					return "card";
				}
				else if(playerbot.getBalance() < 50)
				{
					return "roll";
				}
				else
				{
					return "pay";
				}
			}
			else		//pay is returned if there is no get out of jail card at all
			{
				return "pay";
			}
		}
		else
		{
			return null;
		}		
	}
	
	private String buy()						//buy properties
	{
		int position=playerbot.getPosition();	
		
		//return null if the current position of seshBot is not on an actual property
		if(!boardbot.isProperty(position))
		{
			return null;
		}
		
		Mortgagepos = position;
		Property pos = boardbot.getProperty(position);
		
		//if seshBot is left with a small balance after buying the property on which they are on they will not buy it
		if((playerbot.getBalance() - pos.getPrice()) < 70)
		{
			return null;
		}
		
		//if the property is not owned
		else if(!pos.isOwned())
		{
			mortgage = true; 	//there is a now a mortgage on the property so it can be mortgaged to get funds
			return "buy";		//return buy to purchase the property	
		}
		return null;
	}
	
	public String getDecision () 
	{
			return "pay";	//pay straight out for the chance card	
	}
	
}
