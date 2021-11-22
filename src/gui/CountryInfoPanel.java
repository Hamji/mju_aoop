package gui;

import java.awt.BorderLayout;
import java.awt.TextArea;
import javax.swing.JLabel;

import data.CountryData;

public class CountryInfoPanel extends BasicPanel {
	private JLabel name;
	private TextArea inform;
	
	
	private String dummyName = "Test Name";
	private String dummyInfo = "Test Information";
	
	public CountryInfoPanel() {
		this.setLayout(new BorderLayout(5, 5));
		
		this.name = new JLabel("Empty Name");
		this.inform = new TextArea("Empty");
		this.inform.setEditable(false);
		
		this.add(this.name, BorderLayout.NORTH);
		this.add(this.inform, BorderLayout.CENTER);
		
	}
	
	public CountryInfoPanel(CountryData data) {
		this.setLayout(new BorderLayout(5, 5));
		
		this.name = new JLabel(data.getName());
		this.inform = new TextArea(data.getInform());
		this.inform.setEditable(false);
		
		this.add(this.name, BorderLayout.NORTH);
		this.add(this.inform, BorderLayout.CENTER);
	}
	
	public CountryInfoPanel(String name, String inform) {
this.setLayout(new BorderLayout(5, 5));
		
		this.name = new JLabel(name);
		this.inform = new TextArea(inform);
		this.inform.setEditable(false);
		
		this.add(this.name, BorderLayout.NORTH);
		this.add(this.inform, BorderLayout.CENTER);
	}
}
