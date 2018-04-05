package base;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import tabular.BucketTable;


public class KnowledgeBaseModule 
{
	private static Random RND 		= new Random();

	private Hashtable kb = new Hashtable();
	
	private Vector fieldNames  = new Vector();
	private Vector fieldTables = new Vector();
	
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	//   Constructors
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	
	public KnowledgeBaseModule(String filename, int keyPosition)
	{
		loadKnowledgeBaseFrom(filename, keyPosition);
	}
	
	public KnowledgeBaseModule(String filename)
	{
		loadKnowledgeBaseFrom(filename, 0);
	}
	
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	//  Accessors
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//

	// Get the list of fields that describe concepts (keys) in this knowledge-base module
	
	public Vector<String> getFieldNames()
	{
		return fieldNames;
	}

	// Get the values associated with a specific field of a key concept
	
	public Vector<String> getFieldValues(String fieldName, String key)
	{
		for (int f = 0; f < fieldNames.size(); f++)
		{
			if (fieldName.equals(fieldNames.elementAt(f)))
			{
				Hashtable field = (Hashtable)fieldTables.elementAt(f);
				
				return (Vector)field.get(key);
			}
		}
		
		return null;
	}
	
		
	// Check whether a key concept has a given value for a given field
	
	public boolean hasFieldValue(String fieldName, String key, String value)
	{
		for (int f = 0; f < fieldNames.size(); f++)
		{
			if (fieldName.equals(fieldNames.elementAt(f)))
			{
				Hashtable field = (Hashtable)fieldTables.elementAt(f);
				
				Vector values = (Vector)field.get(key);
				
				if (values == null || values.size() == 0)
					return false;
				
				for (int v = 0; v < values.size(); v++)
					if (value.equals(values.elementAt(v)))
						return true;		
			}
		}
		
		return false;
	}
	
	// Return a list of all the key concepts that have fields/values in this knowledge module
	
	public Vector<String> getAllFrames()
	{
		Vector longest = null;
		
		for (int f = 0; f < fieldTables.size(); f++)
		{
			Hashtable field = (Hashtable)fieldTables.elementAt(f);
			
			Vector list =  (Vector)field.get("*keylist*");
			
			if (list != null && (longest == null || list.size() > longest.size()))
				longest = list;
		}
		
		return longest;
	}
		
	// return a list of key concepts with a given value in a given field
	
	public Vector<String> getAllKeysWithFieldValue(String fieldname, String value)
	{
		Vector<String> matchingKeys = new Vector();

		if (value == null) 
			return matchingKeys;
		else
			value = value.intern();
		
		for (int f = 0; f < fieldTables.size(); f++)
		{
			String name = (String)fieldNames.elementAt(f);
			
			if (!name.equals(fieldname)) continue;
		
			Hashtable field = (Hashtable)fieldTables.elementAt(f);
			
			Vector keys =  (Vector)field.get("*keylist*");
			
			if (keys == null) break;
			
			for (int k = 0; k < keys.size(); k++)
			{
				String key = (String)keys.elementAt(k);
				
				Vector values = (Vector)field.get(key);
				
				if (values != null && values.contains(value))
					matchingKeys.add(key +" " + "(" +value +")");
			}
		}
		
		return matchingKeys;
	}
	public Vector<String> getAllKeysWithFieldValue2(String fieldname, Vector<String> value)
	{
		Vector<String> matchingKeys = new Vector();
	
		for (int f = 0; f < fieldTables.size(); f++)
		{
			String name = (String)fieldNames.elementAt(f);
			
			if (!name.equals(fieldname)) continue;
		
			Hashtable field = (Hashtable)fieldTables.elementAt(f);
			
			Vector keys =  (Vector)field.get("*keylist*");
			
			if (keys == null) break;
			
			for (int k = 0; k < keys.size(); k++)
			{
				String key = (String)keys.elementAt(k);
				
				Vector values = (Vector)field.get(key);
				
				if (values != null && values.contains(value))
					matchingKeys.add(key);
			}
		}
		
		return matchingKeys;
	}
	
