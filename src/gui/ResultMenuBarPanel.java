package gui;

import data.CSV;
import data.Data;
import data.dto.CGIDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ResultMenuBarPanel extends BasicPanel {
    public ResultMenuBarPanel() {
        this.setLayout(new GridLayout(1, 1));

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("File");
        JMenuItem jMenuItem = new JMenuItem("Export");
        jMenuItem.addActionListener(e -> {
            String[] countries = ((CountryListPanel) PanelManager.getInstance().getPanel("CountryListPanel")).getCountries();
            ArrayList<CGIDTO> CGIcountries = new ArrayList<>();

            for(String country : countries) {
                Data data = new Data();
                
                // TODO : getCountryData 확인해야함
                CGIcountries.add(data.getCountryData(country, "!!"));
            }
            try {
                CSV.CGIDataToCSV(CGIcountries);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        jMenu.add(jMenuItem);
        jMenuBar.add(jMenu);
        add(jMenuBar);
    }
}
