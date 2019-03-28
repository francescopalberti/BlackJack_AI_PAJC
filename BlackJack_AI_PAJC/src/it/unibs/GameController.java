package it.unibs;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


/**
 * GuiController.java
 *
 * The GuiController class for the Blackjack Game.
 * Contains GuiController.
 * 
 * DA AGGIUNGERE, CARTA COPERTA DEALER E FASI FINALI + GRAFICA 3 PLAYER
 *
 * @author Francesco Palberti, Enrico Zaninelli
 */

public class GameController {
	private GameView gui;
	private Player human;
    private AIPlayer ai1;
    private Dealer dealer;
    private Deck deck;

    private static Random rnd = new Random();

    private Vector<Player> players;
	private int previousBet=10;
	private int previousOutcome;
	private int level;
	
    public GameController() {
    	gui = new GameView(this);
    }
    
    public void startBlackJack() {
    	dealer = new Dealer();
    	human = new Player("You", Constraint.START_MONEY);
        ai1 = new AIPlayer("Computer", Constraint.EASY, Constraint.START_MONEY);
    	deck = new Deck();
    	players = new Vector<Player>();
    	players.add(human);
        players.add(ai1);
    	playBlackJack();
   }
    
    
    
    private void playBlackJack() {
    	askBets(); 
	}

	/**
     * Asks for bets from players
     */

    private void askBets() {
    	gui.showBetGui();
    }
    
    public void checkBet(int betAmount) {
		if (betAmount > human.getBalance()) { // If bet is higher than balance
			gui.errorMsgBet();
		} else {
			calculateAndSetBet(betAmount);
		}
			 
    }
    
    
    
    /**
     * Gets the betting amount from player. If it is human,
     * asking for an amount. For computers, it is calculated internally. The
     * bet is automatically subtracted from the players total money.
     * @param count Current card count
     * @return amount to bet
     */
    public void calculateAndSetBet(int betAmount) {
    human.setBet(betAmount);
	int normalBet = previousBet;
	    
    if (level == Constraint.EASY) {
        int rand = rnd.nextInt(3);                              
        if (previousOutcome == Constraint.LOSS)  normalBet -= Constraint.MIN_BET * rand;                             
        else if (previousOutcome == Constraint.PUSH);
        else if (previousOutcome == Constraint.WIN) normalBet += Constraint.MIN_BET * rand;                             
    }
    else if (level == Constraint.HARD) {
        int optimal = Constraint.MIN_BET*(betAmount);
        normalBet = optimal;
    }
    
    if (normalBet > ai1.getBalance() / 20) normalBet = ai1.getBalance()/20;                   
    if (normalBet < Constraint.MIN_BET) normalBet = Constraint.MIN_BET;
    
    previousBet = normalBet;
	ai1.setBet(normalBet);
	
	gui.refreshCurrentAmount(human.getBalance()-betAmount,ai1.getBalance()-normalBet);
	gui.allBetPlaced();
	
	deal();
    }
    
    /*
     * Deals out cards to players and dealer
     */

    private void deal() {
        dealerCards(dealer);
        dealCards(human);
        dealCards(ai1);
        refreshGUICard();
        gui.isPlayerTurn();
    }  
    
    
    /**
     * Deals cards to the dealer
     * @param dealer The dealer to deal cards to
     */
    
    private void dealerCards(Dealer dealer){
        Card c1 = deck.dealCard();
        Card c2 = deck.dealCard();
        dealer.startHand(c1, c2);    
        dealer.coverFirstCard();
    }
    
    /**
     * Deals cards to the player
     * @param player The player to deal cards to
     */

    private void dealCards(Player player){
    	Card c1 = deck.dealCard();
        Card c2 = deck.dealCard();
        player.startHand(c1, c2);
    }
        
    /**
     * Gives a card to the player
     * @param player 
     */

    private void giveCard(Player player){
        player.CardHit(deck.dealCard());
    }
    
    
    public void playerHit() {
		giveCard(this.human);
		refreshGUICard();
		if(human.CheckBust()) {			//if player busted
			doAITurns();
			gui.playerBusted();
		}
	}
	
	private void refreshGUICard() {
		gui.updateCardPanels(dealer.getHandCards(), human.getHandCards(), ai1.getHandCards());
		
	}

	public void playerStay() {
		doAITurns();
		gui.playerStand();
	}
	
	/**
     * Asks for AI to do their turns
     */

    public void doAITurns() {
    	new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
			int aiAction;
            do {
            	aiAction = ai1.askComputerAction(dealer.returnFirstHandCard());
            } while (parseAIActions(ai1, aiAction) == true);
            refreshGUICard();
            doDealerTurn();
			}
		}, 5000);
            
    }
	

    /**
     * Processes the AI's actions
     * @param ai The AI to parse actions for
     * @param action The action to do
     * @return boolean representing whether the AI will continue to play
     */

    private boolean parseAIActions(Player ai, int action) {
            switch(action) {
                    case 0: 
                    	gui.aiStand();
                    	return false;
                    case 1: 
                    	giveCard(ai); 
                    	gui.aiHitACard();
                    	return true;
                    default: return false;
            }
    }
    
    /**
     * Does the dealer's turn
     */

    public void doDealerTurn() { 
    	
    	dealer.uncoverFirstCard();
    	if (dealer.getCardTotal() < 16){
			while(dealer.getCardTotal() < 16){
				giveCard(dealer);
				refreshGUICard();
			}
		}
    	gui.dealerFinishedTurn();
    	endGame();
   }

	public void endGame() {
		
		if (human.hasABJ()) {
			human.refreshBalance("BJ");
			gui.playerBlackJack();
		} else {
			if( (dealer.getCardTotal() <= 21) && (human.getCardTotal() <= 21 ) ){

				if (dealer.getCardTotal() > human.getCardTotal()) {
					playerLose();
					}

				if (dealer.getCardTotal() < human.getCardTotal()) {
					playerWon();
				}

				if (dealer.getCardTotal() == human.getCardTotal()) {
					playerTie();
				}				

			}//end if statement when dealer and player are under 21

			if(dealer.CheckBust()){
				
				if(human.CheckBust()){
					playerTie();
				}
				if(human.getCardTotal() <= 21){
					playerWon();
				}
				
			}//end if statement when dealer busted

			if(human.CheckBust() && dealer.getCardTotal() <= 21){
				playerLose();
			}//end if statement when player busted
		}
	}
	
	private void playerLose() {
		human.refreshBalance("LOSE");
		gui.playerLose();
	}
	
	private void playerWon() {
		human.refreshBalance("WIN");
		gui.playerWin();
	}
	
	private void playerTie() {
		gui.playerTie();
	}
	
		
	
	
   }
