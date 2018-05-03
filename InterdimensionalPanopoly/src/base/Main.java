package base;

public class Main 
{
	public static void main(String [] args)
	{
		PersonOfInterest ps = new PersonOfInterest();
		System.out.println(ps.locations + "\n");
		System.out.println(ps.CriminalCardsBalancePos());
		System.out.println(ps.CriminalCardsPosition());	
		System.out.println(ps.CriminalCardsBalanceNeg());
		System.out.println(ps.Opponents());
		System.out.println(ps.Murderer());
		System.out.println(ps.Wealth());
		System.out.println(ps.Blackmailer());
		System.out.println(ps.BlackmailerBr());
		System.out.println(ps.Weapons());
		System.out.println(ps.CarCrash()); // 10 here
		System.out.println(ps.Inherit());
		System.out.println(ps.Pawns());
		System.out.println(ps.Parties());
		System.out.println(ps.Wedding());
		System.out.println(ps.Scammed()); // 15 here
		System.out.println(ps.Lectured());
		System.out.println(ps.Villainy());
		System.out.println(ps.Terrorism());
		System.out.println(ps.Royalty());
		System.out.println(ps.Sports()); // 20 here
		System.out.println(ps.TestDrive());
		System.out.println(ps.TimeTravel());
		System.out.println(ps.DoomsDay());

		ps.Question();
		System.out.print(ps.question + "\n");
		for(int i = 0; i < ps.answers.length; i++)
		{
			System.out.print(ps.answers[i] + "\n");
		} // do this brian to call the questions
		// possibility to add cards based on whether a certain property type is in the game, e.g. star trek planet = card "Beem me up Scotty" and move
		// to location
	}
}
// impending doom 