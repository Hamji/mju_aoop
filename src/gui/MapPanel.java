package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MapPanel extends BasicPanel {
	private Image img;
	
	private JButton africa = new JButton("0");
	private JButton asia = new JButton("0");
	private JButton america = new JButton("0");
	private JButton europe = new JButton("0");
	private JButton south = new JButton("0");
	
	public MapPanel(Image img) {
		
	      this.img = img;
	      this.setLayout(new BorderLayout(5,5));
	      setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setLayout(null);

	      this.add(africa);
	      this.add(asia);
	      this.add(america);
	      this.add(europe);
	      this.add(south);
	}
	
	public void paint(Graphics g) {
//	      africa.setBounds(550, 350, 100, 100);
//	      asia.setBounds(800, 200, 100, 100);
//	      america.setBounds(200, 200, 100, 100);
//	      south.setBounds(270, 480, 100, 100);
//	      europe.setBounds(500, 150, 100, 100);
//	      
	      g.drawImage(img, 0, 0, 860, 500, null);
	      
	}
}
