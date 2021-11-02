package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import data.dto.C19Data;

public class Database {
		//DB Connection
		private Connection con;
		//DB설정파일 
		private static final String RESOURCE = "src/resource/db.properties";
		//DB설정값
		private String DB_DRIVER_CLASS;
		private String DB_URL;
		private String DB_USERNAME;
		private String DB_PASSWORD;
		
		//생성자 
		public Database() {
			con = null;
		}
		
		//DB연결 
		public boolean conncetDB() {
			Properties properties = new Properties();
			try {
				//DB설정파일 읽기, FileNotFoundException
				FileReader resources = new FileReader(RESOURCE);
				//DB설정파일 불러오기, IOException
				properties.load(resources);
				DB_DRIVER_CLASS = properties.getProperty("driver").toString();
				DB_URL = properties.getProperty("url").toString();
				DB_USERNAME = properties.getProperty("username");
				DB_PASSWORD = properties.getProperty("password");
				//DB드라이버가져오기, ClassNotFoundException, SQLException
				Class.forName(DB_DRIVER_CLASS);
				con = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
				System.out.println("DB연결 성공");
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
			return true;
		}
		//insertData
		public void insertC19Data() {}
		public void insertCGIData() {}
		//selectData
		public ArrayList<C19Data> selectC19Data(String sql){
			return new ArrayList<C19Data>();
		}
}
