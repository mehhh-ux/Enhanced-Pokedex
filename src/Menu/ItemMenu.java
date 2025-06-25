package Menu;

import Item.ItemController;
import Item.ItemModel;
import Item.ItemView;

import java.util.ArrayList;
import java.util.Scanner;

public class ItemMenu {
    private ItemView view = new ItemView();
    private ItemController controller = new ItemController();
    private Scanner scanner = new Scanner(System.in);
    private int option;
    private String key;
    private ArrayList<ItemModel> results = new ArrayList<>();

    public void run(){
        System.out.println("--- Welcome to the Item Menu ---");
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Show All Item");
            System.out.println("2. Search Item");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    view.printAllItems(controller.getAllItems());
                    break;
                case 2:
                    key = view.promptSearchKey();
                    results = controller.searchItem(key);
                    view.printAllItems(results);
                    break;
                case 3:
                    System.out.println("Exiting Item Menu. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
