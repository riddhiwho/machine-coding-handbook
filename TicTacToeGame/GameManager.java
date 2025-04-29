import java.util.Scanner;

public class GameManager {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public GameManager(Player player1, Player player2){
        this.board = new Board();
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }
    
    public static String checkGameStatus(String[][] board) {
        int n = board.length;
        boolean isBoardFull = true;
    
        // Check rows and columns
        for (int i = 0; i < n; i++) {
            // Check row
            String rowVal = board[i][0];
            if (rowVal.equals("-")) {
                isBoardFull = false;  // At least one empty cell in the row, so board is not full
                continue;
            }
            boolean sameRow = true;
    
            for (int j = 1; j < n; j++) {
                if (!board[i][j].equals(rowVal)) {
                    sameRow = false;
                    break;
                }
            }
            if (sameRow) {
                return rowVal;
            }
    
            // Check column
            String colVal = board[0][i];
            if (colVal.equals("-")) {
                isBoardFull = false;  // At least one empty cell in the column, so board is not full
                continue;
            }
            boolean sameCol = true;
    
            for (int j = 1; j < n; j++) {
                if (!board[j][i].equals(colVal)) {
                    sameCol = false;
                    break;
                }
            }
            if (sameCol) {
                return colVal;
            }
        }
    
        // Check diagonals
        String diag1Val = board[0][0];
        if (!diag1Val.equals("-")) {
            boolean sameDiag1 = true;
            for (int i = 1; i < n; i++) {
                if (!board[i][i].equals(diag1Val)) {
                    sameDiag1 = false;
                    break;
                }
            }
            if (sameDiag1) {
                return diag1Val;
            }
        }
    
        String diag2Val = board[0][n - 1];
        if (!diag2Val.equals("-")) {
            boolean sameDiag2 = true;
            for (int i = 1; i < n; i++) {
                if (!board[i][n - 1 - i].equals(diag2Val)) {
                    sameDiag2 = false;
                    break;
                }
            }
            if (sameDiag2) {
                return diag2Val;
            }
        }
    
        // Check draw
        if (isBoardFull) {
            return "Draw";
        }
    
        // Game still ongoing
        return null;
    }

    public void startGame(){
        System.out.println("Game starts!");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.printBoard();
            System.out.println("Turn: " + currentPlayer.getSymbol());
    
            System.out.print("Insert Row (0-2): ");
            int row = scanner.nextInt();
            System.out.print("Insert Column (0-2): ");
            int col = scanner.nextInt();
    
            board.makeAMove(row, col, currentPlayer);
    
            String result = checkGameStatus(board.getBoardArray());
            if (result != null) {
                board.printBoard();
                System.out.println("Result: " + result);
                break; // <<< Break the loop if game is over
            }
    
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
        System.out.println("Game ends!");
        scanner.close();
    }

}
