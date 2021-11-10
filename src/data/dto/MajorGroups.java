package data.dto;

import java.util.HashMap;
import java.util.Map;

public class MajorGroups {
	private Map<String, Double> MGMap;
	
	public MajorGroups() {
		this.MGMap = new HashMap<String, Double>();
	}
	
	public void setMGData(String rg, Double db) {
		this.MGMap.put(rg, db);
	}
	
	public Map<String, Double> getMGData() {
		return this.MGMap;
	}
	
	//DB저장을 위한 String변
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(String key : MGMap.keySet()) {
			result.append(key + ":" + MGMap.get(key).toString() + ",");
		}
		return result.toString();
	}
}
