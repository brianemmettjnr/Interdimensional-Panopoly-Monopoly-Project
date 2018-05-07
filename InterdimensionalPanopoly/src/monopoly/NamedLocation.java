//NamedLocation: Properties that cannot be owned e.g. GO, Free Parking, Go to Jail etc.

package monopoly;

import interfaces.Identifiable;
import interfaces.Locatable;

public class NamedLocation implements Identifiable, Locatable {
	
	private String name;
	
	public NamedLocation(String name) {
		this.name = name;
	}

	@Override
	public String getIdentifier() {
		return name;
	}

	@Override
	public int getPosition() 
	{
		return 0;
	}
}
