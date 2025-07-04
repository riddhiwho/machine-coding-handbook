
// Pawn Class
class Pawn extends Piece {
    public Pawn(Color color) {
        super(color);
    }
    
    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, ChessBoard board) {
        int direction = (color == Color.WHITE) ? -1 : 1;
        int rowDiff = toRow - fromRow;
        int colDiff = Math.abs(toCol - fromCol);
        
        // Forward move
        if (colDiff == 0) {
            if (rowDiff == direction && board.getPiece(toRow, toCol) == null) {
                return true;
            }
            // Initial two-square move
            if (!hasMoved && rowDiff == 2 * direction && 
                board.getPiece(toRow, toCol) == null && 
                board.getPiece(fromRow + direction, fromCol) == null) {
                return true;
            }
        }
        // Diagonal capture
        else if (colDiff == 1 && rowDiff == direction) {
            Piece targetPiece = board.getPiece(toRow, toCol);
            return targetPiece != null && targetPiece.getColor() != this.color;
        }
        
        return false;
    }
    
    @Override
    public String getSymbol() {
        return color == Color.WHITE ? "P" : "p";
    }
    
    @Override
    public String getName() {
        return "Pawn";
    }
}