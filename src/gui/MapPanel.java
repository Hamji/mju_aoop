package gui;

import data.Data;
import data.dto.CGIDTO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.*;

public class MapPanel extends BasicPanel {
	private Image img;
	private enum COUNTRY{AFRICA, ASIA, AMERICA, EUROPE, SOUTH};
	private Continent[] continents = new Continent[5];
	private Map<COUNTRY, ArrayList<CGIDTO>> continents_list = new HashMap<>();

	public MapPanel(Image img) {
	      this.img = img;
	      this.setLayout(new BorderLayout(5,5));
	      setSize(new Dimension(img.getWidth(null) , img.getHeight(null)));
	      setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setLayout(null);

		  continents[COUNTRY.AFRICA.ordinal()] = new Continent(); continents[COUNTRY.AFRICA.ordinal()].setCountry(new String[] {"아프리카"});
		  continents[COUNTRY.ASIA.ordinal()] = new Continent(); continents[COUNTRY.ASIA.ordinal()].setCountry(new String[] {"아시아"});
		  continents[COUNTRY.AMERICA.ordinal()] = new Continent(); continents[COUNTRY.AMERICA.ordinal()].setCountry(new String[] {"아메리카"});
		  continents[COUNTRY.EUROPE.ordinal()] = new Continent(); continents[COUNTRY.EUROPE.ordinal()].setCountry(new String[] {"유럽"});
		  continents[COUNTRY.SOUTH.ordinal()] = new Continent(); continents[COUNTRY.SOUTH.ordinal()].setCountry(new String[] {"남아메리카"});

  		  for (int i = 0; i < continents.length; i++) {
			  int finalI = i;
			  continents[i].addActionListener(e -> new ResultFrame(continents[finalI].getCountries()));
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
		// 초기화
		for(Continent continent: continents) continent.reset();
		String[] weathers = ((DetailFilterPanel) PanelManager.getInstance().getPanel("DetailFilterPanel")).getWeather();
		String[] faithes = ((DetailFilterPanel) PanelManager.getInstance().getPanel("DetailFilterPanel")).getFaith();

		boolean isFirst = true;
		Set<String> filtered_country = new HashSet<>();
		for(String filter: filters) {
			Set<String> current_filter = new HashSet<>();
			ArrayList<CGIDTO> dtos;
			for(Continent continent: continents) {
				if(continent.hasName(filter)) {
					continent.clicked(); // Enable
					dtos = Data.getInstance().getDataLocation(filter);
					for(CGIDTO cgidto: dtos) current_filter.add(cgidto.getCountry());

				}
			}

			for(String weather: weathers) {
				if (weather.equals(filter)) {
					dtos = Data.getInstance().getDataWeather(filter);
					for(CGIDTO dto: dtos) current_filter.add(dto.getCountry());
				}
			}

			for(String faith: faithes) {
				if(faith.equals(filter)) {
					dtos = Data.getInstance().getDataReligion(filter);
					for(CGIDTO dto: dtos) current_filter.add(dto.getCountry());
				}
			}

			if(isFirst) {
				isFirst = false;
				filtered_country.addAll(current_filter);
			} else {
				filtered_country.retainAll(current_filter);
			}
		}

		// 필터에 해당되는 나라 이름들
		for(Continent continent: continents) {
			continent.update(filtered_country);
		}

		repaint();
	}

}
