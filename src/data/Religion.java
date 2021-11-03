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
}
