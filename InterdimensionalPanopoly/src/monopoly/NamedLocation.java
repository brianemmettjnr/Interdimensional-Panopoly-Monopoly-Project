//NamedLocation: Properties that cannot be owned e.g. GO, Free Parking, Go to Jail etc.

package monopoly;

import interfaces.Locatable;

public class NamedLocation implements Locatable {
	
	private String name;
	private Locatable left = null, right = null;
	
	public NamedLocation(String name) {
		this.name = name;
	}

	@Override
	public String getIdentifier() {
		return name;
	}
	
	//sets property to the left
	public void setLeft(Locatable leftProperty) {
		left = leftProperty;
	}
	
	//sets property to the right
	public void setRight(Locatable rightProperty) {
		right = rightProperty;
	}

	@Override
	public Locatable goLeft() {
		return left;
	}

	@Override
	public Locatable goRight() {
		return right;
	}

}
