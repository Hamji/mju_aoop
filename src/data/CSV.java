package data;

import data.dto.*;
import java.io.*;
import java.util.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSV {
	private String dir;
	
	public CSV() {
		dir = null;
	}
	
//	private boolean downloadC19() {
//		return false;
//	}
	
//	public void C19DataToCSV(ArrayList<C19DTO>) {
//	
//	}
	
//	public void CGIDataToCSV(ArrayList<CGIDTO>) {
//		//?
//	}
	
	//csvÆÄÀÏ¿¡¼­ µ¥ÀÌÅÍ¸¦ ÃßÃâÇÏ´Â ¸Ş¼Òµå
	public ArrayList<CGIDTO> getCGIData() {
		ArrayList<CGIDTO> csvList = new ArrayList<CGIDTO>();
		String csvFilePath = "\resource";
		File csv = new File(csvFilePath);
		BufferedReader lineReader = null;
		String lineText = null;		
		
		 try {			 	
			 	lineReader = new BufferedReader(new FileReader(csv, Charset.forName("UTF-8")));
			 	
			 	//¼Ó¼º ÀÌ¸§µéÀ» Á¦¿ÜÇÑ µ¥ÀÌÅÍµéÀ» °¡Á®¿À±â À§ÇØ ÇÑ ÁÙÀ» ¸ÕÀú ÀĞ´Â´Ù. 
			 	lineReader.readLine();
			 	
	            while ((lineText = lineReader.readLine()) != null) {
	            	//,·Î °¢ ¼Ó¼ºÀ» ±¸ºĞÇÒ ¶§ ""¾ÈÀÇ ,´Â ¹«½ÃÇÏ¿© ±¸ºĞÇÑ´Ù.
	                String[] data = lineText.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

	    			//Á¾±³ Ã³¸®
	    			String rg = data[6];
	    			Religion filteredRg = new Religion();	    			
	    			
	    			//µ¥ÀÌÅÍ Áß ºÒÇÊ¿äÇÑ Á¤º¸µéÀ» »èÁ¦ Ã³¸®
	    			rg = rg.replace("\"", "");
	    			rg = rg.replace(" µî", "");
	    			rg = rg.replace(",", "");
	    			rg = rg.replace(" ", "");
	    			rg = rg.replace("(", "");
	    			rg = rg.replace(")", "");
	    			
	    			//Á¤±Ô½ÄÀ» ÀÌ¿ëÇÏ¿© ÆĞÅÏ¿¡ ¸Â´Â µ¥ÀÌÅÍ¸¸ ÃßÃâÇÏ¿© Arraylist¿¡ ÀúÀå
	    			Pattern pattern = Pattern.compile("[°¡-ÆRA-Za-z]+\\d+[.]\\d+%|[°¡-ÆRA-Za-z]+\\d+%|[°¡-ÆRA-Za-z]+\\d+[-]\\d+%|[°¡-ÆRA-Za-z]+\\\\d+%[°¡-ÆRA-Za-z]+");
	    			Matcher m = pattern.matcher(rg);
	    			List<String> rgData = new ArrayList<String>();
	    			while(m.find()) {
	    				String result = m.group();
	    				rgData.add(result);
	    			}
	    			
	    			//±âÅ¸ Ç×¸ñÀÇ Áßº¹À» Ã³¸® & ºñÀ²ÀÌ 100%°¡ ¾ÈµÇ´Â µ¥ÀÌÅÍ Ã³¸® & ºñÀ²ÀÌ ¹üÀ§°ªÀ¸·Î ÁÖ¾îÁø µ¥ÀÌÅÍÀÇ Ã³¸® 
	    			Double sumRate = 0.0;
	    			int gita = -1;
	    			
	    			for(int i = 0; i < rgData.size(); i++) {	 			
	    				sumRate = sumRate + Double.parseDouble(rgData.get(i).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""));
	    				
	    				if((rgData.get(i).replaceAll("([0-9.]+[%]?)", "")).contains("±âÅ¸")) {
	    					// "±âÅ¸" Ç×¸ñÀÌ ÀÌ¹Ì ÀÖÀ¸¸é, ±× Ç×¸ñÀÇ ÀÎµ¦½º¸¦ ±¸ÇÔ
	    					gita = i;
	    				}
	    				//¹üÀ§°ª Ã³¸®
	    				if(rgData.get(i).contains("-")) {
	    					int index = rgData.get(i).indexOf("-");
	    					String substr1 = rgData.get(i).substring(index-2, index);
	    					String substr2 = rgData.get(i).substring(index+1, index+3);
	    					Double mid;
	    					mid = Double.parseDouble(substr1) + Math.round(((Double.parseDouble(substr2) - Double.parseDouble(substr1)) / 2) * 100)/100.0;
	    					rgData.set(i, rgData.get(i).replaceAll("([0-9.]+[%]?)|-", "") + Double.toString(mid) + "%");
	    				}
	    				
	    			}
	    			
	    			//±âÅ¸ Ã³¸®
	    			if(!rgData.isEmpty()) {
		    			if(sumRate < 100.0) {
		    				if(gita == -1) {
			    				Double toAdd = 100.0 - sumRate;	 
		    					toAdd = Math.round(toAdd * 100) / 100.0;
		    					rgData.add("±âÅ¸" + Double.toString(toAdd) + "%");
			    			} else {
			    				Double toAdd = 100.0 - sumRate;
			    				toAdd = Math.round(toAdd * 100) / 100.0;
			    				rgData.set(gita ,"±âÅ¸" + Double.toString(toAdd + Double.parseDouble(rgData.get(gita).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""))) + "%");
			    			}
		    			} else if(gita != -1 && sumRate >= 100.0) {
		    				rgData.set(gita ,"±âÅ¸" + Double.toString(Math.round(Double.parseDouble(rgData.get(gita).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", "")) * 100)/100.0) + "%");
		    			}
	    			}
	    			
	    			System.out.println(rgData);
    			
	    			//°¢ Á¾±³ µ¥ÀÌÅÍ¸¦ ÀÌ¸§°ú ºñÀ²·Î ³ª´©¾î¼­ Religion¿¡ ÀúÀå
	    			String rg_name;
	    			String rg_number;
	    			Double rg_rate;	
	    			
	    			String[] rgArray = (String[]) rgData.toArray(new String[rgData.size()]);
	    			
	    			for(int i = 0; i< rgArray.length; i++) {
	    				System.out.println(rgArray[i]);
	    				rg_name = rgArray[i].replaceAll("([0-9.]+[%]?)", "");
	    				if(rg_name.contains("±âÅ¸")) {
	    					rg_name = "±âÅ¸";
	    				}	    				
	    				rg_number = rgArray[i].replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", "");
	    				rg_rate = Double.parseDouble(rg_number);
	    				
	    				filteredRg.setRData(rg_name, rg_rate);	    				
	    			}
	    			
	    			
	    			
	    			//±×·ì °¡°ø
	    			String mg = data[7];
	    			MajorGroups filteredMg = new MajorGroups();	
	    			
	    			//µ¥ÀÌÅÍ Áß ºÒÇÊ¿äÇÑ Á¤º¸µéÀ» »èÁ¦ Ã³¸®
	    			mg = mg.replace("\"", "");
	    			mg = mg.replace(" µî", "");
	    			mg = mg.replace(",", "");
	    			mg = mg.replace(" ", "");
	    			mg = mg.replace("(", "");
	    			mg = mg.replace(")", "");
	    			
	    			//Á¤±Ô½ÄÀ» ÀÌ¿ëÇÏ¿© ÆĞÅÏ¿¡ ¸Â´Â µ¥ÀÌÅÍ¸¸ ÃßÃâÇÏ¿© Arraylist¿¡ ÀúÀå
	    			Pattern pattern2 = Pattern.compile("[°¡-ÆRA-Za-z]+\\d+[.]\\d+%|[°¡-ÆRA-Za-z]+\\d+%|[°¡-ÆRA-Za-z]+\\d+[-]\\d+%|[°¡-ÆRA-Za-z]+\\d+%[°¡-ÆRA-Za-z]+");
	    			Matcher m2 = pattern2.matcher(mg);
	    			List<String> mgData = new ArrayList<String>();
	    			while(m2.find()) {
	    				String result = m2.group();
	    				mgData.add(result);
	    			}
	    			
	    			//±âÅ¸ Ç×¸ñÀÇ Áßº¹À» Ã³¸® & ºñÀ²ÀÌ 100%°¡ ¾ÈµÇ´Â µ¥ÀÌÅÍ Ã³¸® & ºñÀ²ÀÌ ¹üÀ§°ªÀ¸·Î ÁÖ¾îÁø µ¥ÀÌÅÍÀÇ Ã³¸® 
	    			Double sumRate1 = 0.0;
	    			int gita1 = -1;
	    			
	    			for(int i = 0; i < mgData.size(); i++) {	 			
	    				
	    				sumRate1 = sumRate1 + Double.parseDouble(mgData.get(i).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""));
	    				
	    				if((mgData.get(i).replaceAll("([0-9.]+[%]?)", "")).contains("±âÅ¸")) {
	    					gita1 = i;
	    				}
	    				//¹üÀ§°ª Ã³¸®
	    				if(mgData.get(i).contains("-")) {
	    					int index = mgData.get(i).indexOf("-");
	    					String substr1 = mgData.get(i).substring(index-2, index);
	    					String substr2 = mgData.get(i).substring(index+1, index+3);
	    					Double mid;
	    					mid = Double.parseDouble(substr1) + Math.round(((Double.parseDouble(substr2) - Double.parseDouble(substr1)) / 2) * 100)/100.0;
	    					mgData.set(i, mgData.get(i).replaceAll("([0-9.]+[%]?)|-", "") + Double.toString(mid) + "%");
	    				}
	    				
	    			}
	    			//±âÅ¸ Ã³¸®
	    			if(!mgData.isEmpty()) {
		    			if(sumRate1 <100.0) {
			    			if(gita1 == -1) {
			    				Double toAdd = 100.0 - sumRate1;	 
		    					toAdd = Math.round(toAdd * 100) / 100.0;
		    					mgData.add("±âÅ¸" + Double.toString(toAdd) + "%");
			    			} else {
			    				Double toAdd = 100.0 - sumRate1;
			    				toAdd = Math.round(toAdd * 100) / 100.0;
			    				mgData.set(gita1 ,"±âÅ¸" + Double.toString(toAdd + Double.parseDouble(mgData.get(gita1).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""))) + "%");
			    			}
		    			} else if(gita1 != -1 && sumRate1 >= 100.0) {
		    				mgData.set(gita1 ,"±âÅ¸" + Double.toString(Double.parseDouble(mgData.get(gita1).replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", ""))) + "%");
		    			}
	    			}	    			
	    			
	    			System.out.println(mgData);
    			
	    			//°¢ ÁÖ¿ä¹ÎÁ· µ¥ÀÌÅÍ¸¦ ÀÌ¸§°ú ºñÀ²·Î ³ª´©¾î¼­ MajorGroups¿¡ ÀúÀå
	    			String mg_name;
	    			String mg_number;
	    			Double mg_rate;
	    			
	    			
	    			String[] mgArray = (String[]) mgData.toArray(new String[mgData.size()]);

	    			for(int i = 0; i< mgArray.length; i++) {	
	    				mg_name = mgArray[i].replaceAll("([0-9.]+[%]?)", "");
	    				if(mg_name.contains("±âÅ¸")) {
	    					mg_name = "±âÅ¸";
	    				}	    	
	    				mg_number = mgArray[i].replaceAll("[^[0-9]{1,2}.?[0-9]?]|[0-9]+-|[0-9]+/[0-9]+", "");
	    				mg_rate = Double.parseDouble(mg_number);
	    				
	    				filteredMg.setMGData(mg_name, mg_rate);
	    			}
	    			
	    			//¸éÀû Ç×¸ñÀÇ ºó µ¥ÀÌÅÍ Ã³¸®
	    			if(data[9] == "") {
	    				data[9] = "0.0";
	    			}	    			    			
	    			
	    			//Builder Ã³¸®
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
	    			
	    			//ÇÑ ³ª¶óÀÇ CGIDTO °´Ã¼¸¦ ArrayList¿¡ Ãß°¡
	                csvList.add(dto);
	            }
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (lineReader != null) { 
	                	lineReader.close(); // »ç¿ë ÈÄ BufferedReader¸¦ ´İ¾ÆÁØ´Ù.
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
