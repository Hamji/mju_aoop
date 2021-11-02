import java.awt.EventQueue;

import gui.MainFrame;

public class FreshmanProject {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
			
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
		});
	}

}