	public Vector<String> getAllKeys(String fieldname)
	{
		Vector<String> matchingKeys = new Vector();

		
		for (int f = 0; f < fieldTables.size(); f++)
		{
			String name = (String)fieldNames.elementAt(f);
			
			if (!name.equals(fieldname)) continue;
		
			Hashtable field = (Hashtable)fieldTables.elementAt(f);
			
			Vector keys =  (Vector)field.get("*keylist*");
			
			if (keys == null) break;
			
			for (int k = 0; k < keys.size(); k++)
			{
				String key = (String)keys.elementAt(k);
				
				Vector values = (Vector)field.get(key);
					matchingKeys.add(key);
			}
		}
		
		return matchingKeys;
	}
	
	
	public Vector<String> getAllKeysWithValue(String fieldname, String value)
	{
		Vector<String> matchingKeys = new Vector();

		if (value == null) 
			return matchingKeys;
		else
			value = value.intern();
		
		for (int f = 0; f < fieldTables.size(); f++)
		{
			String name = (String)fieldNames.elementAt(f);
			
			if (!name.equals(fieldname)) continue;
		
			Hashtable field = (Hashtable)fieldTables.elementAt(f);
			
			Vector keys =  (Vector)field.get("*keylist*");
			
			if (keys == null) break;
			
			for (int k = 0; k < keys.size(); k++)
			{
				String key = (String)keys.elementAt(k);
				
				Vector values = (Vector)field.get(key);
				
				if (values != null && values.contains(value))
					matchingKeys.add(key);
			}
		}
		
		return matchingKeys;
	}
	
	public Vector<String> getAllKeysWithFieldValueAndKey(String fieldname, String value, String keyss)
	{
		Vector<String> matchingKeys = new Vector();

		if (value == null) 
			return matchingKeys;
		else
			value = value.intern();
		
		for (int f = 0; f < fieldTables.size(); f++)
		{
			String name = (String)fieldNames.elementAt(f);
			
			if (!name.equals(fieldname)) continue;
		
			Hashtable field = (Hashtable)fieldTables.elementAt(f);
			
			Vector keys =  (Vector)field.get(keyss);
			
			if (keys == null) break;
			
			for (int k = 0; k < keys.size(); k++)
			{
				String key = (String)keys.elementAt(k);
				
				Vector values = (Vector)field.get(key);
				
				if (values != null && values.contains(value))
					matchingKeys.add(key);
			}
		}
		
		return matchingKeys;
	}
	
	public Vector<String> getAllKeysWithoutFieldValue(String fieldname, String value)
	{
		Vector<String> matchingKeys = new Vector();

		if (value == null) 
			return matchingKeys;
		else
			value = value.intern(); 
		
		for (int f = 0; f < fieldTables.size(); f++)
		{
			String name = (String)fieldNames.elementAt(f);
			
			if (!name.equals(fieldname)) continue;
		
			Hashtable field = (Hashtable)fieldTables.elementAt(f);
			
			Vector keys =  (Vector)field.get("*keylist*");
			
			if (keys == null) break;
			
			for (int k = 0; k < keys.size(); k++)
			{
				String key = (String)keys.elementAt(k);
				
				Vector values = (Vector)field.get(key);
				
				if (values != null && values.contains(value) == false)
					matchingKeys.add(key);
			}
		}
		
		return matchingKeys;
	}
	
	
	public String selectRandomlyFrom(Vector<String> choices)
	{
		if (choices == null || choices.size() == 0)
			return null;
		else
			return choices.elementAt(RND.nextInt(choices.size()));
	}
	
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	//   Useful public tools
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//

	

	// Get the intersection of two lists of concepts
	
	public Vector intersect(Vector v1, Vector v2)
	{
		if (v1 == null || v1.size() == 0)
			return null;
		
		if (v2 == null || v2.size() == 0)
			return null;
		
		if (v1.size()*v2.size() < 1000)
		{
			Vector common = new Vector();
			
			for (int i = 0; i < v1.size(); i++)
				if (v2.contains(v1.elementAt(i)))
					common.add(v1.elementAt(i));
			
			return common;
		}
		
		Hashtable seen = new Hashtable();
		
		for (int i = 0; i < v2.size(); i++)
			seen.put(v2.elementAt(i), "seen");
		
		Vector common = new Vector();
		
		for (int i = 0; i < v1.size(); i++)
			if (seen.get(v1.elementAt(i)) != null)
				common.add(v1.elementAt(i));
		
		return common;
	}
	
	
	// Get the union of two lists of concepts
	
