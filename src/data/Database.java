package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import data.dto.C19DTO;

public class Database {
	//데이터베이스 Connection
	private Connection con;
	//DB연결 상수
	private static final String DB_DRIVER_CLASS = "org.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://주소:포트/데이터베이스";
	private static final String DB_USERNAME = "계정";
	private static final String DB_PASSWORD = "패스워드";
	
	//생성자
	public Database() {
		con = null;
	}
	
	//DB연결
	public boolean conncetDB() {
		try {
			Class.forName(DB_DRIVER_CLASS);
			con = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//insertData
	public void insertC19Data() {
		
	}
	public void insertCGIData() {
		
	}
	//selectData
	public ArrayList<C19DTO> selectC19Data(String sql){
		return new ArrayList<C19DTO>();
	}
}
