package TicTacToeGame;

public class Board {
    private String[][] board;
    private int size = 3;

    public Board(){
        board = new String[size][size];
        initialiseBoard();
    }

    private void initialiseBoard(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                board[i][j] = "-";
            }
        }
    }

    public void makeAMove(int row, int col, Player currentPlayer){
        if (row >= 0 && row < size && col >= 0 && col < size) {
            if (board[row][col].equals("-")) {
                board[row][col] = currentPlayer.getSymbol();
            } else {
                System.out.println("Cell already occupied at (" + row + ", " + col + ")");
            }
        } else {
            System.out.println("Out of bounds move at (" + row + ", " + col + ")");
        }
    }

    public void printBoard(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String[][] getBoardArray() {
        return board;
    }

}
