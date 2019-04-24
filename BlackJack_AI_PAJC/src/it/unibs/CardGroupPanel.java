package it.unibs;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * This class extends JPanel, and will create a panel which displays the images of a number of cards, 
 * stored in an instance of Deck, next to each other.
 *  Additionally the card total is displayed using standard Ace subtraction rule.*
 * @author Francesco Palberti, Enrico Zaninelli
 */


public class CardGroupPanel extends JPanel{
	
	private int numCards;
	private static final String coveredCard_URL = "resources\\cardImages\\backCover.png";
	private int total;
	private int height, width, gap;
	CardGroup cardGroup;
	JLabel playerScoreLbl;
	
	public CardGroupPanel() {
		setVisible(false);
		setLayout(null);
		setOpaque(false); // for transparent background
	}

	public void refreshCardGroupPanel(CardGroup cardGroup, int left, int top, int width, int height, int gap) {
		removeCards();
		setVisible(true);
		this.height = height;
		this.width = width;
		this.gap = gap;
		this.cardGroup = cardGroup;
		numCards = cardGroup.getCount();
		setBounds(left, top, 35 + numCards * (width + gap), height);
		initScoreLbl();
		for (int i = 0; i < numCards; i++) add(madeCardPanel(i));
	}
	
	private void removeCards() {
		removeAll();
	}

	private ImagePanel madeCardPanel(int i) {
		
			ImagePanel cardImagePanel;
			if(cardGroup.get(i).isCovered()) {
				cardImagePanel = new ImagePanel(coveredCard_URL);
			} else {
				cardImagePanel = new ImagePanel(cardGroup.get(i).getFileName());
				}
			cardImagePanel.setBounds(35 + i * (width + gap), 0, width, height);
			return cardImagePanel;
	}
	
	public void showScoreLbl() {
		playerScoreLbl.setVisible(true);
	}

	private void initScoreLbl() {
		total = cardGroup.blackJackValue();
		playerScoreLbl = new JLabel((cardGroup.hasABlackJack() ? "BJ" : total) + "");
		playerScoreLbl.setForeground(Color.WHITE);
		playerScoreLbl.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		playerScoreLbl.setVerticalAlignment(SwingConstants.CENTER);
		playerScoreLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		playerScoreLbl.setBounds(0, 0, 30, height);
		playerScoreLbl.setVisible(false);
		add(playerScoreLbl);
	}
}
