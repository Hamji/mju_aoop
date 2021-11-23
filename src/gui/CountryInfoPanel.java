package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.nio.ByteOrder;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.CountryData;

public class CountryInfoPanel extends BasicPanel {
	private JLabel name;
	private TextArea inform;
	private static final String url = "https://www.tripadvisor.co.kr/Search?q=";
	private JButton urlButton;
	private static String strName;
	
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
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new GridLayout(2,1));
		
		this.infoPanel.add(this.inform);
		this.infoPanel.add(new JButton("hello"));
		
		this.add(this.name, BorderLayout.NORTH);
		this.add(this.infoPanel, BorderLayout.CENTER);
		this.add(urlButton, BorderLayout.SOUTH);
	}
	
	public void drawInfo(String name) {
		
	}
}
