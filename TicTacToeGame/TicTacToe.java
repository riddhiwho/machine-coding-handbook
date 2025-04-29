package TicTacToeGame;

import java.util.Scanner;

public class TicTacToe {

    String[][] board;
    String x;
    String o;
    // can make it static?
    static String currentPlayer;

    // single instance? try to follow singleton pattern
    public TicTacToe(){
        this.board = new String[3][3];
        // initialise default values?
        x = "X";
        o = "O";
        currentPlayer = x;
    }

    // public static String checkRows(String[][] board){
    //     for(int i=0; i<board.length; i++){
    //         String val = board[i][0];
    //         if(val.equals("")){
    //             continue;
    //         }
    //         boolean sameRow = true;
    //         for(int j=1; j<board.length; j++){
    //             if(!board[i][j].equals(val)){
    //                 sameRow=false;
    //                 break;
    //             }
    //         }
    //         if(sameRow) return val;
    //     }
    //     return null;
    // }

    // public static boolean checkCols(String[][] board){
    //     for(int i=0; i<2; i++){
    //         String val = board[0][i];
    //         for(int j=0; j<2; j++){
    //             if(board[i][j]!=val){
    //                 return false;
    //             }
    //         }
    //     }
    //     return true;
    // }

    // public static boolean checkDiagonal1(String[][] board){
    //     String val = board[0][0];
    //     for(int i=0; i<2; i++){
    //         if(board[i][i]!=val){
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    // public static boolean checkDiagonal2(String[][] board){
    //     String val = board[0][2];
    //     int row=0;
    //     int col=2;
    //     for(int i=0; i<2; i++){
    //         if(board[row][col]!=val){
    //             return false;
    //         }
    //         row++;
    //         col--;
    //     }
    //     return true;
    // }

    // public static boolean checkDraw(String[][] board){
    //     if(!checkRows(board) && !checkCols(board) && !checkDiagonal1(board) && !checkDiagonal2(board)){
    //         for(int i=0; i<board.length; i++){
    //             for(int j=0; j<board.length; j++){
    //                 if(board[i][j]!=null){
    //                     return false;
    //                 }
    //             }
    //         }
    //     }
    //     return true;
    // }

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
    
    

    // public static boolean isWinOrDraw(String[][] board, String x, String o){
    //     return false;
    //     // return (checkRows(board) || checkCols(board) || checkDiagonal1(board) || checkDiagonal2(board) || checkDraw(board));
    // }

    public static void makeAMove(String[][] board, String x, String o, int[] move){
        if(move[0]>=0 && move[0]<=board.length && move[1]>=0 && move[1]<=board.length ){
            int row = move[0];
            int col = move[1];
            board[row][col] = currentPlayer;
        }else{
            // will move print address or values?
            System.out.println("Invalid move: " + move + ". Should be within the bounds."); 
            return;
        }
    }

    public String playGame(){
        String res = "";
        
        System.out.println("Game starts!");
        System.out.println("Turn: " + currentPlayer);
        Scanner scanner = new Scanner(System.in);
        while (checkGameStatus(board)!=null) {
            //make a move
            // take cmd line arg?
            
            // System.out.println(currentPlayer + " to make the move: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            makeAMove(board, x, o, new int[]{row, col});
            currentPlayer = currentPlayer=="X" ? "O" : "X";
        }   
        scanner.close();
        return res;
    }

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.playGame();
    }

}
