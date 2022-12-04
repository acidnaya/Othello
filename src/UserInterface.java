import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {

    public static void printOptions(int score) {
        System.out.println("--------------------------------------------------");
        System.out.println("-----------------------MENU-----------------------");
        System.out.println("--------------------------------------------------");
        System.out.println("0. EXIT THE GAME");
        System.out.println("1. EASY GAME");
        System.out.println("2. HARD GAME");
        System.out.println("3. PVP\n");
        System.out.println("CURRENT BEST SCORE: " + score + "");
        System.out.println("--------------------------------------------------");
    }

    public static void printGameInfo(char firstSymbol, char secondSymbol, char spaceSymbol, char availableSymbol) {
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("-----------------------GAME-----------------------");
        System.out.println("--------------------------------------------------");
        System.out.println("[" + firstSymbol + "]" + " - symbol of first player");
        System.out.println("[" + secondSymbol + "]" + " - symbol of second player");
        System.out.println("[" + spaceSymbol + "]" + " - empty cell");
        System.out.println("[" + availableSymbol + "]" + " - symbol of available move");
        System.out.println();
    }

    public static int getInt(String s) {
        Scanner input = new Scanner( System.in );
        System.out.println(s);
        while (true) {
            try {
                int a = input.nextInt();
                return a;
            }
            catch (InputMismatchException e) {
                input.next();
                System.out.println("Please enter a number!");
            }
        }
    }

    public static boolean playerWantsToUndoMove() {
        System.out.println("0. UNDO MOVE");
        System.out.println("1. MAKE MOVE");
        Scanner input = new Scanner( System.in );
        while (true) {
            try {
                int a = input.nextInt();
                return a == 0;
            }
            catch (InputMismatchException e) {
                input.next();
                System.out.println("Please enter 0 or 1!");
            }
        }
    }
}
