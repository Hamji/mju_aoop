package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DetailFilterPanel extends BasicPanel {
    final private String[] region = {"아시아", "아프리카", "아메리카", "유럽"};
    final private String[] weather = {"건조", "습함", "더움", "추움"};
    final private String[] faith = {"이슬람", "가톨릭", "개신교", "힌두교", "무교"};
    private ArrayList<JButton> jButtons = new ArrayList<>();

    public DetailFilterPanel() {
        setLayout(new GridLayout(10, 1));
        setVisible(true);
    }
    
    public void drawButton(String categori) {
        String[] target = null;
        switch(categori) {
            case "지역": target = region; break;
            case "날씨": target = weather; break;
            case "종교": target = faith; break;
            default: System.out.println("[DetailFilterPanel][drawButton] unExpected Categori: " + categori); return;
        }
        
        // 버튼이 안지워진다...
        for(JButton btn: jButtons) this.remove(btn);
        for(String text: target) {
            JButton btn = new JButton(text);
            btn.setPreferredSize(new Dimension(200, 50));
            btn.setVisible(true);
            btn.addActionListener(e -> ((ApplyedListPanel)PanelManager.getInstance().getPanel("ApplyedListPanel")).addFilter(text));
            this.add(btn);
            jButtons.add(btn);

        }
        revalidate();
    }

}
