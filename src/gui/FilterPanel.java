package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

public class FilterPanel extends BasicPanel {
	final private String[] categori = {"지역", "날씨", "종교"};

	
	private List<String> list;
	private List<JButton> btnList;
	private DetailFilterPanel detailFilterPanel;

	private class FilterButtons extends BasicPanel {
		private GridBagLayout gbl_panel;

		public FilterButtons() {
			this.gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			this.setLayout(gbl_panel);

			GridBagConstraints gbcList[] = new GridBagConstraints[list.size()];
			setLayout(gbl_panel);

			// 버튼 삽입
			for (int i = 0; i < list.size(); i++) {
				JButton temp = new JButton(list.get(i));
				temp.setPreferredSize(new Dimension(200, 50));
				btnList.add(temp);
				gbcList[i] = new GridBagConstraints();
				gbcList[i].insets = new Insets(0, 0, 5, 0);
				gbcList[i].gridx = 0;
				gbcList[i].gridy = i;

				temp.addActionListener(e -> detailFilterPanel.drawButton(temp.getText()));
				this.add(temp, gbcList[i]);
			}
		}
	}

	public FilterPanel() {
		this.list = new ArrayList<String>(Arrays.asList(this.categori));
		this.btnList = new ArrayList<JButton>();
		//System.out.print(list);

		for(JButton btn: btnList) this.remove(btn); // 요친구 언제 쓰는거에요?
		detailFilterPanel = new DetailFilterPanel();
		FilterButtons filterButtons = new FilterButtons();

		PanelManager.getInstance().addPanel(detailFilterPanel);
		PanelManager.getInstance().addPanel(filterButtons);

		this.setLayout(new GridLayout(1, 2));
		this.add(filterButtons);
		this.add(detailFilterPanel);
	}

}
