package it.unibs;

/**
 * Player objects represent a standard player
 *
 * @author Francesco Palberti, Enrico Zaninelli
 */


public class Player {
	

	private CardGroup handCards;
	private int balance;
	private String name;
    private int bet;
    
    /**
     * Constructor for Player object.
     *
     * @param null
     */

	public Player() {
		handCards = new CardGroup();
	}//end Constructor
	
	public Player(String pName, int startMoney) {
		name = pName;
        balance = startMoney;
        bet = 0;
        handCards = new CardGroup();
	}
	
	
	/**
	 * @return the balance
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * @param string LOSE, WIN or BJ
	 */
	public void refreshBalance(String result) {
		switch (result) {
        case "LOSE":
            balance-=bet;
            break;
        case "WIN":
        	balance+=bet;
            break;
        case "BJ":
        	balance=(int) ((bet*1.5)+balance);
            break;
		}
	}

	/**
	 * @return the bet
	 */
	public int getBet() {
		return bet;
	}

	/**
	 * @param bet the bet to set
	 */
	public void setBet(int bet) {
		this.bet = bet;
	}

	public void startHand (Card card1, Card card2) {
		handCards = new CardGroup();
		handCards.add(card1);
		handCards.add(card2);
	}

	public void newHand() {
		handCards = new CardGroup();
	}

	public int getCardTotal() {
		return handCards.blackJackValue();
	}

	public void CardHit(Card ca){
		handCards.add(ca);
	}
	
	public void doubleDown(Card ca){
		handCards.add(ca);
		bet=getDoubleBet();
	}
	
	private int getDoubleBet() {
		return bet*2;
	}


	/**
	 * Verifica che sia possibile o meno il raddoppio
	 *
	 * @return vero o falso
	 */	
	public boolean checkDoubleDown() {
		if(!(getBalance()>getDoubleBet())) return false;
		if(!(handCards.blackJackValue()>8 && handCards.blackJackValue()<16) ) return false;
		return true;
	}

	
	public boolean isRunOutFunds() {
		return balance == 0;
	}
	
	/**
	 * Returns whether or not the hand contains an ace.
	 *
	 * @return true if the hand contains an ace, false if does not
	 */

	private boolean hasAce() {
		return handCards.hasAce();
	}

	
	/**
	 * Returns whether or not the player bust
	 *
	 * @return true if the player bust
	 */

	public boolean checkBust(){
		return handCards.CheckBust();
	}
	
	/**
	 * Returns first card
	 *
	 * @return the first card in the hand
	 */

	public Card returnFirstHandCard(){
		return handCards.get(0);
	}
	
	/**
	 * @return the handCards
	 */
	public CardGroup getHandCards() {
		return handCards;
	}

	/**
	 * Returns whether or not the hand is a blackJack.
	 *
	 * @return true if the hand contains an ace, false if does not
	 */

	public boolean hasABJ() {
		return handCards.hasABlackJack();
	}
	
	/**
	 * Returns the number of cards in the hand.
	 *
	 * @return number of cards in the hand
	 */

	public int getCountCardHand() {
		return handCards.getCount();
	}

	public String GetLoseBalance() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
