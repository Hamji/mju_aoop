package data;

import java.util.HashMap;
import java.util.Map;

public class Religion {
	private Map<String, Double> RMap;
	
	public Religion(String rg, Double db) {
		RMap = new HashMap<String, Double>();
		if(db == null) {
			this.RMap.put(rg, 0.0);
		}
		this.RMap.put(rg, db);
	}
	
	public Religion() {
		// TODO Auto-generated constructor stub
	}

	public Map<String, Double> getRData() {
		return this.RMap;
	}

	public void setRData(String rg_name, Double rg_rate) {
		// TODO Auto-generated method stub
		
	}
}
