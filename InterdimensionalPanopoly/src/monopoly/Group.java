package monopoly;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import interfaces.Groupable;
import interfaces.Identifiable;

public class Group implements Identifiable
{
	private String name;
	private ArrayList<Groupable> members = new ArrayList<Groupable>();
	private Color color;
	
	Group (String name)
	{
		this.name = name;
		color = new Color(ThreadLocalRandom.current().nextInt(0, 255 + 1),ThreadLocalRandom.current().nextInt(0, 255 + 1)
				,ThreadLocalRandom.current().nextInt(0, 255 + 1));
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

    public Color getColor() 
    {
        return color;
	}
}
