
import java.util.Scanner;

/**
 *
 * @author ema
 */
public class PawnRace {
    
    public static void main(String[] args) {
        // TODO code application logic here
        assert(args.length == 4) :
            "Console input must contain: playerA, playerB, whiteGap and blackGap";

        Scanner scanner = new Scanner(System.in);

        boolean isPlayerAComputer = args[0].charAt(0) == 'C';
        boolean isPlayerBComputer = args[1].charAt(0) == 'C';
        char whiteGap = args[2].charAt(0);
        char blackGap = args[3].charAt(0);
        
        Board board = new Board(whiteGap, blackGap);
        Game game = new Game(board);
        Player playerA = new Player(game, board, Color.WHITE, isPlayerAComputer);
        Player playerB = new Player(game, board, Color.BLACK, isPlayerBComputer);
        
        while (!game.isFinished()) {
            board.display();
            System.out.println();
            Color currentColor = game.getCurrentPlayer();
            
            Player currentPlayer = currentColor == Color.WHITE ? playerA : playerB;
            boolean isComputerPlayer = currentPlayer.isComputerPlayer();
            if (isComputerPlayer) {
                currentPlayer.makeMove();
            } else {
                System.out.println("Insert your move");
                String moveString = scanner.nextLine();
                Move move = game.parseMove(moveString);
                while (!game.isMoveValid(move)) {
                    System.out.println("The move you entered is invalid, please try again");
                    moveString = scanner.nextLine();
                    move = game.parseMove(moveString);
                }
                Square from = move.getFrom();
                Square to = move.getTo();
                int fromX = from.getX();
                int fromY = from.getY();
                int toX = to.getX();
                int toY = to.getY();
                System.out.println("From square " + fromX + " " + fromY + ", to square " + toX + " " + toY);
                
                
                game.applyMove(move);
            }
        }
        
        board.display();
        System.out.println();
        System.out.println("The winner is " + game.getGameResult());
    }
    
}
