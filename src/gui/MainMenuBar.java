package gui;

import data.CSV;
import data.dto.CGIDTO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class MainMenuBar extends JMenuBar {
    private String filePath = null;

    public MainMenuBar() {
        JMenu jMenu = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(e -> saveFilter());

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(e -> loadFilter());

        JMenuItem saveAs = new JMenuItem("Save as ...");
        saveAs.addActionListener(e -> saveAsFilter());

        JMenuItem export = new JMenuItem("Export");
        export.addActionListener(e -> export());

        jMenu.add(load);
        jMenu.add(save);
        jMenu.add(saveAs);
        jMenu.add(export);

        add(jMenu);
    }

    private void saveFilter() {
        if (filePath == null) saveAsFilter();
        try{
            String[] filterList = ((ApplyedListPanel) PanelManager.getInstance().getPanel("ApplyedListPanel")).getFilterList();
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutput oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(filterList);
            oos.close();
        } catch (Exception e) {

        }
    }

    private void export() {
        Continent[] continents = ((MapPanel) PanelManager.getInstance().getPanel("MapPanel")).getContinents();
        ArrayList<CGIDTO> cgidtoArrayList = new ArrayList<>();
        for(Continent continent: continents) Collections.addAll(cgidtoArrayList, continent.getCountriesDTO());

        try {
            CSV.getInstance().CGIDataToCSV(cgidtoArrayList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void loadFilter() {
        JFileChooser fc = new JFileChooser();
        // 바탕화면
        fc.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());

        // 기본 filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("filter files (*.filter)", "filter");
        fc.setFileFilter(filter);

        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            filePath = file.getAbsolutePath();


            try{
                FileInputStream fileOutputStream = new FileInputStream(filePath);
                ObjectInput oos = new ObjectInputStream(fileOutputStream);
                String[] filterList = (String[])oos.readObject();
                oos.close();
                ((ApplyedListPanel) PanelManager.getInstance().getPanel("ApplyedListPanel")).setFilterList(filterList);
            } catch (Exception e) {

            }
        } else {
            System.out.println("[Error] MainMenuBar load");
        }
    }

    private void saveAsFilter() {
        JFileChooser fc = new JFileChooser();
        // 바탕화면
        fc.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());

        // 기본 filter
        fc.setSelectedFile(new File("save.filter"));

        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            filePath = file.getAbsolutePath();

            String[] filterList = ((ApplyedListPanel) PanelManager.getInstance().getPanel("ApplyedListPanel")).getFilterList();
            try{
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                ObjectOutput oos = new ObjectOutputStream(fileOutputStream);
                oos.writeObject(filterList);
                oos.close();
            } catch (Exception e) {

            }
        } else {
            System.out.println("[Error] MainMenuBar saveAs...");
        }
    }
}
