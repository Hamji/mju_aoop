package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import data.dto.CGIDTO;
import data.dto.MajorGroups;
import data.dto.Religion;

public class Database {
	//데이터베이스 Connection
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
	public boolean connectDB() {
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
			System.out.println("DB연결성공");
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

	//CSV파일의 데이터를 DB에 저장
	public void insertCGIData(ArrayList<CGIDTO> dtoList) throws SQLException {
		try {
			//DB Connection, config
			connectDB();
            con.setAutoCommit(false);
			//Set SQL
            String sql = "INSERT INTO cgi (country, country_code, capital, location, major_city, religion, major_groups, media, area, area_description, language, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            //Make Query
            for(CGIDTO dto: dtoList) {
            	pst.setString(1, dto.getCountry());
            	pst.setString(2, dto.getCountryCode());
            	pst.setString(3, dto.getCapital());
            	pst.setString(4, dto.getLocation());
            	pst.setString(5, dto.getMajorCity());
            	pst.setString(6, dto.getReligion().toString());
            	pst.setString(7, dto.getMajorGroups().toString());
            	pst.setString(8, dto.getMedia());
            	pst.setDouble(9, dto.getArea());
            	pst.setString(10, dto.getAreaDescription());
            	pst.setString(11, dto.getLanguage());
            	pst.setInt(12, dto.getYear());
            	//Execute Query
            	pst.execute();
            }
            //Commit and close
            con.commit();
            con.close();
        } catch (SQLException ex) {
        	ex.printStackTrace();
            //실패 시, 롤백함
            try {
            	 con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
	}
	
	//입력받은 문자열과 관련된 모든 CGI 데이터를 추출함
	public ArrayList<CGIDTO> selectCGIData(String filter) {
		ArrayList<CGIDTO> cgiList = new ArrayList<CGIDTO>();
		try {
			//DB연결
			connectDB();
			//입력받은 문자열로 시작하는 문자열을 각 국가의 이름, 국가코드, 수도, 주요도시와 비교하고 같으면 select하는 sql문
			String sql = "SELECT * FROM cgi WHERE country LIKE '" + filter + "%' OR country_code LIKE '" + filter + "%' OR capital LIKE '" + filter + "%' OR major_city LIKE '" + filter + "%'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();            
            while(rs.next()) {            	
            	CGIDTO dto = new CGIDTO.Builder()
            			.setCountry(rs.getString(1))
            			.setCountryCode(rs.getString(2))
            			.setCapital(rs.getString(3))
            			.setLocation(rs.getString(4))
            			.setMajorCity(rs.getString(5))
            			.setReligion(transRData(rs.getString(6)))
            			.setMajorGroups(transMGData(rs.getString(7)))
            			.setMedia(rs.getString(8))
            			.setArea(rs.getDouble(9))
            			.setAreaDescription(rs.getString(10))
            			.setLanguage(rs.getString(11))
            			.setYear(rs.getInt(12))
            			.build();
            	cgiList.add(dto);
            }
            //DB커넥션 종료
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return cgiList;
	}
	
	//국가명을 기준으로 검색
	public ArrayList<CGIDTO> selectCGIDataName(String keyword){
		ArrayList<CGIDTO> cgiList = new ArrayList<CGIDTO>();
		try {
			//DB연결
			connectDB();
			//sql Query set
			String sql = "SELECT * FROM cgi WHERE country LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%"+keyword+"%");
            //executeQuery
            ResultSet rs = pst.executeQuery();
            //객체 리스트로 저장
            while(rs.next()) {            	
            	CGIDTO dto = new CGIDTO.Builder()
            			.setCountry(rs.getString(1))
            			.setCountryCode(rs.getString(2))
            			.setCapital(rs.getString(3))
            			.setLocation(rs.getString(4))
            			.setMajorCity(rs.getString(5))
            			.setReligion(transRData(rs.getString(6)))
            			.setMajorGroups(transMGData(rs.getString(7)))
            			.setMedia(rs.getString(8))
            			.setArea(rs.getDouble(9))
            			.setAreaDescription(rs.getString(10))
            			.setLanguage(rs.getString(11))
            			.setYear(rs.getInt(12))
            			.build();
            	cgiList.add(dto);
            }
            //DB커넥션 종료
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return cgiList;
	}
	//위치정보를 기준으로 검색
	public ArrayList<CGIDTO> selectCGIDataLocation(String keyword){
		ArrayList<CGIDTO> cgiList = new ArrayList<CGIDTO>();
		try {
			connectDB();
			//sqlQuery set
			String sql = "SELECT * FROM cgi WHERE location LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%"+keyword+"%");
            //execute Query
            ResultSet rs = pst.executeQuery();            
            //객체리스트로 변환
            while(rs.next()) {            	
            	CGIDTO dto = new CGIDTO.Builder()
            			.setCountry(rs.getString(1))
            			.setCountryCode(rs.getString(2))
            			.setCapital(rs.getString(3))
            			.setLocation(rs.getString(4))
            			.setMajorCity(rs.getString(5))
            			.setReligion(transRData(rs.getString(6)))
            			.setMajorGroups(transMGData(rs.getString(7)))
            			.setMedia(rs.getString(8))
            			.setArea(rs.getDouble(9))
            			.setAreaDescription(rs.getString(10))
            			.setLanguage(rs.getString(11))
            			.setYear(rs.getInt(12))
            			.build();
            	cgiList.add(dto);
            }
            //DB커넥션 종료
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return cgiList;
	}
	//기후를 기준으로 검색
	public ArrayList<CGIDTO> selectCGIDataWeather(String keyword){
		ArrayList<CGIDTO> cgiList = new ArrayList<CGIDTO>();
		
		try {
			connectDB();
			//sql Query Set
			String sql = "SELECT * FROM cgi WHERE weather LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%"+keyword+"%");
            //execute Query
            ResultSet rs = pst.executeQuery();      
            //객체리스트로 변환
            while(rs.next()) {            	
            	CGIDTO dto = new CGIDTO.Builder()
            			.setCountry(rs.getString(1))
            			.setCountryCode(rs.getString(2))
            			.setCapital(rs.getString(3))
            			.setLocation(rs.getString(4))
            			.setMajorCity(rs.getString(5))
            			.setReligion(transRData(rs.getString(6)))
            			.setMajorGroups(transMGData(rs.getString(7)))
            			.setMedia(rs.getString(8))
            			.setArea(rs.getDouble(9))
            			.setAreaDescription(rs.getString(10))
            			.setLanguage(rs.getString(11))
            			.setYear(rs.getInt(12))
            			.build();
            	cgiList.add(dto);
            }
            //DB커넥션 종료
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return cgiList;
	}
	//종교를 기준으로 검색
	public ArrayList<CGIDTO> selectCGIDataReligion(String keyword){
		ArrayList<CGIDTO> cgiList = new ArrayList<CGIDTO>();
		try {
			connectDB();
			//sql Query Set
			String sql = "SELECT * FROM cgi WHERE religion LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%"+keyword+"%");
            //execute Query
            ResultSet rs = pst.executeQuery();            
            //객체리스트로 변환
            while(rs.next()) {            	
            	CGIDTO dto = new CGIDTO.Builder()
            			.setCountry(rs.getString(1))
            			.setCountryCode(rs.getString(2))
            			.setCapital(rs.getString(3))
            			.setLocation(rs.getString(4))
            			.setMajorCity(rs.getString(5))
            			.setReligion(transRData(rs.getString(6)))
            			.setMajorGroups(transMGData(rs.getString(7)))
            			.setMedia(rs.getString(8))
            			.setArea(rs.getDouble(9))
            			.setAreaDescription(rs.getString(10))
            			.setLanguage(rs.getString(11))
            			.setYear(rs.getInt(12))
            			.build();
            	cgiList.add(dto);
            }
            //DB커넥션 종료
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return cgiList;
	}
	
	//String Data 객체로 변환
	private Religion transRData(String data) {
		Religion result = new Religion();
		String[] tmp = data.split(",");
		for(String s : tmp) {
			result.setRData(s.split(":")[0].toString(), Double.parseDouble(s.split(":")[1]));
		}		
		return result; 
	}
	private MajorGroups transMGData(String data) {
		MajorGroups result = new MajorGroups();
		String[] tmp = data.split(",");
		for(String s : tmp) {
			result.setMGData(s.split(":")[0].toString(), Double.parseDouble(s.split(":")[1]));
		}		
		return result; 
	}
}
