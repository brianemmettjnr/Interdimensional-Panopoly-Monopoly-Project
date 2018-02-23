package monopoly;

public interface Actionable extends Identifiable{

	public boolean performActionOn(Playable player);
	public String explainAction();
}