import java.util.List;
import java.util.Optional;

public abstract class Player {
    protected char symbol;

    public char getSymbol() {
        return symbol;
    }

    public Player(char symbol) {
        this.symbol = symbol;
    }
    public abstract Optional<Move> makeMove(Board board, List<Move> availableMoves);
}

class StupidBot extends Player {

    public StupidBot(char symbol) {
        super(symbol);
    }

    @Override
    public Optional<Move> makeMove(Board board, List<Move> availableMoves) {
        Move move = null;
        if (!availableMoves.isEmpty())
            move = java.util.Collections.max(availableMoves);
        return Optional.ofNullable(move);
    }
}

class CleverBot extends Player {
    StupidBot opponent;

    public CleverBot(char selfSymbol, char opponentSymbol) {
        super(selfSymbol);
        opponent = new StupidBot(opponentSymbol);
    }

    @Override
    public Optional<Move> makeMove(Board board, List<Move> availableMoves) {
        java.util.Collections.sort(availableMoves);
        Move bestMove = null;
        int biggestPoint = -1000; // поменять?
        for (Move move : availableMoves) {
            var currentBoard = new Board(board);
            currentBoard.addMove(move);
            var opMove = opponent.makeMove(currentBoard, currentBoard.getAvailableMoves(opponent.getSymbol()));
            if ((opMove.isPresent() ? move.compareTo(opMove.get()) : move.getAddedPoints()) >= biggestPoint) {
                bestMove = move;
                biggestPoint = bestMove.getAddedPoints();
            }
        }
        return Optional.ofNullable(bestMove);
    }
}

class HumanPlayer extends Player {

    public HumanPlayer(char symbol) {
        super(symbol);
    }

    private Move getMoveInput(List<Move> availableMoves) {
        System.out.println("Please choose and enter a move.");
        while (true) {
            int row = UserInterface.getInt("Please enter a number of row:");
            int column = UserInterface.getInt("Please enter a number of column:");
            Move humansMove = new Move(row, column, symbol, 0);
            for (Move move : availableMoves) {
                if(move.equals(humansMove))
                    return move;
            }
            System.out.println("Please enter a correct move!");
        }
    }

    @Override
    public Optional<Move> makeMove(Board board, List<Move> availableMoves) {
        Move move = null;
        if (!UserInterface.playerWantsToUndoMove()) {
            move = getMoveInput(availableMoves);
        }
        return Optional.ofNullable(move);
    }
}
