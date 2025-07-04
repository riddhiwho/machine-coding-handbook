// Bishop Class
class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
    }
    
    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, ChessBoard board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        if (rowDiff == colDiff && rowDiff > 0) {
            if (board.isPathClear(fromRow, fromCol, toRow, toCol)) {
                Piece targetPiece = board.getPiece(toRow, toCol);
                return targetPiece == null || targetPiece.getColor() != this.color;
            }
        }
        return false;
    }
    
    @Override
    public String getSymbol() {
        return color == Color.WHITE ? "B" : "b";
    }
    
    @Override
    public String getName() {
        return "Bishop";
    }
}