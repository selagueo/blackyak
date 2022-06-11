import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public class Game {
    private enum State {
        INITIALIZE,
        GAME_START,
        PLAYER_TURN,
        DEALER_TURN,
        PLAYER_BUST,
        DEALER_BUST,
        GAME_OVER,
        END_GAME,
    }
    private enum WinState {
        PLAYER_INIT,
        PLAYER_WON,
        PLAYER_LOST,
        PLAYER_TIE,
    }

    private Deck deck;
    private Hand handPlayer;
    private Hand handDealer;
    WinState winState;
    private State state;
    private float timer = 0.0f;
    public Game () {
        deck = new Deck();
        handPlayer = new Hand();
        handDealer = new Hand();
        state = State.INITIALIZE;
        winState = WinState.PLAYER_INIT;
    }
    public void update(float deltaTime) {
        switch (state) {
            case INITIALIZE: doInitializeState(); break;
            case GAME_START: doGameStartState(); break;
            case PLAYER_TURN: doPlayerTurnState(); break;
            case DEALER_TURN: doDealerTurnState(); break;
            case PLAYER_BUST: doPlayerBustState(); break;
            case DEALER_BUST: doDealerBustState(); break;
            case GAME_OVER: doGameOverState(); break;
            case END_GAME: doEndGameState(); break;
        }

        if(state == State.PLAYER_TURN)
        {
            if(timer > 1.0f) {
                handPlayer.update(deltaTime);
            }

            if(timer > 4.0f)
            {
                handDealer.update(deltaTime);
            }

            timer += deltaTime;

        }

        if(Input.getKeyDown(GLFW_KEY_D)) {
            printState();
        }
    }

    private void printState() {
        System.out.println("Current state " + this.state);
        System.out.println("Player hand: " + handPlayer.getValue());
        System.out.println("Dealer hand: " + handDealer.getValue());
    }

    private void doInitializeState() {
        winState = WinState.PLAYER_INIT;
        deck.reset();
        deck.shuffle();
        handPlayer.clear();
        handDealer.clear();
        //TODO: Add bets action
        state = State.GAME_START;
        timer = 0.0f;
    }

    private void doGameStartState() {
        Card card = deck.getCard();
        handPlayer.getCard(card, new Animation(card.getSprite(), card.getSprite().getTransform().position,
                           new Vector2f(1280.0f*0.5f, 200.0f)));
        card = deck.getCard();
        handPlayer.getCard(card, new Animation(card.getSprite(), card.getSprite().getTransform().position,
                           new Vector2f(1280.0f*0.5f - 6*6, 200.0f + 6*6)));
        card = deck.getCard();
        handDealer.getCard(card, new Animation(card.getSprite(), card.getSprite().getTransform().position,
                           new Vector2f(1280.0f*0.5f - 22*2.1f - 100, 500.0f)));
        card = deck.getCard();
        handDealer.getCard(card, new Animation(card.getSprite(), card.getSprite().getTransform().position,
                           new Vector2f(1280.0f*0.5f + 22*2.1f - 100, 500.0f)));
        //TODO This card should face down.
        state = State.PLAYER_TURN;
    }
    private void doPlayerTurnState() {
        int value = handPlayer.getValue();

        if (value < 21) {
            if (Input.playerStand()) {
                state = State.DEALER_TURN;
            } else if (Input.PlayerHit()) {
                Card card = deck.getCard();
                handPlayer.getCard(card, new Animation(card.getSprite(), card.getSprite().getTransform().position, new Vector2f(1280.0f*0.5f, 720.0f*0.5f)));
                state = State.PLAYER_TURN;
                printState();
            }
        } else if (value == 21) {
            state = State.DEALER_TURN;
        } else if (value > 21) {
            state = State.PLAYER_BUST;
        }
    }
    private void doDealerTurnState() {
        int value = handDealer.getValue();
        if (value < 17) {
            Card card = deck.getCard();
            handDealer.getCard(card, new Animation(card.getSprite(), card.getSprite().getTransform().position, new Vector2f(1280.0f*0.5f, 200.0f)));
        } else if (value >= 17 && value <= 21) {
            state = State.END_GAME;
        } else if (value > 21){
            state = State.DEALER_BUST;
        }
    }
    private void doPlayerBustState() {
        //TODO bets logic here
        winState = WinState.PLAYER_LOST;
        state = State.GAME_OVER;
    }
    private void doDealerBustState() {
        //TODO bets logic here
        winState = WinState.PLAYER_WON;
        state = State.GAME_OVER;
    }
    private void doGameOverState() {
        //TODO bets logic
        state = State.INITIALIZE;
    }
    private void doEndGameState() {
        int valuePlayer = handPlayer.getValue();
        int valueDealer = handDealer.getValue();
        if (valuePlayer == valueDealer) {
            //TODO bets logic
            winState = WinState.PLAYER_TIE;
        } else if (valuePlayer > valueDealer) {
            //TODO bets logic
            winState = WinState.PLAYER_WON;
        } else if (valuePlayer < valueDealer) {
            //TODO bets logic
            winState = WinState.PLAYER_LOST;
        }
        state =  State.GAME_OVER;
    }

// TODO: fix ...
    private Sprite background = new Sprite(AssetManager.getTexture("table"),
                          1280*0.5f, 720*0.5f,
                                  1280, 720,
                                  0);

    public void render() {

        Renderer renderer = Renderer.getInstance();
        renderer.setShader(AssetManager.getShader("default"));
        background.render();
        renderer.setShader(AssetManager.getShader("spritesheet"));
        //deck.render();
        handPlayer.render();
        handDealer.render();
    }
}
