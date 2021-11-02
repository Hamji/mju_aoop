package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SearchPanel extends BasicPanel {
	private JButton searchBtn;
	private JTextField searchTxt;
	
	public SearchPanel() {
		this.searchBtn = new JButton("search");
		this.searchTxt = new JTextField();
		this.searchTxt.setColumns(10);
		this.setLayout(new BorderLayout(5, 5));
		this.add(searchBtn, BorderLayout.EAST);
		this.add(searchTxt, BorderLayout.CENTER);
		
		this.searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ResultFrame();
			}
		});
	}
}
