package it.unibs;

public class Dealer  extends Player {

	public Dealer() {
		super();
		// TODO Auto-generated constructor stub
	}

	
//sistenare
	public void flipSecondCard() {
		returnFirstHandCard().unCoverCard();
	}

	
}
