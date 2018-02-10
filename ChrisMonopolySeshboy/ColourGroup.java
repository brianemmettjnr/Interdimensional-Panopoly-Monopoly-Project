import java.util.ArrayList;

public class ColourGroup {

	private ArrayList<Site> sites = new ArrayList<Site>();
	private String name;
	
	ColourGroup (String name) {
		this.name = name;
		return;
	}
	
	public void addMember (Site site) {
		sites.add(site);
		return;
	}
	
	public ArrayList<Site> getMembers () {
		return sites;
	}
	
	public String getName () {
		return name;
	}
	
	public int size () {
		return sites.size();
	}
	
}
