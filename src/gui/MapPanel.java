package gui;

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
	private JButton[] countries = new JButton[5];
	
	public MapPanel(Image img) {
	      this.img = img;
	      this.setLayout(new BorderLayout(5,5));
	      setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setLayout(null);

		for (int i = 0; i < countries.length; i++) {
			countries[i] = new JButton("0");
			this.add(countries[i]);
		}

	}
	
	public void paint(Graphics g) {
	      countries[COUNTRY.AFRICA.ordinal()].setBounds(550, 350, 100, 100);
		  countries[COUNTRY.ASIA.ordinal()].setBounds(800, 200, 100, 100);
		  countries[COUNTRY.AMERICA.ordinal()].setBounds(200, 200, 100, 100);
		  countries[COUNTRY.SOUTH.ordinal()].setBounds(270, 480, 100, 100);
		  countries[COUNTRY.EUROPE.ordinal()].setBounds(500, 150, 100, 100);
	      g.drawImage(img, 0, 0, 860, 500, null);
	}

	public void updateButtons(String[] filters) {
		// 필터들에 따라서 button update
		System.out.println(Arrays.toString(filters));
	}
}
