package gui;

import java.awt.GridLayout;

import javax.swing.JDialog;

public class ResultFrame extends JDialog {
	private CountryListPanel countryListPanel;
	private CountryInfoPanel countryInfoPanel;
	
	public ResultFrame(String text) {
		this.setBounds(500, 200, 800, 500);
		this.setSize(800, 500);
		this.setResizable(false);
		this.setLayout(new GridLayout(1, 2));
		this.setVisible(true);
		
		this.countryInfoPanel = new CountryInfoPanel();
		this.countryListPanel = new CountryListPanel();
		
		this.add(countryListPanel);
		this.add(countryInfoPanel);
	}
}
