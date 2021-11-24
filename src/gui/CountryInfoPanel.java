package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
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
   
   private JPanel infoPanel;
   
   private String dummyName = "Test Name";
   private String dummyInfo = "Test Information";
   
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
      this.infoPanel.add(new JButton("hello"));
      
      this.add(this.name, BorderLayout.NORTH);
      this.add(this.infoPanel, BorderLayout.CENTER);
      this.add(urlButton, BorderLayout.SOUTH);
   }

   public void drawInfo(String country) {
      //System.out.println(country);
	  Data d = new Data();
	  CGIDTO temp;
	  this.name.setText(country);
	  this.remove(urlButton);
	  this.urlButton = null;
	  this.urlButton = new JButton("더 많은 정보");
	  URIListener ul = new URIListener(this.name.getText());
	  this.urlButton.addActionListener(ul);
	  this.add(urlButton, BorderLayout.SOUTH);
   }
}
