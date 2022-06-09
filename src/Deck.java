import enums.CardRanks;
import enums.CardSuits;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private static ArrayList<Card> cardsTable;
    private static int CARD_TABLE_SIZE = 52;

    private final int MAX_CARDS = 52;
    private int topCard = 0;
    private ArrayList<Integer> indices;
    private Random random;

    static {
        cardsTable = new ArrayList<Card>(CARD_TABLE_SIZE);
    }

    public Deck() {
        random = new Random();
        indices = new ArrayList<Integer>(MAX_CARDS);
        setupDeck();
        shuffleDeck();
    }

    public Card getCard(boolean faceDown) {
        if (topCard < MAX_CARDS) {
            int cardIndex = indices.get(topCard++);
            Card card = cardsTable.get(cardIndex);
            card.setFaceDown(faceDown);
            return card;
        } else {
            return null;
        }
    }

    public void shuffleDeck() {
        topCard = 0;
        indices.clear();
        while(indices.size() < MAX_CARDS) {
            int cardIndex = random.nextInt(MAX_CARDS);
            if(!indices.contains(cardIndex)) {
                indices.add(cardIndex);
            }
        }
    }

    public void printDeck() {
        for(int i = 0; i < indices.size(); i++) {
            int cardIndex = indices.get(i);
            System.out.println((i+1) + " Rank: " + cardsTable.get(cardIndex).getRank() +
                                       " Suit: " + cardsTable.get(cardIndex).getSuit());
        }
    }

    private void setupDeck() {
        topCard = 0;
        addSuitToDeck(CardSuits.CLUBS);
        addSuitToDeck(CardSuits.SPADES);
        addSuitToDeck(CardSuits.HEARTS);
        addSuitToDeck(CardSuits.DIAMONDS);
    }

    private void addSuitToDeck(CardSuits suit) {
        cardsTable.add(new Card(suit, CardRanks.ACE));
        cardsTable.add(new Card(suit, CardRanks.TWO));
        cardsTable.add(new Card(suit, CardRanks.THREE));
        cardsTable.add(new Card(suit, CardRanks.FOUR));
        cardsTable.add(new Card(suit, CardRanks.FIVE));
        cardsTable.add(new Card(suit, CardRanks.SIX));
        cardsTable.add(new Card(suit, CardRanks.SEVEN));
        cardsTable.add(new Card(suit, CardRanks.EIGHT));
        cardsTable.add(new Card(suit, CardRanks.NINE));
        cardsTable.add(new Card(suit, CardRanks.TEN));
        cardsTable.add(new Card(suit, CardRanks.JACK));
        cardsTable.add(new Card(suit, CardRanks.QUEEN));
        cardsTable.add(new Card(suit, CardRanks.KING));
    }
}
