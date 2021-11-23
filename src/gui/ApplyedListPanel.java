package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class ApplyedListPanel extends BasicPanel {
	private DefaultListModel items;
	private JList list;
	private JLabel label;

	private class TopPanel extends BasicPanel {
		public TopPanel() {
			setLayout(new GridLayout(1, 2));
			JButton button = new JButton("삭제");
			button.addActionListener(e -> removeFilter());

			add(label);
			add(button);
		}
	}

	public ApplyedListPanel() {
		this.setLayout(new BorderLayout(0, 0));
		items = new DefaultListModel();
		list = new JList(items);
		label = new JLabel("적용된 필터");
		
		this.add(new TopPanel(), BorderLayout.NORTH);
		this.add(list, BorderLayout.CENTER);
	}

	public void addFilter(String filter) {
		if(!items.contains(filter)) items.add(list.getModel().getSize(), filter);
		((MapPanel) PanelManager.getInstance().getPanel("MapPanel")).updateButtons(Arrays.copyOf(items.toArray(), items.size(), String[].class));
	}

	public void removeFilter() {
		int[] selectedIndices = list.getSelectedIndices();

		for(int i = selectedIndices.length - 1; i >= 0; i--) {
			items.remove(selectedIndices[i]);
		}
	}

	public String[] getFilterList() {
		return Arrays.copyOf(items.toArray(), items.size(), String[].class);
	}

	public void setFilterList(String[] filterList) {
		for (String filter: filterList) addFilter(filter);
	}
}
