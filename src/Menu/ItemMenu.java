/**
 * ItemMenu.java is the class that controls the main loop of the item menu.
 * An MVC design-based management system for Item.
 * Also responsible for exiting out of the item menu.
 */
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
        running = true;

        while (running) {
            System.out.println("\n=========================================");
            System.out.println("       Welcome to the Item Menu!         ");
            System.out.println("=========================================");
            System.out.println("1. Show All Item");
            System.out.println("2. Search Item");
            System.out.println("3. Exit");
            System.out.println("-----------------------------------------");
            System.out.print("Enter your choice: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    view.printAllItems(controller.getAllItems());
                    view.pressAnyKeyPrompt();
                    break;
                case 2:
                    key = view.promptSearchKey();
                    results = controller.searchItem(key);
                    view.printAllItems(results);
                    view.pressAnyKeyPromptSearch(key);
                    results.clear();
                    break;
                case 3:
                    System.out.println("Exiting Item Menu. Bye!\n");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
