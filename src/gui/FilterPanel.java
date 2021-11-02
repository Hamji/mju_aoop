package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;

public class FilterPanel extends BasicPanel {
	final private String[] categori = {"지역", "날씨", "문화", "종교", "코로나"};
	final private String[] region = {"아시아", "아프리카", "아메리카", "유럽"};
	final private String[] weather = {"건조", "습함", "더움", "추움"};
	final private String[] faith = {"이슬람", "가톨릭", "개신교", "흰두교", "무교"};
	
	private List<String> list;
	private List<JButton> btnList;
	
	private GridBagLayout gbl_panel;
	
	public FilterPanel() {
		this.list = new ArrayList<String>(Arrays.asList(this.categori));
		this.btnList = new ArrayList<JButton>();
		//System.out.print(list);
		
		this.gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.setLayout(gbl_panel);
		drawBtn();
	
	}
	
	public void drawBtn() {
		// 버튼 삭제
		for(JButton btn : this.btnList) 
			this.remove(btn);
		
		GridBagConstraints gbcList[] = new GridBagConstraints[list.size()];
		
		// 버튼 삽입
		for (int i = 0; i < this.list.size(); i++) {
			JButton temp = new JButton(this.list.get(i));
			temp.setPreferredSize(new Dimension(200, 50));
			this.btnList.add(temp);
			gbcList[i] = new GridBagConstraints();
			gbcList[i].insets = new Insets(0, 0, 5, 0);
			gbcList[i].gridx = 0;
			gbcList[i].gridy = i;

			this.add(temp, gbcList[i]);
		}
	}
}
