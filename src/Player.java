
import java.util.*;

/**
 *
 * @author ema
 */
public class Player {
    private Game game;
    private Board board;
    private Color color;
    private boolean isComputerPlayer;
            
    public Player(Game game, Board board, Color color, boolean isComputerPlayer) {
        this.game = game;
        this.board = board;
        this.color = color;
        this.isComputerPlayer = isComputerPlayer;
    }
    
    public Color getColor() {
        return color;
    }
    
    public boolean isComputerPlayer() {
        return isComputerPlayer;
    }
    
    public Square[] getAllPawns() {
        List<Square> pawnsList = new ArrayList<Square>(); 
        
        for (int i = 0; i < board.getSide(); i++) {
            for (int j = 0; j < board.getSide(); j++) {
                Square square = board.getSquare(i, j);
                Color currentColor = square.occupiedBy();
                if(currentColor == color) {
                    pawnsList.add(square);
                }
            }
        }
        Square[] pawnsArray = new Square[pawnsList.size()];
        return pawnsList.toArray(pawnsArray);
    }
    
    public Move[] getAllValidMoves() {
        List<Move> movesList = new ArrayList<Move>(); 

        for (int i = 1; i < board.getSide() - 1; i++) {
            for (int j = 0; j < board.getSide(); j++) {
                addMoves(i, j, movesList);
            }
        }
        
        Move[] pawnsArray = new Move[movesList.size()];
        return movesList.toArray(pawnsArray);    
    }
    
    public boolean isPassedPawn(Square square) {
        Color currentColor = square.occupiedBy();
        if (currentColor != color) {
            return false;
        }
        
        boolean isPassedPawn = true;
        int x = square.getX();
        int y = square.getY();
        int left = Math.max(0, y - 1);
        int right = Math.min(board.getSide() - 1, y + 1);
        int extreme = color == Color.BLACK ? 0 : board.getSide() - 1;
        int change = ColorUtil.getDirection(color);
        int start = Math.min(extreme, x + change);
        int end = Math.max(extreme, x + change);
        
        for (int i = start; i <= end; i++) {
            for (int j = left; j <= right; j++) {
                Square newSquare = board.getSquare(i, j);
                Color newColor = newSquare.occupiedBy();
                if (ColorUtil.areOppositeColors(color, newColor)) {
                    isPassedPawn = false;
                }
            }
        }
        
        return isPassedPawn;     
    }
    
    public void makeMove() {
        if (isComputerPlayer()) {
            Move[] movesList = getAllValidMoves();
            int randomIndex = new Random().nextInt(movesList.length);
            Move move = movesList[randomIndex];
            game.applyMove(move);
        }
    }
    
    private void addMoves(int i, int j, List<Move> movesList) {
        Square square = board.getSquare(i, j);
        Color currentColor = square.occupiedBy();
        
        if(currentColor == color) {
            int change = ColorUtil.getDirection(color);

            //Move straight of 1 position
            Square newSquareStraight = board.getSquare(i + change, j);
            Color newSquareStraightColor = newSquareStraight.occupiedBy();
            if (newSquareStraightColor == Color.NONE) {
                movesList.add(new Move(square, newSquareStraight, false, false));                   
            }
            
            //Move straight of 2 positions (if it has not moved yet)
            if (!game.hasPawnBeenMoved(square)) {
                int toX = i + 2 * change;
                int toY = j;
                if (GeneralUtil.areValidCoord(toX, toY)) {
                    Square newSquare = board.getSquare(toX, toY);
                    Color newSquareColor = newSquare.occupiedBy();
                    if (newSquareColor == Color.NONE) {
                        movesList.add(new Move(square, newSquare, false, false));                   
                    }   
                }
            }

            //Capture a pawn on the left
            if(j > 0) {
                int newSquareLeftX = i + change;
                int newSquareLeftY = j - 1;
                Square newSquareLeft = board.getSquare(newSquareLeftX, newSquareLeftY);
                Color newSquareLeftColor = newSquareLeft.occupiedBy();
                if (ColorUtil.areOppositeColors(color, newSquareLeftColor)) {
                    movesList.add(new Move(square, newSquareLeft, true, false));                   
                } else if (newSquareLeftColor == Color.NONE) {
                    //En passant capture
                    Move lastMove = game.getLastMove();
                    Square from = lastMove.getFrom();
                    int fromX = from.getX();
                    int fromY = from.getY();
                    
                    Square to = lastMove.getTo();
                    int toX = to.getX();
                    int toY = to.getY();
                    
                    if (fromY == newSquareLeftY && fromX == newSquareLeftX + change) {
                        if(toY == newSquareLeftY && toX == newSquareLeftX - change) {
                            movesList.add(new Move(square, newSquareLeft, true, false));                   
                        }
                    }
                }
            }
            
            //Capture a pawn on the left, en passant
            if(j > 0) {
                Square newSquareLeft = board.getSquare(i + change, j - 1);
                Color newSquareLeftColor = newSquareLeft.occupiedBy();
                if (ColorUtil.areOppositeColors(color, newSquareLeftColor)) {
                    movesList.add(new Move(square, newSquareLeft, true, false));                   
                }
            }
            
            //Capture a pawn on the right, 1 position far
            if(j < board.getSide() - 1) {
                Square newSquareRight = board.getSquare(i + change, j + 1);
                Color newSquareRightColor = newSquareRight.occupiedBy();
                if (ColorUtil.areOppositeColors(color, newSquareRightColor)) {
                    movesList.add(new Move(square, newSquareRight, true, false));                   
                }
            }
        }
    }
    
}
