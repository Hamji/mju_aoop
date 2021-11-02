package data;

import java.util.HashMap;
import java.util.Map;

public class MajorGroups {
	private Map<String, Double> MGMap;
	
	public MajorGroups(String grp, Double db) {
		MGMap = new HashMap<String, Double>();
		if(db == null) {
			this.MGMap.put(grp, 0.0);
		}
		this.MGMap.put(grp, db);
	}
	
	public MajorGroups() {
		// TODO Auto-generated constructor stub
	}

	public Map<String, Double> getMGData() {
		return this.MGMap;
	}

	public void setMGData(String mg_name, Double mg_rate) {
		// TODO Auto-generated method stub
		
	}
}
