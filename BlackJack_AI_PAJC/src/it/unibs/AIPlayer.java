package it.unibs;

import java.util.Random;

public class AIPlayer extends Player {

	private static Random rnd = new Random();
	private int level;
	
	public AIPlayer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AIPlayer(String pName, int difficulty, int startMoney) {
		super(pName,  startMoney);
		this.level=difficulty;
	}
	
	/**
     * For AI players only. Causes player to play Blackjack accordingly.
     * @param dealerCard the dealer's visible card
     * @return -1 means not a computer, 0 means stand, 1 means hit, 2 means surrender, 3 means double
     */
    public int askComputerAction(Card dealerCard) {
                if(hasABJ()){
                    return 0;
                }
                
                if(CheckBust()) {
                    return 0;
                }
                if(level == Constraint.EASY){
                    if(getCardTotal() == 11){
                        if (rnd.nextInt(101) < (4/13) * 100){
                            return 3;
                        }
                    }        
                    else{
                        int nowValue = getCardTotal();                            
                        if (nowValue <= 11) return 1;
                        double bustingChance = (nowValue - 8) / (double) 13;
                        double successChance = 1 - bustingChance;                            
                        if(rnd.nextInt(120) < successChance*100 - 16) 
                            return 1;
                        else
                            return 0;
                    }
                }else{ //HARD AI code
                    if (getCountCardHand() == 2) {
                            if((dealerCard.getValue() <= 9 && getCardTotal() == 10) ||   
                                            (dealerCard.getValue() <= 10 && getCardTotal() == 11))
                                    return 3;
                    }
                    if(dealerCard.getHighValue() >= 7){
                        if(getCardTotal() >=17) return 0;
                        else return 1;
                    }
                    else if(dealerCard.getHighValue() <= 6){
                        if(getCardTotal() > 11) return 0;
                        else return 1;
                    } 
                    else return 0; //just in case
                }
            return -1;
    }

}
