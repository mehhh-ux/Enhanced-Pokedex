///**
// * ItemMenu.java is the class that controls the main loop of the item menu.
// * An MVC design-based management system for Item.
// * Also responsible for exiting out of the item menu.
// */
//package Menu;
//
///**
// * Import Item MVC.
// */
//import Item.ItemController;
//import Item.Item;
//import Item.ItemView;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class ItemMenu {
//    /**
//     * Instantiation and initialization of objects and variables.
//     */
//    private ItemView view = new ItemView();
//    private ItemController controller = new ItemController();
//    private Scanner scanner = new Scanner(System.in);
//    private int option;
//    private String key = "";
//    private ArrayList<Item> results = new ArrayList<>();
//    private boolean running;
//    /**
//     * The run() method is responsible for displaying the Item Management Menu.
//     */
//    public void run(){
//        running = true;
//
//        /**
//         * This is the main loop of the menu.
//         * Runs the appropriate method based on user input.
//         * If 1, then print all items in the Pokedex.
//         * If 2, then search for an item based on the user's input (key) then print results.
//         * If 3, then return to main menu (PokedexDriver.java).
//         */
//        while (running) {
//            System.out.println("\n=========================================");
//            System.out.println("       Welcome to the Item Menu!         ");
//            System.out.println("=========================================");
//            System.out.println("1. Show All Item");
//            System.out.println("2. Search Item");
//            System.out.println("3. Exit");
//            System.out.println("-----------------------------------------");
//            System.out.print("Enter your choice: ");
//
//            option = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (option) {
//                case 1:
//                    view.printAll(controller.getAllItems(), key);
//                    if (!controller.getAllItems().isEmpty()) {
//                        view.pressAnyKeyPrompt();
//                    }
//                    break;
//                case 2:
//                    key = view.promptForSearchKey();
//                    results = controller.searchItem(key);
//                    view.printAll(results, key);
//                    if (!results.isEmpty()) {
//                        view.pressAnyKeyPromptForSearch(key);
//                    }
//                    results.clear();
//                    break;
//                case 3:
//                    System.out.println("Exiting Item Menu. Bye!\n");
//                    running = false;
//                    break;
//                default:
//                    System.out.println("Invalid option. Please try again.");
//            }
//        }
//    }
//}