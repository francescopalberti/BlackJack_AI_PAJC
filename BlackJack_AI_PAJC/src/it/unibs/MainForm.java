package it.unibs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class MainForm {

	public static void main(String[] args) {
        new MainForm();
    }

    public MainForm() {
        SwingUtilities.invokeLater(new Runnable() {
		    @Override
		    public void run() {
		        JFrame frame = new JFrame("PAJC BlackJack");
		        frame.setSize(1280, 720);
		        frame.setLayout(null);
				frame.setResizable(false);
		       
		        frame.setLocationRelativeTo(null);
		       
		        frame.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
		        ImagePanel bgImagePanel = new ImagePanel("resources\\background.png");
				frame.setContentPane(bgImagePanel);
				bgImagePanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
				JButton btnStartGame = new JButton();      		
				btnStartGame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GameLogic application = new GameLogic(); 
						application.startNewBlackJack(); 
						frame.setVisible(false);
					}
				});
				btnStartGame.setIcon(new ImageIcon("resources\\buttons\\btn_play.png"));
				btnStartGame.setBorderPainted(false);
				btnStartGame.setFocusPainted(false);
				btnStartGame.setContentAreaFilled(false);
				btnStartGame.setBounds(500, 475, 250, 76);
				
				frame.add(btnStartGame);
				
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						int delete = JOptionPane.showConfirmDialog(null, "Vuoi chiudere il frame ? ","Attenzione !",JOptionPane.YES_NO_OPTION);	
						if(delete==0){
							System.exit(0);
						}
					}
				});
				
				frame.setVisible(true);
		            
		    }
		});
	}
}
