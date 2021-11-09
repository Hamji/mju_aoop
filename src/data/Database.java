package data;

import java.beans.Statement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;

import data.dto.CGIDTO;
import data.dto.MajorGroups;
import data.dto.Religion;
import data.dto.CGIDTO.Builder;
import data.CSV;

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
	public void insertC19Data() {
		
	}
	
	//CSV파일의 데이터를 DB에 저장
	public void insertCGIData() throws SQLException {
		
		try {
			this.connectDB();
            
            String sql = "INSERT INTO 데이터베이스 이름(country, country_code, capital, location, major_city, religion, major_groups, media, area, area_description, language, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            java.sql.PreparedStatement statement = this.con.prepareStatement(sql);
 
            CSV csv = new CSV();
            ArrayList<CGIDTO> csvList = new ArrayList<CGIDTO>();
            
          //csv파일의 데이터를 가져옴
            csvList = csv.getCGIData();
            
            int i = 0;
            while(i < csvList.size()) {
            	statement.setString(1, csvList.get(i).getCountry());
            	statement.setString(2, csvList.get(i).getCountryCode());
            	statement.setString(3, csvList.get(i).getCapital());
            	statement.setString(4, csvList.get(i).getLocation());
            	statement.setString(5, csvList.get(i).getMajorCity());
            	statement.setString(6, csvList.get(i).getReligion().getRData().toString());
            	statement.setString(7, csvList.get(i).getMajorGroups().getMGData().toString());            	
            	statement.setString(8, csvList.get(i).getMedia());
            	statement.setDouble(9, csvList.get(i).getArea());
            	statement.setString(10, csvList.get(i).getAreaDescription());
            	statement.setString(11, csvList.get(i).getLanguage());
            	statement.setInt(12, csvList.get(i).getYear());
            
            	statement.execute();
            	i = i + 1;
            }
            
            this.con.commit();
            this.con.close();
 
        } catch (SQLException ex) {
            ex.printStackTrace();
 
          //실패 시, 롤백함
            try {
            	 this.con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
	}
	
	//입력받은 문자열과 관련된 모든 CGI 데이터를 추출함
	public ArrayList<CGIDTO> selectCGIData(String filter) {
		ArrayList<CGIDTO> csvList = new ArrayList<CGIDTO>();
		
		try {
			connectDB();
            
			//입력받은 문자열로 시작하는 문자열을 각 국가의 이름, 국가코드, 수도, 주요도시와 비교하고 같으면 select하는 sql문
			String sql = "SELECT * FROM 데이터베이스 이름WHERE country LIKE '" + filter + "%' OR country_code LIKE '" + filter + "%' OR capital LIKE '" + filter + "%' OR major_city LIKE '" + filter + "%'";
			
            java.sql.PreparedStatement statement = this.con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();            

            //속성 이름이 들어간 처음 행을 건너뜀
            int i = 1;
            
            while(rs.next()) {            	
            	
            	//종교 처리
            	String rg = rs.getString(6);
            	Religion filteredRg = new Religion();
            	rg = rg.replace(",", "");
    			rg = rg.replace(" ", "");
    			rg = rg.replace("{", "");
    			rg = rg.replace("}", "");
    			
    			Pattern pattern = Pattern.compile("[가-힣A-Za-z]+\\d+[.]\\d+%|[가-힣A-Za-z]+\\d+%|[가-힣A-Za-z]+\\d+[-]\\d+%|[가-힣A-Za-z]+\\\\d+%[가-힣A-Za-z]+");
    			Matcher m = pattern.matcher(rg);
    			List<String> rgData = new ArrayList<String>();
    			while(m.find()) {
    				String result = m.group();
    				rgData.add(result);
    			}
    			
    			String rg_name;
    			String rg_number;
    			Double rg_rate;	
    			
    			String[] rgArray = (String[]) rgData.toArray(new String[rgData.size()]);
    			
    			for(int j = 0; j< rgArray.length; j++) {
    				System.out.println(rgArray[j]);
    				rg_name = rgArray[j].replaceAll("([0-9.]+[%]?)", ""); 				
    				rg_number = rgArray[j].replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", "");
    				rg_rate = Double.parseDouble(rg_number);
    				
    				filteredRg.setRData(rg_name, rg_rate);	    				
    			}
            	
    			//주요민족 처리
    			String mg = rs.getString(7);
    			MajorGroups filteredMg = new MajorGroups();	
    			mg = mg.replace(",", "");
    			mg = mg.replace(" ", "");
    			mg = mg.replace("{", "");
    			mg = mg.replace("}", "");

    			Pattern pattern2 = Pattern.compile("[가-힣A-Za-z]+\\d+[.]\\d+%|[가-힣A-Za-z]+\\d+%|[가-힣A-Za-z]+\\d+[-]\\d+%|[가-힣A-Za-z]+\\d+%[가-힣A-Za-z]+");
    			Matcher m2 = pattern2.matcher(mg);
    			List<String> mgData = new ArrayList<String>();
    			while(m2.find()) {
    				String result = m2.group();
    				mgData.add(result);
    			}
    			
    			String mg_name;
    			String mg_number;
    			Double mg_rate;
    			
    			String[] mgArray = (String[]) mgData.toArray(new String[mgData.size()]);

    			for(int k = 0; k< mgArray.length; k++) {	
    				mg_name = mgArray[k].replaceAll("([0-9.]+[%]?)", "");    	
    				mg_number = mgArray[k].replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", "");
    				mg_rate = Double.parseDouble(mg_number);
    				
    				filteredMg.setMGData(mg_name, mg_rate);
    			}
            	
    			//Builder 처리
    			CGIDTO dto = new CGIDTO.Builder()
    					.setCountry(rs.getString(1)) 
    					.setCountryCode(rs.getString(2))
    					.setCapital(rs.getString(3))
    					.setLocation(rs.getString(4))
    					.setMajorCity(rs.getString(5))
    					.setReligion(filteredRg)
    					.setMajorGroups(filteredMg)
    					.setMedia(rs.getString(8))
    					.setArea(Double.parseDouble(rs.getString(9)))
    					.setAreaDescription(rs.getString(10))
    					.setLanguage(rs.getString(11))
    					.setYear(Integer.parseInt(rs.getString(12)))
    					.build();
            	
            	csvList.add(dto);
            	
            	i = i + 1;
            }
            
            this.con.close();
 
        } catch (SQLException ex) {
            ex.printStackTrace();
 
            try {
            	 this.con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
		return csvList;
	}
}
