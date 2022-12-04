import java.util.ArrayList;
import java.util.List;

public class Board {
    private final char[][] board;
    private final int size;
    private final char spaceSymbol;
    private final char availableSymbol;

    Board(int boardSize, char firstSymbol, char secondSymbol, char spaceSymbol, char availableSymbol) {
        this.spaceSymbol = spaceSymbol;
        this.availableSymbol = availableSymbol;
        size = boardSize;
        board = new char[size][size];
        initEmptyBoard();
        board[boardSize / 2 - 1][boardSize / 2 - 1] = firstSymbol;
        board[boardSize / 2][boardSize / 2] = firstSymbol;
        board[boardSize / 2][boardSize / 2 - 1] = secondSymbol;
        board[boardSize / 2 - 1][boardSize / 2] = secondSymbol;
    }

    Board(Board other) {
        this.spaceSymbol = other.spaceSymbol;
        this.availableSymbol = other.availableSymbol;
        this.size = other.size;
        board = new char[other.size][];
        for (int i = 0; i < other.size; i++)
            board[i] = other.board[i].clone();
    }

    private void initEmptyBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = spaceSymbol;
            }
        }
    }

    private boolean addCellToList(List<Move> cells, char symbol, int x, int y) { // ууууу
        if (x < 0 || y < 0 || x >= size || y >= size) {
            cells.clear();
            return false;
        }
        if (board[x][y] == spaceSymbol) {
            cells.clear();
            return false;
        } else if (board[x][y] == symbol) {
            return false;
        }
        cells.add(new Move(x, y, symbol, size)); //
        return true;
    }

    private List<Move> getRecoloredVerticalCells(char symbol, int x, int y) {
        List<Move> upCells = new ArrayList<>();
        List<Move> downCells = new ArrayList<>();
        int i = x - 1;
        while (addCellToList(upCells, symbol, i, y)) {
            i--;
        }
        i = x + 1;
        while (addCellToList(downCells, symbol, i, y)) {
            i++;
        }
        upCells.addAll(downCells);
        return upCells;
    }

    private List<Move> getRecoloredHorizontalCells(char symbol, int x, int y) {
        List<Move> leftCells = new ArrayList<>();
        List<Move> rightCells = new ArrayList<>();
        int j = y - 1;
        while (addCellToList(leftCells, symbol, x, j)) {
            j--;
        }
        j = y + 1;
        while (addCellToList(rightCells, symbol, x, j)) {
            j++;
        }
        leftCells.addAll(rightCells);
        return leftCells;
    }

    private List<Move> getRecoloredDiagonal1Cells(char symbol, int x, int y) {
        List<Move> leftUpCells = new ArrayList<>();
        List<Move> rightDownCells = new ArrayList<>();
        int i = x - 1;
        int j = y - 1;
        while (addCellToList(leftUpCells, symbol, i, j)) {
            i--;
            j--;
        }
        i = x + 1;
        j = y + 1;
        while (addCellToList(rightDownCells, symbol, i, j)) {
            i++;
            j++;
        }
        leftUpCells.addAll(rightDownCells);
        return leftUpCells;
    }

    private List<Move> getRecoloredDiagonal2Cells(char symbol, int x, int y) {
        List<Move> rightUpCells = new ArrayList<>();
        List<Move> leftDownCells = new ArrayList<>();
        int i = x - 1;
        int j = y + 1;
        while (addCellToList(rightUpCells, symbol, i, j)) {
            i--;
            j++;
        }
        i = x + 1;
        j = y - 1;
        while (addCellToList(leftDownCells, symbol, i, j)) {
            i++;
            j--;
        }
        rightUpCells.addAll(leftDownCells);
        return rightUpCells;
    }

    private List<Move> getRecoloredCells(char symbol, int x, int y) {
        var recoloredCells = getRecoloredHorizontalCells(symbol, x, y);
        recoloredCells.addAll(getRecoloredVerticalCells(symbol, x, y));
        recoloredCells.addAll(getRecoloredDiagonal1Cells(symbol, x, y));
        recoloredCells.addAll(getRecoloredDiagonal2Cells(symbol, x, y));
        return recoloredCells;
    }

    public List<Move> getAvailableMoves(char symbol) {
        List<Move> availableMoves = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == spaceSymbol) {
                    var recoloredCells = getRecoloredCells(symbol, i, j);
                    if (recoloredCells.size() > 0) {
                        availableMoves.add(new Move(i, j, symbol, recoloredCells));
                    }
                }
            }
        }
        return availableMoves;
    }

    public void addMove(Move move) {
        board[move.getX()][move.getY()] = move.getSymbol();
        for (Move recolored : move.getRecolored()) {
            board[recolored.getX()][recolored.getY()] = recolored.getSymbol();
        }
    }

    public void print() {
        System.out.print(spaceSymbol);
        for (int i = 0; i < size; i++) {
            System.out.print("|" + i);
        }
        System.out.println("|");
        for (int i = 0; i < size; i++) {
            System.out.print(i);
            for (int j = 0; j < size; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.println("|");
        }
        System.out.println();
    }

    public void printAvailableMoves(List<Move> availableMoves) {
        var availableMovesBoard = new Board(this);
        for (Move move : availableMoves) {
            availableMovesBoard.board[move.getX()][move.getY()] = availableSymbol;
        }
        availableMovesBoard.print();
    }

    public int getScore(char symbol) {
        int score = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == symbol) {
                    score++;
                }
            }
        }
        return score;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Board)) {
            return false;
        }
        Board otherBoard = (Board)other;
        if (size != otherBoard.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != otherBoard.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
