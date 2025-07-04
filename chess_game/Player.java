// Player Class
class Player {
    private Color color;
    
    public Player(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return color == Color.WHITE ? "White" : "Black";
    }
}
