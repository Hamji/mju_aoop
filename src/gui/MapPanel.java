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
	private HashMap<String, Set> filterInfo;

	public MapPanel(Image img) {
	      this.img = img;
	      this.setLayout(new BorderLayout(5,5));
	      setSize(new Dimension(img.getWidth(null) , img.getHeight(null)));
	      setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));
	      setLayout(null);
		  filterInfo = new HashMap<>();
		  continents[COUNTRY.AFRICA.ordinal()] = new Continent(); continents[COUNTRY.AFRICA.ordinal()].setCountry(new String[] {"아프리카"});
		  continents[COUNTRY.ASIA.ordinal()] = new Continent(); continents[COUNTRY.ASIA.ordinal()].setCountry(new String[] {"아시아"});
		  continents[COUNTRY.AMERICA.ordinal()] = new Continent(); continents[COUNTRY.AMERICA.ordinal()].setCountry(new String[] {"아메리카", "미국", "중미", "북미"});
		  continents[COUNTRY.EUROPE.ordinal()] = new Continent(); continents[COUNTRY.EUROPE.ordinal()].setCountry(new String[] {"유럽"});
		  continents[COUNTRY.SOUTH.ordinal()] = new Continent(); continents[COUNTRY.SOUTH.ordinal()].setCountry(new String[] {"남아메리카", "남미"});

		  continents[COUNTRY.AFRICA.ordinal()].setBounds(550, 350, 100, 100);
		  continents[COUNTRY.ASIA.ordinal()].setBounds(800, 200, 100, 100);
		  continents[COUNTRY.AMERICA.ordinal()].setBounds(200, 200, 100, 100);
		  continents[COUNTRY.SOUTH.ordinal()].setBounds(270, 480, 100, 100);
		  continents[COUNTRY.EUROPE.ordinal()].setBounds(500, 150, 100, 100);

  		  for (int i = 0; i < continents.length; i++) {
			  int finalI = i;
			  continents[i].addActionListener(e -> new ResultFrame(continents[finalI].getCountries(), continents[finalI].getCountriesDTO()));
			  this.add(continents[i]);
		  }
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, 1000, 700, null);

	}


	public void updateButtons(String[] filters) {
		// 초기화
		for(Continent continent: continents) continent.reset();
		String[] weathers = ((DetailFilterPanel) PanelManager.getInstance().getPanel("DetailFilterPanel")).getWeather();
		String[] faithes = ((DetailFilterPanel) PanelManager.getInstance().getPanel("DetailFilterPanel")).getFaith();


		// 국가 먼저 처리
		Set<String> country = new HashSet<>();
		for(String filter: filters) {
			for(Continent continent: continents) {
				if(continent.hasName(filter)) {
					continent.clicked();
					if(filterInfo.containsKey(filter)) country.addAll(filterInfo.get(filter));
					else {
						Set<String> cgidtoSet = new HashSet<>();
						for(CGIDTO dto: Data.getInstance().getDataLocation(filter)) cgidtoSet.add(dto.getCountry());
						country.addAll(cgidtoSet);
						filterInfo.put(filter, country);
					}
				}
			}
		}

		boolean isFirst = true;
		Set<String> filtered_country = new HashSet<>();
		for(String filter: filters) {
			boolean isContinent = false;
			Set<String> current_filter = new HashSet<>();

			for(Continent continent: continents) if(continent.hasName(filter)) isContinent = true;

			if(isContinent) continue;
			if (filterInfo.containsKey(filter)) { // 필터 값을 가지고 있다면
				current_filter.addAll(filterInfo.get(filter));
			} else {
				ArrayList<CGIDTO> dtos = null;

				for(String weather: weathers) {
					if (weather.equals(filter)) {
						ArrayList<String> detailFilter = new ArrayList<>();
						if (filter.equals("추움"))  {
							detailFilter.add("추움"); detailFilter.add("한랭"); detailFilter.add("북극"); detailFilter.add("저온"); detailFilter.add("추운");
						} else if(filter.equals("더움")) {
							detailFilter.add("온화"); detailFilter.add("열대"); detailFilter.add("고온");
						} else if(filter.equals("건조")) {
							detailFilter.add("건조");
						} else {
							detailFilter.add("습함"); detailFilter.add("다습"); detailFilter.add("습");
						}
						for(String dFilter: detailFilter) {
							if(dtos == null) dtos = Data.getInstance().getDataWeather(dFilter);
							else dtos.addAll(Data.getInstance().getDataWeather(dFilter));
						}
						for(CGIDTO dto: dtos) current_filter.add(dto.getCountry());
					}
				}

				for(String faith: faithes) {
					if(faith.equals(filter)) {
						dtos = Data.getInstance().getDataReligion(filter);
						for(CGIDTO dto: dtos) current_filter.add(dto.getCountry());
					}
				}
				filterInfo.put(filter, current_filter);
			}

			if(isFirst || current_filter.size() == 0) {
				isFirst = false;
				filtered_country.addAll(current_filter);
			} else {
				filtered_country.retainAll(current_filter);
			}
		}

		for(Continent continent: continents) {
			continent.update(filtered_country);
		}

		revalidate();
		repaint();
	}

	public Continent[] getContinents() {
		return continents;
	}
}
