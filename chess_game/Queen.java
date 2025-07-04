// Queen Class
class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }
    
    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, ChessBoard board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        // Queen moves like rook or bishop
        if (fromRow == toRow || fromCol == toCol || rowDiff == colDiff) {
            if (board.isPathClear(fromRow, fromCol, toRow, toCol)) {
                Piece targetPiece = board.getPiece(toRow, toCol);
                return targetPiece == null || targetPiece.getColor() != this.color;
            }
        }
        return false;
    }
    
    @Override
    public String getSymbol() {
        return color == Color.WHITE ? "Q" : "q";
    }
    
    @Override
    public String getName() {
        return "Queen";
    }
}
