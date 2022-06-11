import org.joml.Vector2f;

import java.util.LinkedList;
import java.util.Random;

public class Deck {
    private final int DECK_AMMOUNT = 1;
    private final int MAX_SHUFFLE_ITER = 512;
    private LinkedList<Card> cards;
    private Random random;

    private  void addSuitToDeck(Card.Suits suit) {
        cards.add(new Card(suit, Card.Ranks.ACE));
        cards.add(new Card(suit, Card.Ranks.TWO));
        cards.add(new Card(suit, Card.Ranks.THREE));
        cards.add(new Card(suit, Card.Ranks.FOUR));
        cards.add(new Card(suit, Card.Ranks.FIVE));
        cards.add(new Card(suit, Card.Ranks.SIX));
        cards.add(new Card(suit, Card.Ranks.SEVEN));
        cards.add(new Card(suit, Card.Ranks.EIGHT));
        cards.add(new Card(suit, Card.Ranks.NINE));
        cards.add(new Card(suit, Card.Ranks.TEN));
        cards.add(new Card(suit, Card.Ranks.JACK));
        cards.add(new Card(suit, Card.Ranks.QUEEN));
        cards.add(new Card(suit, Card.Ranks.KING));
    }
    public Deck() {
        random = new Random();
        cards = new LinkedList<Card>();
        reset();
    }
    public void reset() {
        cards.clear();
        for (int i = 0; i < DECK_AMMOUNT; i++) {
            addSuitToDeck(Card.Suits.CLUBS);
            addSuitToDeck(Card.Suits.SPADES);
            addSuitToDeck(Card.Suits.HEARTS);
            addSuitToDeck(Card.Suits.DIAMONDS);
        }
    }
    public Card getCard() {
        Card card = cards.pollFirst();
        return card; //NOTE: poolFirst returns null if the list is empty
    }

    public void shuffle() {
        for (int iter = 0; iter < MAX_SHUFFLE_ITER; iter++) {
            int cardIndex = random.nextInt(cards.size());
            int cardNewIndex = random.nextInt(cards.size());
            Card card = cards.remove(cardIndex);
            cards.add(cardNewIndex, card);
        }
    }

    public void print() {
        for (Card card : cards) {
            System.out.println(card);
        }
    }

    public void render() {
        for (Card card : cards) {
            card.render();
        }
    };
}
