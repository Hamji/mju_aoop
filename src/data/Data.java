package data;

import java.util.ArrayList;

import data.dto.CGIDTO;
import data.dto.MajorGroups;
import data.dto.Religion;
import data.dto.CGIDTO.Builder;
import data.CSV;
import data.Database;

public class Data {
	//DB에 접근을 위한 DB객체, CSV읽기, 쓰기를 위한 CSV객체
	private Database db;
	private CSV csv;
	
	public Data() {
		db = new Database();
		csv = new CSV();
	}
	
	//데이터를 가져오기 위한 메서드들
	
	//입력받은 문자열과 관련된 국가들의 개수를 리턴하는 함수
	public int getCountryCount(String filter) {
		ArrayList<CGIDTO> result = new ArrayList<CGIDTO>();
		result = db.selectCGIData(filter);
		return result.size();
	}
	
	//입력받은 문자열과 관련된 국가들 중 선택한 국가의 정보를 리턴하는 함수
	public CGIDTO getCountryData(String filter, String selected) throws Exception {
		CGIDTO resultDTO = new CGIDTO();
		
		try {	
			
			for(int i = 0; i < getCountryCount(filter); i++) {
				if(db.selectCGIData(filter).get(i).getCountry() == selected) {
					resultDTO = db.selectCGIData(filter).get(i);
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return resultDTO;
	}
	
	//sql의 any연산자 사용??
//	public ArrayList<CGIDTO> getFilteredData(String[] filter) {
//		return new ArrayList<CGIDTO>;
//	}
}