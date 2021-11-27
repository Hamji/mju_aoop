package gui;

import data.Data;
import data.dto.CGIDTO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Continent extends JButton {
    private String[] name;
    private Boolean isClicked = false;
    private Set<String> countries;

    public Continent() {
        super();
        super.setText("0");
        countries = new HashSet<>();
    }

    public void setCountry(String[] name) {
        this.name = name;

        for (String key: name) {
            ArrayList<CGIDTO> lists = Data.getInstance().getDataLocation(key);
            for(CGIDTO cgidto: lists) {
                countries.add(cgidto.getCountry());
            }
        }
    }

    public void reset() {
        isClicked = false;
        countries = new HashSet<>();
        for (String key: name) {
            ArrayList<CGIDTO> lists = Data.getInstance().getDataLocation(key);
            for(CGIDTO cgidto: lists) {
                countries.add(cgidto.getCountry());
            }
        }
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
        countries.retainAll(filter_countries);

        super.setText(String.valueOf(countries.size()));
    }

    public String[] getCountries() {
        return countries.toArray(new String[0]);
    }
}
