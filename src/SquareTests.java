/**
 * Created by ema on 27/12/15.
 */
public class SquareTests {

    public static void runSquareTests() {
        constructorTests();
        getColorLetterTests();
        toStringTests();
        setOccupierTests();
    }
    private static void constructorTests() {
        int x = 5;
        int y = 4;
        Square square = new Square(x, y);

        testGetX(square, x);
        testGetY(square, y);
        testOccupiedBy(square, Color.NONE);
    }

    private static void getColorLetterTests() {
        Square blackSquare = new Square(0, 0);
        blackSquare.setOccupier(Color.BLACK);
        Square whiteSquare = new Square(0, 0);
        whiteSquare.setOccupier(Color.WHITE);
        Square emptySquare = new Square(0, 0);
        emptySquare.setOccupier(Color.NONE);

        testGetColorLetter(blackSquare, 'B');
        testGetColorLetter(whiteSquare, 'W');
        testGetColorLetter(emptySquare, '.');

    }

    private static void toStringTests() {
        Square square = new Square(0, 0);
        testToString(square, "a1");

        Square square2 = new Square(1, 0);
        testToString(square2, "b1");

        Square square3 = new Square(1, 1);
        testToString(square3, "b2");

        Square square4 = new Square(1, 2);
        testToString(square4, "b3");

    }

    private static void setOccupierTests() {
        Square blackSquare = new Square(0, 0);
        blackSquare.setOccupier(Color.BLACK);
        Square whiteSquare = new Square(0, 0);
        whiteSquare.setOccupier(Color.WHITE);
        Square emptySquare = new Square(0, 0);
        emptySquare.setOccupier(Color.NONE);

        testOccupiedBy(blackSquare, Color.BLACK);
        testOccupiedBy(whiteSquare, Color.WHITE);
        testOccupiedBy(emptySquare, Color.NONE);
    }

    private static void testGetX(Square square, int expectedX) {
        int actualX = square.getX();
        if (expectedX != actualX) {
            System.out.println("SquareTests: square.getX returned " + actualX +
                " but the expected value was " + expectedX);
        }
    }

    private static void testGetY(Square square, int expectedY) {
        int actualY = square.getY();
        if (expectedY != actualY) {
            System.out.println("SquareTests: square.getY returned " + actualY +
                " but the expected value was " + expectedY);
        }
    }

    public static void testOccupiedBy(Square square, Color expectedColor) {
        Color actualColor = square.occupiedBy();
        if (expectedColor != actualColor) {
            System.out.println("SquareTests: square.OccupiedBy returned " + actualColor +
                " but the expected value was " + expectedColor + ", at position " +
                square.getX() + ", " + square.getY());
        }
    }

    private static void testGetColorLetter(Square square, char expectedColorLetter) {
        char actualColorLetter = square.getColorLetter();
        if (expectedColorLetter != actualColorLetter) {
            System.out.println("SquareTests: square.getColorLetter returned " + actualColorLetter +
                " but the expected value was " + expectedColorLetter);
        }
    }

    private static void testToString(Square square, String expectedString) {
        String actualString = square.toString();
        if (!expectedString.equals(actualString)) {
            System.out.println("SquareTests: square.toString returned " + actualString +
                " but the expected value was " + expectedString);
        }
    }

}