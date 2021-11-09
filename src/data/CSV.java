package data;

import data.dto.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSV {
	private static final String API_PROPERTIES = "src/resource/apikey.properties";
	private String dir;
	
	public CSV() {
		dir = null;
	}
	
	ArrayList<C19Data> getC19Api(String date){
		ArrayList<C19Data> dto = new ArrayList<>();
		Properties properties = new Properties();
		StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19NatInfStateJson");
		try {
			FileReader resource = new FileReader(API_PROPERTIES);
			properties.load(resource);
			//API��ȸ�� ���� URL����
			urlBuilder.append("?"+URLEncoder.encode("serviceKey","UTF-8") + "=" + properties.getProperty("enckey").toString());
			urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode(properties.getProperty("deckey").toString(),"UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*��������ȣ*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*�� ������ ��� ��*/
	        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*�˻��� ������ ������ ����*/
	        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*�˻��� ������ ������ ����*/
	        URL url = new URL(urlBuilder.toString());
	        //HTML Request
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Request code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	        	rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	        	rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while((line = rd.readLine())!=null) {
	        	sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        System.out.println(sb.toString());
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public void C19DataToCSV(ArrayList<C19Data> list) {
	
	}
	
	public void CGIDataToCSV(ArrayList<CGIDTO> list) throws IOException {
		
		FileWriter writer = new FileWriter("C:\\Users\\foot0\\Desktop\\test.csv");
		
		int i = 0;
		while(i < list.size()) {
			List<String> joinList = new ArrayList<String>();
			joinList.add(list.get(i).getCountry());
			joinList.add(list.get(i).getCountryCode());
			joinList.add(list.get(i).getCapital());
			joinList.add(list.get(i).getLocation());
			joinList.add(list.get(i).getMajorCity());
			joinList.add(list.get(i).getReligion().getRData().toString());
			joinList.add(list.get(i).getMajorGroups().getMGData().toString());
			joinList.add(list.get(i).getMedia());
			joinList.add(Double.toString(list.get(i).getArea()));
			joinList.add(list.get(i).getAreaDescription());
			joinList.add(list.get(i).getLanguage());
			joinList.add(Integer.toString(list.get(i).getYear()));
			
			writer.write(joinList.stream().collect(Collectors.joining(",")));
			writer.write("\n"); 
			
			i++;
		}
		
		writer.close();
	}
	
	//csv파일에서 데이터를 가져옴
	public ArrayList<CGIDTO> getCGIData() {
		ArrayList<CGIDTO> csvList = new ArrayList<CGIDTO>();
		String csvFilePath = "\resource";
		File csv = new File(csvFilePath);
		BufferedReader lineReader = null;
		String lineText = null;		
		
		 try {			 	
			 	lineReader = new BufferedReader(new FileReader(csv));
			 	
			 	//속성 이름들을 제외한 데이터들을 가져오기 위해 한 줄을 먼저 읽는다. 
			 	lineReader.readLine();
			 	
	            while ((lineText = lineReader.readLine()) != null) {
	            	//,로 각 속성을 구분할 때 ""안의 ,는 무시하여 구분한다.
	                String[] data = lineText.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

	    			//종교 처리
	    			String rg = data[6];
	    			Religion filteredRg = new Religion();	    			
	    			
	    			//데이터 중 불필요한 정보들을 삭제 처리
	    			rg = rg.replace("\"", "");
	    			rg = rg.replace(" 등", "");
	    			rg = rg.replace(",", "");
	    			rg = rg.replace(" ", "");
	    			rg = rg.replace("(", "");
	    			rg = rg.replace(")", "");
	    			
	    			//", "",""*" 등으로 각 종교를 구분하여 데이터를 분리하여 배열로 저장
	    			Pattern pattern = Pattern.compile("[가-힣A-Za-z]+\\d+[.]\\d+%|[가-힣A-Za-z]+\\d+%|[가-힣A-Za-z]+\\d+[-]\\d+%|[가-힣A-Za-z]+\\\\d+%[가-힣A-Za-z]+");
	    			Matcher m = pattern.matcher(rg);
	    			List<String> rgData = new ArrayList<String>();
	    			while(m.find()) {
	    				String result = m.group();
	    				rgData.add(result);
	    			}
	    			
	    			//종교 비율이 100%가 아닌 곳에 기타를 추가한다
	    			Double sumRate = 0.0;
	    			int gita = -1;
	    			
	    			for(int i = 0; i < rgData.size(); i++) {	 			
	    				sumRate = sumRate + Double.parseDouble(rgData.get(i).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""));
	    				
	    				if((rgData.get(i).replaceAll("([0-9.]+[%]?)", "")).contains("기타")) {
	    					// "기타" 항목의 인덕스를 구함
	    					gita = i;
	    				}
	    				//비율 범위 처리
	    				if(rgData.get(i).contains("-")) {
	    					int index = rgData.get(i).indexOf("-");
	    					String substr1 = rgData.get(i).substring(index-2, index);
	    					String substr2 = rgData.get(i).substring(index+1, index+3);
	    					Double mid;
	    					mid = Double.parseDouble(substr1) + Math.round(((Double.parseDouble(substr2) - Double.parseDouble(substr1)) / 2) * 100)/100.0;
	    					rgData.set(i, rgData.get(i).replaceAll("([0-9.]+[%]?)|-", "") + Double.toString(mid) + "%");
	    				}
	    				
	    			}
	    			
	    			//기타 처리
	    			if(!rgData.isEmpty()) {
		    			if(sumRate < 100.0) {
		    				if(gita == -1) {
			    				Double toAdd = 100.0 - sumRate;	 
		    					toAdd = Math.round(toAdd * 100) / 100.0;
		    					rgData.add("기타" + Double.toString(toAdd) + "%");
			    			} else {
			    				Double toAdd = 100.0 - sumRate;
			    				toAdd = Math.round(toAdd * 100) / 100.0;
			    				rgData.set(gita ,"기타" + Double.toString(toAdd + Double.parseDouble(rgData.get(gita).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""))) + "%");
			    			}
		    			} else if(gita != -1 && sumRate >= 100.0) {
		    				rgData.set(gita ,"기타" + Double.toString(Math.round(Double.parseDouble(rgData.get(gita).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", "")) * 100)/100.0) + "%");
		    			}
	    			}
	    			
	    			System.out.println(rgData);
    			
	    			String rg_name;
	    			String rg_number;
	    			Double rg_rate;	
	    			
	    			String[] rgArray = (String[]) rgData.toArray(new String[rgData.size()]);
	    			
	    			for(int i = 0; i< rgArray.length; i++) {
	    				System.out.println(rgArray[i]);
	    				rg_name = rgArray[i].replaceAll("([0-9.]+[%]?)", "");
	    				if(rg_name.contains("기타")) {
	    					rg_name = "기타";
	    				}	    				
	    				rg_number = rgArray[i].replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", "");
	    				rg_rate = Double.parseDouble(rg_number);
	    				
	    				filteredRg.setRData(rg_name, rg_rate);	    				
	    			}
	    			
	    			
	    			
	    			//주요민족 가공
	    			String mg = data[7];
	    			MajorGroups filteredMg = new MajorGroups();	
	    			
	    			//데이터 중 불필요한 정보들을 삭제 처리
	    			mg = mg.replace("\"", "");
	    			mg = mg.replace(" 등", "");
	    			mg = mg.replace(",", "");
	    			mg = mg.replace(" ", "");
	    			mg = mg.replace("(", "");
	    			mg = mg.replace(")", "");
	    			
	    			//", "",""*" 등으로 각 종교를 구분하여 데이터를 분리하여 배열로 저장
	    			Pattern pattern2 = Pattern.compile("[가-힣A-Za-z]+\\d+[.]\\d+%|[가-힣A-Za-z]+\\d+%|[가-힣A-Za-z]+\\d+[-]\\d+%|[가-힣A-Za-z]+\\d+%[가-힣A-Za-z]+");
	    			Matcher m2 = pattern2.matcher(mg);
	    			List<String> mgData = new ArrayList<String>();
	    			while(m2.find()) {
	    				String result = m2.group();
	    				mgData.add(result);
	    			}
	    			
	    			//주요민족 비율이 100%가 아닌 곳에 기타를 추가한다
	    			Double sumRate1 = 0.0;
	    			int gita1 = -1;
	    			
	    			for(int i = 0; i < mgData.size(); i++) {	 			
	    				
	    				sumRate1 = sumRate1 + Double.parseDouble(mgData.get(i).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""));
	    				
	    				if((mgData.get(i).replaceAll("([0-9.]+[%]?)", "")).contains("기타")) {
	    					gita1 = i;
	    				}
	    				//주요민족 비율 범위 처리
	    				if(mgData.get(i).contains("-")) {
	    					int index = mgData.get(i).indexOf("-");
	    					String substr1 = mgData.get(i).substring(index-2, index);
	    					String substr2 = mgData.get(i).substring(index+1, index+3);
	    					Double mid;
	    					mid = Double.parseDouble(substr1) + Math.round(((Double.parseDouble(substr2) - Double.parseDouble(substr1)) / 2) * 100)/100.0;
	    					mgData.set(i, mgData.get(i).replaceAll("([0-9.]+[%]?)|-", "") + Double.toString(mid) + "%");
	    				}
	    				
	    			}
	    			//기타 처리
	    			if(!mgData.isEmpty()) {
		    			if(sumRate1 <100.0) {
			    			if(gita1 == -1) {
			    				Double toAdd = 100.0 - sumRate1;	 
		    					toAdd = Math.round(toAdd * 100) / 100.0;
		    					mgData.add("기타" + Double.toString(toAdd) + "%");
			    			} else {
			    				Double toAdd = 100.0 - sumRate1;
			    				toAdd = Math.round(toAdd * 100) / 100.0;
			    				mgData.set(gita1 ,"기타" + Double.toString(toAdd + Double.parseDouble(mgData.get(gita1).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""))) + "%");
			    			}
		    			} else if(gita1 != -1 && sumRate1 >= 100.0) {
		    				mgData.set(gita1 ,"기타" + Double.toString(Double.parseDouble(mgData.get(gita1).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""))) + "%");
		    			}
	    			}	    			
	    			
	    			System.out.println(mgData);
    			
	    			String mg_name;
	    			String mg_number;
	    			Double mg_rate;
	    			
	    			
	    			String[] mgArray = (String[]) mgData.toArray(new String[mgData.size()]);

	    			for(int i = 0; i< mgArray.length; i++) {	
	    				mg_name = mgArray[i].replaceAll("([0-9.]+[%]?)", "");
	    				if(mg_name.contains("기타")) {
	    					mg_name = "기타";
	    				}	    	
	    				mg_number = mgArray[i].replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", "");
	    				mg_rate = Double.parseDouble(mg_number);
	    				
	    				filteredMg.setMGData(mg_name, mg_rate);
	    			}
	    			
	    			//면적이 없는 나라에 대한 처리
	    			if(data[9] == "") {
	    				data[9] = "0.0";
	    			}	    			    			
	    			
	    			//Builder 처리
	    			CGIDTO dto = new CGIDTO.Builder()
	    					.setCountry(data[0])	
	    					.setCountryCode(data[1])
	    					.setCapital(data[2])
	    					.setClimate(data[3])
	    					.setLocation(data[4])
	    					.setMajorCity(data[5])
	    					.setReligion(filteredRg)
	    					.setMajorGroups(filteredMg)
	    					.setMedia(data[8])
	    					.setArea(Double.parseDouble(data[9]))
	    					.setAreaSource(data[10])
	    					.setAreaDescription(data[11])
	    					.setLanguage(data[12])
	    					.setYear(Integer.parseInt(data[13]))
	    					.build();
	    			
	    			//각 나라의 CGIDTO 데이터를 리스트에 추가함
	                csvList.add(dto);
	            }
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (lineReader != null) { 
	                	lineReader.close(); // 사용 후 BufferedReader를 닫아준다.
	                }
	            } catch(IOException e) {
	                e.printStackTrace();
	            }
	        }
		 
		 return csvList;
	}
	
	
	
	
//	public ArrayList<C19DTO> getC19Data() {
//		return new ArrayList<C19DTO>();
//	}
	
}
