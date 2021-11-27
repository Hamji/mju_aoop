package gui;

import java.awt.BorderLayout;

import javax.swing.*;

public class SearchPanel extends BasicPanel {

	private JButton searchBtn;
	private JComboBox searchTxt;
	
	public SearchPanel() {
		this.searchBtn = new JButton("search");
		this.searchTxt = new AutoCompleteJComboBox();

		this.setLayout(new BorderLayout(5, 5));
		this.add(searchBtn, BorderLayout.EAST);
		this.add(searchTxt, BorderLayout.CENTER);
		
		this.searchBtn.addActionListener(e -> new ResultFrame(new String[]{(String)searchTxt.getSelectedItem()}, null));
	}

}
