//TaxableProperty: Super Tax, Income Tax etc.

package monopoly;

import interfaces.Taxable;

public class TaxableProperty extends NamedLocation implements Taxable{
	
	private int flatAmount;

	public TaxableProperty(String name, int flat) {
		super(name);
		flatAmount = flat;
	}

	@Override
	public int getFlatAmount() {
		return flatAmount;
	}

}
