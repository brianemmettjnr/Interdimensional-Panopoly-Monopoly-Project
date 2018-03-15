package monopoly;

import java.util.ArrayList;

import interfaces.Groupable;
import interfaces.Identifiable;

public class Group implements Identifiable
{
	private String name;
	private ArrayList<Groupable> members = new ArrayList<Groupable>();
	
	Group (String name)
	{
		this.name = name;
	}

	@Override
	public String getIdentifier() 
	{
		return name;
	}
	
	public void addMember(Groupable property)
	{
		members.add(property);
	}
	
	public ArrayList<Groupable> getMembers()
	{
		return members;
	}
}
