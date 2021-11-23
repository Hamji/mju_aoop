package gui;

import data.Data;
import data.dto.CGIDTO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MapPanel extends BasicPanel {
	private Image img;
	private enum COUNTRY{AFRICA, ASIA, AMERICA, EUROPE, SOUTH};
	private JButton[] continents = new JButton[5];

	public MapPanel(Image img) {
	      this.img = img;
	      this.setLayout(new BorderLayout(5,5));
	      setSize(new Dimension(img.getWidth(null) , img.getHeight(null)));
	      setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setLayout(null);

		for (int i = 0; i < continents.length; i++) {
			continents[i] = new JButton("0");
			continents[i].addActionListener(e -> new ResultFrame(getCountries()));
			this.add(continents[i]);
		}

	}
	
	public void paint(Graphics g) {
		continents[COUNTRY.AFRICA.ordinal()].setBounds(550, 350, 100, 100);
		continents[COUNTRY.ASIA.ordinal()].setBounds(800, 200, 100, 100);
		continents[COUNTRY.AMERICA.ordinal()].setBounds(200, 200, 100, 100);
		continents[COUNTRY.SOUTH.ordinal()].setBounds(270, 480, 100, 100);
		continents[COUNTRY.EUROPE.ordinal()].setBounds(500, 150, 100, 100);
	    g.drawImage(img, 0, 0, 1000, 700, null);
	}

	public void updateButtons(String[] filters) {
		// 필터들에 따라서 button update
		System.out.println(Arrays.toString(filters));
		ArrayList<CGIDTO> cgidtos = new Data().getFilteredData(filters);
		System.out.println("PRINTING!!!!");
		System.out.println(cgidtos);

		for(CGIDTO cgidto: cgidtos) {
			System.out.println(cgidto.getCountry());
		}
	}

	private String[] getCountries() {
		return new String[0];
	}
}
