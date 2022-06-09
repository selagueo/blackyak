public class Game {
    private enum State2 {
        TURN_FIRST, TURN_PLAYER, TURN_DEALER, TURN_WIN,
    }
    private enum State {
        PLAYER_DEALER_GET_2,
        PLAYER_GET_1,
        DEALER_GET_1,
        PLAYER_HAND_VALUE,
        DEALER_HAND_VALUE,
        PLAYER_WINS,
        DEALER_WINS,
        COMPARE_HANDS,
    }

    private Deck deck;
    private Hand handPlayer;
    private Hand handDealer;
    State state;
    boolean getOtherCard = false;
    boolean win = false;

    public Game () {
        deck = new Deck();
        handPlayer = new Hand();
        handDealer = new Hand();
        state = State.PLAYER_DEALER_GET_2;
    }

    public void update() {
        if (state == State.PLAYER_DEALER_GET_2) {
            deck.shuffleDeck();
            handPlayer.getCard(deck, false);
            handPlayer.getCard(deck, false);
            handDealer.getCard(deck, false);
            handDealer.getCard(deck, true);
            state = State.PLAYER_HAND_VALUE;
        } else if (state == State.PLAYER_HAND_VALUE) {
            int value = handPlayer.getHandValue();
            if (value == 21) { //TODO When player hits for another card, the dealer can tie.
                state = State.PLAYER_WINS;
            } else if (value > 21) {
                state = State.DEALER_WINS;
            } else {
                if (getOtherCard) { //TODO Ask the player for other card
                    state = State.PLAYER_GET_1;
                } else {
                    state = State.DEALER_HAND_VALUE;
                }
            }
        } else if (state == State.DEALER_HAND_VALUE) {
            int value = handDealer.getHandValue();
            if (value == 21) {
                state = State.DEALER_WINS;
            } else if (value > 21) {
                state = State.PLAYER_WINS;
            } else if (value >= 17) {
                state = State.COMPARE_HANDS;
            } else {
                state = State.DEALER_GET_1;
            }
        } else if (state == State.PLAYER_GET_1) {
            handPlayer.getCard(deck, false);
            state = State.PLAYER_HAND_VALUE;
        } else if (state == State.DEALER_GET_1) {
            handDealer.getCard(deck, false);
            state = State.DEALER_HAND_VALUE;
        } else if (state == State.COMPARE_HANDS) {
            state = (handPlayer.getHandValue() > handDealer.getHandValue()) ? State.PLAYER_WINS : State.DEALER_WINS;
        } else if (state == State.PLAYER_WINS) {
            // TODO Player wins logic HERE!
        } else if (state == State.DEALER_WINS) {
            // TODO Dealer wins logic HERE!
        }
    }
}
