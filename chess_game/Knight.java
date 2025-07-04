// Knight Class
class Knight extends Piece {
    public Knight(Color color) {
        super(color);
    }
    
    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, ChessBoard board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            Piece targetPiece = board.getPiece(toRow, toCol);
            return targetPiece == null || targetPiece.getColor() != this.color;
        }
        return false;
    }
    
    @Override
    public String getSymbol() {
        return color == Color.WHITE ? "N" : "n";
    }
    
    @Override
    public String getName() {
        return "Knight";
    }
}