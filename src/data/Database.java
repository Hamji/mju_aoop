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
	private static final String RESOURCE = "src/resource/db-real.properties";
	//DB설정값
	private String DB_DRIVER_CLASS = null;
	private String DB_URL = null;
	private String DB_USERNAME = null;
	private String DB_PASSWORD = null;

	//생성자
	public Database() {
		con = null;
	}
	
	//DB연결
	public boolean connectDB() {
		//최초 접속시 DB설정값 읽어오기
		if(DB_DRIVER_CLASS == null || DB_URL == null || DB_USERNAME == null || DB_PASSWORD == null) {
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
			} catch(FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch(IOException e){
				e.printStackTrace();
				return false;
			}
		}
		try {
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
		} 
		return true;
	}

	//CSV파일의 데이터를 DB에 저장
	public void insertCGIData(ArrayList<CGIDTO> dtoList){
		/*
		//테이블이 없을경우 생성
		try {
			String cTableSQL = "CREATE TABLE IF NOT EXISTS 'cgi' "
					+ "'country' varchar(50) NOT NULL,"
					+ "'country_code' varchar(2) DEFAULT NULL,"
					+ "'capital' varchar(100) DEFAULT NULL,"
					+ "'climate' varchar(200) DEFAULT NULL,"
					+ "'location' varchar(200) DEFAULT NULL,"
					+ "'major_city' varchar(200) DEFAULT NULL,"
					+ "'religion' text,"
					+ "'major_group' text,"
					+ "'media' text,"
					+ "'area' double DEFAULT NULL,"
					+ "'area_source' varchar(100) DEFAULT NULL,"
					+ "'area_desc' varchar(100) DEFAULT NULL,"
					+ "'language' text,"
					+ "'year' int DEFAULT NULL,"
					+ "PRIMARY KEY ('country')"
					+ ");";
			connectDB();
			PreparedStatement pst = con.prepareStatement(cTableSQL);
			pst.execute();
			con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		 */
		//이미 데이터가 들어가있으면 데이터를 삽입하지 않고 프로그램실행
		try{
			connectDB();
			String sql = "Select count(country) FROM cgi";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				//삽입 쿼리가 한개라도 오류가 나면 commit되지 않아 한개라도 들어갔다면 모든 데이터가 들어간것으로 간주
				if(rs.getInt(1) != 0){
					con.close();
					return;
				}
			}
		} catch (SQLException e){
			e.printStackTrace();
		}

		//데이터가 삽입되지 않았다면 데이터 삽입
		try {
			//DB Connection, config
			connectDB();
            con.setAutoCommit(false);
			//Set SQL
            String sql = "INSERT INTO cgi (country, country_code, capital, climate, location, major_city, religion, major_group, media, area, area_source, area_desc, language, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            //Make Query
            for(CGIDTO dto: dtoList) {
            	pst.setString(1, dto.getCountry());
            	pst.setString(2, dto.getCountryCode());
            	pst.setString(3, dto.getCapital());
            	pst.setString(4, dto.getClimate());
            	pst.setString(5, dto.getLocation());
            	pst.setString(6, dto.getMajorCity());
            	pst.setString(7, dto.getReligion().toString());
            	pst.setString(8, dto.getMajorGroups().toString());
            	pst.setString(9, dto.getMedia());
            	pst.setDouble(10, dto.getArea());
            	pst.setString(11, dto.getAreaSource());
            	pst.setString(12, dto.getAreaDescription());
            	pst.setString(13, dto.getLanguage());
            	pst.setInt(14, dto.getYear());
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
			String sql = "SELECT country, country_code, capital, climate, location, major_city, religion, major_group, media, area, area_source, area_desc, language, year FROM cgi WHERE country LIKE '" + filter + "%' OR country_code LIKE '" + filter + "%' OR capital LIKE '" + filter + "%' OR major_city LIKE '" + filter + "%'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();            
            while(rs.next()) {            	
            	CGIDTO dto = new CGIDTO.Builder()
            			.setCountry(rs.getString(1))
            			.setCountryCode(rs.getString(2))
            			.setCapital(rs.getString(3))
            			.setClimate(rs.getString(4))
            			.setLocation(rs.getString(5))
            			.setMajorCity(rs.getString(6))
            			.setReligion(transRData(rs.getString(7)))
            			.setMajorGroups(transMGData(rs.getString(8)))
            			.setMedia(rs.getString(9))
            			.setArea(rs.getDouble(10))
            			.setAreaSource(rs.getString(11))
            			.setAreaDescription(rs.getString(12))
            			.setLanguage(rs.getString(13))
            			.setYear(rs.getInt(14))
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
			String sql = "SELECT country, country_code, capital, climate, location, major_city, religion, major_group, media, area, area_source, area_desc, language, year FROM cgi WHERE country LIKE ?";
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
            			.setClimate(rs.getString(4))
            			.setLocation(rs.getString(5))
            			.setMajorCity(rs.getString(6))
            			.setReligion(transRData(rs.getString(7)))
            			.setMajorGroups(transMGData(rs.getString(8)))
            			.setMedia(rs.getString(9))
            			.setArea(rs.getDouble(10))
            			.setAreaSource(rs.getString(11))
            			.setAreaDescription(rs.getString(12))
            			.setLanguage(rs.getString(13))
            			.setYear(rs.getInt(14))
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
			String sql = "SELECT country, country_code, capital, climate, location, major_city, religion, major_group, media, area, area_source, area_desc, language, year FROM cgi WHERE location LIKE ?";
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
            			.setClimate(rs.getString(4))
            			.setLocation(rs.getString(5))
            			.setMajorCity(rs.getString(6))
            			.setReligion(transRData(rs.getString(7)))
            			.setMajorGroups(transMGData(rs.getString(8)))
            			.setMedia(rs.getString(9))
            			.setArea(rs.getDouble(10))
            			.setAreaSource(rs.getString(11))
            			.setAreaDescription(rs.getString(12))
            			.setLanguage(rs.getString(13))
            			.setYear(rs.getInt(14))
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
			String sql = "SELECT country, country_code, capital, climate, location, major_city, religion, major_group, media, area, area_source, area_desc, language, year FROM cgi WHERE weather LIKE ?";
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
            			.setClimate(rs.getString(4))
            			.setLocation(rs.getString(5))
            			.setMajorCity(rs.getString(6))
            			.setReligion(transRData(rs.getString(7)))
            			.setMajorGroups(transMGData(rs.getString(8)))
            			.setMedia(rs.getString(9))
            			.setArea(rs.getDouble(10))
            			.setAreaSource(rs.getString(11))
            			.setAreaDescription(rs.getString(12))
            			.setLanguage(rs.getString(13))
            			.setYear(rs.getInt(14))
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
			String sql = "SELECT country, country_code, capital, climate, location, major_city, religion, major_group, media, area, area_source, area_desc, language, year FROM cgi WHERE religion LIKE ?";
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
            			.setClimate(rs.getString(4))
            			.setLocation(rs.getString(5))
            			.setMajorCity(rs.getString(6))
            			.setReligion(transRData(rs.getString(7)))
            			.setMajorGroups(transMGData(rs.getString(8)))
            			.setMedia(rs.getString(9))
            			.setArea(rs.getDouble(10))
            			.setAreaSource(rs.getString(11))
            			.setAreaDescription(rs.getString(12))
            			.setLanguage(rs.getString(13))
            			.setYear(rs.getInt(14))
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
	//이름전체 반환
	public ArrayList<String> selectAllCountryName(){
		ArrayList<String> result = null;
		try {
			connectDB();
			String sql = "SELECT country FROM cgi";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()) {
				result.add(rs.getString("country"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return result;
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