	public Vector union(Vector v1, Vector v2)
	{
		if (v1 == null || v1.size() == 0)
			return v2;
		
		if (v2 == null || v2.size() == 0)
			return v1;
		
		Hashtable seen = new Hashtable();
		
		Vector union = new Vector();

		
		for (int i = 0; i < v1.size(); i++)
		{
			seen.put(v1.elementAt(i), "seen");		
			union.add(v1.elementAt(i));
		}
			
		for (int i = 0; i < v2.size(); i++)
			if (seen.get(v2.elementAt(i)) != null)
				union.add(v2.elementAt(i));
		
		return union;
	}
	
	
	// Get the union of two lists of concepts
	
	public Vector difference(Vector v1, Vector v2)
	{
		if (v1 == null || v1.size() == 0)
			return null;
		
		if (v2 == null || v2.size() == 0)
			return v1;
		
		Hashtable seen = new Hashtable();
		
		Vector difference = new Vector();

		
		for (int i = 0; i < v2.size(); i++)
			seen.put(v2.elementAt(i), "seen");		
			
		for (int i = 0; i < v1.size(); i++)
			if (seen.get(v1.elementAt(i)) == null)
				difference.add(v1.elementAt(i));
		
		return difference;
	}
	
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	//   Invert a Field to get a table mapping from values to key concepts
	//     e.g. invert the Positive Talking Points field to map from positive adjectives to people
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//

	public Hashtable getInvertedField(String givenField)
	{
		return getInvertedField(givenField, new Hashtable());
	}
	
	
	
	public Hashtable getInvertedField(String givenField, Hashtable inversion)
	{
		Vector invertedKeys = new Vector();
		
		inversion.put("*keylist*", invertedKeys);
		
		for (int f = 0; f < fieldTables.size(); f++)
		{
			String fieldName      = (String)fieldNames.elementAt(f);
			
			if (!fieldName.equals(givenField))
				continue;
			
			Hashtable fieldTable  = (Hashtable)fieldTables.elementAt(f);
			
			Vector keylist =  (Vector)fieldTable.get("*keylist*"), values = null, entries = null;
			
			if (keylist == null) continue;
			
			String key = null, value = null;
			
			for (int k = 0; k < keylist.size(); k++)
			{
				key    = (String)keylist.elementAt(k);
				values = (Vector)fieldTable.get(key);
				
				if (values == null) continue;
				
				for (int v = 0; v < values.size(); v++)
				{
					value = (String)values.elementAt(v);
					
					entries = (Vector)inversion.get(value);
					
					if (entries == null)
					{
						entries = new Vector();
						inversion.put(value, entries);
						
						invertedKeys.add(value);
					}
					
					if (!entries.contains(key))
						entries.add(key);
				}
			}	
		}
		
		return inversion;
	}
	
	
	
	public BucketTable invertFieldInto(String givenField, BucketTable inversion)
	{
		Vector invertedKeys = new Vector();
		
		inversion.put("*keylist*", invertedKeys);
		
		for (int f = 0; f < fieldTables.size(); f++)
		{
			String fieldName      = (String)fieldNames.elementAt(f);
			
			if (!fieldName.equals(givenField))
				continue;
			
			Hashtable fieldTable  = (Hashtable)fieldTables.elementAt(f);
			
			Vector keylist =  (Vector)fieldTable.get("*keylist*"), values = null, entries = null;
			
			if (keylist == null) continue;
			
			String key = null, value = null;
			
			for (int k = 0; k < keylist.size(); k++)
			{
				key    = (String)keylist.elementAt(k);
				values = (Vector)fieldTable.get(key);
				
				if (values == null) continue;
				
				for (int v = 0; v < values.size(); v++)
				{
					value = (String)values.elementAt(v);
					
					inversion.put(value, key);
				}
			}	
		}
		
		return inversion;
	}
	
	
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	//   Return a list of overlapping fieldname:fieldvalue pairs for two key concepts
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	
	public Vector getOverlappingFields(String key1, String key2)
	{
		return getOverlappingFields(key1, key2, fieldNames);
	}
	
	
	public Vector getOverlappingFields(String key1, String key2, Vector salient)
	{
		String fieldName     = null;
		Hashtable fieldTable = null;
		
		Vector overlap       = new Vector();
		
		for (int n = 0; n < fieldNames.size(); n++)
		{
			fieldName = (String)fieldNames.elementAt(n);
			
			if (salient != fieldNames && !salient.contains(fieldName))
				continue;
			
			fieldTable = (Hashtable)fieldTables.elementAt(n);
			
			Vector common = intersect((Vector)fieldTable.get(key1), (Vector)fieldTable.get(key2));
			
			if (common == null) continue;
			
			for (int c = 0; c < common.size(); c++)
			{
				overlap.add(fieldName + "=" + (String)common.elementAt(c));
			}
		}
		
		return overlap;
	}
	
	
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	//   Load Knowledge-Base Module from a Given File
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//

