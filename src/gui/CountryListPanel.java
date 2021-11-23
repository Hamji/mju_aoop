package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class CountryListPanel extends BasicPanel {
   private String[] countries;
   public CountryListPanel(String[] data) {
      countries = data;
      this.setLayout(new BorderLayout());
      JList temp = new JList(data);
      temp.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            int idx = e.getClickCount();
            ((CountryInfoPanel) PanelManager.getInstance().getPanel("CountryInfoPanel")).drawInfo(data[idx - 1]);
         }
      });
      this.add(new JScrollPane(temp), BorderLayout.CENTER);
   }

   public String[] getCountries() {
      return countries;
   }
}