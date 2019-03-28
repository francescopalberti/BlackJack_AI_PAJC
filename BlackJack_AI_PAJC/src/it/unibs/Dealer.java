package it.unibs;

public class Dealer extends Player {

	public Dealer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void coverFirstCard() {
		returnFirstHandCard().coverCard();
	}

	public void uncoverFirstCard() {
		returnFirstHandCard().unCoverCard();
	}

	
}
