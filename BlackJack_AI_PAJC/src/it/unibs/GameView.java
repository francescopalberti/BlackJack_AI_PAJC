package it.unibs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


/**
 * Class that manage the GUI of the game
 *
 * @author Francesco Palberti, Enrico Zaninelli
 */

public class GameView extends JFrame {
	private GameController controller; // client GUI controller
	private JFrame frame; // Creating an instance of the MainFrame class.

	//the panels for the player's dealer's cards and ai' cards
	private CardGroupPanel dealerCardPanel = null;
	private CardGroupPanel playerCardPanel = null;  
	private CardGroupPanel aiPlayerCardPanel = null;
	
	// Creating the GUI elements in the window builder
	private JLabel lblInitBalanceValue;
	private JLabel lblInitialBalance;

	private JButton btnEndGame;
	private JLabel lblBetAmount;
	private JButton btnBet;
	private JLabel lblPlayerCrntBalnc;
	private JLabel lblAiCrntBalnc;
	private JLabel lblPlyBalanceAmount;
	private JLabel lblAiBalanceAmount;
	private JLabel lblDealer;
	private JLabel lblPlayer;
	private JButton btnHit;
	private JButton btnStand;
	private JLabel lblInfo;
	private JLabel lblInfoAi;
	private JButton btnContinue;
	
	private JButton btn1;
	private JButton btn5;
	private JButton btn10;
	private JButton btn25;
	private JButton btn100;
	
	int betAmount;
	/**
     * Constructor for GUI object. Set up GUI
     *
     * @param aController. Client controller
     */
	
    public GameView(GameController gameController) {
    	frame = new GameFrame();
    	frame.setVisible(true);
    	this.controller = gameController;
    	initGuiObjects();
    }	 // end Client constructor
    
