package interfaces;


public interface Buildable {
	public boolean isUnimproved();
	public int buildableUnits();
	public int buildingCost();
	public int currentUnits();
	public int build(int units);
	public void demolish(int units);
	public String getBuildings();
}
