/**
 * ItemController.java is responsible for receiving and giving Item data.
 * Responsible for saving the list of all items in the Pokedex.
 * Able to give a list of items based on an operation or a method.
 */
package Item;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

    public Item getItemByName(String name) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }

        return null;
    }

    public void loadFromFile(String filename) throws Exception {
        items.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean inItemSection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.equals("ITEM:")) {
                    inItemSection = true;
                    continue;
                }

                if (!inItemSection) {
                    continue;
                }

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                String name = parts[0].trim();
                String category = parts[1].trim();
                String desc = parts[2].trim();
                String effect = parts[3].trim();
                double buyingPrice = Double.parseDouble(parts[4].trim());
                double sellingPrice = Double.parseDouble(parts[5].trim());

                Item i = new Item(name, category, desc, effect, buyingPrice, sellingPrice);
                items.add(i);
            }
        }
    }

    public void saveToFile(String filename) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Item i : items) {
                StringBuilder sb = new StringBuilder();
                writer.write("ITEM:\n");

                sb.append(i.getName()).append(",");
                sb.append(i.getCategory()).append(",");
                sb.append(i.getDescription()).append(",");
                sb.append(i.getEffect()).append(",");
                sb.append(i.getBuyingPrice()).append(",");
                sb.append(i.getSellingPrice()).append(",");

                writer.write(sb.toString());
                writer.newLine();
            }
        }
    }
}