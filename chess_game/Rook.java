// Rook Class
class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }
    
    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, ChessBoard board) {
        if (fromRow == toRow || fromCol == toCol) {
            if (board.isPathClear(fromRow, fromCol, toRow, toCol)) {
                Piece targetPiece = board.getPiece(toRow, toCol);
                return targetPiece == null || targetPiece.getColor() != this.color;
            }
        }
        return false;
    }
    
    @Override
    public String getSymbol() {
        return color == Color.WHITE ? "R" : "r";
    }
    
    @Override
    public String getName() {
        return "Rook";
    }
}