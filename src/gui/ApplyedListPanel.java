package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JList;

public class ApplyedListPanel extends BasicPanel {
	private JList list;
	private JLabel label;
	
	public ApplyedListPanel() {
		this.setLayout(new BorderLayout(0, 0));
		list = new JList();
		label = new JLabel("적용된 필터");
		
		this.add(label, BorderLayout.NORTH);
		this.add(list, BorderLayout.CENTER);
	}
}
