package data;

import data.dto.C19DTO;

public class Data {
	//DB에 접근을 위한 DB객체, CSV읽기, 쓰기를 위한 CSV객체
	private Database db;
	private CSV csv;
	
	//생성자
	public Data() {
		db = new Database();
		csv = new CSV();
	}
	
	//데이터를 가져오기 위한 메서드들
	public int getCountryCount(String filter) {
		return 0;
	}
	public C19DTO getC19Data(String filter) {
		return new C19DTO();
	}
}
