package Menu;

import Move.MoveController;
import Move.MoveModel;
import Move.MoveView;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveMenu {
    private MoveController controller = new MoveController();
    private MoveView view = new MoveView();
    private ArrayList<MoveModel> results = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private int option;
    private String key;

    public void run(){
        System.out.println("--- Welcome to the Move Menu ---");
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Move");
            System.out.println("2. Show All Move");
            System.out.println("3. Search Move");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    controller.addMove(view.promptMoveData());
                    break;
                case 2:
                    view.printAllMoves(controller.getAllMoves());
                    break;
                case 3:
                    key = view.promptSearchKey();
                    results = controller.searchMove(key);
                    view.printAllMoves(results);
                    break;
                case 4:
                    System.out.println("Exiting Move Menu. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
