
/**
 *
 * @author ema
 */
public class GeneralUtil {
    public static boolean areValidCoord(int x, int y) {
        boolean isXValid = x >= 0 && x < Board.getSide();
        boolean isYValid = y >= 0 && y < Board.getSide();
        
        return isXValid && isYValid;
    }
}
