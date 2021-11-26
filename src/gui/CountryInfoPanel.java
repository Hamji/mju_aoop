package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.ByteOrder;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.CountryData;
import data.Data;
import data.dto.CGIDTO;

class URIListener implements ActionListener{
	private String data;
	private String url = "https://www.tripadvisor.co.kr/Search?q=";
	
	public URIListener(String data) {
		this.data = new String(data);
	}
	@Override
    public void actionPerformed(ActionEvent e) {
		Desktop desktop = Desktop.getDesktop();
		try {
			System.out.println(data);
			String subURL = this.data;
			if (data.equals("Empty Name"))
				subURL = "";
			subURL = URLEncoder.encode(subURL, "UTF-8");
            URI uri = new URI(url + subURL);
            desktop.browse(uri);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
}

public class CountryInfoPanel extends BasicPanel {
   private JLabel name;
   private TextArea inform;
   private static final String url = "https://www.tripadvisor.co.kr/Search?q=";
   private JButton urlButton;
   private ChartPanel chartPanel;
   
   private JPanel infoPanel;
   
   private String dummyName = "Test Name";
   private String infoString;
   
   private Map<String, Double> rMap;
   
   Color[] color = {Color.RED, Color.BLUE, Color.CYAN, Color.ORANGE,
		   Color.MAGENTA, Color.GREEN, Color.YELLOW };
   
   class ChartPanel extends JPanel{
	  public void paintComponent(Graphics g) {
		  super.paintComponent(g);
		  
		  int startAngle = 0;
		  
		  
		  if (rMap.isEmpty()) {
			  System.out.println("null");
			  return;
		  }
		  String[] keyArr = rMap.keySet().toArray(new String[0]);
		  
		  for (int i = 0; i < keyArr.length; i++) {
			  g.setColor(color[i]);
			  int temp = (int) Math.round(rMap.get(keyArr[i]));
			  g.drawString( keyArr[i] + " : " + Integer.toString(temp) + "%", 10 , 20 + i * 20);
			  //System.out.println(keyArr[i]);
		  }
		  for (int i = 0; i < keyArr.length; i++) {
			  g.setColor(color[i]);
			  g.fillArc(150, 50, 100, 100, startAngle, (int)(rMap.get(keyArr[i]) * 36 / 10.0));
			  startAngle += (int)(rMap.get(keyArr[i]) * 36 / 10.0);
		  }
	  }
   }
   
   public CountryInfoPanel() {
      this.name = new JLabel("Empty Name");
      this.inform = new TextArea("Empty");
      this.inform.setEditable(false);
      
      this.setLayout();
      
   }
   
   public CountryInfoPanel(CountryData data) {
      this.name = new JLabel(data.getName());
      this.inform = new TextArea(data.getInform());
      this.inform.setEditable(false);
      
      this.setLayout();
   }
   
   public CountryInfoPanel(String name, String inform) {
      this.name = new JLabel(name);
      this.inform = new TextArea(inform);
      this.inform.setEditable(false);
      
      this.setLayout();
   }
   
   public void setLayout() {
      this.setLayout(new BorderLayout());
      
      this.urlButton = new JButton("더 많은 정보");
      this.urlButton.addActionListener(new URIListener(this.name.getText()));
      
      this.infoPanel = new JPanel();
      this.infoPanel.setLayout(new GridLayout(2,1));
      
      this.infoPanel.add(this.inform);
      
      this.add(this.name, BorderLayout.NORTH);
      this.add(this.infoPanel, BorderLayout.CENTER);
      this.add(urlButton, BorderLayout.SOUTH);
   }

   public void drawInfo(String country) {
      //System.out.println("이 나라는 " + country);
	  Data d = Data.getInstance();
	  CGIDTO temp = d.getCountryData(country);
	  
	  
	  // name update
	  this.name.setText(country);
	  
	  // url button update
	  this.remove(urlButton);
	  this.urlButton = null;
	  this.urlButton = new JButton("더 많은 정보");
	  URIListener ul = new URIListener(this.name.getText());
	  this.urlButton.addActionListener(ul);
	  this.add(urlButton, BorderLayout.SOUTH);
   
	  // infomation update
	  this.infoString = new String("");
	  this.infoString += "위치 : " + temp.getLocation() +'\n';
	  this.infoString += "수도 : " + temp.getCapital() + '\n';
	  this.infoString += "주요 도시 : " + temp.getMajorCity() + '\n';
	  this.infoString += "기후 : " + temp.getClimate() + '\n';
	  this.infoString += "국가 번호 : " + temp.getCountryCode() + '\n';
	  this.infoString += "사용 언어 : " + temp.getLanguage() + '\n';
	  this.infoString += "종교 : " + temp.getReligion().toString();
	  this.inform.setText(infoString);
	  
	  // graph
	  this.rMap = temp.getReligion().getRData();
	  if (chartPanel != null)
		  this.infoPanel.remove(chartPanel);
	  this.chartPanel = new ChartPanel();
	  this.infoPanel.add(this.chartPanel);
	  
	  this.repaint();
   }
}
