package Menu;

import Move.MoveController;
import Move.MoveModel;
import Move.MoveView;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveMenu {
    private MoveController controller = new MoveController();
    private Scanner scanner = new Scanner(System.in);
    private int option;
    private String key, tempName;
    private ArrayList<MoveModel> results = new ArrayList<>();
    boolean running;

    /**
     * The run() method is responsible for displaying the Move Management Menu.
     */
    public void run(){
        running = true;

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
                    view.printAllMoves(controller.getAllMoves());
                    view.pressAnyKeyPrompt();
                    break;
                case 3:
                    key = view.promptSearchKey();
                    results = controller.searchMove(key);
                    view.printAllMoves(results);
                    view.pressAnyKeyPromptSearch(key);
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
