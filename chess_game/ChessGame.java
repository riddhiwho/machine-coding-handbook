// Chess Game Backend Implementation in Java

import java.util.*;

// Main Chess Game Class
public class ChessGame {
    private ChessBoard board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentPlayer;
    private GameStatus gameStatus;
    private Scanner scanner;
    
    public ChessGame() {
        board = new ChessBoard();
        whitePlayer = new Player(Color.WHITE);
        blackPlayer = new Player(Color.BLACK);
        currentPlayer = whitePlayer;
        gameStatus = GameStatus.ACTIVE;
        scanner = new Scanner(System.in);
        
        board.initializeBoard();
    }
    
    public boolean makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (gameStatus != GameStatus.ACTIVE) {
            return false;
        }
        
        Piece piece = board.getPiece(fromRow, fromCol);
        
        if (piece == null || piece.getColor() != currentPlayer.getColor()) {
            return false;
        }
        
        if (board.isValidMove(fromRow, fromCol, toRow, toCol)) {
            board.movePiece(fromRow, fromCol, toRow, toCol);
            
            Color opponentColor = getOpponentColor();
            
            if (board.isCheckmate(opponentColor)) {
                gameStatus = (currentPlayer.getColor() == Color.WHITE) ? 
                           GameStatus.WHITE_WINS : GameStatus.BLACK_WINS;
                return true;
            }
            
            if (board.isStalemate(opponentColor)) {
                gameStatus = GameStatus.DRAW;
                return true;
            }
            
            switchPlayer();
            return true;
        }
        
        return false;
    }
    
    public boolean makeMove(String moveNotation) {
        // Parse algebraic notation like "e2e4" or "e2-e4"
        moveNotation = moveNotation.toLowerCase().replace("-", "");
        
        if (moveNotation.length() != 4) {
            return false;
        }
        
        try {
            int fromCol = moveNotation.charAt(0) - 'a';
            int fromRow = 8 - (moveNotation.charAt(1) - '0');
            int toCol = moveNotation.charAt(2) - 'a';
            int toRow = 8 - (moveNotation.charAt(3) - '0');
            
            return makeMove(fromRow, fromCol, toRow, toCol);
        } catch (Exception e) {
            return false;
        }
    }
    
    private void switchPlayer() {
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }
    
    private Color getOpponentColor() {
        return currentPlayer.getColor() == Color.WHITE ? Color.BLACK : Color.WHITE;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public GameStatus getGameStatus() {
        return gameStatus;
    }
    
    public boolean isInCheck() {
        return board.isInCheck(currentPlayer.getColor());
    }
    
    public boolean isInCheck(Color color) {
        return board.isInCheck(color);
    }
    
    public ChessBoard getBoard() {
        return board;
    }
    
    public void displayBoard() {
        board.displayBoard();
    }
    
    public void displayStatus() {
        System.out.println("\n" + "=".repeat(50));
        String playerName = currentPlayer.getColor() == Color.WHITE ? "White" : "Black";
        
        switch (gameStatus) {
            case ACTIVE:
                if (isInCheck()) {
                    System.out.println(playerName + "'s turn - IN CHECK!");
                } else {
                    System.out.println(playerName + "'s turn");
                }
                break;
            case WHITE_WINS:
                System.out.println("Game Over - White wins by checkmate!");
                break;
            case BLACK_WINS:
                System.out.println("Game Over - Black wins by checkmate!");
                break;
            case DRAW:
                System.out.println("Game Over - Draw by stalemate!");
                break;
        }
        System.out.println("=".repeat(50));
    }
    
    public void playConsoleGame() {
        System.out.println("Welcome to Chess!");
        System.out.println("Enter moves in format: e2e4 (from e2 to e4)");
        System.out.println("Type 'quit' to exit, 'board' to show board, 'help' for commands");
        
        while (gameStatus == GameStatus.ACTIVE) {
            displayBoard();
            displayStatus();
            
            System.out.print("Enter move: ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("quit")) {
                break;
            } else if (input.equalsIgnoreCase("board")) {
                continue;
            } else if (input.equalsIgnoreCase("help")) {
                printHelp();
                continue;
            }
            
            if (!makeMove(input)) {
                System.out.println("Invalid move! Try again.");
            }
        }
        
        if (gameStatus != GameStatus.ACTIVE) {
            displayBoard();
            displayStatus();
        }
        
        System.out.println("Thanks for playing!");
    }
    
    private void printHelp() {
        System.out.println("\nCommands:");
        System.out.println("- Move format: e2e4 (moves piece from e2 to e4)");
        System.out.println("- 'board' - Display current board");
        System.out.println("- 'quit' - Exit game");
        System.out.println("- 'help' - Show this help");
        System.out.println("\nColumns: a-h, Rows: 1-8 (1 is white's back rank)");
    }
    
    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.playConsoleGame();
    }
}









