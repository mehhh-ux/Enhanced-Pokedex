/**
 * ItemController.java is responsible for receiving and giving Item data.
 * Responsible for saving the list of all items in the Pokedex.
 * Able to give a list of items based on an operation or a method.
 */
package Item;

import java.util.ArrayList;

public class ItemController {
    /**
     * A list of items.
     * The ArrayList items is used to keep track of all items in the Pokedex.
     * The ArrayList results is used to hold a list of items after using an operation
     (for example the search function).
     */
    private ArrayList<Item> items = Item.initializeItemList();
    private ArrayList<Item> results = new ArrayList<>();

    /**
     * Getter
     * @return the list of items in the Pokedex.
     */
    public ArrayList<Item> getAllItems() {
        return items;
    }

    /**
     * Search for an item or a list of items based off from a key.
     * @param key is the input of the user that will be used to search if
    an item has an attribute that matches said key.
     * @return the resulting list of items.
     */
    public ArrayList<Item> searchItem(String key) {
        key = key.toLowerCase();
        for (Item i : items) {
            if (i.getName().toLowerCase().contains(key) ||
                    i.getCategory().toLowerCase().contains(key) ||
                    i.getEffect().toLowerCase().contains(key) ||
                    String.valueOf(i.getBuyingPrice()).toLowerCase().contains(key) ||
                    String.valueOf(i.getSellingPrice()).toLowerCase().contains(key)) {
                results.add(i);
            }
        }
        return results;
    }
}
