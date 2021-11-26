package gui;

import data.Data;
import data.dto.CGIDTO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Continent extends JButton {
    private String[] name;
    private Boolean isClicked = false;
    private Set<String> init_countries;
    private Set<CGIDTO> init_countries_dto;

    private Set<CGIDTO> countries_dto;
    private Set<String> countries;

    public Continent() {
        super();
        super.setText("0");
        setVisible(true);
        init_countries = new HashSet<>();
        init_countries_dto = new HashSet<>();
        countries = new HashSet<>();
        countries_dto = new HashSet<>();
    }

    public void setCountry(String[] name) {
        this.name = name;
        for (String key: name) {
            ArrayList<CGIDTO> lists = Data.getInstance().getDataLocation(key);
            for(CGIDTO cgidto: lists) {
                init_countries.add(cgidto.getCountry());
                init_countries_dto.add(cgidto);
            }
        }
        countries.addAll(init_countries);
        countries_dto.addAll(init_countries_dto);
    }

    public void reset() {
        isClicked = false;
        countries = new HashSet<>();
        countries.addAll(init_countries);
        super.setText("0");
    }

    public void clicked() {
        isClicked = true;
    }

    public Boolean hasName(String filter) {
        for(String country: name) {
            if(country.equals(filter)) return true;
        }
        return false;
    }

    public void update(Set<String> filter_countries) {
        if(!isClicked) return;
        System.out.println("변경 전: " + countries);
        System.out.println("필터 : " + filter_countries);

        countries.retainAll(filter_countries);
        System.out.println("변경 후 : " + countries);
        super.setText(String.valueOf(countries.size()));
    }

    public String[] getCountries() {
        return countries.toArray(new String[0]);
    }
    public CGIDTO[] getCountriesDTO() {
        return countries_dto.toArray(new CGIDTO[0]);
    }
}
