package mju_advanced_aoop;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JLabel;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("FreshmanTravel");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
//--------------------left panel
		JPanel leftPanel = new JPanel();
		contentPane.add(leftPanel, BorderLayout.WEST);
		GridBagLayout gbl_leftPanel = new GridBagLayout();
		gbl_leftPanel.columnWidths = new int[] {2, 0};
		gbl_leftPanel.rowHeights = new int[]{380, 380, 0};
		gbl_leftPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_leftPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		leftPanel.setLayout(gbl_leftPanel);
		
		JPanel filterPanel = new JPanel();
		
		GridBagConstraints gbc_filterPanel = new GridBagConstraints();
		gbc_filterPanel.fill = GridBagConstraints.BOTH;
		gbc_filterPanel.insets = new Insets(0, 0, 5, 0);
		gbc_filterPanel.gridx = 0;
		gbc_filterPanel.gridy = 0;
		//gbc_filterPanel.gridheight = 1;
		leftPanel.add(filterPanel, gbc_filterPanel);
		filterPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel filterTitle = new JLabel("\uC801\uC6A9\uB41C \uD544\uD130\uAC12");
		filterPanel.add(filterTitle);
		
		textField = new JTextField();
		filterPanel.add(textField);
		textField.setColumns(10);
		
		JPanel buttonPanel = new JPanel();
		
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.gridx = 0;
		gbc_buttonPanel.gridy = 1;
		//gbc_buttonPanel.gridheight = 2;
		leftPanel.add(buttonPanel, gbc_buttonPanel);

//-------------------------------
		
		JPanel rightPanel = new JPanel();
		contentPane.add(rightPanel, BorderLayout.CENTER);
	}

}
