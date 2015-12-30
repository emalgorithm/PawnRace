/**
 * Created by ema on 27/12/15.
 */
public class BoardTests {
    public static void runBoardTests() {
        constructorTests();
    }

    private static void constructorTests() {
        testConstructor(new Board('c', 'f'), 2, 5);
//        testConstructor(new Board('a', 'g'), 1, 6);
//        testConstructor(new Board('d', 'd'), 3, 3);
    }

    private static void testConstructor(Board board, int whiteGap, int blackGap) {
        for (int i = 0; i < Board.getSide(); i++) {
            for (int j = 0; j < Board.getSide(); j++) {
                Square square = board.getSquare(i, j);
                if (i == 1) {
                    if (j == whiteGap) {
                        SquareTests.testOccupiedBy(square, Color.NONE);
                    } else {
                        SquareTests.testOccupiedBy(square, Color.WHITE);
                    }
                } else if (i == 6) {
                    if (j == blackGap) {
                        SquareTests.testOccupiedBy(square, Color.NONE);
                    } else {
                        SquareTests.testOccupiedBy(square, Color.BLACK);
                    }
                } else {
                    SquareTests.testOccupiedBy(square, Color.NONE);
                }
            }
        }
    }
}
