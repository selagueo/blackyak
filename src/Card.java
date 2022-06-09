import enums.CardRanks;
import enums.CardSuits;

public class Card {
    private CardRanks rank;
    private CardSuits suit;
    private boolean faceDown = true;

    public Card(CardSuits suit, CardRanks rank) {
        this.rank = rank;
        this.suit = suit;
    }

    public String toString() {
        return new String(getRank() + " of " + getSuit());
    }

    public void setFaceDown(boolean val) {
        this.faceDown = val;
    }

    public CardRanks getRank() {
        return rank;
    }

    public  CardSuits getSuit() {
        return  suit;
    }
}
