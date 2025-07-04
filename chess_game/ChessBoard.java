// Chess Board Class

import java.util.ArrayList;
import java.util.List;

class ChessBoard {
    private Piece[][] board;
    private static final int SIZE = 8;
    
    public ChessBoard() {
        board = new Piece[SIZE][SIZE];
    }
    
    public void initializeBoard() {
        // Clear board
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = null;
            }
        }
        
        // Place white pieces (bottom of board - row 7,6)
        board[7][0] = new Rook(Color.WHITE);
        board[7][1] = new Knight(Color.WHITE);
        board[7][2] = new Bishop(Color.WHITE);
        board[7][3] = new Queen(Color.WHITE);
        board[7][4] = new King(Color.WHITE);
        board[7][5] = new Bishop(Color.WHITE);
        board[7][6] = new Knight(Color.WHITE);
        board[7][7] = new Rook(Color.WHITE);
        
        for (int i = 0; i < SIZE; i++) {
            board[6][i] = new Pawn(Color.WHITE);
        }
        
        // Place black pieces (top of board - row 0,1)
        board[0][0] = new Rook(Color.BLACK);
        board[0][1] = new Knight(Color.BLACK);
        board[0][2] = new Bishop(Color.BLACK);
        board[0][3] = new Queen(Color.BLACK);
        board[0][4] = new King(Color.BLACK);
        board[0][5] = new Bishop(Color.BLACK);
        board[0][6] = new Knight(Color.BLACK);
        board[0][7] = new Rook(Color.BLACK);
        
        for (int i = 0; i < SIZE; i++) {
            board[1][i] = new Pawn(Color.BLACK);
        }
    }
    
    public Piece getPiece(int row, int col) {
        if (isValidPosition(row, col)) {
            return board[row][col];
        }
        return null;
    }
    
    public void setPiece(int row, int col, Piece piece) {
        if (isValidPosition(row, col)) {
            board[row][col] = piece;
        }
    }
    
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }
    
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isValidPosition(fromRow, fromCol) || !isValidPosition(toRow, toCol)) {
            return false;
        }
        
        if (fromRow == toRow && fromCol == toCol) {
            return false;
        }
        
        Piece piece = getPiece(fromRow, fromCol);
        if (piece == null) {
            return false;
        }
        
        if (!piece.isValidMove(fromRow, fromCol, toRow, toCol, this)) {
            return false;
        }
        
        // Check if move would put own king in check
        Piece capturedPiece = getPiece(toRow, toCol);
        setPiece(toRow, toCol, piece);
        setPiece(fromRow, fromCol, null);
        
        boolean wouldBeInCheck = isInCheck(piece.getColor());
        
        // Restore board state
        setPiece(fromRow, fromCol, piece);
        setPiece(toRow, toCol, capturedPiece);
        
        return !wouldBeInCheck;
    }
    
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = getPiece(fromRow, fromCol);
        if (piece != null) {
            setPiece(toRow, toCol, piece);
            setPiece(fromRow, fromCol, null);
            piece.setMoved(true);
        }
    }
    
    public boolean isInCheck(Color color) {
        int[] kingPos = findKing(color);
        if (kingPos == null) return false;
        
        Color opponentColor = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
        
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Piece piece = getPiece(row, col);
                if (piece != null && piece.getColor() == opponentColor) {
                    if (piece.isValidMove(row, col, kingPos[0], kingPos[1], this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isCheckmate(Color color) {
        if (!isInCheck(color)) return false;
        return !hasValidMoves(color);
    }
    
    public boolean isStalemate(Color color) {
        if (isInCheck(color)) return false;
        return !hasValidMoves(color);
    }
    
    private boolean hasValidMoves(Color color) {
        for (int fromRow = 0; fromRow < SIZE; fromRow++) {
            for (int fromCol = 0; fromCol < SIZE; fromCol++) {
                Piece piece = getPiece(fromRow, fromCol);
                if (piece != null && piece.getColor() == color) {
                    for (int toRow = 0; toRow < SIZE; toRow++) {
                        for (int toCol = 0; toCol < SIZE; toCol++) {
                            if (isValidMove(fromRow, fromCol, toRow, toCol)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private int[] findKing(Color color) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Piece piece = getPiece(row, col);
                if (piece instanceof King && piece.getColor() == color) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }
    
    public boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDir = Integer.compare(toRow, fromRow);
        int colDir = Integer.compare(toCol, fromCol);
        
        int currentRow = fromRow + rowDir;
        int currentCol = fromCol + colDir;
        
        while (currentRow != toRow || currentCol != toCol) {
            if (getPiece(currentRow, currentCol) != null) {
                return false;
            }
            currentRow += rowDir;
            currentCol += colDir;
        }
        return true;
    }
    
    public void displayBoard() {
        System.out.println("\n   a b c d e f g h");
        System.out.println("  " + "-".repeat(17));
        
        for (int row = 0; row < SIZE; row++) {
            System.out.print((8 - row) + "| ");
            for (int col = 0; col < SIZE; col++) {
                Piece piece = getPiece(row, col);
                if (piece == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(piece.getSymbol() + " ");
                }
            }
            System.out.println("|" + (8 - row));
        }
        
        System.out.println("  " + "-".repeat(17));
        System.out.println("   a b c d e f g h");
    }
    
    public List<String> getAllValidMoves(Color color) {
        List<String> moves = new ArrayList<>();
        
        for (int fromRow = 0; fromRow < SIZE; fromRow++) {
            for (int fromCol = 0; fromCol < SIZE; fromCol++) {
                Piece piece = getPiece(fromRow, fromCol);
                if (piece != null && piece.getColor() == color) {
                    for (int toRow = 0; toRow < SIZE; toRow++) {
                        for (int toCol = 0; toCol < SIZE; toCol++) {
                            if (isValidMove(fromRow, fromCol, toRow, toCol)) {
                                String move = "" + (char)('a' + fromCol) + (8 - fromRow) + 
                                            (char)('a' + toCol) + (8 - toRow);
                                moves.add(move);
                            }
                        }
                    }
                }
            }
        }
        
        return moves;
    }
}