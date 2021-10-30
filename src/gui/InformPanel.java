package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

public class InformPanel extends BasicPanel {
	private FilterPanel filterPanel;
	private ApplyedListPanel applyedListPanel;
	
	public InformPanel() {
		this.setBackground(Color.GRAY);
		this.setLayout(new GridLayout(2, 1));
		filterPanel = new FilterPanel();
		applyedListPanel = new ApplyedListPanel();
		
		this.add(this.applyedListPanel);
		this.add(this.filterPanel);
		
	}
}
