package gui;

import java.util.HashMap;

/**
 * 똥 싸겠습니다.
 * 죄송합니다.
 */

/**
 * 
 * @author geonkim
 * 같이 싸시죠...
 */
public class PanelManager {
    private static PanelManager panelManager;
    private static HashMap<String, BasicPanel> panels = new HashMap<>();

    private PanelManager() {}
    public static PanelManager getInstance() {
        if(panelManager == null) {
            panelManager = new PanelManager();
        }
        return panelManager;
    }
    public void addPanel(BasicPanel panel) {
        panels.put(panel.getClass().getSimpleName(), panel);
    }

    public BasicPanel getPanel(String panel) {
        return panels.get(panel);
    }

}
