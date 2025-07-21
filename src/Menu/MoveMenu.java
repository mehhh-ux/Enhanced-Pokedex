/**
 * MoveMenu.java is the class that controls the main loop of the move menu.
 * An MVC design-based management system for Move.
 * Also responsible for exiting out of the move menu.
 */
package Menu;

/**
 * Import Move MVC.
 */
import Move.MoveController;
import Move.Move;
import Move.MoveView;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveMenu {
    /**
     * Instantiation and initialization of objects and variables.
     */
    private MoveView view = new MoveView();
    private MoveController controller = new MoveController();
    private Scanner scanner = new Scanner(System.in);
    private int option;
    private String key = "", tempName;
    private ArrayList<Move> results = new ArrayList<>();
    private boolean running;

    /**
     * The run() method is responsible for displaying the Move Management Menu.
     */
    public void run(){
        running = true;

        /**
         * This is the main loop of the menu.
         * Runs the appropriate method based on user input.
         * If 1, then create and add a new move into the Pokedex.
         * If 2, then print all moves in the Pokedex.
         * If 3, then search for a move based on the user's input (key) then print results.
         * If 4, then return to main menu (PokedexDriver.java).
         */
        while (running) {
            System.out.println("\n=========================================");
            System.out.println("        Welcome to the Move Menu!        ");
            System.out.println("=========================================");
            System.out.println("1. Add Move");
            System.out.println("2. Show All Move");
            System.out.println("3. Search Move");
            System.out.println("4. Exit");
            System.out.println("-----------------------------------------");
            System.out.print("Enter your choice: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    tempName = controller.addMove(view.promptMoveData());
                    view.successfulMoveAddMessage(tempName);
                    break;
                case 2:
                    view.printAll(controller.getAllMoves(), key);
                    if (!controller.getAllMoves().isEmpty()) {
                        view.pressAnyKeyPrompt();
                    }
                    break;
                case 3:
                    key = view.promptForSearchKey();
                    results = controller.searchMove(key);
                    view.printAll(results, key);
                    if (!results.isEmpty()) {
                        view.pressAnyKeyPromptForSearch(key);
                    }
                    results.clear();
                    break;
                case 4:
                    System.out.println("Exiting Move Menu. Bye!\n");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