	private void loadKnowledgeBaseFrom(String filename)
	{
		loadKnowledgeBaseFrom(filename, 0);
	}
	
	
	private void loadKnowledgeBaseFrom(String filename, int keyPosition)
	{
		FileInputStream input;

		try {
		    input = new FileInputStream(filename);
		    
		    loadKnowledgeBaseFrom(input, keyPosition);
		}
		catch (IOException e)
		{
			System.out.println("Cannot find/load knowledge file: " + filename);
			
			e.printStackTrace();
		}
	}
	
		

	private void loadKnowledgeBaseFrom(InputStream stream, int keyPosition)
	{
		String line = null;
		
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(stream, "UTF8"));
			
			loadFieldNames(input.readLine());
			
			while ( (line = input.readLine()) != null)  // Read a line at a time
			{
				parseFieldsIntoKB(fieldNames, line, keyPosition);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	private void parseFieldsIntoKB(Vector fieldNames, String line, int keyPosition)
	{
		StringTokenizer values = new StringTokenizer(line, "\t", true);
		
		int fieldNumber = 0;
		
		Vector valueSets = new Vector();
		
		for (int f = 0; f < fieldNames.size(); f++)
			valueSets.add("");
		
		while (values.hasMoreTokens())
		{
			String token = values.nextToken();
			
			if (token.equals("\t"))
			{
				fieldNumber++;
				
				if (fieldNumber >= valueSets.size())
					break;
				else
					continue;
			}
			
			token = token.trim();
			
			if (token.length() > 0)
				valueSets.setElementAt(token, fieldNumber);
		}
				
		String key = ((String)valueSets.elementAt(keyPosition)).trim().intern();
		
		for (int v = 0; v < valueSets.size(); v++)
		{
			setFieldsInKB((Hashtable)fieldTables.elementAt(v), key, (String)valueSets.elementAt(v));
		}
	}
	
	
	
	private Vector setFieldsInKB(Hashtable field, String key, String valueSet)
	{
		if (valueSet == "") return null;
				
		StringTokenizer values = new StringTokenizer(valueSet, ",", false);
		
		Vector fieldValues = (Vector)field.get(key);
		
		if (fieldValues == null) 
		{
			Vector keyList = (Vector)field.get("*keylist*");
			
			if (keyList == null)
			{
				keyList = new Vector();
				field.put("*keylist*", keyList);
			}
			
			keyList.add(key);
			
			fieldValues = new Vector();
		}
		
		field.put(key, fieldValues);
		
		while (values.hasMoreTokens())
		{
			String value = values.nextToken().trim().intern();
			
			if (value.length() > 0 && !fieldValues.contains(value))
				fieldValues.add(value);
		}
		
		return fieldValues;
	}
	
	
	
	private Vector loadFieldNames(String line)
	{
		StringTokenizer names = new StringTokenizer(line, "\t");
		
		fieldNames.setSize(0);
		fieldTables.setSize(0);
		
		String previous = "", current = "";
		
		Hashtable prevTable = null, currTable = null;
		
		while (names.hasMoreTokens())
		{
			previous  = current;
			current   = names.nextToken().intern();
			
			prevTable = currTable;
			currTable = new Hashtable();
			
			fieldNames.add(current);
			
			if (current == previous && prevTable != null)
				currTable = prevTable;
			
			fieldTables.add(currTable);
		}
		
		return fieldNames;
	}

}
