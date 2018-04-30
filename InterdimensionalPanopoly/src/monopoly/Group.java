package monopoly;

import java.awt.*;
import java.util.ArrayList;

import interfaces.Groupable;
import interfaces.Identifiable;
import interfaces.Rentable;

public class Group implements Identifiable
{
	private String name;
	private ArrayList<Groupable> members = new ArrayList<Groupable>();
	private Color color;
	
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

    public Color getColor() {
		int value=0;
		if(members.get(0) instanceof InvestmentProperty)
			value=((InvestmentProperty) members.get(0)).getPrice();
        return new Color((value/2)%255,(2*value/2)%255,(3*value/2)%255);
    }
}
