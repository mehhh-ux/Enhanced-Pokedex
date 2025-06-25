package Item;

import java.util.ArrayList;

public class ItemController {
    ArrayList<ItemModel> items = new ArrayList<>();
    ArrayList<ItemModel> results = new ArrayList<>();
    //gggggg
    public ArrayList<ItemModel> getAllItems() {
        return items;
    }

    public ArrayList<ItemModel> searchItem(String key) {
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
