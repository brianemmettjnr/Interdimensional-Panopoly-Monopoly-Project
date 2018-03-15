//TaxableProperty: Super Tax, Income Tax etc.

package monopoly;

import interfaces.Taxable;

public class TaxableProperty extends NamedLocation implements Taxable{
	
	private int incomePercentage, flatAmount;

	public TaxableProperty(String name, int percent, int flat) {
		super(name);
		incomePercentage = percent;
		flatAmount = flat;
	}

	@Override
	public int getIncomePercentage() {
		return incomePercentage;
	}

	@Override
	public int getFlatAmount() {
		return flatAmount;
	}

}
