
/**
 *
 * @author ema
 */
public class ColorUtil {
    public static Color getOppositeColor(Color color) {
        assert(color == Color.BLACK || color == Color.WHITE) :
            "Game.getOppositoColor doens't work with empty squares";
        Color oppositeColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        return oppositeColor;
    }
    
    public static boolean areOppositeColors(Color a, Color b) {
        if(a == Color.NONE || b == Color.NONE) {
            return false;
        }
        return a == getOppositeColor(b);
    }
    
    public static int getDirection(Color a) {
        int change = a == Color.BLACK ? -1 : 1;
        return change;
    }
    
    public static int getColorBorder(Color a) {
        if (a == Color.BLACK) {
            return Board.getSide() - 1;
        }
        return 0;
    }
}
