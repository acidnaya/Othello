import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.min;

public class Move implements Comparable<Move> {
    private final int x;
    private final int y;

    private int edges;
    private final char symbol;
    private final List<Move> recolored;

    public char getSymbol() {
        return symbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Move> getRecolored() {
        return recolored;
    }

    public int getAddedPoints() {
        int addedPoints = 0;
        for (Move m: recolored) {
            addedPoints += 10 * (min(m.edges + 1, 2));
        }
        return addedPoints + 4 * edges;
    }

    Move(int x, int y, char symbol, int size) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.recolored = new ArrayList<>();
        edges = 0;
        if (x == 0 || x == size - 1) {
            edges++;
        }
        if (y == 0 || y == size - 1) {
            edges++;
        }
    }

    Move(int x, int y, char symbol, List<Move> recolored) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.recolored = recolored;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Move)) {
            return false;
        }
        Move otherMove = (Move)other;
        return x == otherMove.x && y == otherMove.y && symbol == otherMove.symbol;
    }

    @Override
    public int compareTo(Move other) {
        return getAddedPoints() - other.getAddedPoints();
    }
}
