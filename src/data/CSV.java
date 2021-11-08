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
	private String dir;
	
	public CSV() {
		dir = null;
	}
	
	public void CGIDataToCSV(ArrayList<CGIDTO> list) {
		//?
	}
	
	//CGI CSV파일 읽기
	public ArrayList<CGIDTO> getCGIData() {
		ArrayList<CGIDTO> csvList = new ArrayList<CGIDTO>();
		String csvFilePath = "\resource";
		File csv = new File(csvFilePath);
		BufferedReader lineReader = null;
		String lineText = null;		
		
		 try {			 	
			 	lineReader = new BufferedReader(new FileReader(csv));
			 	
			 	//속성 이름들을 제외한 데이터들을 가져오기 위해 한 줄을 먼저 읽는다
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

	    			//정규식을 이용하여 패턴에 맞는 데이터만 추출하여 Arraylist에 저장
	    			Pattern pattern = Pattern.compile("[가-RA-Za-z]+\\d+[.]\\d+%|[가-RA-Za-z]+\\d+%|[가-RA-Za-z]+\\d+[-]\\d+%|[가-RA-Za-z]+\\\\d+%[가-RA-Za-z]+");
	    			Matcher m = pattern.matcher(rg);
	    			List<String> rgData = new ArrayList<String>();
	    			while(m.find()) {
	    				String result = m.group();
	    				rgData.add(result);
	    			}

	    			//기타 항목의 중복을 처리 & 비율이 100%가 안되는 데이터 처리 & 비율이 범위값으로 주어진 데이터의 처리 
	    			Double sumRate = 0.0;
	    			int gita = -1;

	    			for(int i = 0; i < rgData.size(); i++) {	 			
	    				sumRate = sumRate + Double.parseDouble(rgData.get(i).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""));

	    				if((rgData.get(i).replaceAll("([0-9.]+[%]?)", "")).contains("기타")) {
	    					// "기타" 항목이 이미 있으면, 그 항목의 인덱스를 구함
	    					gita = i;
	    				}
	    				//범위값 처리
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

	    			//각 종교 데이터를 이름과 비율로 나누어서 Religion에 저장
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



	    			//그룹 가공
	    			String mg = data[7];
	    			MajorGroups filteredMg = new MajorGroups();	

	    			//데이터 중 불필요한 정보들을 삭제 처리
	    			mg = mg.replace("\"", "");
	    			mg = mg.replace(" 등", "");
	    			mg = mg.replace(",", "");
	    			mg = mg.replace(" ", "");
	    			mg = mg.replace("(", "");
	    			mg = mg.replace(")", "");

	    			//정규식을 이용하여 패턴에 맞는 데이터만 추출하여 Arraylist에 저장
	    			Pattern pattern2 = Pattern.compile("[가-RA-Za-z]+\\d+[.]\\d+%|[가-RA-Za-z]+\\d+%|[가-RA-Za-z]+\\d+[-]\\d+%|[가-RA-Za-z]+\\d+%[가-RA-Za-z]+");
	    			Matcher m2 = pattern2.matcher(mg);
	    			List<String> mgData = new ArrayList<String>();
	    			while(m2.find()) {
	    				String result = m2.group();
	    				mgData.add(result);
	    			}

	    			//기타 항목의 중복을 처리 & 비율이 100%가 안되는 데이터 처리 & 비율이 범위값으로 주어진 데이터의 처리 
	    			Double sumRate1 = 0.0;
	    			int gita1 = -1;

	    			for(int i = 0; i < mgData.size(); i++) {	 			

	    				sumRate1 = sumRate1 + Double.parseDouble(mgData.get(i).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""));

	    				if((mgData.get(i).replaceAll("([0-9.]+[%]?)", "")).contains("기타")) {
	    					gita1 = i;
	    				}
	    				//범위값 처리
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

	    			//각 주요민족 데이터를 이름과 비율로 나누어서 MajorGroups에 저장
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

	    			//면적 항목의 빈 데이터 처리
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

	    			//한 나라의 CGIDTO 객체를 ArrayList에 추가
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
}
