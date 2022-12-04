public class Menu {
    static final char PLAYER1_SYMBOL = 'x';
    static final char PLAYER2_SYMBOL = 'o';
    static final char SPACE_SYMBOL = ' ';
    static final char AVAILABLE_SYMBOL = '*';
    static final int BOARD_SIZE = 8;

    public static void startMenu() {
        int bestScore = 0;
        Player secondPlayer;
        while (true) {
            UserInterface.printOptions(bestScore);
            int option = UserInterface.getInt("Enter number of an option:");
            switch (option) {
                case 0:
                    return;
                case 1:
                    secondPlayer = new StupidBot(PLAYER2_SYMBOL);
                    break;
                case 2:
                    secondPlayer = new CleverBot(PLAYER2_SYMBOL, PLAYER1_SYMBOL);
                    break;
                case 3:
                    secondPlayer = new HumanPlayer(PLAYER2_SYMBOL);
                    break;
                default:
                    continue;
            }
            //Player firstPlayer = new CleverBot(PLAYER1_SYMBOL, PLAYER2_SYMBOL);
            Player firstPlayer = new HumanPlayer(PLAYER1_SYMBOL);
            UserInterface.printGameInfo(PLAYER1_SYMBOL, PLAYER2_SYMBOL, SPACE_SYMBOL, AVAILABLE_SYMBOL);
            Game game = new Game(BOARD_SIZE, firstPlayer, secondPlayer, SPACE_SYMBOL, AVAILABLE_SYMBOL);
            var score = game.processGame();
            if (score > bestScore) {
                bestScore = score;
            }
        }
    }
}
