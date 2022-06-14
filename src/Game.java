import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

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

    private boolean running = true;
    private Deck deck;
    private Hand handPlayer;
    private Hand handDealer;

    private Sprite background;
    WinState winState;
    private State state;
    private float timer = 0.0f;
    private int betAmount;

    Vector4f fontColor = new Vector4f(0.8f, 0.8f, 0.8f, 1.0f);

    public Game() {
        loadAssets();
        deck = new Deck();
        handPlayer = new Hand();
        handDealer = new Hand();
        state = State.INITIALIZE;
        winState = WinState.PLAYER_INIT;
        background = new Sprite(AssetManager.getTexture("table"),
                1280 * 0.5f, 720 * 0.5f,
                1280, 720,
                0);
        handPlayer.setCash(475);
        AssetManager.getSound("music").play();
    }

    public boolean getRunning() {
        return this.running;
    }

    private void loadAssets() {
        AssetManager.addShader("default", "shaders/vertexShader.glsl", "shaders/fragmentShader.glsl");
        AssetManager.addShader("spritesheet", "shaders/sprVertexShader.glsl", "shaders/sprFragShader.glsl");
        AssetManager.addTexture("table", "images/table.png");
        AssetManager.addTexture("chipOne", "images/chip_one.png");
        AssetManager.addTexture("chipFive", "images/chip_five.png");
        AssetManager.addTexture("chipTen", "images/chip_ten.png");
        AssetManager.addTexture("chipTwentyFive", "images/chip_twentyfive.png");
        AssetManager.addTexture("chipHundred", "images/chip_hundred.png");
        AssetManager.addTexture("deck", "images/deck.png");
        AssetManager.addTexture("font", "images/font.png");
        AssetManager.addSound("music", "sounds/music.ogg", true);

    }

    public void update(float deltaTime) {
        switch (state) {
            case INITIALIZE:
                doInitializeState();
                break;
            case GAME_START:
                doGameStartState();
                break;
            case PLAYER_TURN:
                doPlayerTurnState();
                break;
            case DEALER_TURN:
                doDealerTurnState();
                break;
            case PLAYER_BUST:
                doPlayerBustState();
                break;
            case DEALER_BUST:
                doDealerBustState();
                break;
            case GAME_OVER:
                doGameOverState();
                break;
            case END_GAME:
                doEndGameState();
                break;
        }

        if (state == State.PLAYER_TURN || state == State.DEALER_TURN ||
                state == State.PLAYER_BUST || state == State.DEALER_BUST) {
            if (timer > 1.0f) {
                handPlayer.update(deltaTime);
            }
            if (timer > 4.0f) {
                handDealer.update(deltaTime);
            }
            timer += deltaTime;
        }
        if (Input.getInstance().getKeyDown(GLFW_KEY_D)) {
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
        timer = 0.0f;

        if (((betAmount + 5) < handPlayer.getCash()) && Input.getInstance().getKeyDown(GLFW_KEY_UP)) {
            betAmount += 5;
        } else if (betAmount > 0 && Input.getInstance().getKeyDown(GLFW_KEY_DOWN)) {
            betAmount -= 5;
        }

        if (Input.getInstance().start()) {
            state = State.GAME_START;
        } else if (Input.getInstance().getKeyDown(GLFW_KEY_ESCAPE)) {
            running = false;
        }
    }

    private void doGameStartState() {
        handPlayer.giveCash(-betAmount);

        Card card = deck.getCard();
        handPlayer.getCard(card, new DealCardAnim(card.getSprite(),
                card.getSprite().getTransform().position,
                new Vector2f(1280.0f * 0.5f, 200.0f)));
        card = deck.getCard();
        handPlayer.getCard(card, new DealCardAnim(card.getSprite(),
                card.getSprite().getTransform().position,
                new Vector2f(1280.0f * 0.5f - 6 * 6, 200.0f + 6 * 6)));
        card = deck.getCard();
        handDealer.getCard(card, new DealCardAnim(card.getSprite(),
                card.getSprite().getTransform().position,
                new Vector2f(1280.0f * 0.5f - 200, 500.0f)));
        card = deck.getCard();
        handDealer.getCard(card, new DealCardFaceDownAnim(card.getSprite(),
                card.getSprite().getTransform().position,
                new Vector2f(1280.0f * 0.5f + 22 * 4.2f - 200, 500.0f))); //TODO This card should face down.
        state = State.PLAYER_TURN;
    }

    private void doPlayerTurnState() {
        Card lastPlayerCard = handPlayer.getLastCard();
        Card lastDealerCard = handDealer.getLastCard();
        if (lastPlayerCard.getAnimation().isDone() && lastDealerCard.getAnimation().isDone()) {
            int value = handPlayer.getValue();
            if (value < 21) {
                if (Input.getInstance().playerStand()) {
                    handDealer.changeLastCardAnim(new FlipCardAnim(lastDealerCard.getSprite()));
                    state = State.DEALER_TURN;
                } else if (Input.getInstance().PlayerHit()) {
                    Card card = deck.getCard();
                    Vector2f target = new Vector2f(lastPlayerCard.getSprite().getTransform().position.x - 6 * 6, lastPlayerCard.getSprite().getTransform().position.y + 6 * 6);
                    handPlayer.getCard(card, new DealCardAnim(card.getSprite(), card.getSprite().getTransform().position, target));
                    state = State.PLAYER_TURN;
                    printState();
                }
            } else if (value == 21) {
                handDealer.changeLastCardAnim(new FlipCardAnim(lastDealerCard.getSprite()));
                state = State.DEALER_TURN;
            } else if (value > 21) {
                handDealer.changeLastCardAnim(new FlipCardAnim(lastDealerCard.getSprite()));
                state = State.PLAYER_BUST;
            }
        }
    }

    private void doDealerTurnState() {
        Card lastPlayerCard = handPlayer.getLastCard();
        Card lastDealerCard = handDealer.getLastCard();
        if (lastPlayerCard.getAnimation().isDone() && lastDealerCard.getAnimation().isDone()) {
            int value = handDealer.getValue();
            if (value < 17) {
                Card card = deck.getCard();
                Vector2f target = new Vector2f(lastDealerCard.getSprite().getTransform().position.x + 22 * 4.2f, lastDealerCard.getSprite().getTransform().position.y);
                handDealer.getCard(card, new DealCardAnim(card.getSprite(), card.getSprite().getTransform().position, target));
            } else if (value >= 17 && value <= 21) {
                state = State.END_GAME;
            } else if (value > 21) {
                state = State.DEALER_BUST;
            }
        }
    }

    private void doPlayerBustState() {
        Card lastPlayerCard = handPlayer.getLastCard();
        Card lastDealerCard = handDealer.getLastCard();
        if (lastPlayerCard.getAnimation().isDone() && lastDealerCard.getAnimation().isDone()) {
            System.out.println("Player Burn");
            winState = WinState.PLAYER_LOST;
            state = State.GAME_OVER;
        }
    }

    private void doDealerBustState() {
        Card lastPlayerCard = handPlayer.getLastCard();
        Card lastDealerCard = handDealer.getLastCard();
        if (lastPlayerCard.getAnimation().isDone() && lastDealerCard.getAnimation().isDone()) {
            System.out.println("Dealer Burn");
            winState = WinState.PLAYER_WON;
            state = State.GAME_OVER;
        }
    }

    private void doGameOverState() {
        switch (winState) {
            case PLAYER_WON -> handPlayer.giveCash(betAmount * 2);
            case PLAYER_LOST -> handPlayer.giveCash(0);
            case PLAYER_TIE -> handPlayer.giveCash(betAmount);
        }
        betAmount = 0;
        if (Input.getInstance().restart()) {
            state = State.INITIALIZE;
        }
    }

    private void doEndGameState() {
        int valuePlayer = handPlayer.getValue();
        int valueDealer = handDealer.getValue();
        if (valuePlayer == valueDealer) {
            winState = WinState.PLAYER_TIE;
        } else if (valuePlayer > valueDealer) {
            winState = WinState.PLAYER_WON;
        } else if (valuePlayer < valueDealer) {
            winState = WinState.PLAYER_LOST;
        }
        state = State.GAME_OVER;
    }

    public void render() {
        Renderer renderer = Renderer.getInstance();
        renderer.setShader(AssetManager.getShader("default"));
        background.render();
        renderer.setShader(AssetManager.getShader("spritesheet"));
        //deck.render();
        handDealer.render();
        handPlayer.render();
        renderPlayerChips();

        switch (state) {
            case INITIALIZE:
                renderInitializeState();
                break;
            case GAME_START:
                renderGameStartState();
                break;
            case PLAYER_TURN:
                renderPlayerTurnState();
                break;
            case DEALER_TURN:
                renderDealerTurnState();
                break;
            case PLAYER_BUST:
                renderPlayerBustState();
                break;
            case DEALER_BUST:
                renderDealerBustState();
                break;
            case GAME_OVER:
                renderGameOverState();
                break;
            case END_GAME:
                renderEndGameState();
                break;
        }
    }

    void renderInitializeState() {
        Font.getInstance().render("PRESS: SPACE TO START - ESC TO QUIT", 180, 680, fontColor, 4);
        Font.getInstance().render("UP Arrow (+ 5)", 120, 580, fontColor, 2);
        Font.getInstance().render("Bet: " + betAmount, 120, 550, fontColor, 3);
        Font.getInstance().render("DOWN Arrow (- 5)", 120, 520, fontColor, 2);
    }

    ;

    void renderGameStartState() {
        renderPlayerHand(fontColor);
    }

    ;

    void renderPlayerTurnState() {
        Font.getInstance().render("PRESS: SPACE TO HIT - ENTER TO STAND", 180, 680, fontColor, 4);

        Card lastPlayerCard = handPlayer.getLastCard();
        if (lastPlayerCard.getAnimation().isDone()) {
            renderPlayerHand(fontColor);
        } else {
            Font.getInstance().render("Player Hand: ", 150, 150, fontColor, 4);
        }
        renderBetAmount();
    }

    ;

    void renderDealerTurnState() {
        Font.getInstance().render("PRESS: SPACE TO HIT - ENTER TO STAND", 180, 680, fontColor, 4);
        renderPlayerHand(fontColor);
        renderBetAmount();
    }

    ;

    void renderPlayerBustState() {
        renderPlayerHand(new Vector4f(1, 0, 0, 1));
        renderDealerHand(fontColor);
    }

    ;

    void renderDealerBustState() {
        renderPlayerHand(fontColor);
        renderDealerHand(new Vector4f(1, 0, 0, 1));
    }

    ;

    void renderGameOverState() {
        Font.getInstance().render("PRESS SPACE TO PLAY AGAIN - ESC TO QUIT", 180, 680, fontColor, 4);
        switch (winState) {
            case PLAYER_WON -> Font.getInstance().render("PLAYER WON", 100, 320, fontColor, 6);
            case PLAYER_LOST -> Font.getInstance().render("PLAYER LOST", 100, 320, fontColor, 6);
            case PLAYER_TIE -> Font.getInstance().render("TIE", 120, 320, fontColor, 6);
        }

        Vector4f playerColor = new Vector4f(fontColor);
        Vector4f dealerColor = new Vector4f(fontColor);
        if (winState == WinState.PLAYER_LOST) {
            playerColor = new Vector4f(1, 0, 0, 1);
        }
        if (winState == WinState.PLAYER_WON) {
            dealerColor = new Vector4f(1, 0, 0, 1);
        }
        renderPlayerHand(playerColor);
        renderDealerHand(dealerColor);
    }

    ;

    void renderEndGameState() { /* NOTE: nothing */ }

    ;

    private void renderPlayerHand(Vector4f color) {
        Font.getInstance().render("Player Hand: " + handPlayer.getValue(), 150, 150, color, 4);
    }

    private void renderDealerHand(Vector4f color) {
        Font.getInstance().render("Dealer Hand: " + handDealer.getValue(), 150, 50, color, 4);
    }

    private void renderBetAmount() {
        Font.getInstance().render("YOUR BET: " + betAmount, 740, 150, fontColor, 4);
    }

    private void renderPlayerChips() {
        int posY = 240;
        int posX = 240;
        Font.getInstance().render("$" + handPlayer.getCash(), (1280 / 2) - 20, 30, new Vector4f(0, 0, 0, 1), 2);
        Renderer.getInstance().setShader(AssetManager.getShader("default"));
        int[] chipOffset = new int[5];
        ArrayList<Chip> chips = handPlayer.getChips();
        for (Chip chip : chips) {
            int chipValue = chip.getType().getValue();
            chip.render(posX + (Chip.scale * chipValue), posY + chipOffset[chipValue]);
            chipOffset[chip.getType().getValue()] += 0.2 * Chip.scale;
        }
    }
}
