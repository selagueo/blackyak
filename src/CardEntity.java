import enums.CardRanks;
import enums.CardSuits;

public class CardEntity {
    private final int index;

    public CardEntity(int cardIndex) {
        this.index = cardIndex;
    }

    public int getIndex() {
        return this.index;
    }

    public Card getCard() {
        return Deck.getCardsTable().get(this.index);
    }
    public CardRanks getRank() {
        return getCard().getRank();
    }

    public CardSuits getSuit() {
        return getCard().getSuit();
    }
}
