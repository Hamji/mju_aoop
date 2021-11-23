package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;

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
        jMenu.add(load);
        jMenu.add(save);
        jMenu.add(saveAs);
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
