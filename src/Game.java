
/**
 *
 * @author ema
 */
public class Game {
    private Move[] moves = new Move[10000];
    private int movesIndex;
    private Board board;
    private Color currentPlayer;
    private int capturedByWhite;
    private int capturedByBlack;
    private boolean hasWhitePromoted;
    private boolean hasBlackPromoted;
    
    public Game(Board board) {
        this.board = board;
        movesIndex = 0;
        currentPlayer = Color.WHITE;
        capturedByWhite = 0;
        capturedByBlack = 0;
        hasBlackPromoted = false;
        hasWhitePromoted = false;
    }
    
    public Color getCurrentPlayer() {
        return currentPlayer;
    }
    
    public Move getLastMove() {
        if (movesIndex == 0) {
            return null;
        } else {
            return moves[movesIndex - 1];
        }
    }
    
    public boolean isMoveValid(Move move) {
        if (move == null) {
            return false;
        }
        boolean isMoveValid = true;
        
        Square from = move.getFrom();
        Color fromColor = from.occupiedBy();
        int fromX = from.getX();
        int fromY = from.getY();      
        
        Square to = move.getTo();
        Color toColor = to.occupiedBy();
        int toX = to.getX();
        int toY = to.getY();
        
        System.err.println("From square " + fromX + " " + fromY + ", to square " + toX + " " + toY);
        
        int rowChange = toX - fromX;
        int direction = ColorUtil.getDirection(fromColor);
        
        if (fromColor == Color.NONE) {
            return false;
        }
        
        if (rowChange != direction && rowChange != 2 * direction) {
            isMoveValid = false;
        } else if (rowChange == 2) {   //Moving 2 positions
            int betweenX = fromX + direction;
            int betweenY = fromY;
            Square between = board.getSquare(betweenX, betweenY);
            boolean isPathEmpty = isSquareEmpty(to) && isSquareEmpty(between);
            isMoveValid = !hasPawnBeenMoved(from) && isPathEmpty;
        } else if (rowChange == 1) {
            int absoluteColumnChange = Math.abs(fromY - toY);
            
            if(absoluteColumnChange > 1) {
                isMoveValid = false;
            } else if (absoluteColumnChange == 0) {
                isMoveValid = isSquareEmpty(to);
            } else if (absoluteColumnChange == 1) {
                isMoveValid = ColorUtil.areOppositeColors(fromColor, toColor);
            }
        }
        
        return isMoveValid;
    }
    
    public void applyMove(Move move) {
        moves[movesIndex++] = move;
        board.applyMove(move);
        int toX = move.getTo().getX();
        if (toX == 0 || toX == board.getSide() - 1) {
            setPromoted(currentPlayer);
        }
        currentPlayer = ColorUtil.getOppositeColor(currentPlayer);
    }
    
    public void unapplyMove() {
        if (movesIndex > 0) {
            Move lastMove = moves[--movesIndex];
            board.unapplyMove(lastMove);
        }
    }
    
    public boolean isFinished() {
        return isWinningMove() || !anyValidMove();
    }
    
    public Color getGameResult() {
        assert(isFinished()) :
            "Game.getGameResult: you can't get a result of a non-finished game";
        if (hasBlackPromoted || areAllPawnsCapturedByBlack()) {
            return Color.BLACK;
        } 
        return Color.WHITE; 
    }
    
