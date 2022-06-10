import enums.CardRanks;
import enums.CardSuits;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Deck {
    private static ArrayList<Card> cardsTable;
    private static int CARD_TABLE_SIZE = 52;

    private final int MAX_CARDS = 52;
    private final int MAX_SHUFFLE_ITER = 512;
    private int topCard = 0;
    private LinkedList<CardEntity> cards;
    private Random random;

    private static void addSuitToDeck(CardSuits suit) {
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

    public static ArrayList<Card> getCardsTable() {
        return cardsTable;
    }

    static {
        cardsTable = new ArrayList<Card>(CARD_TABLE_SIZE);
        addSuitToDeck(CardSuits.CLUBS);
        addSuitToDeck(CardSuits.SPADES);
        addSuitToDeck(CardSuits.HEARTS);
        addSuitToDeck(CardSuits.DIAMONDS);
    }

    public Deck() {
        random = new Random();
        cards = new LinkedList<CardEntity>();
        setup();
    }
    private void setup() {
        for (int cardIndex = 0; cardIndex < MAX_CARDS; cardIndex++) {
            cards.add(new CardEntity(cardIndex));
        }
    }
    public void shuffle() {
        topCard = 0;
        for (int shuffleIter = 0; shuffleIter < MAX_SHUFFLE_ITER; shuffleIter++) {
            int cardIndex = random.nextInt(cards.size());
            int otherCardIndex = random.nextInt(cards.size());
            CardEntity card = cards.get(cardIndex);
            CardEntity otherCard = cards.get(otherCardIndex);
            cards.set(otherCardIndex, card);
            cards.set(cardIndex, otherCard);
        }
    }
    public CardEntity getCard(boolean faceDown) {
        return cards.get(topCard++);
    }

    public void print() {
        for (CardEntity card : cards) {
            System.out.println(card.getCard());
        }
    }
}
