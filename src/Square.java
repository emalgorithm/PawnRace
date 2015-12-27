
/**
 *
 * @author ema
 */
public class Square {
    private int x;
    private int y;
    private Color color;
    
    public Square(int x, int y) {
        assert(x >= 0 && x < Board.getSide() && y >= 0 && y < Board.getSide()) :
            "Square.Square: square's coordinates must be between 0 and " +
                Board.getSide();

        this.x = x;
        this.y = y;
        this.color = Color.NONE;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Color occupiedBy() {
        return color;
    }
    
    public char getColorLetter() {
        char colorLetter = '.';
        if (color == Color.BLACK) {
            colorLetter = 'B';
        } else if (color == Color.WHITE) {
            colorLetter = 'W';
        }
        return colorLetter;
    }
    
    public void setOccupier(Color color) {
        this.color = color;
    }
    
    public String toString() {
        String row = Character.toString((char)('a' + x));
        String column = Character.toString((char)('1' + y));
        return row + column;
    }
}
