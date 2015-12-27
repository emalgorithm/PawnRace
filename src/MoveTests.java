/**
 * Created by ema on 27/12/15.
 */
public class MoveTests {
    public static void runMoveTests() {
        constructorTests();
        getSANTests();
    }

    private static void constructorTests() {
        Square from = new Square(0, 0);
        Square to = new Square(1, 0);
        Move move = new Move(from, to, false, false);

        testGetFrom(move, from);
        testGetTo(move, to);
        testIsCapture(move, false);
        testIsEnPassantCapture(move, false);

    }

    private static void getSANTests() {
        Square from = new Square(0, 0);
        Square to = new Square(1, 0);
        Move move = new Move(from, to, true, false);

        testGetSAN(move, from.toString() + "x" + to.toString());


        Square from2 = new Square(0, 0);
        Square to2 = new Square(1, 0);
        Move move2 = new Move(from2, to2, false, false);

        testGetSAN(move2, to2.toString());
    }

    private static void testGetFrom(Move move, Square expectedFrom) {
        Square actualFrom = move.getFrom();
        if (expectedFrom != actualFrom) {
            System.out.println("MoveTests: move.getFrom returned " + actualFrom +
                    " but the expected value was " + expectedFrom);
        }
    }

    private static void testGetTo(Move move, Square expectedTo) {
        Square actualTo = move.getTo();
        if (expectedTo != actualTo) {
            System.out.println("MoveTests: move.getTo returned " + actualTo +
                    " but the expected value was " + expectedTo);
        }
    }

    private static void testIsCapture(Move move, boolean expectedIsCapture) {
        boolean actualIsCapture = move.isCapture();
        if (expectedIsCapture != actualIsCapture) {
            System.out.println("MoveTests: move.isCapture returned " + actualIsCapture +
                    " but the expected value was " + expectedIsCapture);
        }
    }

    private static void testIsEnPassantCapture(Move move, boolean expectedIsEnPassantCapture) {
        boolean actualIsEnPassantCapture = move.isEnPassantCapture();
        if (expectedIsEnPassantCapture != actualIsEnPassantCapture) {
            System.out.println("MoveTests: move.isEnPassantCapture returned " + actualIsEnPassantCapture +
                    " but the expected value was " + expectedIsEnPassantCapture);
        }
    }

    private static void testGetSAN(Move move, String expectedSAN) {
        String actualSAN = move.getSAN();
        if (!expectedSAN.equals(actualSAN)) {
            System.out.println("MoveTests: move.getSAN returned " + actualSAN +
                    " but the expected value was " + expectedSAN);
        }
    }
}
