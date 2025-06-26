package Item;

import java.util.ArrayList;

public class ItemController {
    /**
     * A list of items.
     * The ArrayList items is used to keep track of all items in the Pokedex.
     * The ArrayList results is used to hold a list of items after using an operation
     (for example the search function).
     */
    private ArrayList<ItemModel> items = ItemModel.initializeItemList();
    private ArrayList<ItemModel> results = new ArrayList<>();

    public ArrayList<ItemModel> getAllItems() {
        return items;
    }

    public ArrayList<ItemModel> searchItem(String key) {
        key = key.toLowerCase();
        for (ItemModel i : items) {
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