    public Move parseMove(String san) {
        if (san.length() != 2 && san.length() != 5) {
            return null;
        }
        
        boolean isLong = san.length() == 5;
        boolean isCapture = false;
        boolean isEnPassantCapture = false;
        
        if (isLong) {
            if (san.charAt(2) == 'x') {
                isCapture = true;         
            }
        }
        
        if (isCapture) {
            int fromColumn = san.charAt(0) - 'a';
            int fromRow = san.charAt(1) - '1';
            boolean areFromCoordValid = GeneralUtil.areValidCoord(fromRow, fromColumn);
            
            int toColumn = san.charAt(3) - 'a';
            int toRow = san.charAt(4) - '1';
            boolean areToCoordValid = GeneralUtil.areValidCoord(toRow, toColumn);
            
            if (!areFromCoordValid || !areToCoordValid) {
                return null;
            }
            
            Square from = board.getSquare(fromRow, fromColumn);
            Square to = board.getSquare(toRow, toColumn);
            
            if (isSquareEmpty(to)) {
                isEnPassantCapture = true;
            }
                       
            return new Move(from, to, isCapture, isEnPassantCapture);
            
        } else if (san.length() == 2) {
            int toColumn = san.charAt(0) - 'a';
            int toRow = san.charAt(1) - '1';
            boolean areToCoordValid = GeneralUtil.areValidCoord(toRow, toColumn);
            
            if (!areToCoordValid) {
                return null;
            }
            
            Square to = board.getSquare(toRow, toColumn);
            Color color = to.occupiedBy();
            int direction = ColorUtil.getDirection(color);
            
            int fromColumn= toColumn;
            int fromRow = toRow - direction;
            Square from = board.getSquare(fromRow, fromColumn);
            if (isSquareEmpty(from)) {  //The pawn moves of 2 squares
                from = board.getSquare(fromRow - direction, fromColumn);
            }
            
            return new Move(from, to, isCapture, isEnPassantCapture); 
        }
        
        return null;
    }
    
    private boolean isWinningMove() {
        return isPawnPromoted() || areAllPawnsCaptured();
    }
    
    private boolean isPawnPromoted() {
        return hasBlackPromoted || hasWhitePromoted;
    }
    
    private boolean areAllPawnsCaptured() {
        return areAllPawnsCapturedByWhite() || areAllPawnsCapturedByBlack();
    }
    
    private boolean areAllPawnsCapturedByWhite() {
        return capturedByWhite == board.getSide() - 1;
    }
    
    private boolean areAllPawnsCapturedByBlack() {
        return capturedByBlack == board.getSide() - 1;
    }
    
    private boolean anyValidMove() {
        int boardSide = board.getSide();
        
        for (int i = 0; i < boardSide; i++) {
            for (int j = 0; j < boardSide; j++) {
                boolean canPawnMove = canPawnMove(i, j);
                if (canPawnMove) {
                    return true;
                }
            } 
        }
        
        return false;
    }
    
    private boolean canPawnMove(int i, int j) {
        Square square = board.getSquare(i, j);
        Color color = square.occupiedBy();
        int change = ColorUtil.getDirection(color);
        
        if (i + change >= 0 && i + change < board.getSide()) {
            Square nextSquare = board.getSquare(i + change, j);
            Color nextSquareColor = nextSquare.occupiedBy();
            if (nextSquareColor == Color.NONE) {
                return true;
            }
        }
        
        Color leftColor = Color.NONE;
        if (j != 0 && i + change >= 0 && i + change < board.getSide()) {
            Square leftSquare = board.getSquare(i + change, j - 1);
            leftColor = leftSquare.occupiedBy();
        }

        Color rightColor = Color.NONE;
        if (j != board.getSide() - 1 && i + change >= 0 && i + change < board.getSide()) {
            Square rightSquare = board.getSquare(i + change, j + 1);
            rightColor = rightSquare.occupiedBy();
        }

        boolean canMoveLeft = ColorUtil.areOppositeColors(color, leftColor);
        boolean canMoveRight = ColorUtil.areOppositeColors(color, rightColor);
        boolean canMove = canMoveLeft || canMoveRight;

        return canMove;
    }
    
    public boolean hasPawnBeenMoved(Square square) {
        Color color = square.occupiedBy();
        assert(color != Color.NONE) :
            "Cannot call Game.hasPawnBeenMoved on empty square";
        int x = square.getX();
        int y = square.getY();
        int colorBorder = ColorUtil.getColorBorder(color);
        int distanceFromBorder = Math.abs(x - colorBorder);
        
        return distanceFromBorder != 1;
    }
    
    private boolean isSquareEmpty(Square square) {
        Color color = square.occupiedBy();
        return color == Color.NONE;
    }
    
    private void setPromoted(Color color) {
        assert(color != Color.NONE) :
            "Game.setPromoted: Color.None is not a player and cannot be promoted";
        if (color == Color.BLACK) {
            hasBlackPromoted = true;
        } else if (color == Color.WHITE) {
            hasWhitePromoted = true;
        }
    }
}
    
    
