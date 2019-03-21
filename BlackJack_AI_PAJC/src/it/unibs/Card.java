package it.unibs;

/**
 * Card objects represent a standard playing card with a rank and a suit.
 *
 * @author Francesco Palberti, Enrico Zaninelli
 */

public class Card {
	private final Rank RANK;    // rank of the card
    private final Suit SUIT;    // suit of the card
	private boolean covered=false;
    
    /**
     * Constructor for Card object.
     *
     * @param rank Rank of the card
     * @param suit Suit of the card
     */

    public Card(Rank rank, Suit suit) {
        RANK = rank;
        SUIT = suit;
    }
    

    /**
     * Ranks that cards can have.
     */

    public enum Rank {
        ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10);

        private int value;  // value of the rank

        /**
         * Constructor for Rank object.
         *
         * @param value Value of the rank
         */

        Rank(int value) {
            this.value = value;
        }

        /**
         * Returns a string representation of the rank.
         *
         * @return the string representation of the rank
         */

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Suits that cards can have.
     */

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;

        /**
         * Returns a string representation of the suit.
         *
         * @return the string representation of the suit
         */

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Returns the value of the card.
     *
     * @return the value of the card
     */

    public int getValue() {
        return RANK.value;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank of the card
     */

    public Rank getRank() {
        return RANK;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return the string representation of the card
     */

    @Override
    public String toString() {
        return RANK + "--" + SUIT;
    }
    
    public String getFileName() { // Get the file name of the image of this card
		return String.format("C:\\Users\\franc\\git\\BlackJack-PAJC\\cardImages\\%s\\%s.png", this.SUIT, this.RANK); // Return file name
    }

	public void coverCard() {
		setCovered(true);
		
	}

	public boolean isCovered() {
		return covered;
	}

	public void setCovered(boolean covered) {
		this.covered = covered;
	}

	public void unCoverCard() {
		setCovered(false);
		
	}
    
	/**
     * Gets the highest possible value of the ace
     * @return integer of the highest possible value of the ace
     */
    public int getHighValue() {
    	if (getRank() ==  Rank.ACE) {
    		return 11;
    	} else {
    		return getValue();
    	}
    }
}
