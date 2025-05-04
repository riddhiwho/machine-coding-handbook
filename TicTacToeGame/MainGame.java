package TicTacToeGame;

public class MainGame {
    public static void main(String[] args) {
        Player player1 = new Player("Akika", "X");
        Player player2 = new Player("Orion", "O");

        GameManager gameManager = new GameManager(player1, player2);
        gameManager.startGame();
    }
}
