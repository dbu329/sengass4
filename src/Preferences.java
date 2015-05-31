import java.util.ArrayList;

public class Preferences {
	
	private ArrayList<String> prefList;
	
	public Preferences(ArrayList<String> pList) {
		prefList = new ArrayList<String>();
		prefList.addAll(pList);
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
		// adding suppressed warnings to local variables is
		// allowed as we are not interfering with at the 'global' scale.
		// http://stackoverflow.com/questions/509076/how-do-i-address-unchecked-cast-warnings
		// http://docs.oracle.com/javase/7/docs/api/java/lang/SuppressWarnings.html
		@SuppressWarnings("unchecked")
		ArrayList<String> prefListCopy = (ArrayList<String>) this.prefList.clone();
		
		// since it's an array list, gets rid of the objects "Cost" and "Time" in the copied arraylist
		prefListCopy.remove("Cost");
		prefListCopy.remove("Time");
		
		// returns the remaining preferences. aka the 'airline' that the user has requested.
		return prefListCopy.get(0);
	}
	
	/**
	 * Returns the preferences of the list
	 * @return
	 */
	public ArrayList<String> getPrefList() {
		return prefList;
	}
	
	

}
