package data.dto;

import java.util.HashMap;
import java.util.Map;

public class Religion {
	private Map<String, Double> RMap;
	
	public Religion() {
		this.RMap = new HashMap<String, Double>();
	}
	
	public void setRData(String rg, Double db) {
		this.RMap.put(rg, db);
	}
	
	public Map<String, Double> getRData() {
		return this.RMap;
	}
	
	//DB저장을 위한 String변
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(String key : RMap.keySet()) {
			result.append(key + ":" + RMap.get(key).toString() + ",");
		}
		return result.toString();
	}
}
