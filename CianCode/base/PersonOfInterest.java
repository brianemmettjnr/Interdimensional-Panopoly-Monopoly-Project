package base;

//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	//  Please excuse lack of comments atm, JFrame are yet to be implemented and questions are just 		//
	//	examples, beyond PersonOfIntrest class still a lot of useless methods I need to clean up	//
	// 	will get on that over next few days, run from this class and see results, any input welcome		//												
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

//Use the knowledge-base(s) of famous people (real and fictional) to generate apt comparisons

public class PersonOfInterest
{
	static Random DICE = new Random();
	

	
	// Various modules of the knowledge-base
	
	private KnowledgeBaseModule NOC          = null;
	private KnowledgeBaseModule NOC1          = null;
	private KnowledgeBaseModule NOC2          = null;
	private KnowledgeBaseModule WORLDS       = null;
	
	private static Vector allPeople				 = null;
	private static Vector allPeople2			= null;
	private static Vector<String> checkers			= null;

	

	
	public PersonOfInterest()
	{
		
		NOC1		  = new KnowledgeBaseModule("./Soft Eng 3/CianCode/Veale's The NOC List.txt", 3);
		NOC2		  = new KnowledgeBaseModule("./Soft Eng 3/CianCode/Veale's The NOC List.txt", 5);
		WORLDS        = new KnowledgeBaseModule("./Soft Eng 3/CianCode/Veale's domains.txt", 0);
		checkers = WORLDS.getAllKeys("Specific Domains"); 
		
		ArrayList<String> locations = new ArrayList<String>();
		Set<String> removes = new HashSet<>();
		
		for(int q = 0; q <= checkers.size()-1; q++)
		{
			String world = (String) checkers.get(q);
			allPeople = NOC1.getAllKeysWithFieldValue("Domains", world);
			int rands = DICE.nextInt(3)+1;
			if(allPeople.size() >= 3)
			{
				//System.out.println(allPeople); // gets all property based on domains where num properties >= 3
				
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
		System.out.println(locations);
				
	}
	
	public ArrayList<String> placenames (ArrayList<String> locations)
	{
		ArrayList<String> places = new ArrayList<String>();
		places.addAll(locations);
		return places;
	}
	
	
	public static void main(String[] args)
	{
		PersonOfInterest stereonomicon = new PersonOfInterest();
	}
		
}	

