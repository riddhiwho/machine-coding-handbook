// King Class
class King extends Piece {
    public King(Color color) {
        super(color);
    }
    
    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, ChessBoard board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        if (rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff > 0)) {
            Piece targetPiece = board.getPiece(toRow, toCol);
            return targetPiece == null || targetPiece.getColor() != this.color;
        }
        return false;
    }
    
    @Override
    public String getSymbol() {
        return color == Color.WHITE ? "K" : "k";
    }
    
    @Override
    public String getName() {
        return "King";
    }
}