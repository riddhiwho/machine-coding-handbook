package TicTacToeGame;

import java.util.Scanner;

public class TicTacToeDraft {
    String[][] board;
    String currentPlayer;

    public TicTacToeDraft(){
        this.board = new String[3][3];
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++){
                board[i][j] = "";
            }
        }
        currentPlayer = "X";
    }

    public static String checkGameStatus(String[][] board) {
        int n = board.length;
        boolean isBoardFull = true;
    
        // Check rows and columns
        for (int i = 0; i < n; i++) {
            // Check row
            String rowVal = board[i][0];
            if (rowVal.equals("")) {
                continue;  // Skip this row if the first cell is empty
            }
            boolean sameRow = true;
            
            for (int j = 1; j < n; j++) {
                if (!board[i][j].equals(rowVal)) {
                    sameRow = false;
                    break;
                }
                if (board[i][j].equals("")) {
                    isBoardFull = false;
                }
            }
            if (sameRow) {
                return rowVal;
            }
    
            // Check column
            String colVal = board[0][i];
            if (colVal.equals("")) {
                continue;  // Skip this column if the first cell is empty
            }
            boolean sameCol = true;
    
            for (int j = 1; j < n; j++) {
                if (!board[j][i].equals(colVal)) {
                    sameCol = false;
                    break;
                }
                if (board[j][i].equals("")) {
                    isBoardFull = false;
                }
            }
            if (sameCol) {
                return colVal;
            }
        }
    
        // Check diagonals
        String diag1Val = board[0][0];
        if (!diag1Val.equals("")) {
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
        if (!diag2Val.equals("")) {
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

    public void makeAMove(int row, int col){
        if(row>=0 && row<board.length && col>=0 && col<board.length && (board[row][col].equals("")) ){
            // try catch - throw proper exceptions
            // unit testing
            board[row][col] = currentPlayer;
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }else{
            // will move print address or values?
            System.out.println("Invalid move. ");
            if(!board[row][col].equals("")){
                System.out.println("Already occupied: " +row + " " + col); 
            }
            if(row<0 || row > 2 || col<0 || col>2){
                System.out.println("Out of bounds: " + row + " " + col);
            }
            
            return;
        }
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j].equals("") ? "_" : board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void playGame(){        
        System.out.println("Game starts!");
        Scanner scanner = new Scanner(System.in);

        while (checkGameStatus(board)==null) {
            printBoard();
            System.out.println("Turn: " + currentPlayer);

            System.out.print("Insert Row (0-2): ");
            int row = scanner.nextInt();
            System.out.print("Insert Column (0-2): ");
            int col = scanner.nextInt();

            makeAMove(row, col);

            String result = checkGameStatus(board);
            if (result != null) {
                printBoard();
                System.out.println("Result: " + result);
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        TicTacToeDraft ticTacToe = new TicTacToeDraft();
        ticTacToe.playGame();
    }
}
