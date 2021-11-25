package data;

import java.util.ArrayList;
import java.util.HashSet;

import data.dto.CGIDTO;

public class Data {
	//DB에 접근을 위한 DB객체, CSV읽기, 쓰기를 위한 CSV객체
	private static Data data = null;
	private static Database db = null;
	private static CSV csv = null;

	private Data() {
		db = new Database();
		csv = CSV.getInstance();
		db.insertCGIData(csv.getCGIData());
	}
	public static Data getInstance() {
		if(data == null) {
			data = new Data();
			db = new Database();

			//CSV파일을 읽어 DB에 삽입
			db.insertCGIData(CSV.getInstance().getCGIData());
		}

		return data;
	}
	
	//데이터를 가져오기 위한 메서드들
	//입력받은 문자열과 관련된 국가들의 개수를 리턴하는 함수
	public int getCountryCount(String filter) {
		ArrayList<CGIDTO> result = new ArrayList<CGIDTO>();
		result = db.selectCGIData(filter);
		return result.size();
	}

	//국가명을 받으면 국가정보를 반환하는 메서드
	public CGIDTO getCountryData(String filter){
		return db.selectCGIDataName(filter).get(0);
	}
	
	//필터의 키워드들을 모두 검색하여 검색된 결과목록을 보내는 메서드
	public ArrayList<CGIDTO> getFilteredData(String[] keywords){
		//중복되는 국가정보를 제거하기 위해 HashSet사용
		HashSet<CGIDTO> result = new HashSet<CGIDTO>();
		//Keyword들을 반복하며 결과값을 저장
		for(String keyword : keywords) {
			ArrayList<CGIDTO> tmp = new ArrayList<>();
			tmp = db.selectCGIData(keyword);
			for(int i = 0;i<tmp.size();i++) {
				result.add(tmp.get(i));
			}
		}
		//결과값 ArrayList로 반환
		return new ArrayList<CGIDTO>(result);
	}
	
	//검색된 항목 반환
	public ArrayList<CGIDTO> getSearchData(String keyword){
		return db.selectCGIData(keyword);
	}
	//종교로 검색
	public ArrayList<CGIDTO> getDataReligion(String keyword) {
		return db.selectCGIDataReligion(keyword);
	}
	//기후로 검색
	public ArrayList<CGIDTO> getDataWeather(String keyword){
		return db.selectCGIDataWeather(keyword);
	}
	//국가명으로 검색
	public ArrayList<CGIDTO> getDataName(String keyword){
		return db.selectCGIDataName(keyword);
	}
	//지역으로 검색
	public ArrayList<CGIDTO> getDataLocation(String keyword){
		return db.selectCGIDataLocation(keyword);
	}
	//모든 국가명 반환
	public ArrayList<String> getAllCountryName(){
		return db.selectAllCountryName();
	}
}
	
	