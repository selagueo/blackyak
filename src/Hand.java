import java.util.ArrayList;

public class Hand {
    private ArrayList<CardEntity> hand;

    public void getCard (Deck deck, boolean faceDown) {
        hand.add(deck.getCard(faceDown));
    }

    public void clearHand() {
        hand.clear();
    }

    public int getHandValue () {
        int value = 0;
        for (CardEntity card : hand) {
            switch (card.getRank()) {
                case ACE -> value += (value >= 11) ? 1 : 11;
                case TWO -> value += 2;
                case THREE -> value += 3;
                case FOUR -> value += 4;
                case FIVE -> value += 5;
                case SIX -> value += 6;
                case SEVEN -> value += 7;
                case EIGHT -> value += 8;
                case NINE -> value += 9;
                case TEN, JACK, QUEEN, KING -> value += 10;
            }
        }
        return value;
    }
}
