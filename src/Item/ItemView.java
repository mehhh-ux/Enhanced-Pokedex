/**
 * ItemView.java is responsible for all outputs in the item menu.
 */
package Item;

import Interfaces.Displayable;

import java.util.ArrayList;
import java.util.Scanner;

public class ItemView implements Displayable<Item> {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Header for displaying items and their attributes.
     */
    @Override
    public void printHeader() {
        System.out.printf(
                "\n%-25s %-20s %-45s %-80s %15s %15s\n",
                "Name", "Category", "Description", "Effect", "Buying Price", "Selling Price"
        );
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays all attributes of the item.
     * @param item is a class of Item which contains all the attribute to be displayed.
     */
    @Override
    public void displayAttributes(Item item) {
        System.out.printf(
                "%-25s %-20s %-45s %-80s %15s %15.2f\n",
                item.getName(), item.getCategory(), item.getDescription(), item.getEffect(), (item.getBuyingPrice() != 0.00) ? String.format("%.2f", item.getBuyingPrice()) : "Not Sold", item.getSellingPrice());
    }

    /**
     * Prints and displays the attributes of every item objects in an ArrayList.
     * @param items is a list of item objects.
     * @param key is a string that is searched by the user (this is used with the search method)
     */
    @Override
    public void printAll(ArrayList<Item> items, String key) {
        if (items.isEmpty() && !key.isEmpty()) {
            System.out.println("No item containing the word '" + key + "' in the Pokedex.");
            return;
        }else if (items.isEmpty()) {
            System.out.println("No item to show in the pokedex");
            return;
        }

        printHeader();
        for (Item i : items) {
            displayAttributes(i);
        }
    }

    /**
     * Asks the user to enter their desired keyword for filter.
     * @return user's input.
     */
    @Override
    public String promptForSearchKey() {
        System.out.println("\nYou are in the process of searching a item!");
        System.out.println("------------------------------------------");
        System.out.print("Enter a search keyword: ");
        return scanner.nextLine();
    }

    /**
     * Asks the user to press enter to continue.
     */
    @Override
    public void pressAnyKeyPrompt(){
        System.out.print("Displayed all item/s available in the Pokedex.\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Asks the user to press enter to continue after searching for an item.
     * @param key is a string that is searched by the user
     */
    @Override
    public void pressAnyKeyPromptForSearch(String key){
        System.out.print("Displayed all item/s containing the word/number '" + key + "' in the Pokedex.\nPress Enter to continue...");
        scanner.nextLine();
    }
}