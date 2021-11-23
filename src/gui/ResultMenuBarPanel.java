package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultMenuBarPanel extends BasicPanel {
    public ResultMenuBarPanel() {
        this.setLayout(new GridLayout(1, 1));

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("File");
        JMenuItem jMenuItem = new JMenuItem("Export");
        jMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO CountryInfo, CountryList 패널 완성 후 작업
            }
        });
        jMenu.add(jMenuItem);
        jMenuBar.add(jMenu);
        add(jMenuBar);
    }
}
