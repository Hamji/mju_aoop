package gui;

import data.CountryData;

import java.awt.GridLayout;

import javax.swing.JDialog;

public class ResultFrame extends JDialog {
	private CountryListPanel countryListPanel;
	private CountryInfoPanel countryInfoPanel;
	
	public ResultFrame(String[] countries) {
		this.setBounds(500, 200, 800, 500);
		this.setSize(800, 500);
		this.setResizable(false);
		this.setLayout(new GridLayout(1, 2));
		this.setVisible(true);

		// TODO country가 실제로 있는 country인지 확인해야함

		this.countryInfoPanel = new CountryInfoPanel();
		this.countryListPanel = new CountryListPanel();

		this.add(countryListPanel);
		this.add(countryInfoPanel);
	}
}
