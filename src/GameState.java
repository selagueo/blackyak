import states.*;

public class GameState {
    InitializeState initializeState;
    GameStartState gameStartState;
    PlayerTurnState playerTurnState;
    PlayerBustState playerBustState;
    DealerTurnState dealerTurnState;
    DealerBustState dealerBustState;
    EndGameState endGameState;
    GameOverState gameOverState;
}