    // This function runs when the program starts or when the game ends. It displays the initial GUI objects to enter an initial balance and start/stop a game
 	public void initGuiObjects() {
 		
 		lblInitBalanceValue = new JLabel(String.format("$%d", Constraint.START_MONEY)); // Text field to store initial balance
 		lblInitBalanceValue.setBounds(131, 280, 89, 28);
 		lblInitBalanceValue.setForeground(Color.WHITE);
 		frame.getContentPane().add(lblInitBalanceValue);
 		
 		lblInitialBalance = new JLabel("Initial Balance:"); // Initial balance label
 		lblInitialBalance.setFont(new Font("Arial", Font.BOLD, 13));
 		lblInitialBalance.setForeground(Color.GRAY);
 		lblInitialBalance.setBounds(30, 286, 100, 16);
 		frame.getContentPane().add(lblInitialBalance);
 		
 		lblPlayerCrntBalnc = new JLabel("Your Current Balance"); // Current balance label
 		lblPlyBalanceAmount = new JLabel(); // Balance label, shows current balance
 		
 		lblAiCrntBalnc = new JLabel("AI Current Balance"); // Current balance label
 		lblAiBalanceAmount = new JLabel(); // Balance label, shows current balance
 		
 		
 		lblDealer = new JLabel("Dealer"); // Dealer label
 		lblPlayer = new JLabel("Player"); // Player label
 		
 		btnBet = new JButton("Bet"); // Deal button
 		btnBet.setIcon(new ImageIcon("resources\\buttons\\btn_bet.png"));
 		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getBet();
			}
		});
 		btnBet.setBounds(1100, 625, 150, 35);
 		frame.getContentPane().add(btnBet);
 		
 		btnHit = new JButton("Hit"); // Hit button
 		btnHit.setIcon(new ImageIcon("resources\\buttons\\btn_hit.png"));
 		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.playerHit();; // When pressed, hit
			}
		});
 		btnHit.setBounds(1050, 570, 160, 35);
 		frame.getContentPane().add(btnHit);
 		
 		initChip();
 		
			
 		btnStand = new JButton("Stand"); // Stand button
 		btnStand.setIcon(new ImageIcon("resources\\buttons\\btn_stand.png"));
 		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.playerStay(); // When pressed, stand
			}
		});
 		btnStand.setBounds(1050, 625, 160, 35);
 		frame.getContentPane().add(btnStand);
		
 		btnContinue = new JButton("Continue"); // When the final outcome is reached, press this to accept and continue the game
 		btnContinue.setEnabled(false);
		btnContinue.setVisible(false);
		btnContinue.setBounds(290, 444, 320, 35);
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptOutcome();
			}
		});	
 		
		
		frame.getContentPane().add(btnContinue);
 		
 		btnEndGame = new JButton("End Game"); // End game button, this removes all GUI objects and starts from scratch
 		btnEndGame.setEnabled(false);
 		btnEndGame.setVisible(false);
 		btnEndGame.setBounds(221, 610, 99, 50);
 		btnEndGame.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				frame.getContentPane().removeAll(); // Remove all objects from screen
 				frame.repaint(); // Repaint to show update
 				controller.endGame(); 
 			}
 		});
 		frame.getContentPane().add(btnEndGame);
 		
 		lblInfo = new JLabel("Welcome!"); // Deal info label
		lblInfo.setBackground(Color.ORANGE);
		lblInfo.setOpaque(false);
		lblInfo.setForeground(Color.ORANGE);
		lblInfo.setFont(new Font("Arial", Font.BOLD, 16));
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setBounds(50, 620, 320, 28);
		frame.getContentPane().add(lblInfo);
		
		lblInfoAi = new JLabel("Welcome! I'm AI"); // Deal info label
		lblInfoAi.setBackground(Color.CYAN);
		lblInfoAi.setOpaque(false);
		lblInfoAi.setForeground(Color.CYAN);
		lblInfoAi.setFont(new Font("Arial", Font.BOLD, 16));
		lblInfoAi.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoAi.setBounds(50, 650, 320, 28);
		frame.getContentPane().add(lblInfoAi);
		
		lblPlyBalanceAmount.setText(String.format("$%d", Constraint.START_MONEY));
		lblPlyBalanceAmount.setForeground(Color.ORANGE);
		lblPlyBalanceAmount.setFont(new Font("Arial", Font.BOLD, 40));
		lblPlyBalanceAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlyBalanceAmount.setBounds(400, 632, 272, 50);
		frame.getContentPane().add(lblPlyBalanceAmount);
		
		lblAiBalanceAmount.setText(String.format("$%d", Constraint.START_MONEY));
		lblAiBalanceAmount.setForeground(Color.ORANGE);
		lblAiBalanceAmount.setFont(new Font("Arial", Font.BOLD, 40));
		lblAiBalanceAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblAiBalanceAmount.setBounds(580, 632, 272, 50);
		frame.getContentPane().add(lblAiBalanceAmount);
		
 		lblBetAmount = new JLabel(String.format("$%d", 0)); // Bet amount info label
		lblBetAmount.setFont(new Font("Arial", Font.BOLD, 40));
		lblBetAmount.setForeground(Color.WHITE);
		lblBetAmount.setBounds(30, 320, 100, 60);
		frame.getContentPane().add(lblBetAmount);
 		
 		lblPlayerCrntBalnc.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerCrntBalnc.setFont(new Font("Arial", Font.BOLD, 16));
		lblPlayerCrntBalnc.setForeground(Color.WHITE);
		lblPlayerCrntBalnc.setBounds(400, 615, 272, 22);
		frame.getContentPane().add(lblPlayerCrntBalnc);
		
		lblAiCrntBalnc.setHorizontalAlignment(SwingConstants.CENTER);
		lblAiCrntBalnc.setFont(new Font("Arial", Font.BOLD, 16));
		lblAiCrntBalnc.setForeground(Color.WHITE);
		lblAiCrntBalnc.setBounds(580, 615, 272, 22);
		frame.getContentPane().add(lblAiCrntBalnc);
 	}
 	
 	
 	private void initChip() {
 		
 		
 		btn1 = new JButton("1"); // Stand button
 		btn1.setIcon(new ImageIcon("resources\\chip\\1_chip.png"));
 		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshCurrentBet(1);
			}
		});
 		btn1.setBounds(1150, 515, 75, 60);
 		btn1.setBorderPainted(false);
		btn1.setFocusPainted(false);
		btn1.setContentAreaFilled(false);
 		frame.getContentPane().add(btn1);
 		
 		btn5 = new JButton("5"); // Stand button
 		btn5.setIcon(new ImageIcon("resources\\chip\\5_chip.png"));
 		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshCurrentBet(5);
			}
		});
 		btn5.setBounds(1080, 545, 75, 60);
 		btn5.setBorderPainted(false);
		btn5.setFocusPainted(false);
		btn5.setContentAreaFilled(false);
 		frame.getContentPane().add(btn5);
 		
 		btn10 = new JButton("10"); // Stand button
 		btn10.setIcon(new ImageIcon("resources\\chip\\10_chip.png"));
 		btn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshCurrentBet(10);
			}
		});
 		btn10.setBounds(1010, 575, 75, 60);
 		btn10.setBorderPainted(false);
		btn10.setFocusPainted(false);
		btn10.setContentAreaFilled(false);
 		frame.getContentPane().add(btn10);
 		
 		btn25 = new JButton("25"); // Stand button
 		btn25.setIcon(new ImageIcon("resources\\chip\\25_chip.png"));
 		btn25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshCurrentBet(25);
			}
		});
 		btn25.setBounds(940, 595, 75, 60);
 		btn25.setBorderPainted(false);
 		btn25.setFocusPainted(false);
 		btn25.setContentAreaFilled(false);
 		frame.getContentPane().add(btn25);
 		
 		btn100 = new JButton("100"); // Stand button
 		btn100.setIcon(new ImageIcon("resources\\chip\\100_chip.png"));
 		btn100.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshCurrentBet(100);
			}
		});
 		btn100.setBounds(870, 610, 75, 60);
 		btn100.setBorderPainted(false);
 		btn100.setFocusPainted(false);
 		btn100.setContentAreaFilled(false);
 		frame.getContentPane().add(btn100);
	}

	/**
     * Show the the bet gui view after the getBet message
     *
     */
 	
 	public void showBetGui() { 
 		betAmount=0;
 		
 		btnHit.setEnabled(false);
 		btnHit.setVisible(false);
 		
 		btnStand.setEnabled(false);
 		btnStand.setVisible(false);
 		
 		btnBet.setEnabled(true);
 		btnBet.setVisible(true);
		
		lblInfo.setText("Please enter a bet and click Deal");
		
		
		lblInfoAi.setText("I'm calculating the bet");
		

		btnBet.setEnabled(true);
		
		
		frame.repaint();
 	}
 	
 	/**
     * Gets the bet from the player
     *
     */
 	
 	public void getBet() {
 		controller.checkBet(betAmount);
	}
 	
 	public void errorMsgBet() {
 		lblInfo.setText("Error: Bet higher than balance!"); // Give an error
 		betAmount=0;
 		refreshCurrentBet(0);
		return;
 	}
 	
 	public void refreshCurrentAmount(int playerBalance, int aiBalance) {
 		lblAiBalanceAmount.setText(String.format("$%d", aiBalance)); 		
 		lblPlyBalanceAmount.setText(String.format("$%d", playerBalance));
 		frame.repaint();
 	}
 	
 	public void refreshCurrentBet(int chipImport) {
 		betAmount+=chipImport;
 		lblBetAmount.setText(String.format("$%d", betAmount)); 		
 		frame.repaint();
 	}
 	
 	/**
     * After all the bet have been placed show the players cards
     *
     */
 	
 	public void allBetPlaced() {
 		btnBet.setEnabled(false);
 		btnBet.setVisible(false);
 		removeChip();
		
		lblDealer.setForeground(Color.WHITE);
		lblDealer.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblDealer.setBounds(415, 158, 82, 28);
		frame.getContentPane().add(lblDealer);

		
		lblPlayer.setForeground(Color.WHITE);
		lblPlayer.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblPlayer.setBounds(415, 266, 82, 28);
		frame.getContentPane().add(lblPlayer);

		lblInfo.setText("Cards dealt"); 
		frame.repaint(); 
		
		lblInfoAi.setText("Cards dealt"); 
		frame.repaint(); 
		
	}
 	
 	private void removeChip() {
		btn1.setVisible(false);
		btn5.setVisible(false);
		btn10.setVisible(false);
		btn25.setVisible(false);
		btn100.setVisible(false);
	}

	/**
     * Displays dealer and player cards as images
     *
     */

 	public void updateCardPanels(CardGroup dealerCards, CardGroup playerCards, CardGroup aiPlayerCards) 
 	{
		if (dealerCardPanel != null) { // If they're already added, remove them
			frame.getContentPane().remove(dealerCardPanel);
			frame.getContentPane().remove(playerCardPanel);
			frame.getContentPane().remove(aiPlayerCardPanel);
		}
		// Create and display two panels
		if(dealerCards!=null) {
			dealerCardPanel = new CardGroupPanel(dealerCards, 580 - (dealerCards.getCount() * 40), 50, 70, 104, 10);
			frame.getContentPane().add(dealerCardPanel);
		}
		if(playerCards!=null) {
			playerCardPanel = new CardGroupPanel(playerCards, 350 - (playerCards.getCount() * 40), 300, 70, 104, 10);
			playerCardPanel.showScoreLbl();
			frame.getContentPane().add(playerCardPanel);
		}
		
		if(aiPlayerCards!=null) {
			aiPlayerCardPanel = new CardGroupPanel(aiPlayerCards, 790 - (aiPlayerCards.getCount() * 40), 300, 70, 104, 10);
			aiPlayerCardPanel.showScoreLbl();
			frame.getContentPane().add(aiPlayerCardPanel);
		}
		
		
		frame.repaint();
	}
 	
 	/**
     * 
     *
     */
    
 	public void isPlayerTurn() { 
 		btnStand.setVisible(true);
		btnStand.setEnabled(true);
 		btnHit.setEnabled(true);
 		btnHit.setVisible(true);
 		lblInfo.setText("Is your turn!"); 
		frame.repaint();
	}
 	
 	/**
     * 
     *
     */
 	
 	public void playerBusted() { 
 		btnHit.setEnabled(false);
 		btnStand.setEnabled(false);
 		lblInfo.setBackground(Color.CYAN);
 		lblInfo.setText("You BUSTED! Please Wait other players..."); 
 		lblInfoAi.setText("Is my turn, I'm running the probability...");
		frame.repaint();

	}
 	
 	public void aiHitACard() {
 		lblInfoAi.setText("I hit a card");
		frame.repaint();
	}
 	
 	public void aiStand() {
 		lblInfoAi.setText("I Stand!");
		frame.repaint();
	}
 	
 	public void playerStand() { 
 		btnHit.setEnabled(false);
 		btnStand.setEnabled(false);
 		lblInfo.setBackground(Color.CYAN);
 		lblInfo.setText("You Stand! Please Wait other players..."); 
 		lblInfoAi.setText("Is my turn, I'm running the probability...");
		frame.repaint();
		
	}
 	
 	public void dealerFinishedTurn() {
 		dealerCardPanel.showScoreLbl();
 		frame.repaint();
 	}
 	

 	public void playerTie() {
 		lblInfo.setText("You Tie!"); 
		outcomeHappened();
 		frame.repaint();
	}

	public void playerWin() {
		lblInfo.setText("You Won!"); 
		outcomeHappened();
		frame.repaint();
	}
	
	

	public void playerLose() {
		lblInfo.setText("You Lose!"); 
		outcomeHappened();
		frame.repaint();
	}	
	
	public void playerBlackJack() {
		lblInfo.setText("You Have a BlackJack!!"); 
		outcomeHappened();
		frame.repaint();
	}
 	
	public void updateBalance(int balance) {
		lblPlyBalanceAmount.setText(String.format("$%d", balance));
	}
	public void outcomeHappened() { //If something's happened, this round is over. Show the results of round and Continue button
		
		btnHit.setEnabled(false);
		btnStand.setEnabled(false);

		// Fancy effects, highlight info label orange and delay the display of Continue button by 500ms
		lblInfo.setOpaque(true);
		lblInfo.setForeground(Color.RED);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				btnEndGame.setEnabled(true);
		 		btnEndGame.setVisible(true);
				btnContinue.setEnabled(true);
				btnContinue.setVisible(true);
				btnContinue.requestFocus();
			}
		}, 500);

	}

	public void acceptOutcome() { // When outcome is reached

		lblInfo.setOpaque(false);
		lblInfo.setForeground(Color.ORANGE);
		
		// Remove deal objects
		
		frame.getContentPane().removeAll(); // Remove all objects from screen, restart the game

	}
	
	public void gameIsFinished() {
		
			int choice = JOptionPane.showOptionDialog(null, "You have run out of funds. Press Yes to add $100, or No to end the current game.", "Out of funds", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (choice == JOptionPane.YES_OPTION) {
				lblPlyBalanceAmount.setText(String.format("$%d", Constraint.START_MONEY));
			} else {
				frame.getContentPane().removeAll();
				frame.repaint();
				return;
			}
		
	}

	
	public static int heightFromWidth(int width) { // 500x726 original size, helper function to get height proportional to width
		return (int) (1f * width * (380f / 255f));
	}
	

	
	
	
}