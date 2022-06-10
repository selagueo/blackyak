public class GameTemplate {
    private enum State {
        STATE_INITIALIZE,
        STATE_GAME_START,
        STATE_PLAYER_TURN,
        STATE_PLAYER_BUST,
        STATE_DEALER_TURN,
        STATE_DEALER_BUST,
        STATE_END_GAME,
        STATE_GAME_OVER
    }
    private Deck deck;
    private Hand handPlayer;
    private Hand handDealer;
    State state;
    boolean getOtherCard = false;
    boolean win = false;

    public void Game () {
        deck = new Deck();
        handPlayer = new Hand();
        handDealer = new Hand();
        state = GameTemplate.State.STATE_INITIALIZE;
    }
}
