// Abstract Piece Class
abstract class Piece {
    protected Color color;
    protected boolean hasMoved;
    
    public Piece(Color color) {
        this.color = color;
        this.hasMoved = false;
    }
    
    public Color getColor() {
        return color;
    }
    
    public boolean hasMoved() {
        return hasMoved;
    }
    
    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }
    
    public abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, ChessBoard board);
    public abstract String getSymbol();
    public abstract String getName();
}
