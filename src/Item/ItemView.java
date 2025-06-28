/**
 * ItemView.java is responsible for all outputs in the item menu.
 */
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
     * @param key is a string that is searched by the user (this is used with the search method)
     */
    public void printAllItems(ArrayList<ItemModel> items, String key) {
        if (items.isEmpty()) {
            System.out.println("No item containing the word '" + key + "' in the Pokedex.");
            return;
        }

        printHeader();
        for (ItemModel i : items) {
            displayItemAttributes(i);
        }
    }

    /**
     * Asks the user to enter their desired keyword for filter.
     * @return user's input.
     */
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
        System.out.print("Displayed all item/s available in the Pokedex.\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Asks the user to press enter to continue after searching for an item.
     * @param key is a string that is searched by the user
     */
    public void pressAnyKeyPromptSearch(String key){
        System.out.print("Displayed all item/s containing the word/number '" + key + "' in the Pokedex.\nPress Enter to continue...");
        scanner.nextLine();
    }
}