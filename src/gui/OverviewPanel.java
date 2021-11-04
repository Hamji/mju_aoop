package gui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;

import gui.MainFrame;

public class OverviewPanel extends BasicPanel{
	private SearchPanel searchPanel;
	private MapPanel mapPanel;
	
	public OverviewPanel() {
		this.setLayout(new BorderLayout(0, 0));
		
		this.searchPanel = new SearchPanel();
		this.mapPanel = new MapPanel(new ImageIcon(MainFrame.class.getResource("../image/image.jpg"))
				.getImage());

		PanelManager.getInstance().addPanel(searchPanel);
		PanelManager.getInstance().addPanel(mapPanel);
		this.add(this.searchPanel, BorderLayout.NORTH);
		this.add(this.mapPanel, BorderLayout.CENTER);
	}
}
