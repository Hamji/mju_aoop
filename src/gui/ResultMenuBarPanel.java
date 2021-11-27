package gui;

import data.CSV;
import data.Data;
import data.dto.CGIDTO;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ResultMenuBarPanel extends BasicPanel {
    public ResultMenuBarPanel() {
        this.setLayout(new GridLayout(1, 1));

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("File");
        JMenuItem jMenuItem = new JMenuItem("Export");
        jMenuItem.addActionListener(e -> {
            try {
                CSV.getInstance().CGIDataToCSV(Arrays.asList(((CountryListPanel) PanelManager.getInstance().getPanel("CountryListPanel")).getCountriesDTO()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        jMenu.add(jMenuItem);
        jMenuBar.add(jMenu);
        add(jMenuBar);
    }
}
