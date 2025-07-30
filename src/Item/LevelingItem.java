package Item;

import java.util.ArrayList;

/**
 * Represents a Leveling Item used to increase a Pokémon's level.
 *
 * This class extends the Item class and defines a specific
 * category of item that can affect a Pokémon's experience or level directly.
 */
public class LevelingItem extends Item {
    public LevelingItem(String name, String description, String effect, double buyingPrice, double sellingPrice){
        super(name, "Leveling Item", description, effect, buyingPrice, sellingPrice);
    }


    /**
     * Initializes and returns a list of predefined Leveling Items.
     *
     * @return a list containing all Leveling Items
     */
    public static ArrayList<Item> getAll(){
        ArrayList<Item> levelingItems = new ArrayList<>();
        levelingItems.add(new LevelingItem("Rare Candy","A candy that is packed with energy.", "Increases level by 1",
                0.00, 2400.00));
        return levelingItems;
    }
}