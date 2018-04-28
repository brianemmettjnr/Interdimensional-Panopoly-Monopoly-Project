//TaxableProperty: Super Tax, Income Tax etc.

package monopoly;

import interfaces.Taxable;

import java.awt.*;

public class TaxableProperty extends NamedLocation implements Taxable{
	public Color color=Color.GREEN.darker().darker();
	private int flatAmount;

	TaxableProperty(String name, int flat) {
		super(name);
		flatAmount = flat;
	}

	@Override
	public int getFlatAmount() {
		return flatAmount;
	}

}
