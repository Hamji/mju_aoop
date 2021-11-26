package gui;

import data.CountryData;
import data.dto.CGIDTO;

import java.awt.*;

import javax.swing.*;

public class ResultFrame extends JDialog {
	private CountryListPanel countryListPanel;
	private CountryInfoPanel countryInfoPanel;
	private ResultMenuBarPanel resultMenuBarPanel;
	
	public ResultFrame(String[] countries, CGIDTO[] dtos) {
		this.setBounds(500, 200, 800, 500);
		this.setSize(800, 500);
		this.setResizable(false);
		this.setLayout(new BorderLayout(5, 5));
		this.setVisible(true);

		// TODO country가 실제로 있는 country인지 확인해야함
		this.countryInfoPanel = new CountryInfoPanel();
		this.countryListPanel = new CountryListPanel(countries, dtos);
		this.resultMenuBarPanel = new ResultMenuBarPanel();

		PanelManager.getInstance().addPanel(countryInfoPanel);
		PanelManager.getInstance().addPanel(countryListPanel);
		PanelManager.getInstance().addPanel(resultMenuBarPanel);

		JPanel gridPanel = new JPanel(new GridLayout(1, 2));
		gridPanel.add(countryListPanel);
		gridPanel.add(countryInfoPanel);

		this.add(resultMenuBarPanel, BorderLayout.NORTH);
		this.add(gridPanel, BorderLayout.CENTER);

		revalidate();
	}
}
