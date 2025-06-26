package Item;

import java.util.ArrayList;
import java.util.Scanner;

public class ItemView {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Header for displaying items and their attributes.
     */
    public void printHeader() {
        System.out.printf(
                "\n%-25s %-20s %-45s %-80s %15s %15s\n",
                "Name", "Category", "Description", "Effect", "Buying Price", "Selling Price"
        );
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays all attributes of the item.
     */
    public void displayItemAttributes(ItemModel item) {
        System.out.printf(
                "%-25s %-20s %-45s %-80s %15s %15.2f\n",
                item.getName(), item.getCategory(), item.getDescription(), item.getEffect(), (item.getBuyingPrice() != 0) ? String.format("%.2f", item.getBuyingPrice()) : "Not Sold", item.getSellingPrice());
    }

    /**
     * Prints and displays the attributes of every item objects in an ArrayList.
     * @param items is a list of item objects.
     */
    public void printAllItems(ArrayList<ItemModel> items) {
        if (items.isEmpty()) {
            System.out.println("No items in the Pokedex.");
            return;
        }

        printHeader();
        for (ItemModel i : items) {
            displayItemAttributes(i);
        }
    }

    public String promptSearchKey() {
        System.out.println("\nYou are in the process of searching a item!");
        System.out.println("------------------------------------------");
        System.out.print("Enter a search keyword: ");
        return scanner.nextLine();
    }

    /**
     * Asks the user to press enter to continue.
     */
    public void pressAnyKeyPrompt(){
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}