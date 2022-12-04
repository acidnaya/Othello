import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;

public class Game {
    private Board board;
    private final List<Board> states;
    private final Player firstPlayer;
    private final Player secondPlayer;

    Game(int BoardSize, Player first, Player second, char spaceSymbol, char availableSymbol) {
        board = new Board(BoardSize, first.getSymbol(), second.getSymbol(), spaceSymbol, availableSymbol);
        firstPlayer = first;
        secondPlayer = second;
        states = new ArrayList<>();
        states.add(new Board(board));
    }

    private int getMaxHumanScore() {
        int firstScore = board.getScore(firstPlayer.symbol);
        int secondScore = board.getScore(secondPlayer.symbol);
        if (firstScore > secondScore) {
            System.out.println(firstPlayer.getSymbol() + "–player won with score " + firstScore + "!");
        } else if (firstScore < secondScore) {
            System.out.println(secondPlayer.getSymbol() + "–player won with score " + secondScore + "!");
        } else {
            System.out.println("Tie!");
        }
        return secondPlayer instanceof HumanPlayer ? max(firstScore, secondScore) : firstScore;
    }

    public int processGame() {
        board.print();
        Player currentPlayer = firstPlayer;
        while (true) {
            var availableMoves = board.getAvailableMoves(currentPlayer.getSymbol());
            if (availableMoves.isEmpty()) {
                if (states.size() >= 2 && states.get(states.size() - 1).equals(states.get(states.size() - 2))) {
                    System.out.println("No available moves for both players. Game is over!");
                    break;
                }
                states.add(new Board(board));
                System.out.println("No available moves for " + currentPlayer.getSymbol() + "–player!");
            } else {
                System.out.println("Your turn " + currentPlayer.getSymbol() + "–player!");
                board.printAvailableMoves(availableMoves);
                var move = currentPlayer.makeMove(new Board(board), availableMoves);
                if (move.isEmpty()) {
                    if (states.size() > 2) {
                        board = new Board(states.get(states.size() - 3));
                        states.remove(states.size() - 1);
                        states.remove(states.size() - 1);
                    }
                    continue;
                }
                else {
                    board.addMove(move.get());
                    states.add(new Board(board));
                    board.print();
                }
            }
            currentPlayer = currentPlayer.equals(firstPlayer) ? secondPlayer : firstPlayer;
        }
        return getMaxHumanScore();
    }
}
