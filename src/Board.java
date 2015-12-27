
/**
 *
 * @author ema
 */
public class Board {
    private static final int side = 8;
    private Square[][] chessboard = new Square[side][side];
    
    public Board(char whiteGapChar, char blackGapChar) {
        int whiteGap = whiteGapChar - 'A';
        int blackGap = blackGapChar - 'A';
        
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                chessboard[i][j] = new Square(i, j);
            }
        }
        
        int whiteStartingRow = 1;
        int blackStartingRow = 6;
        
        for (int i = 0; i < side; i++) {
            if(i != whiteGap) {
                chessboard[whiteStartingRow][i].setOccupier(Color.WHITE);
            }
        }
        
        for (int i = 0; i < side; i++) {
            if (i != blackGap) {
                chessboard[blackStartingRow][i].setOccupier(Color.BLACK);
            }
        }
    }
    
    public static int getSide() {
        return side;
    }
    
    public Square getSquare(int x, int y) {
        assert(x >= 0 && x < side && y >= 0 && y < side) :
            "Board.getSquare: Tried to get a square outside the board";
        return chessboard[x][y];
    }
    
    public void applyMove(Move move) {
        Square from = move.getFrom();
        Square to = move.getTo();
        Color fromColor = from.occupiedBy();
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();
        
        chessboard[fromX][fromY].setOccupier(Color.NONE);
        chessboard[toX][toY].setOccupier(fromColor);
    }
    
    public void unapplyMove(Move move) {
        Square from = move.getFrom();
        Square to = move.getTo();
        Color fromColor = from.occupiedBy();
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();
        
        if (move.isCapture()) {
            Color toColor = fromColor == Color.BLACK ? Color.WHITE : Color.BLACK;
            chessboard[toX][toY].setOccupier(toColor);
            chessboard[fromX][fromY].setOccupier(fromColor);
        } else {
            chessboard[toX][toY].setOccupier(Color.NONE); 
            chessboard[fromX][fromY].setOccupier(fromColor);
        } 
    }
    
    public void display() {
        int spaces = 3;
        
        printLettersLine(spaces);
        System.out.println();
        
        for (int i = side - 1; i >= 0; i--) {
            printChessboardRow(i, spaces - 1);
            System.out.println();
        }
        
        System.out.println();
        printLettersLine(spaces);
    }
    
    private void printSpaces(int spaces) {
        for (int i = 0; i < spaces; i++) {
            System.out.print(" ");
        }
    }
    
    private void printLettersLine(int spaces) {
        String columns = "ABCDEFGH";
        
        printSpaces(spaces);

        for (char letter : columns.toCharArray()) {
            System.out.print(letter + " ");
        }
        
        printSpaces(spaces - 1);
    }
    
    private void printChessboardRow(int row, int spaces) {
        System.out.print(row + 1);
        printSpaces(spaces);

        for (int j = 0; j < side; j++) {
            char colorLetter = chessboard[row][j].getColorLetter();
            System.out.print(colorLetter + " ");
        }

        printSpaces(spaces - 1);
        System.out.print(row + 1);
    }
}
