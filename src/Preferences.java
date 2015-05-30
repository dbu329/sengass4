import java.util.ArrayList;

public class Preferences {
	
	private static final String COST = "Cost";
	private static final String TIME = "Time";
	
	private ArrayList<String> prefList;
	
	public Preferences(ArrayList<String> pList) {
		System.out.println("0");
		prefList = new ArrayList<String>();
		System.out.println("1");
		prefList.addAll(pList);
		System.out.println("2");
	}
	
	public String get(int i) {
		return prefList.get(i);
	}
	
	public int length() {
		return prefList.size();
	}
	
	public String getFirstPref() {
		return prefList.get(0);
	}
	
	public String getSecondPref() {
		return prefList.get(1);
	}
	
	public String getThirdPref() {
		return prefList.get(2);
	}
	
	// order of preference is NOT known.
	// this function gets the 'airline'...whereever it might be.
	public String airLinePreference() {
		
		return "null";
	}

}
