package gui;

import data.Data;
import data.dto.CGIDTO;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class CountryListPanel extends BasicPanel {
   private String[] countries;
   private CGIDTO[] cgidtos;
   public CountryListPanel(String[] data, CGIDTO[] cgidtos) {
      countries = data;
      if(cgidtos == null) {
         ArrayList<CGIDTO> temp = new ArrayList<>();
         for(String country: countries) temp.add(Data.getInstance().getCountryData(country));
         this.cgidtos = temp.toArray(new CGIDTO[0]);
      } else this.cgidtos = cgidtos;

      this.setLayout(new BorderLayout());
      JList temp = new JList(data);
      temp.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            ((CountryInfoPanel) PanelManager.getInstance().getPanel("CountryInfoPanel")).drawInfo(data[temp.locationToIndex(e.getPoint())]);
         }
      });
      this.add(new JScrollPane(temp), BorderLayout.CENTER);
   }

   public String[] getCountries() {
      return countries;
   }

   public CGIDTO[] getCountriesDTO() {
      return cgidtos;
   }
}