package gui;

import java.awt.BorderLayout;

import javax.swing.*;

public class ApplyedListPanel extends BasicPanel {
	private DefaultListModel items;
	private JList list;
	private JLabel label;
	
	public ApplyedListPanel() {
		this.setLayout(new BorderLayout(0, 0));
		items = new DefaultListModel();
		list = new JList(items);
		label = new JLabel("적용된 필터");
		
		this.add(label, BorderLayout.NORTH);
		this.add(list, BorderLayout.CENTER);
	}

	public void addFilter(String filter) {
		items.add(list.getModel().getSize(), filter);
	}
}
