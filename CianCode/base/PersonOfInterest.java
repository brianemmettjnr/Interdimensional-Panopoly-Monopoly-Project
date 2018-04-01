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
	
	private String knowledgeDir	= null;   // directory where knowledge-base(s) can be found
	
	// Various modules of the knowledge-base
	
	private KnowledgeBaseModule NOC          = null;
/*	private KnowledgeBaseModule CATEGORIES   = null;
	private KnowledgeBaseModule CLOTHES      = null;
	private KnowledgeBaseModule CREATIONS    = null;
	private KnowledgeBaseModule DOMAINS      = null;
	private KnowledgeBaseModule VEHICLES     = null;
	private KnowledgeBaseModule WEAPONS	     = null;
	private KnowledgeBaseModule PLACES       = null;
	private KnowledgeBaseModule SUPERLATIVES = null;
	private KnowledgeBaseModule COMPARATIVES = null;
	private KnowledgeBaseModule ANTONYMS	 = null;
	private KnowledgeBaseModule PAST_PERFECTS= null;*/
	private KnowledgeBaseModule NOC1          = null;
	private KnowledgeBaseModule NOC2          = null;
	private KnowledgeBaseModule WORLDS       = null;


	
	private static Vector allPeople				 = null;
	private static Vector allPeople2			= null;
	
	private static Vector fictionalPeople			 = null;
	private static Vector realPeople				 = null;
	private static Vector men						 = null;
	private static Vector women					 = null;
	private static Vector fathers				= null;
	private static Vector notfathers			= null;
	private static Vector mothers				= null;
	private static Vector notmothers			= null;
	private static Vector insideusa    			= null;
	private static Vector outsideusa				= null;
	private static Vector fictionalworldsDC 		= null;
	private static Vector fictionalworldsSW		= null;
	private static Vector realworldAmPol		= null;
	private static Vector fictionalworldsMarvel  = null;
	private static Vector fictionalworldsLOTR    = null;
	private static Vector fictionalworldsSimpsons = null;
	private static Vector<String> checkers			= null;
	private static Vector threeplus					=null;	
	private static Vector twoonly				= null;
	

	
	public PersonOfInterest(String kbDirectory)
	{
		knowledgeDir = kbDirectory;
		
		NOC           = new KnowledgeBaseModule(knowledgeDir + "Veale's The NOC List.txt", 0);
		/*CATEGORIES    = new KnowledgeBaseModule(knowledgeDir + "Veale's Category Hierarchy.txt", 0);
		CLOTHES       = new KnowledgeBaseModule(knowledgeDir + "Veale's clothing line.txt", 1);  // 1 is the column number of the key value
		CREATIONS     = new KnowledgeBaseModule(knowledgeDir + "Veale's creations.txt", 0);
		DOMAINS       = new KnowledgeBaseModule(knowledgeDir + "Veale's domains.txt", 0);
		VEHICLES      = new KnowledgeBaseModule(knowledgeDir + "Veale's vehicle fleet.txt", 1);  // 1 is the column number of the key value
		WEAPONS	      = new KnowledgeBaseModule(knowledgeDir + "Veale's weapon arsenal.txt", 1);  // 1 is the column number of the key value
		PLACES        = new KnowledgeBaseModule(knowledgeDir + "Veale's place elements.txt", 0);		
		SUPERLATIVES  = new KnowledgeBaseModule(knowledgeDir + "superlatives.txt", 0);
		COMPARATIVES  = new KnowledgeBaseModule(knowledgeDir + "comparatives.txt", 0);
		ANTONYMS	  = new KnowledgeBaseModule(knowledgeDir + "antonyms.txt", 0);
		PAST_PERFECTS = new KnowledgeBaseModule(knowledgeDir + "past perfects.txt", 0);
		POS_QUALITIES = NOC.getInvertedField("Positive Talking Points");
		NEG_QUALITIES = NOC.getInvertedField("Negative Talking Points");
		ALL_QUALITIES = NOC.getInvertedField("Positive Talking Points");
		ALL_QUALITIES = NOC.getInvertedField("Negative Talking Points", ALL_QUALITIES);*/
		NOC1		  = new KnowledgeBaseModule(knowledgeDir + "Veale's The NOC List.txt", 3);
		NOC2		  = new KnowledgeBaseModule(knowledgeDir + "Veale's The NOC List.txt", 5);
		WORLDS        = new KnowledgeBaseModule(knowledgeDir + "Veale's domains.txt", 0);

				
	
		realPeople      = NOC.difference(allPeople, fictionalPeople);
		men			    = NOC.getAllKeysWithFieldValue("Gender", "male");
		women			= NOC.getAllKeysWithFieldValue("Gender", "female");
		insideusa 		= NOC.getAllKeysWithFieldValue("Address 3", "USA");
		outsideusa		= NOC.getAllKeysWithoutFieldValue("Address 3", "USA");
		fathers 		= NOC.getAllKeysWithFieldValue("Category", "Father");
		notfathers 		= NOC.getAllKeysWithoutFieldValue("Category", "Father");
		mothers			= NOC.getAllKeysWithFieldValue("Category", "Mother");
		notmothers		= NOC.getAllKeysWithoutFieldValue("Category", "Mother");
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
		
/*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		locations is the full ArrayList of locations, with no duplicates start from random start area each time, have between 137-153 individual properties
		all properties have a shared world with at least 2 other properties for colour groups 
		
*////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
	}
		
	public static void main(String[] args)
	{
		String kdir = "";
		String tdir = "";	
		
		PersonOfInterest stereonomicon = new PersonOfInterest(kdir);
	//	ArrayList<String> locations = new ArrayList<String>();
		
		/*
		int qs = DICE.nextInt(5) + 1;
		for(int z = 0; z < qs; z++)
		{
			int n = DICE.nextInt(fictionalworldsDC.size());
			locations.add((String) fictionalworldsDC.get(n));
			fictionalworldsDC.remove(n);
		}
		for(int z = 0; z < qs; z++)
		{
			int n = DICE.nextInt(fictionalworldsSW.size());
			locations.add((String) fictionalworldsSW.get(n));
			fictionalworldsSW.remove(n);
		}
		for(int z = 0; z < qs; z++)
		{
			int n = DICE.nextInt(realworldAmPol.size());
			locations.add((String) realworldAmPol.get(n));
			realworldAmPol.remove(n);
		}
		
		for(int z = 0; z < qs; z++)
		{
			int n = DICE.nextInt(fictionalworldsMarvel.size());
			locations.add((String) fictionalworldsMarvel.get(n));
			fictionalworldsMarvel.remove(n);
		}
		
		for(int z = 0; z < qs; z++)
		{
			int n = DICE.nextInt(fictionalworldsLOTR.size());
			locations.add((String) fictionalworldsLOTR.get(n));
			fictionalworldsLOTR.remove(n);
		}
		
		for(int z = 0; z < qs; z++)
		{
			int n = DICE.nextInt(fictionalworldsSimpsons.size());
			locations.add((String) fictionalworldsSimpsons.get(n));
			fictionalworldsSimpsons.remove(n);
		}
		// sometimes only gets 1 need to fix this
		
		for(int q = 0; q < locations.size(); q++)
		{
			System.out.println(locations.get(q));
		}*/
				
	}
		/*///
		
		int qs = DICE.nextInt(8) + 1;
		
		if(qs == 1)
		{
			System.out.println("Which of the following is fictitious?");
			
			for(int z = 0; z < 3; z++)
			{
				int n = DICE.nextInt(realPeople.size());
				System.out.println(realPeople.get(n));
			}
			int n = DICE.nextInt(fictionalPeople.size());
			System.out.println(fictionalPeople.get(n));
		}
		
		else if(qs == 2)
		{
			System.out.println("Which of the following is a real person?");
			
			for(int z = 0; z < 3; z++)
			{
				int n = DICE.nextInt(fictionalPeople.size());
				System.out.println(fictionalPeople.get(n));
			}
			int n = DICE.nextInt(realPeople.size());
			System.out.println(realPeople.get(n));
		}
		else if(qs == 3)
		{
			System.out.println("Which of the following does not call America home?");
			for(int z = 0; z < 3; z++)
			{
				int n = DICE.nextInt(insideusa.size());
				System.out.println(insideusa.get(n));
			}
			int n = DICE.nextInt(outsideusa.size());
			System.out.println(outsideusa.get(n));
		}
		else if(qs == 4)
		{
			System.out.println("Which of the following resides in America?");
			for(int z = 0; z < 3; z++)
			{
				int n = DICE.nextInt(outsideusa.size());
				System.out.println(outsideusa.get(n));
			}
			int n = DICE.nextInt(insideusa.size());
			System.out.println(insideusa.get(n));
		}
		else if(qs == 5)
		{
			System.out.println("Which of the following is a father?");
			for(int z = 0; z < 3; z++)
			{
				int n = DICE.nextInt(notfathers.size());
				System.out.println(notfathers.get(n));
			}
			int n = DICE.nextInt(fathers.size());
			System.out.println(fathers.get(n));
		}
		else if(qs == 6)
		{
			System.out.println("Which of the following is not a father?");
			for(int z = 0; z < 3; z++)
			{
				int n = DICE.nextInt(fathers.size());
				System.out.println(fathers.get(n));
			}
			int n = DICE.nextInt(notfathers.size());
			System.out.println(notfathers.get(n));
		}
		else if(qs == 7)
		{
			System.out.println("Which of the following is not a mother?");
			for(int z = 0; z < 3; z++)
			{
				int n = DICE.nextInt(mothers.size());
				System.out.println(mothers.get(n));
			}
			int n = DICE.nextInt(notmothers.size());
			System.out.println(notmothers.get(n));
		}
		else if(qs == 8)
		{
			System.out.println("Which of the following is a mother?"); // very few categorised as numbers
			for(int z = 0; z < 3; z++)
			{
				int n = DICE.nextInt(notmothers.size());
				System.out.println(notmothers.get(n));
			}
			int n = DICE.nextInt(mothers.size());
			System.out.println(mothers.get(n));
		}	
	}*/
		
}	

